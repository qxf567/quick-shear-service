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
	private static final long serialVersionUID = 1L;

	/** id(自增) */
    private Long id;

    /** 姓名 */
    private String name;

    /** 电话 */
    private String phoneNumber;

    /** 所属门店 */
    private Long shopId;

    /** 状态(0无效1有效300待审核) */
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
