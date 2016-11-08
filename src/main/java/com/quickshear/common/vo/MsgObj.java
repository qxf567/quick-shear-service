package com.quickshear.common.vo;

import java.io.Serializable;

public class MsgObj implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7246321855389996135L;
	private String msg;

	public MsgObj() {

	}

	public MsgObj(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
