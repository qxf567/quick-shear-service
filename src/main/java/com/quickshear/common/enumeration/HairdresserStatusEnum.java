package com.quickshear.common.enumeration;


/**
 * 发型师状态
 * @author QIAN
 *
 */
public enum HairdresserStatusEnum{
	
	INVALID(0, "待审核"),
	OPEN(1, "有效"),
	REST(2,"无效");
	
	private Integer code;
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * @param code
	 * @param name
	 */
	private HairdresserStatusEnum(final Integer code, final String name) {
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
	public static HairdresserStatusEnum valueOfCode(Integer code) {
		if(null == code) {
			return null;
		}
		for(HairdresserStatusEnum pp : values()) {
			if(pp.getCode().equals(code)) {
				return pp;
			}
		}
		return null;
	}
}
