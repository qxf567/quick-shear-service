package com.quickshear.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 *
 * @author Administrator
 * @date 2016-11-3
 *
 */
public class Hairstyle implements Serializable {
    /** id(自增) */
    private Long id;

    /** 发型名称 */
    private String name;

    /** 介绍明细 */
    private String detail;

    /** 图片(主图) */
    private String mainImageUrl;

    /** 图片(副图) */
    private String multiImageUrls;

    /** 收费价格 */
    private BigDecimal price;

    /** 状态(0无效1有效) */
    private Integer status;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public String getMultiImageUrls() {
        return multiImageUrls;
    }

    public void setMultiImageUrls(String multiImageUrls) {
        this.multiImageUrls = multiImageUrls;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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