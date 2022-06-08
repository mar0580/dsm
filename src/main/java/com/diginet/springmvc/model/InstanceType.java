package com.diginet.springmvc.model;

import java.io.Serializable;

public class InstanceType implements Serializable{
	
	private static final long serialVersionUID = 1792234670207861944L;

	private String name ;
	private int qtdAvailable;
	private boolean included;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQtdAvailable() {
		return qtdAvailable;
	}
	public void setQtdAvailable(int qtdAvailable) {
		this.qtdAvailable = qtdAvailable;
	}
	public boolean isIncluded() {
		return included;
	}
	public void setIncluded(boolean included) {
		this.included = included;
	}
}
