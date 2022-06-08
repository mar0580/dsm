package com.diginet.springmvc.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digicon.util.DLicense;
import com.digicon.util.DLicenseException;
import com.digicon.util.InstanceTypesEnum;
import com.digicon.util.ModulesEnum;
import com.digicon.util.MsgTypeEnum;
import com.digicon.util.PemStatusEnum;
import com.digicon.util.StatusUpdaterUtil;
import com.digicon.util.Util;
import com.diginet.springmvc.entity.InstanceVO;
import com.diginet.springmvc.entity.ModuleVO;
import com.diginet.springmvc.model.Company;
import com.diginet.springmvc.model.Instance;
import com.diginet.springmvc.model.InstanceType;
import com.diginet.springmvc.model.License;
import com.diginet.springmvc.model.LicenseBuilder;
import com.diginet.springmvc.model.Machine;
import com.diginet.springmvc.model.Module;
import com.diginet.springmvc.model.Product;

import br.com.digicon.configuration.Utils;
import br.com.digicon.helper.GenericFunctionHelper;
import br.com.digicon.helper.InstanceStatusValueEnum;

@Service("licenseService")
public class LicenseHelper {

	@Autowired
	UserService userService;

	@Autowired
	UpdateLicenseHelper updateLicenseService;

	private License license;

	private PemStatusEnum licenseStatus;	
	
	private boolean isHddSerialAndMacAddress = false;

	private List<Product> products;
	
	private static final String REQUEST_CLIENT_TO_DIGICON = "NEW_REQUEST_KEY";
	private static final String DIGINET_WEBSERVICE = "webService";

	public LicenseHelper() throws DLicenseException {
		this.reload();
	}

	public void reload() {
		this.products = null;
		LocalDateTime endDate = null;
		try {
			license = new LicenseBuilder().getLicense();
			if (Util.hasLicense(license)) { 
				setLicenseStatus(PemStatusEnum.VALID);
				for (Product product : license.getProducts()) {
					if (product.getProductCode().equals(InstanceTypesEnum.DIGINET) && (product.getInstances() != null)){
						for (Instance instance : product.getInstances()) {
							if (instance.getEndDate() != null) {
								endDate  =instance.getEndDate();
							}
						}
					} else if (product.getProductCode().equals(InstanceTypesEnum.GAS) && (product.getInstances() != null)){
						for (Instance instance : product.getInstances()) {
							if (instance.getEndDate() != null) {
								endDate  =instance.getEndDate();
							}
						}
					}					
				}
				this.products = license.getProducts();					
				new StatusUpdaterUtil(endDate, license.getMachineInfo().getHddSerial().toString(), license.getMachineInfo().getMacAddress().toString());
				this.isHddSerialAndMacAddress = isLicenseValid(endDate, license.getMachineInfo().getHddSerial().toString(), license.getMachineInfo().getMacAddress().toString());				
			} else {
				setLicenseStatus(PemStatusEnum.NOT_FOUND);
			}
		} catch (DLicenseException e) {
			setLicenseStatus(PemStatusEnum.NOT_FOUND);
		}
	}
	
	public License getLicense(){
		return this.license;
	}

	public boolean isValid() {
		return licenseStatus == PemStatusEnum.VALID;
	}

	public Company getCompany() {
		return license.getCompanyInfo();
	}

	public Machine getMachine() {
		return license.getMachineInfo();
	}

	public List<Module> getModules() throws IOException, URISyntaxException {
		List<Module> updateModules = updateLicenseService.getModules();
		List<Module> moduleList = new ArrayList<>();
		for (Product product : this.products) {
			if (Util.hasModules(product)) {
				for (Module module : product.getModules()) {
					moduleList.add(ValidateModule(module, updateModules));
				}
			}
		}
		return moduleList;
	}

