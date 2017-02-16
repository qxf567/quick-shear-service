package com.quickshear.common.enumeration;


/**
 * 发型师状态
 * @author QIAN
 *
 */
public enum HairdresserStatusEnum{
	/** 无效  */
	INVALID(0, "无效"),
	/** 有效  */
    VALID(1, "有效"),
    /** 待审核  */
	PENDING(300,"待审核");
	
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
