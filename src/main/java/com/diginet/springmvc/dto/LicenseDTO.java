package com.diginet.springmvc.dto;

import java.io.Serializable;
import java.util.List;

import com.diginet.springmvc.model.Instance;
import com.diginet.springmvc.model.InstanceType;
import com.diginet.springmvc.model.Module;
import com.google.gson.annotations.Expose;

public class LicenseDTO implements Serializable{
	
	private static final long serialVersionUID = -3600139276178415166L;

	@Expose
	private List<Instance> instances;
	
	@Expose
	private List<InstanceType> instanceTypes;
	
	@Expose
	private List<Module> modules;

	public List<Instance> getInstances() {
		return instances;
	}
	public void setInstances(List<Instance> instances) {
		this.instances = instances;
	}
	public List<Module> getModules() {
		return modules;
	}
	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	public List<InstanceType> getInstanceTypes() {
		return instanceTypes;
	}
	public void setInstanceTypes(List<InstanceType> instanceTypes) {
		this.instanceTypes = instanceTypes;
	}
}
