package com.digicon.util;

import java.util.HashMap;
import java.util.Map;

public enum InstanceTypesEnum {
	DIGINET(04), GAS(21), SAM(22);

	private int value;

	private static Map<Integer, InstanceTypesEnum> map = new HashMap<Integer, InstanceTypesEnum>();

	InstanceTypesEnum(int value) {
		this.value = value;
	}

	static {
		for (InstanceTypesEnum l : InstanceTypesEnum.values()) {
			map.put(l.value, l);
		}
	}

	public int getValue() {
		return value;
	}

	public String getStrStatus() {
		return Util.addZeroIfLess10(value);
	}

	public String getStrName() {
		return this.toString();
	}

	public static String getStringName(int type) {
		InstanceTypesEnum iEnum = null;
		switch (type) {
		case 4:
			iEnum = InstanceTypesEnum.DIGINET;
			break;
		case 21:
			iEnum = InstanceTypesEnum.GAS;
			break;
		case 22:
			iEnum = InstanceTypesEnum.SAM;
			break;
		}
		return iEnum.toString();
	}
}