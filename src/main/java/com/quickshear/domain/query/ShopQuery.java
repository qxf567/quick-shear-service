package com.quickshear.domain.query;

import java.io.Serializable;
import java.math.BigDecimal;

import com.quickshear.common.vo.BaseQuery;

/**
 * 门店信息查询
 */
public class ShopQuery extends BaseQuery implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -7057309047169490433L;

	/** 门店id(自增) */
    private Long id;

    /** 门店名称 */
    private String name;

    /** 城市id */
    private Long cityId;

    /** 坐标经度 */
    private Double longitude;

    /** 坐标纬度 */
    private Double latitude;

    /** 经纬度hash值 */
    private String geocode;

    /** 状态(0无效1正常营业2暂停营业) */
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

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getGeocode() {
		return geocode;
	}

	public void setGeocode(String geocode) {
		this.geocode = geocode;
	}

}
