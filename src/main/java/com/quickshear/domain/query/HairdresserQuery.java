package com.quickshear.domain.query;

import java.io.Serializable;

import com.quickshear.common.vo.BaseQuery;

/**
 * 发型师信息查询
 */
public class HairdresserQuery extends BaseQuery implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = -3025614684397016248L;

	/** id(自增) */
    private Long id;

    /** 姓名 */
    private String name;

    /** 电话 */
    private String phoneNumber;

    /** 所属门店 */
    private Long shopId;

    /** 微信id */
    private String wechatId;

    /** 状态(0待审核1有效2无效) */
    private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
