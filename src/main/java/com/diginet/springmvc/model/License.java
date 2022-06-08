package com.diginet.springmvc.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;

public class License implements Serializable{
	
	private static final long serialVersionUID = 5720946189573471073L;
	
	@Expose
	private String compatibilityVersion;
	
	@Expose
	private Company companyInfo;
	
	@Expose
	private Machine machineInfo;
	
	@Expose
	private List<Product> products;
	
	public String getCompatibilityVersion() {
		return compatibilityVersion;
	}
	public void setCompatibilityVersion(String compatibilityVersion) {
		this.compatibilityVersion = compatibilityVersion;
	}
	public Company getCompanyInfo() {
		return companyInfo;
	}
	public void setCompanyInfo(Company companyInfo) {
		this.companyInfo = companyInfo;
	}
	public Machine getMachineInfo() {
		return machineInfo;
	}
	public void setMachineInfo(Machine machineInfo) {
		this.machineInfo = machineInfo;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
