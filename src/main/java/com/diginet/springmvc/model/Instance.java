package com.diginet.springmvc.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.digicon.util.InstanceTypesEnum;
import com.digicon.util.LocalDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.annotations.Expose;



public class Instance implements Serializable {
	
	private static final long serialVersionUID = -2371767666511814003L;
	@Expose 
	private int id;

	@Expose
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime startDate;

	@Expose
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime endDate;

	@Expose
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime originalEndDate;

	@Expose
	private int deviceLimit;

	@Expose
	private int originalDeviceLimit;

	@Expose
	private InstanceTypesEnum product;
	
	@Expose
	@JsonProperty(value="deviceLimitExtended") //It only performed deserialization with this annotation, when is boolean
	private boolean deviceLimitExtended;

	@Expose
	@JsonProperty(value="endDateExtended") //It only performed deserialization with this annotation, when is boolean
	private boolean endDateExtended;

	@Expose
	@JsonProperty(value="registered") //It only performed deserialization with this annotation, when is boolean 
	private boolean registered;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	
	public LocalDateTime getOriginalEndDate() {
		return originalEndDate;
	}

	public void setOriginalEndDate(LocalDateTime originalEndDate) {
		this.originalEndDate = originalEndDate;
	}

	public int getDeviceLimit() {
		return deviceLimit;
	}

	public void setDeviceLimit(int deviceLimit) {
		this.deviceLimit = deviceLimit;
	}
	
	public int getOriginalDeviceLimit() {
		return originalDeviceLimit;
	}

	public void setOriginalDeviceLimit(int originalDeviceLimit) {
		this.originalDeviceLimit = originalDeviceLimit;
	}

	public InstanceTypesEnum getProduct() {
		return product;
	}

	public void setProduct(InstanceTypesEnum product) {
		this.product = product;
	}

	public boolean isDeviceLimitExtended() {
		return deviceLimitExtended;
	}

	public void setDeviceLimitExtended(boolean deviceLimitExtended) {
		this.deviceLimitExtended = deviceLimitExtended;
	}

	public boolean isEndDateExtended() {
		return endDateExtended;
	}

	public void setEndDateExtended(boolean endDateExtended) {
		this.endDateExtended = endDateExtended;
	}

	public boolean isResgistered() {
		return registered;
	}

	public void setResgistered(boolean resgistered) {
		this.registered = resgistered;
	}
	
	
	
	
}