package com.quickshear.domain.query;

import java.io.Serializable;

import com.quickshear.common.vo.BaseQuery;

/**
 * 系统用户信息查询
 */
public class UserQuery extends BaseQuery implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = -998124707764880792L;

	/** id(自增) */
    private Long id;

    /** 电话 */
    private String phoneNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


}
