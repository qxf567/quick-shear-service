package com.quickshear.domain.query;

import java.io.Serializable;
import java.math.BigDecimal;

import com.quickshear.common.vo.BaseQuery;

/**
 * 门店信息查询
 */
public class ShopQuery extends BaseQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7057309047169490433L;

	/** 门店id(自增) */
    private Long id;

    /** 门店名称 */
    private String name;

    /** 城市id */
    private Long cityId;

    /** 坐标经度 */
    private BigDecimal longitude;

    /** 坐标纬度 */
    private BigDecimal latitude;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
