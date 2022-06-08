package com.digicon.util;

import java.util.HashMap;
import java.util.Map;

public enum InstanceServiceEnum {
	PARADA(0),
	EM_EXECUCAO(1);
	
	private int status;

	private static Map<Integer, InstanceServiceEnum> map = new HashMap<Integer, InstanceServiceEnum>();

	InstanceServiceEnum(int status) {
		this.status = status;
	}

	static {
		for (InstanceServiceEnum l : InstanceServiceEnum.values()) {
			map.put(l.status, l);
		}
	}

	public int getStatus() {
		return status;
	}

	public static String getStrStatus(int id) {
		String msg = "";
		
		switch (map.get(id)) {
		case PARADA:
			msg = "Parado";
			break;
		case EM_EXECUCAO:
			msg = "Em execução";
			break;
		}
		return msg;
	}

}
