package com.quickshear.domain.query;

import java.io.Serializable;

import com.quickshear.common.vo.BaseQuery;

/**
 * 城市信息查询
 */
public class CityQuery extends BaseQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5240619874361475540L;

	/** id(自增) */
    private Long id;

    /** 城市名称(简称) */
    private String name;

    /** 城市名称(全称) */
    private String fullName;

    /** 上级城市id */
    private Long pid;

    /** 城市全路径id */
    private String fullPathId;

    /** 城市全路径名称 */
    private String fullPathName;

    /** 状态(1有效0无效) */
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getFullPathId() {
		return fullPathId;
	}

	public void setFullPathId(String fullPathId) {
		this.fullPathId = fullPathId;
	}

	public String getFullPathName() {
		return fullPathName;
	}

	public void setFullPathName(String fullPathName) {
		this.fullPathName = fullPathName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