	public List<InstanceType> getInstanceTypes() {
		List<InstanceType> list = new ArrayList<>();
		for (Product product : this.products) {
			InstanceType it = new InstanceType();
			it.setName(product.getProductCode().toString().toUpperCase());
			if (Util.hasInstances(product)) {
				switch (product.getProductCode()) {
				case DIGINET:
					it.setIncluded(true);
					it.setQtdAvailable(getQtdInstancesAvailable(product));
					break;
				case GAS:
					it.setIncluded(true);
					it.setQtdAvailable(getQtdInstancesAvailable(product));
					break;
				case SAM:
					it.setIncluded(true);
					it.setQtdAvailable(getQtdInstancesAvailable(product));
					break;
				}
			} else {
				it.setIncluded(false);
				it.setQtdAvailable(0);
			}
			list.add(it);
		}
		return list;
	}

	private Module ValidateModule(Module module, List<Module> updateModules) {
		if (module.isAvailable() == true) {
			if (isModuleInstalled(module.getName())) {
				module.setStatus("installed");
			} else {
				module.setStatus("available");
			}
		} else {
			if (isModuleInstalled(module.getName())) {
				uninstallModule(InstanceTypesEnum.valueOf(module.getType().toUpperCase()), module.getName());
			}
			module.setStatus("notAvailable");

			for (Module um : updateModules) {
				if (module.getType().trim().equals(um.getType().trim())
						&& module.getName().trim().equals(um.getName().trim())) {
					if (um.getAvailable()) {
						module.setRequested(true);
					}
					break;
				} else {
					module.setRequested(false);
				}
			}
		}
		return module;
	}

	/**
	 * Return instances that are stored in the database by validating with the
	 * License.key. Do not return instances uninstalled.
	 * 
	 * @return
	 */
	public List<InstanceVO> getInstances() {
		List<InstanceVO> instancesVoList = new ArrayList<>();
		for (Product product : this.products) {
			if (Util.hasInstances(product)) {
				InstanceVO instanceVo;
				for (com.diginet.springmvc.model.Instance instance : product.getInstances()) {					
					instanceVo = getInstanceByLicense(instance, product.getProductCode());
					if (instanceVo.getInstanceStatus() != InstanceStatusValueEnum.NOT_INSTALLED.getValue()) {

						instanceVo.setStrInstanceStatus(
								InstanceStatusValueEnum.getStrStatus(instanceVo.getInstanceStatus()));
						instanceVo.setStrInstanceType(InstanceTypesEnum.getStringName(instanceVo.getInstanceType()));

						instancesVoList.add(instanceVo);
					}
				}
			}
		}
		return instancesVoList;
	}

	/**
	 * Return instances in the License.key template and not in the database
	 * template.
	 * 
	 * @param type
	 * @return
	 */
	public List<Instance> getLicenseInstancesWithUpdates(String type) {		
		List<Instance> licenseInstancesList = new ArrayList<>();
		for (Product product : this.products) {
			if (product.getProductCode() == InstanceTypesEnum.valueOf(type.toUpperCase())) {
				// Add the list of registered instances.
				if (Util.hasInstances(product)) {
					for (Instance licenseInstance : product.getInstances()) {
						licenseInstance.setProduct(product.getProductCode());
						licenseInstance.setResgistered(true);
						licenseInstancesList.add(checkInstanceForRequests(licenseInstance));
					}
				}
				// Add list Of Updates
				if (updateLicenseService.getUpdateStatus() == PemStatusEnum.VALID) {
					for (Instance updateInstance : updateLicenseService.getLicenseInstancesByType(type)) {

						/*
						 * If it is null, it means that the license does not
						 * have registered instances for this product, then all
						 * of the update.key list must be added.
						 */
						if (product.getInstances() == null) {
							licenseInstancesList.add(updateInstance);
						} else {
							Instance aux = filterByNonRepeated(updateInstance, product.getInstances());
							if (aux != null) {
								licenseInstancesList.add(aux);
							}
						}
					}
				}
			}
		}
		return licenseInstancesList;
	}

