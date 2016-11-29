package com.quickshear.common.enumeration;


/**
 * 店铺状态
 * @author QIAN
 *
 */
public enum ShopStatusEnum{
	
	INVALID(0, "无效"),
	OPEN(1, "营业中"),
	REST(2,"暂停营业");
	
	private Integer code;
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * @param code
	 * @param name
	 */
	private ShopStatusEnum(final Integer code, final String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
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
	public static ShopStatusEnum valueOfCode(Integer code) {
		if(null == code) {
			return null;
		}
		for(ShopStatusEnum pp : values()) {
			if(pp.getCode().equals(code)) {
				return pp;
			}
		}
		return null;
	}
}
