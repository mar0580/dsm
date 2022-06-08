package com.diginet.springmvc.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

public class Module implements Serializable{
	
	private static final long serialVersionUID = -7390529939073202632L;
	
	@Expose
	@JsonProperty("type")
	private String type;

	@Expose
	@JsonProperty("name")
	private String name;
	
	@Expose
	@JsonProperty("available")
	private Boolean available;
	
	@Expose
	@JsonProperty("requested")
	private Boolean requested;
	
	@Expose
	@JsonProperty("status")
	private String status;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean isAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Boolean isRequested() {
		return requested;
	}
	public void setRequested(Boolean requested) {
		this.requested = requested;
	}
	public Boolean getAvailable() {
		return available;
	}
	
	
}
