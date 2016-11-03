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
public class Shop implements Serializable {
    /** 门店id(自增) */
    private Long id;

    /** 门店名称 */
    private String name;

    /** 电话 */
    private String phoneNumber;

    /** 营业时间 */
    private String businessHours;

    /** 城市id */
    private Long cityId;

    /** 坐标经度 */
    private BigDecimal longitude;

    /** 坐标纬度 */
    private BigDecimal latitude;

    /** 详细地址 */
    private String address;

    /** 门店图片(主图) */
    private String mainImageUrl;

    /** 门店图片(副图) */
    private String multiImageUrls;

    /** 状态(0无效1正常营业2暂停营业) */
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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