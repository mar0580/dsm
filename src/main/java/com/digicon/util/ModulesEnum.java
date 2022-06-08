package com.digicon.util;

public enum ModulesEnum {
	WEBSERVICE(new Long[]{200000000010L}), 
	AUTOMATICPROCESS(new Long[]{200000000014L}), 
	IMPORTEXPORT(new Long[]{200000000008L, 200000000009L}),
	ALARMMONITOR(new Long[]{200000000015L}), 
	EVENTMONITOR(new Long[]{200000000016L}), 
	DASHBOARD(new Long[]{200000000017L}), 
	REPORTS(new Long[]{200000000018L});
	
	private Long[] modulesId;

	ModulesEnum(Long[] modulesId) {
		this.modulesId = modulesId;
	}
	
	public Long[] getIds(){
		return modulesId;
	}
}