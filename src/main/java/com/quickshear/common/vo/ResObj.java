package com.quickshear.common.vo;

import java.io.Serializable;

public class ResObj<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5673302291220976676L;

	private int code;

	private MsgObj message = new MsgObj();

	private PageVo<T> page;

	private T businessObj;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public MsgObj getMessage() {
		return message;
	}

	public void setMessage(MsgObj message) {
		this.message = message;
	}

	public PageVo<T> getPage() {
		return page;
	}

	public void setPage(PageVo<T> page) {
		this.page = page;
	}

	public T getBusinessObj() {
		return businessObj;
	}

	public void setBusinessObj(T businessObj) {
		this.businessObj = businessObj;
	}
}
