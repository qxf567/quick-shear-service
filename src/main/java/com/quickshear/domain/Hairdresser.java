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
public class Hairdresser implements Serializable {
    /** id(自增) */
    private Long id;

    /** 姓名 */
    private String name;

    /** 电话 */
    private String phoneNumber;

    /** 登陆密码 */
    private String password;

    /** 所属门店 */
    private Long shopId;

    /** 微信id */
    private String wechatId;

    /** 身份证正面照 */
    private String identityCardFacePhoto;

    /** 身份证背面照 */
    private String identityCardBackPhoto;

    /** 休息日 */
    private String restday;

    /** 状态(0待审核1有效2无效) */
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getIdentityCardFacePhoto() {
        return identityCardFacePhoto;
    }

    public void setIdentityCardFacePhoto(String identityCardFacePhoto) {
        this.identityCardFacePhoto = identityCardFacePhoto;
    }

    public String getIdentityCardBackPhoto() {
        return identityCardBackPhoto;
    }

    public void setIdentityCardBackPhoto(String identityCardBackPhoto) {
        this.identityCardBackPhoto = identityCardBackPhoto;
    }

    public String getRestday() {
        return restday;
    }

    public void setRestday(String restday) {
        this.restday = restday;
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