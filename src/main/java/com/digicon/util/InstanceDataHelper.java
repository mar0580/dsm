package com.digicon.util;

import br.com.digicon.helper.InstanceStatusValueEnum;

public class InstanceDataHelper {
	
	private int instanceId;
	private InstanceTypesEnum instanceType;
	private InstanceStatusValueEnum instanceStatus;
	
	public int getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}
	public InstanceTypesEnum getInstanceType() {
		return instanceType;
	}
	public void setInstanceType(InstanceTypesEnum instanceType) {
		this.instanceType = instanceType;
	}
	public InstanceStatusValueEnum getInstanceStatus() {
		return instanceStatus;
	}
	public void setInstanceStatus(InstanceStatusValueEnum instanceStatus) {
		this.instanceStatus = instanceStatus;
	}
}
