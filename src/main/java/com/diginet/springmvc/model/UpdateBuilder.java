package com.diginet.springmvc.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import com.digicon.util.InstanceTypesEnum;
import com.digicon.util.Util;

public class UpdateBuilder {

	private JSONObject joUpdateKey;
	private Module moduleToPut = null;
	private Module moduleToCancel = null;
	private InstanceTypesEnum instanceTypesToCancel = null;
	private List<Instance> instancesToPut = null;
	private License updateLicense = new License();

	public UpdateBuilder() {
		this.setUpdateContent();
	}

	public License getLicense() {
		if (joUpdateKey == null) {
			updateLicense = new License();
			return null;
		}
		this.setCompatibilityVersion();
		this.setCompany();
		this.setMachine();
		this.setProducts();
		return this.updateLicense;
	}

	public License putInstances(List<Instance> updateInstances) throws IOException, URISyntaxException {
		System.out.println("[DEBUG] Inside putInstances method");
		this.setInstancesToPut(updateInstances);
		this.setUpdateContent();
		License license = this.getLicense();
		this.setInstancesToPut(null);
		return license;
	}

	public License cancelInstances(InstanceTypesEnum type) throws IOException, URISyntaxException {
		System.out.println("[DEBUG] Inside cancelInstances()");
		this.setInstanceTypesToCancel(type);
		this.setUpdateContent();
		License license = this.getLicense();
		this.setInstanceTypesToCancel(null);
		return license;
	}

	public License putModule(Module module) throws IOException, URISyntaxException {
		System.out.println("[DEBUG] Inside putModule method > name : " + module.getName());
		this.setModuleToPut(module);
		this.setUpdateContent();
		License license = this.getLicense();
		this.setModuleToPut(null);
		return license;
	}

	public License removeModule(Module module) {
		System.out.println("[DEBUG] Inside cancelModule method > name : " + module.getName());
		this.setModuleToCancel(module);
		License license = this.getLicense();
		this.setModuleToCancel(null);
		return license;
	}

	private void setCompatibilityVersion() {
		String compatibilityVersion = joUpdateKey.getJSONObject("compatibility").get("version").toString();
		updateLicense.setCompatibilityVersion(compatibilityVersion);
	}

	private void setCompany() {
		Company company = new Company();
		company.setIdentifier(joUpdateKey.getJSONObject("company").get("identifier").toString());
		company.setName(joUpdateKey.getJSONObject("company").get("name").toString());
		company.setCountry(joUpdateKey.getJSONObject("company").get("country").toString());
		//company.setUsername(joLicense.getJSONObject("company").get("username").toString());
		//company.setPassword(joLicense.getJSONObject("company").get("password").toString());
		updateLicense.setCompanyInfo(company);
	}

	private void setMachine() {
		Machine machine = new Machine();
		machine.setLicenseSerial(String.valueOf(joUpdateKey.getJSONObject("machine").getInt("licenseSerial")));
		machine.setMacAddress(joUpdateKey.getJSONObject("machine").get("macAddress").toString());
		machine.setHddSerial(joUpdateKey.getJSONObject("machine").get("hddSerial").toString());
		updateLicense.setMachineInfo(machine);
	}

	private void setProducts() {
		List<Product> products = new ArrayList<>();
		JSONObject diginetJson =  getJsonProductByType(InstanceTypesEnum.DIGINET);
		JSONObject gasJson = getJsonProductByType(InstanceTypesEnum.GAS);
		JSONObject samJson = getJsonProductByType(InstanceTypesEnum.SAM);

		products.add(createProduct(diginetJson, InstanceTypesEnum.DIGINET));
		products.add(createProduct(gasJson, InstanceTypesEnum.GAS));
		products.add(createProduct(samJson, InstanceTypesEnum.SAM));

		updateLicense.setProducts(products);
	}
	
	public JSONObject getJsonProductByType(InstanceTypesEnum type){
		JSONObject productsJson = joUpdateKey.getJSONObject("products");
		return (!productsJson.isNull(type.getStrStatus())) ? productsJson.getJSONObject(type.getStrStatus()) : null;
	}

	private Product createProduct(JSONObject productInfo, InstanceTypesEnum instanceType) {
		Product product = new Product();
		if (productInfo == null) {
			System.out.println("Product is null > "+instanceType);
			if(this.hasInstanceToPut(instanceType)){
				product.setInstances(getInstancesToPut(instanceType));
			}
			product.setProductCode(instanceType);
			return product;
		}
		
		product.setProductCode(instanceType);
		product.setData(productInfo.getJSONObject("data").toMap());
		product.setModules(createModuleList(productInfo, instanceType));
		List<Instance> instances = new ArrayList<>();

		if (this.getInstanceTypesToCancel() == instanceType){
			//Send empty
			System.out.println("Is to cancel!!!");
			product.setInstances(instances);
		} else if (!this.hasInstanceToPut(instanceType)) {
			instances = getPemInstancesByProduct(productInfo, instanceType);
			product.setInstances(instances);
		} else {
			// Add all instances to put
			System.out.println("Is to add!!!");
			product.setInstances(getInstancesToPut(instanceType));
		}
		return product;
	}
	
