package com.quickshear.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.quickshear.common.vo.BaseQuery;

/**
 * 订单信息查询
 */
public class OrderQuery extends BaseQuery implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /** 订单号 */
    private Long orderId;

    /** 顾客id */
    private Long customerId;

    /** 顾客联系电话 */
    private String customerNumber;

    /** 门店 */
    private String shopId;

    /** 发型师id */
    private Long hairdresserId;

    /** 发型id */
    private Long hairstyleId;

    /** 订单支付方式(0线下1微信) */
    private Integer payType;

    /** 订单状态(0待支付1支付完成100服务完成300取消) */
    private Integer orderStatus;

    /** 是否已评价(0否1是) */
    private Integer isEvaluate;

    /** 预约时间 */
    private Date appointmentTime;

    public Long getOrderId() {
	return orderId;
    }

    public void setOrderId(Long orderId) {
	this.orderId = orderId;
    }

    public Long getCustomerId() {
	return customerId;
    }

    public void setCustomerId(Long customerId) {
	this.customerId = customerId;
    }

    public String getCustomerNumber() {
	return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
	this.customerNumber = customerNumber;
    }

    public String getShopId() {
	return shopId;
    }

    public void setShopId(String shopId) {
	this.shopId = shopId;
    }

    public Long getHairdresserId() {
	return hairdresserId;
    }

    public void setHairdresserId(Long hairdresserId) {
	this.hairdresserId = hairdresserId;
    }

    public Long getHairstyleId() {
	return hairstyleId;
    }

    public void setHairstyleId(Long hairstyleId) {
	this.hairstyleId = hairstyleId;
    }

    public Integer getPayType() {
	return payType;
    }

    public void setPayType(Integer payType) {
	this.payType = payType;
    }

    public Integer getOrderStatus() {
	return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
	this.orderStatus = orderStatus;
    }

    public Integer getIsEvaluate() {
	return isEvaluate;
    }

    public void setIsEvaluate(Integer isEvaluate) {
	this.isEvaluate = isEvaluate;
    }

    public Date getAppointmentTime() {
	return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
	this.appointmentTime = appointmentTime;
    }

}
