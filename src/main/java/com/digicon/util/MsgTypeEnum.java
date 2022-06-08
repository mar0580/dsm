package com.digicon.util;

public enum MsgTypeEnum {
	MSG_ERR(1), MSG_INFO(2), MSG_WRN(3);

	private int status;

	private MsgTypeEnum(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public static String getStrStatus(MsgTypeEnum type) {
		String msg = "";

		switch (type) {
		case MSG_ERR:
			msg = "error";
			break;
		case MSG_INFO:
			msg = "info";
			break;
		case MSG_WRN:
			msg = "warning";
			break;
		}
		return msg;
	}

}