	public Instance filterByCanceledInstances(Instance pemInstance, List<Instance> instancesToPut){
		for(Instance instanceToPut : instancesToPut){
			if(Util.isLicenseInstancesEquals(pemInstance, instanceToPut)){
				return null;
			}
		}
		return pemInstance;
	}
	
	public List<Instance> getPemInstancesByProduct(JSONObject productInfo, InstanceTypesEnum instanceType){
		List<Instance> pemInstances = new ArrayList<>();
		for (Object instance : productInfo.getJSONArray("instances")) {
			pemInstances.add(createInstance((JSONObject) instance, instanceType));
		}
		return pemInstances;
	}

	public Boolean isModuleToCancel(String name, InstanceTypesEnum type) {
		if (this.moduleToCancel == null) {
			return false;
		}
		String moduleToCancelName = this.moduleToCancel.getName().toLowerCase().trim();
		if (name.toLowerCase().trim().equals(moduleToCancelName)
				&& type == InstanceTypesEnum.valueOf(this.moduleToCancel.getType().toUpperCase())) {
			return true;
		}

		return false;
	}

	public Boolean isModuleToPut(InstanceTypesEnum instanceType) {
		return this.hasModuleToPut() && instanceType == InstanceTypesEnum.valueOf(moduleToPut.getType().toUpperCase());
	}

	private List<Module> createModuleList(JSONObject productInfo, InstanceTypesEnum instanceType) {
		List<Module> modulesList = new ArrayList<>();
		if (productInfo.isNull("modules")){
			return null;
		}		
		JSONObject joModules = productInfo.getJSONObject("modules");
		Iterator<String> modules = joModules.keys();

		while (modules.hasNext()) {
			String key = modules.next();
			if (!isModuleToCancel(key, instanceType)) {
				Module module = new Module();
				module.setName(key);
				module.setAvailable((Boolean) joModules.get(key));
				module.setType(instanceType.toString().toLowerCase());
				modulesList.add(module);
			}
		}

		if (isModuleToPut(instanceType)) {
			modulesList.add(this.moduleToPut);
		}

		System.out.println("======================Print module list " + instanceType + "======================");
		for (Module m : modulesList) {
			System.out.println("Type :" + m.getType());
			System.out.println("Module :" + m.getName());
			System.out.println("UpdateModule available:" + m.getAvailable());
			// System.out.println("UpdateModule requested:" +m.getRequested());
		}
		System.out.println("==========================End Print module list============================");
		return modulesList;
	}

	private Instance createInstance(JSONObject instanceInfo, InstanceTypesEnum instanceType) {
		Instance instance = new Instance();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		instance.setId(instanceInfo.getInt("id"));
		instance.setEndDate(LocalDateTime.parse(instanceInfo.get("endDate").toString(), formatter));
		instance.setDeviceLimit(instanceInfo.getInt("deviceLimit"));
		instance.setProduct(instanceType);
		return instance;
	}

	private void setUpdateContent() {
		this.joUpdateKey = null;
	}
	
	private Boolean hasInstanceToPut(InstanceTypesEnum type){
		if (this.instancesToPut != null){
			for (Instance instance : instancesToPut){
				if (instance.getProduct() == type){
					return true;
				}
			}
		}
		return false;
	}

	private Boolean hasModuleToPut(){
		return this.moduleToPut != null;
	}
	
	public Module getModuleToPut() {
		return moduleToPut;
	}

	public void setModuleToPut(Module moduleToPut) {
		this.moduleToPut = moduleToPut;
	}

	public Module getModuleToCancel() {
		return moduleToCancel;
	}

	public void setModuleToCancel(Module moduleToCancel) {
		this.moduleToCancel = moduleToCancel;
	}

	public List<Instance> getInstancesToPut(InstanceTypesEnum instanceType) {
		if (this.instancesToPut != null){
			List<Instance> instances = new ArrayList<>();
			for (Instance instanceToPut : instancesToPut) {
				if (instanceToPut.getProduct() == instanceType){
					System.out.println("\n\ninstanceToput Product     > "+instanceToPut.getProduct());
					System.out.println("instanceToput ID          > "+instanceToPut.getId());
					System.out.println("instanceToput DeviceLimit > "+instanceToPut.getDeviceLimit());
					System.out.println("instanceToput EndDate     > "+instanceToPut.getEndDate());
					instances.add(instanceToPut);
				}
			}
		}
		return instancesToPut;
	}

	public void setInstancesToPut(List<Instance> instancesToPut) {
		this.instancesToPut = instancesToPut;
	}

	public InstanceTypesEnum getInstanceTypesToCancel() {
		return instanceTypesToCancel;
	}

	public void setInstanceTypesToCancel(InstanceTypesEnum instanceTypesToCancel) {
		this.instanceTypesToCancel = instanceTypesToCancel;
	}
}
