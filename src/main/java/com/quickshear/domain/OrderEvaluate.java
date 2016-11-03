package com.quickshear.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 * @author Administrator
 * @date 2016-11-3
 *
 */
public class OrderEvaluate implements Serializable {
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

    /** 内容 */
    private String content;

    /** 服务评分 */
    private Integer score;

    /** 是否有效(0否1是) */
    private Integer isValid;

    /** 创建时间 */
    private Date cTime;

    /** 最后修改时间 */
    private Date mTime;

    private static final long serialVersionUID = 1L;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getmTime() {
        return mTime;
    }

    public void setmTime(Date mTime) {
        this.mTime = mTime;
    }
}