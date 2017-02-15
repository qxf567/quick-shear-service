package com.quickshear.common.enumeration;


/**
 * 性别
 * @author QIAN
 *
 */
public enum SexEnum{
	
	MAN(0, "男"),
	GIRL(1, "女");
	
	private Integer code;
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * @param code
	 * @param name
	 */
	private SexEnum(final Integer code, final String name) {
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
	public static SexEnum valueOfCode(Integer code) {
		if(null == code) {
			return null;
		}
		for(SexEnum pp : values()) {
			if(pp.getCode().equals(code)) {
				return pp;
			}
		}
		return null;
	}
}
