package com.digicon.util;

import java.util.HashMap;
import java.util.Map;

public enum PemStatusEnum {
	VALID(1),
	INVALID(2),
	NOT_FOUND(3);

	private int status;

	private static Map<Integer, PemStatusEnum> map = new HashMap<Integer, PemStatusEnum>();

	PemStatusEnum(int status) {
		this.status = status;
	}

	static {
		for (PemStatusEnum l : PemStatusEnum.values()) {
			map.put(l.status, l);
		}
	}

	public int getStatus() {
		return status;
	}

	public static String getStrStatus(int id) {
		String msg = "";
		
		switch (map.get(id)) {
		case VALID:
			msg = "V�lida";
			break;
		case INVALID:
			msg = "Inv�lida";
			break;
		case NOT_FOUND:
			msg = "N�o encontrada";
			break;
		}
		return msg;
	}

}
