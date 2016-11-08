package com.quickshear.domain.query;

import java.io.Serializable;

import com.quickshear.common.vo.BaseQuery;

/**
 * 评价信息查询
 */
public class OrderEvaluateQuery extends BaseQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6278034358426903595L;

	/** 自增id */
    private Long id;

    /** 订单id */
    private Long orderId;

    /** 门店 */
    private Long shopId;

    /** 发型师id */
    private Long hairdresserId;

    /** 顾客id */
    private Long customerId;

    /** 服务评分 */
    private Integer score;

    /** 是否有效(0否1是) */
    private Integer isValid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getHairdresserId() {
		return hairdresserId;
	}

	public void setHairdresserId(Long hairdresserId) {
		this.hairdresserId = hairdresserId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
}
