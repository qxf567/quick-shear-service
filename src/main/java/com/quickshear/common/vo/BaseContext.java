package com.quickshear.common.vo;

public class BaseContext extends BaseObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4920801922346866617L;

	/**
	 * 对象编码
	 */
	private String code;

	/**
	 * 对象标识ID
	 */
	private String field;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
}
