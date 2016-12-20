package com.quickshear.common.enumeration;


/**
 * 订单状态
 * @author QIAN
 *
 */
public enum OrderStatusEnum{
	
        PAY_WAIT(0, "待支付"),
	PAY_COMPLETE(1, "支付完成"),
	ORDER_RECEIVE(50,"已接单"),
	SERVICE_COMPLETE(100,"服务完成"),
	CANCEL(300,"取消");
	
	private Integer code;
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * @param code
	 * @param name
	 */
	private OrderStatusEnum(final Integer code, final String name) {
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
	public static OrderStatusEnum valueOfCode(Integer code) {
		if(null == code) {
			return null;
		}
		for(OrderStatusEnum pp : values()) {
			if(pp.getCode().equals(code)) {
				return pp;
			}
		}
		return null;
	}
}
