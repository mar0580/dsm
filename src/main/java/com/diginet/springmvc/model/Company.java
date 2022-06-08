package com.diginet.springmvc.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

public class Company implements Serializable{
	
	private static final long serialVersionUID = -3630340511475530273L;
	
	@Expose
	@JsonProperty("country")
	private String country;
	
	@Expose
	@JsonProperty("name")
	private String name;

	@Expose
	@JsonProperty("identifier")
	private String identifier;

	@Expose
	@JsonProperty("username")
	private String username;
	
	@Expose
	@JsonProperty("password")
	private String password;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
