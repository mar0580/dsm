package com.diginet.springmvc.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.digicon.util.InstanceTypesEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

public class Product implements Serializable{
	
	private static final long serialVersionUID = 6497641882330302942L;
	
	@Expose
	@JsonProperty("productCode")
	private InstanceTypesEnum productCode;
	
	@Expose
	@JsonProperty("modules")
	private List<Module> modules;

	@Expose
	@JsonProperty("data")
	private Map<String, Object> data;

	@Expose
	@JsonProperty("instances")
	private List<Instance> instances;

	public InstanceTypesEnum getProductCode() {
		return productCode;
	}
	public void setProductCode(InstanceTypesEnum productCode) {
		this.productCode = productCode;
	}
	
	public List<Module> getModules() {
		return modules;
	}
	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	public Map<String, Object> getData() {
		return data; 
	}
	public void setData(Map<String,Object> data) {
		this.data = data; 
	}
	public List<Instance> getInstances() {
		return instances;
	}
	public void setInstances(List<Instance> instances) {
		this.instances = instances;
	}
	
}
