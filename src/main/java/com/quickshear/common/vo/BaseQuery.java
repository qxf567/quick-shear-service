package com.quickshear.common.vo;

import com.quickshear.common.base.BasePo;

public class BaseQuery extends BasePo{
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	/**
	 * 当前页
	 */
	private Integer pageNo;

	/**
	 * 每页显示条数
	 */
	private Integer pageSize;
	/**
	 * 排序属性
	 */
	private String sort = "m_time";
	/**
	 * 排序类型，asc 或者 desc
	 */
	private String sortType = BaseQuery.DESC;

	/**
     * 系统标识
     */
    private String appkey;
	
	public Integer getPageNo() {
		if (pageNo == null || pageNo < 1) {
			this.pageNo = 1;
		}
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		if (pageSize == null || pageSize < 1) {
			this.pageSize = 10;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }
}
