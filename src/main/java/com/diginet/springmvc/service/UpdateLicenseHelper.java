package com.diginet.springmvc.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digicon.util.InstanceTypesEnum;
import com.digicon.util.LocalDateTimeSerializer;
import com.digicon.util.PemStatusEnum;
import com.digicon.util.Util;
import com.diginet.springmvc.model.Company;
import com.diginet.springmvc.model.Instance;
import com.diginet.springmvc.model.License;
import com.diginet.springmvc.model.Machine;
import com.diginet.springmvc.model.Module;
import com.diginet.springmvc.model.Product;
import com.diginet.springmvc.model.UpdateBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Service("updateLicenseService")
public class UpdateLicenseHelper {

	@Autowired
	UserService userService;

	@Autowired
	LicenseHelper licenseService;

	public static License updateLicense;

	private List<Product> products;

	private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
			.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()).create();

	private PemStatusEnum updateStatus;

	/**
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public UpdateLicenseHelper() {
		this.reload();
	}

	/**
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void reload() {
		updateLicense = new UpdateBuilder().getLicense();
		if (Util.hasLicense(updateLicense)) {
			setUpdateStatus(PemStatusEnum.VALID);
			this.products = updateLicense.getProducts();
		} else {
			this.products = null;
			setUpdateStatus(PemStatusEnum.NOT_FOUND);
		}
	}

	/**
	 * 
	 * @return
	 */
	public Company getCompany() {
		return updateLicense.getCompanyInfo();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isValid() {
		return this.updateStatus == PemStatusEnum.VALID;
	}

	/**
	 * 
	 * @return
	 */
	public Machine getMachine() {
		return updateLicense.getMachineInfo();
	}

	/**
	 * 
	 * @return
	 */
	public List<Module> getModules() {
		List<Module> moduleList = new ArrayList<>();
		if (this.products == null) {
			return moduleList;
		}
		for (Product product : this.products) {
			if (Util.hasModules(product)) {
				for (Module module : product.getModules()) {
					moduleList.add(module);
				}
			}
		}
		return moduleList;
	}

	/**
	 * 
	 * @return
	 */
	public List<List<Instance>> getInstances() {
		List<List<Instance>> list = new ArrayList<>();
		for (Product product : this.products) {
			System.out.println("Product : " + product.getProductCode());
			if (Util.hasInstances(product)) {
				System.out.println("Product : " + product.getProductCode() + " contain instance!");
				list.add(product.getInstances());
			}
		}
		return list;
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	public List<Instance> getLicenseInstancesByType(String type) {
		List<Instance> licenseInstancesList = new ArrayList<>();
		System.out.println("[DEBUG] Inside getLicensesInstanceByType UpdadeLicenseHelper");
		for (Product product : this.products) {
			if (Util.hasInstances(product)
					&& product.getProductCode() == InstanceTypesEnum.valueOf(type.toUpperCase())) {
				for (Instance instance : product.getInstances()) {
					instance.setProduct(product.getProductCode());
					instance.setDeviceLimitExtended((instance.getDeviceLimit() > 0) ? true : false);
					instance.setEndDateExtended((instance.getEndDate() != null) ? true : false);
					instance.setResgistered(false);
					licenseInstancesList.add(instance);
				}
			}
		}
		return licenseInstancesList;
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	public List<Instance> getLicenseInstances() {
		List<Instance> licenseInstancesList = new ArrayList<>();
		System.out.println("[DEBUG] Inside getLicensesInstanceByType UpdadeLicenseHelper");
		for (Product product : this.products) {
			if (Util.hasInstances(product)) {
				for (Instance instance : product.getInstances()) {
					instance.setProduct(product.getProductCode());
					instance.setDeviceLimitExtended((instance.getDeviceLimit() > 0) ? true : false);
					instance.setEndDateExtended((instance.getEndDate() != null) ? true : false);
					instance.setResgistered(false);
					licenseInstancesList.add(instance);
				}
			}
		}
		return licenseInstancesList;
	}

	/**
	 * 
	 * @param licenseUpdate
	 * @return
	 */
	public String generateJsonFile(License licenseUpdate) {
		System.out.println("Update License to be send to Dlicense");
		JSONObject json = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		map.put("version", licenseUpdate.getCompatibilityVersion());
		json.put("compatibility", new JSONObject(map));
		json.put("company", new JSONObject(this.gson.toJson(licenseUpdate.getCompanyInfo())));
		json.put("machine", new JSONObject(this.gson.toJson(licenseUpdate.getMachineInfo())));
		json.put("products", generateJsonProducts(licenseUpdate.getProducts()));
		return json.toString();
	}

	/**
	 * 
	 * @param products
	 * @return
	 */
	public JSONObject generateJsonProducts(List<Product> products) {
		JSONObject json = new JSONObject();
		for (Product product : products) {
			JSONObject jProduct = new JSONObject();
			int contNull = 0;
			if (Util.hasModules(product)) {
				jProduct.put("modules", generateJsonModulesObject(product.getModules()));
			} else {
				contNull++;
			}
			if (Util.hasInstances(product)) {
				jProduct.put("instances", generateJsonInstancesObject(product.getInstances()));
			} else {
				contNull++;
			}
			jProduct.put("data", product.getData());
			if (contNull < 2) {
				json.put(Util.addZeroIfLess10(product.getProductCode().getValue()), jProduct);
			}
		}

		return json;

	}

	/**
	 * 
	 * @param modules
	 * @return
	 */
	public JSONObject generateJsonModulesObject(List<Module> modules) {
		JSONObject joModule = new JSONObject();
		for (Module module : modules) {
			joModule.put(module.getName(), module.getAvailable());
		}
		return joModule;
	}

	/**
	 * 
	 * @param instances
	 * @return
	 */
	public JSONArray generateJsonInstancesObject(List<Instance> instances) {
		JSONArray jaInstances = new JSONArray();
		for (Instance instance : instances) {
			JSONObject json = new JSONObject();
			json.put("deviceLimit", String.valueOf(instance.getDeviceLimit()));
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
			json.put("endDate", instance.getEndDate().format(formatter));
			json.put("id", String.valueOf(instance.getId()));
			jaInstances.put(json);
		}
		return jaInstances;
	}

	/**
	 * 
	 * @param updateInstance
	 * @param licenseInstances
	 * @return
	 */
	public Instance filterByNewsOrExtended(Instance updateInstance, List<Instance> licenseInstances) {
		System.out.println("[DEBUG] Inside filterByNewsOrExtended");
		for (Instance licenseInstance : licenseInstances) {

			if (licenseInstance.getProduct() == updateInstance.getProduct()
					&& licenseInstance.getId() == updateInstance.getId()) {
				// FIND, then check for diference;
				if (Util.isEndDateEqual(licenseInstance.getOriginalEndDate(), updateInstance.getEndDate())
						&& licenseInstance.getOriginalDeviceLimit() == updateInstance.getDeviceLimit()) {
					// it is equal, return null
					return null;
				} else {
					return updateInstance;
				}
			}
		}
		return updateInstance;
	}

	/**
	 * 
	 * @return
	 */
	public PemStatusEnum getUpdateStatus() {
		return updateStatus;
	}

	/**
	 * 
	 * @param updateStatus
	 */
	public void setUpdateStatus(PemStatusEnum updateStatus) {
		this.updateStatus = updateStatus;
	}
}