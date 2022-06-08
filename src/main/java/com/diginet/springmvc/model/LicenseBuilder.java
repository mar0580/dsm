package com.diginet.springmvc.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import com.digicon.util.DLicense;
import com.digicon.util.DLicenseException;
import com.digicon.util.InstanceTypesEnum;
import com.digicon.util.StatusUpdaterUtil;
import com.digicon.util.Util;

public class LicenseBuilder {

	private JSONObject joLicense;
	private License license = new License();

	public LicenseBuilder() throws DLicenseException {
		this.setLicenseContent();
	}

	public License getLicense() {
		if (joLicense == null)
			return null;
		this.setCompatibilityVersion();
		this.setCompany();
		this.setMachine();
		this.setProducts();
		return this.license;
	}

	private void setCompatibilityVersion() {
		String compatibilityVersion = joLicense.getJSONObject("compatibility").get("version").toString();
		license.setCompatibilityVersion(compatibilityVersion);
	}

	private void setCompany() {
		Company company = new Company();
		company.setIdentifier(joLicense.getJSONObject("company").get("identifier").toString());
		company.setName(joLicense.getJSONObject("company").get("name").toString());
		company.setCountry(joLicense.getJSONObject("company").get("country").toString());
		company.setUsername(joLicense.getJSONObject("company").get("username").toString());
		company.setPassword(joLicense.getJSONObject("company").get("password").toString());
		license.setCompanyInfo(company);
	}

	private void setMachine() {
		Machine machine = new Machine();
		machine.setLicenseSerial(String.valueOf(joLicense.getJSONObject("machine").getInt("licenseSerial")));
		machine.setMacAddress(joLicense.getJSONObject("machine").get("macAddress").toString());
		machine.setHddSerial(joLicense.getJSONObject("machine").get("hddSerial").toString());
		license.setMachineInfo(machine);
	}

	private void setProducts() {
		List<Product> products = new ArrayList<>();
		JSONObject productsJson = joLicense.getJSONObject("products");
		JSONObject diginetJson = (!productsJson.isNull(InstanceTypesEnum.DIGINET.getStrStatus()))
				? productsJson.getJSONObject(InstanceTypesEnum.DIGINET.getStrStatus()) : null;
		JSONObject samJson = (!productsJson.isNull(InstanceTypesEnum.SAM.getStrStatus()))
				? productsJson.getJSONObject(InstanceTypesEnum.SAM.getStrStatus()) : null;
		JSONObject gasJson = (!productsJson.isNull(InstanceTypesEnum.GAS.getStrStatus()))
				? productsJson.getJSONObject(InstanceTypesEnum.GAS.getStrStatus()) : null;

		products.add(createProduct(diginetJson, InstanceTypesEnum.DIGINET));
		products.add(createProduct(gasJson, InstanceTypesEnum.GAS));
		products.add(createProduct(samJson, InstanceTypesEnum.SAM));

		license.setProducts(products);
	}

	private Product createProduct(JSONObject productInfo, InstanceTypesEnum instanceType) {
		Product product = new Product();
		if (productInfo == null) {
			product.setProductCode(instanceType);
			return product;
		}
		product.setProductCode(instanceType);
		product.setData(productInfo.getJSONObject("data").toMap());
		product.setModules(createModuleList(productInfo, instanceType));
		List<Instance> instances = new ArrayList<>();
		for (Object instance : productInfo.getJSONArray("instances")) {
			instances.add(createInstance((JSONObject) instance, instanceType));
		}
		product.setInstances(instances);
		return product;
	}

	private List<Module> createModuleList(JSONObject productInfo, InstanceTypesEnum InstanceType) {
		List<Module> modulesList = new ArrayList<>();
		JSONObject joModules = productInfo.getJSONObject("modules");
		Iterator<String> modules = joModules.keys();

		while (modules.hasNext()) {
			String key = modules.next();
			Module module = new Module();
			module.setName(key);
			module.setAvailable((Boolean) joModules.get(key));
			module.setType(InstanceType.toString().toLowerCase());
			modulesList.add(module);
		}

		return modulesList;
	}
	
	public Optional<Instance> findInstanceByName(String name){
		return this.getLicense()
					.getProducts()
					.stream()
					.flatMap(p -> {
						if (p.getInstances()!= null){ 
							return p.getInstances().stream(); 
						}
						return new ArrayList<Instance>().stream(); 
					}) 
					.filter(instance -> compareInstanceName(name, instance))   
					.findAny();
	}
	 
	private boolean compareInstanceName(String nameToCompare, Instance instance){
		String instanceName = instance.getProduct().getStrName() + "-" + Util.addZeroIfLess10(instance.getId());		 
		return instanceName.equalsIgnoreCase(nameToCompare);    
	}
	
	private Instance createInstance(JSONObject instanceInfo, InstanceTypesEnum instanceType) {
		Instance instance = new Instance();						   
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");		
		new StatusUpdaterUtil(LocalDateTime.parse(instanceInfo.get("endDate").toString().substring(0, 19), formatter));		
		instance.setId(instanceInfo.getInt("id"));
		instance.setStartDate(LocalDateTime.parse(instanceInfo.get("startDate").toString().substring(0, 19), formatter));
		instance.setEndDate(LocalDateTime.parse(instanceInfo.get("endDate").toString().substring(0, 19), formatter));
		instance.setOriginalEndDate(LocalDateTime.parse(instanceInfo.get("endDate").toString().substring(0, 19), formatter));
		instance.setDeviceLimit(instanceInfo.getInt("deviceLimit"));
		instance.setOriginalDeviceLimit(instanceInfo.getInt("deviceLimit"));
		instance.setResgistered(true);
		instance.setProduct(instanceType);
		return instance;
	}

	private void setLicenseContent() throws DLicenseException {
		String strJsonData = new DLicense().getLicenseKeyJsonData();
		
		try {
			this.joLicense = new JSONObject(strJsonData);
		} catch (Exception e) {
			throw new DLicenseException(e);
		}
	}
}
