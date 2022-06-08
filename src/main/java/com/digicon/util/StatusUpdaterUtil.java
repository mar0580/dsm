package com.digicon.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.diginet.springmvc.model.Instance;

import br.com.digicon.configuration.Utils;
import br.com.digicon.dao.InstanceDAO;
import br.com.digicon.helper.GenericFunctionHelper;
import br.com.digicon.helper.InstanceStatusValueEnum;
import br.com.digicon.main.InstanceDeleter;
import br.com.digicon.model.InstanceVO;

public class StatusUpdaterUtil {

	private final List<Instance> instances;
	private Integer daysBetween;
	private int expirado;
	private boolean isHddSerialAndMacAddress;

	public StatusUpdaterUtil(List<Instance> instances) {
		this.instances = instances;
	}

	public StatusUpdaterUtil(LocalDateTime endDate) {
		this.instances = new ArrayList<>();
		this.daysBetween = getDaysBetween(endDate);
		this.expirado = getDaysBetween(endDate);
	}
	
	public StatusUpdaterUtil(LocalDateTime endDate, String hddSerial, String macAddress) {		
		this.instances = new ArrayList<>();			
		if ((this.expirado >= 0) && (Utils.getHddSerial().toString().trim().equals(hddSerial.toString())) && (Utils.getMacAddress().toString().trim().equals(macAddress.toString()))) {
			this.isHddSerialAndMacAddress = true;
		} else {
			this.isHddSerialAndMacAddress = false;
		}
	}

	public static int getDaysBetween(LocalDateTime endDate) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date actualDate = new Date();
		Date expirationDate = new Date();
		try {
			expirationDate = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());
			actualDate = formatter.parse(GenericFunctionHelper.getCurrentTimeStamp());
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return Days.daysBetween(new DateTime(actualDate), new DateTime(expirationDate)).getDays();
	}

	public boolean compareExpiration(int daysEnabled, int daysReturned) {
		if (daysEnabled >= daysReturned) {
			return true;
		}
		return false;
	}

	private void updateStatus(String name, InstanceStatusValueEnum statusValue) {
		InstanceVO newInstance = new InstanceVO();
		newInstance.setInstanceStatus(statusValue.getValue());
		InstanceDAO.updateInstance(name, newInstance);
	}

	public boolean compareDate(Integer daysBetween, String instanceName, InstanceStatusValueEnum statusValue) {
		if (compareExpiration(statusValue.getDays(), daysBetween)) {
			if (InstanceDAO.getInstanceByName(instanceName).getInstanceStatus() != statusValue
					.getValue()) {
				return true;
			}
		}
		return false;
	}

	public boolean isExpired(String instanceName) {
		return compareDate(daysBetween, instanceName, InstanceStatusValueEnum.EXPIRED);
	}

	public boolean isNearExpired(String instanceName) {
		return compareDate(daysBetween, instanceName, InstanceStatusValueEnum.NEAR_EXPIRED);
	}

	public InstanceStatusValueEnum checkAndUpdateStatus(String instanceName, InstanceStatusValueEnum statusValue,
			Function<String, Boolean> func) {
		if (func.apply(instanceName)) {
			updateStatus(instanceName, statusValue);
			return statusValue;
		}
		return InstanceStatusValueEnum.INSTALLED;
	}

	public InstanceStatusValueEnum resolveInstanceStatusToOperation(String instanceName,
			InstanceStatusValueEnum statusValue) {
		Function<String, Boolean> func;
		if (statusValue.equals(InstanceStatusValueEnum.NEAR_EXPIRED)) {
			func = this::isNearExpired;
		} else {
			func = this::isExpired;
		}
		return checkAndUpdateStatus(instanceName, statusValue, func);
	}
	

	public InstanceStatusValueEnum updateStatus(Instance instance) {
		this.daysBetween = getDaysBetween(instance.getEndDate()); 
		String instanceName = instance.getProduct().getStrName() + "-" + Util.addZeroIfLess10(instance.getId());
		
		InstanceStatusValueEnum currentStatus = resolveInstanceStatusToOperation(instanceName, InstanceStatusValueEnum.NEAR_EXPIRED);
		InstanceStatusValueEnum expiredStatusIfExpiredInstance = resolveInstanceStatusToOperation(instanceName, InstanceStatusValueEnum.EXPIRED); 
		
		if (expiredStatusIfExpiredInstance != InstanceStatusValueEnum.EXPIRED){
			return currentStatus;
		}
		     
		return expiredStatusIfExpiredInstance;    
	}

	/**
	 * Probably outdated; should not be uninstalled when expired;
	 */
	@SuppressWarnings("unused")
	private void uninstallByExpiration(String name) {
		String retorno = InstanceDeleter.deleteInstance(name, InstanceStatusValueEnum.EXPIRED.getValue());
		if (retorno.startsWith("ERR")) {
			System.out.println(retorno);
		}
	}

	public void updateStatus() {
		if (instances != null) {
			instances.forEach(this::updateStatus);
		}
	}
	
	public int getExpirado() {
		return expirado;
	}

	public void setExpirado(int expirado) {
		this.expirado = expirado;
	}

	public boolean isHddSerialAndMacAddress() {
		return isHddSerialAndMacAddress;
	}

	public void setHddSerialAndMacAddress(boolean isHddSerialAndMacAddress) {
		this.isHddSerialAndMacAddress = isHddSerialAndMacAddress;
	}	
}
