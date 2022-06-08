package com.diginet.springmvc.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

public class Machine implements Serializable{

	private static final long serialVersionUID = 5747139800370361599L;
	
	
	@Expose
	@JsonProperty("macAddress")
	private String macAddress;

	@Expose
	@JsonProperty("hddSerial")
	private String hddSerial;
	
	@Expose
	@JsonProperty("licenseSerial")
	private String licenseSerial;
	
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getHddSerial() {
		return hddSerial;
	}
	public void setHddSerial(String hddSerial) {
		this.hddSerial = hddSerial;
	}
	public String getLicenseSerial() {
		return licenseSerial;
	}
	public void setLicenseSerial(String licenseSerial) {
		this.licenseSerial = licenseSerial;
	}
}
