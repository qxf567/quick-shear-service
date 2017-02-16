package com.quickshear.common.enumeration;


/**
 * 系统角色 
 * 
 */
public enum RoleEnum {
	/** 管理员  */
	ADMIN("1", "管理员"),
	/** 发型师  */
	STYLIST("2", "发型师"),
	/** 顾客  */
    CUSTOMER("3","顾客"),
    /** 待审核用户  */
    PENDING("300","待审核用户");
    

	private String code;
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * @param code
	 * @param name
	 */
	private RoleEnum(final String code, final String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 找不到则返回null
	 * @param code
	 * @return
	 */
	public static RoleEnum valueOfCode(String code) {
		if(null == code) {
			return null;
		}
		for(RoleEnum pp : values()) {
			if(pp.getCode().equals(code)) {
				return pp;
			}
		}
		return null;
	}
}