	public List<Instance> getLicenseInstancesOnlyWithUpdates() {

		List<Instance> licenseInstancesList = new ArrayList<>();
		for (Product product : this.products) {
			if (Util.hasInstances(product)) {
				for (Instance licenseInstance : product.getInstances()) {
					licenseInstance.setProduct(product.getProductCode());
					Instance aux = checkInstanceForRequests(licenseInstance);
					if (Util.hasUpdates(aux)){
						licenseInstancesList.add(aux);
					}
				}
			}
			// Add list Of Updates
			if (updateLicenseService.getUpdateStatus() == PemStatusEnum.VALID) {
				for (Instance updateInstance : updateLicenseService.getLicenseInstancesByType(product.getProductCode().toString())) {
					/*
					 * If it is null, it means that the license does not have
					 * registered instances for this product, then all of the
					 * update.key list must be added.
					 */
					if (product.getInstances() == null) {
						licenseInstancesList.add(updateInstance);
					} else {
						Instance aux = filterByNonRepeated(updateInstance, product.getInstances());
						if (aux != null) {
							licenseInstancesList.add(aux);
						}
					}
				}
			}
		}
		return licenseInstancesList;
	}

	/**
	 * Return instances in the License.key template and not in the database
	 * template.
	 * 
	 * @return
	 */
	public List<Instance> getLicenseInstances() {
		List<Instance> licenseInstancesList = new ArrayList<>();
		for (Product product : this.products) {
			if (Util.hasInstances(product)) {
				for (Instance instance : product.getInstances()) {
					instance.setProduct(product.getProductCode());
					licenseInstancesList.add(checkInstanceForRequests(instance));
				}
			}
		}
		return licenseInstancesList;
	}

	public Instance getOriginalLicenseInstance(Instance updateLicense) throws DLicenseException {
		LicenseBuilder ub = new LicenseBuilder();
		for (Product product : ub.getLicense().getProducts()) {
			if (Util.hasInstances(product)) {
				for (Instance instance : product.getInstances()) {
					if (updateLicense.getProduct() == product.getProductCode()
							&& updateLicense.getId() == instance.getId()) {
						// FINDED
						instance.setProduct(product.getProductCode());
						instance.setResgistered(true);
						return instance;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Do not return update instance that are already in license instances. In
	 * order not to repeat them
	 * 
	 * @param updateInstance
	 * @param licenseInstances
	 * @return
	 */
	public Instance filterByNonRepeated(Instance updateInstance, List<Instance> licenseInstances) {		
		for (Instance licenseInstance : licenseInstances) {
			if (licenseInstance.getProduct() == updateInstance.getProduct()
					&& licenseInstance.getId() == updateInstance.getId()) {
				return null;
			}
		}
		return updateInstance;
	}

	/**
	 * Checks if the instance passed by parameter has requests in update.key. If
	 * it exists, it will be set to "true" in the setEndDateExtended() and
	 * setDeviceLimitExtended().
	 * 
	 * @param licenseInstance
	 * @return Instance licenseInstance
	 */
	public Instance checkInstanceForRequests(Instance licenseInstance) {
		if (updateLicenseService.getUpdateStatus() == PemStatusEnum.VALID) {
			for (Instance updateInstance : updateLicenseService.getLicenseInstancesByType(licenseInstance.getProduct().toString())) {
				if (Util.isLicenseInstancesEquals(updateInstance, licenseInstance)) {
					if (!Util.isEndDateEqual(licenseInstance.getEndDate(), updateInstance.getEndDate())) {
						licenseInstance.setEndDate(updateInstance.getEndDate());
						licenseInstance.setEndDateExtended(true);
					}
					if (licenseInstance.getDeviceLimit() != updateInstance.getDeviceLimit()) {
						licenseInstance.setDeviceLimit(updateInstance.getDeviceLimit());
						licenseInstance.setDeviceLimitExtended(true);
					}
					break;
				}
			}
		}
		return licenseInstance;
	}

	public List<InstanceVO> getInstanceByType(InstanceTypesEnum type) {
		List<InstanceVO> instancesVoList = new ArrayList<>();
		for (Product product : this.products) {
			if (Util.hasInstances(product) && product.getProductCode() == type) {
				InstanceVO instanceVo;
				for (com.diginet.springmvc.model.Instance instance : product.getInstances()) {
					instanceVo = getInstanceByLicense(instance, product.getProductCode());
					instancesVoList.add(instanceVo);
				}
			}
		}
		return instancesVoList;
	}

	private InstanceVO getInstanceByLicense(com.diginet.springmvc.model.Instance licenseInstance,
			InstanceTypesEnum type) {

		String name = type.toString().toLowerCase() + "-" + Util.addZeroIfLess10(licenseInstance.getId());
		InstanceVO instancesVO = new InstanceVO();

		try {
			instancesVO = userService.findInstanceByName(name);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate ld = licenseInstance.getEndDate().toLocalDate();
			instancesVO.setEndDate(ld.format(formatter));
			instancesVO.setDeviceLimit(licenseInstance.getDeviceLimit());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instancesVO;
	}

	public String getLicenseContent() throws DLicenseException {
		return new DLicense().getLicenseKeyJsonData();
	}

	public static String getLicensePath() throws URISyntaxException {
		try {
			return GenericFunctionHelper.goBackPath(System.getProperty("user.dir"), 2) + "\\License.key";
		} catch (StringIndexOutOfBoundsException e) {
			return "C:\\DigiconApp\\License.key";
		}
	}

	public String updatePass(String strJSON) throws UnsupportedEncodingException {
		try {
			// Read from file
			JSONObject jo = new JSONObject(strJSON);
			String pass_curr = jo.getString("currentPassword");
			String pass_newer = jo.getString("newPassword");
			String pass_older = this.license.getCompanyInfo().getPassword();

			String user = jo.getString("username");

			if (!user.equals(this.license.getCompanyInfo().getUsername())) {
				return Util.msg("Usuário incorreto!", MsgTypeEnum.MSG_ERR);
			}

			if (!pass_curr.equals(this.license.getCompanyInfo().getPassword())) {
				return Util.msg("Senha atual não confere.", MsgTypeEnum.MSG_ERR);
			}
			// Compare values
			if (pass_newer.equals(pass_older)) {
				// Update value in object
				return Util.msg("Senha informada é igual a atual.", MsgTypeEnum.MSG_WRN);
			}
			// Write into the file
			if (new DLicense().setDSMPassword(pass_newer) < 0) {
				return Util.msg("Erro ao alterar a senha!", MsgTypeEnum.MSG_ERR);
			}
			return Util.msg("Alteração realizada com sucesso!", MsgTypeEnum.MSG_INFO);
		} catch (Exception e) {
			//e.printStackTrace();
			System.err.println("Could not update file");
		}
		return Util.msg("Não foi possível alterar o arquivo de licença!", MsgTypeEnum.MSG_ERR);
	}

	public boolean isInstancesAvaiable(InstanceTypesEnum type) {
		for (Product product : this.products) {
			if (product.getProductCode() == type && product.getInstances().size() > 0) {
				return true;
			}
		}
		return false;
	}

	public int getQtdInstancesAvailable(Product product) {
		int qtdInDB = 0, qtdExpired = 0, ret = 0;
		List<InstanceVO> instanceList;
		switch (product.getProductCode()) {
		case DIGINET:
			instanceList = userService.findInstancesByType(InstanceTypesEnum.DIGINET.getValue());
			break;
		case GAS:
			instanceList = userService.findInstancesByType(InstanceTypesEnum.GAS.getValue());
			break;
		case SAM:
			instanceList = userService.findInstancesByType(InstanceTypesEnum.SAM.getValue());
			break;
		default:
			return ret;
		}

		if (instanceList == null) {
			return ret;
		}

		for (InstanceVO instanceVO : instanceList) {
			if (instanceVO.getInstanceStatus() == InstanceStatusValueEnum.EXPIRED.getValue()) {
				qtdExpired++;
			} else if (instanceVO.getInstanceStatus() != InstanceStatusValueEnum.NOT_INSTALLED.getValue()) {
				qtdInDB++;
			}
		}
		ret = (product.getInstances().size() - qtdExpired) - qtdInDB;
		return (ret < 0) ? 0 : ret;
	}

	public boolean isModuleInstalled(String name) {
		boolean isInstaled = true;
		ModulesEnum modules = ModulesEnum.valueOf(name.toUpperCase());
		for (Long id : modules.getIds()) {
			try {
				ModuleVO module = userService.findModuleById(id);
				if (module == null) {
					isInstaled = false;
				}
			} catch (Exception e) {
				isInstaled = false;
			}
		}
		return isInstaled;
	}

	public void uninstallModule(InstanceTypesEnum product, String name) {
		String path = "";
		System.out.println("root path    [" + GenericFunctionHelper.getDebugRootPath() + "]");

		path += GenericFunctionHelper.getDebugRootPath() + "scripts\\uninstall_"
				+ Util.addZeroIfLess10(product.getValue()) + "_" + name + ".sql";
		System.out.println("path[" + path + "]");

		try {
			userService.uninstallModule(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PemStatusEnum getLicenseStatus() {
		return licenseStatus;
	}

	public void setLicenseStatus(PemStatusEnum licenseStatus) {
		this.licenseStatus = licenseStatus;
	}
	
	private boolean isLicenseValid(LocalDateTime endDate, String hddSerial, String macAddress) {		
			if ((StatusUpdaterUtil.getDaysBetween(endDate) >= 0) && (Utils.getHddSerial().toString().trim().equals(hddSerial.toString()))
					&& (Utils.getMacAddress().toString().trim().equals(macAddress.toString()))) {
				this.isHddSerialAndMacAddress = true;
			} else {
				this.isHddSerialAndMacAddress = false;
			}
		return isHddSerialAndMacAddress;
	}
	

	public boolean isHddSerialAndMacAddress() {
		return isHddSerialAndMacAddress;
	}

	public void setHddSerialAndMacAddress(boolean isHddSerialAndMacAddress) {
		this.isHddSerialAndMacAddress = isHddSerialAndMacAddress;
	}
	
	public String getTypeLicenseForNewRequest() throws IOException, URISyntaxException {		
		
		for (InstanceType instance : getInstanceTypes()) {
			
			if ((instance.getName().equals(InstanceTypesEnum.GAS.toString())) && (instance.isIncluded()) && (instance.getQtdAvailable() > 0)) {

				return DLicense.generateRequestKey(InstanceTypesEnum.GAS.toString(), REQUEST_CLIENT_TO_DIGICON);
				
			} else if ((instance.getName().equals(InstanceTypesEnum.DIGINET.toString())) && (instance.isIncluded()) && (instance.getQtdAvailable() > 0)) {
				
				for (com.diginet.springmvc.model.Module module : getModules()) {
					
					if ((module.getName().toString().equals(DIGINET_WEBSERVICE)) && (module.getAvailable().booleanValue())) {

						return DLicense.generateRequestKey("DIGINET_WEBSERVICE", REQUEST_CLIENT_TO_DIGICON);
						
					} else if ((module.getName().toString().equals(DIGINET_WEBSERVICE)) && (!module.getAvailable().booleanValue())) {

						return DLicense.generateRequestKey(InstanceTypesEnum.DIGINET.toString(), REQUEST_CLIENT_TO_DIGICON);
					}
				}
			}			
		}
		return null;				
	}
}