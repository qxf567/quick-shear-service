package com.quickshear.service;

import java.util.List;

import com.quickshear.common.vo.PageVo;
import com.quickshear.domain.Customer;
import com.quickshear.domain.query.CustomerQuery;



/**
 * 客户相关服务
 * 
 * @author qianxiaofei
 */
public interface CustomerService  {

	
	/**
	 * 根据主键查询
	 * @param id 主键
	 * @return 实体对象
	 */
	Customer findbyid(Long id) throws Exception;
	
	/**
	 * 根据openid查询
	 * @param openid 
	 * @return 实体对象
	 */
	Customer findbyOpenId(String openId) throws Exception;

	/**
	 * 添加
	 * @param t 实体对象
	 * @return 0：失败，1：成功
	 */
	int save(Customer t) throws Exception;

	/**
	 * 更新，更新实体对象
	 * @param t 实体对象
	 * @return 0:失败，1：成功
	 */
	int update(Customer t) throws Exception;
	
	/**
	 * 根据条件查询
	 * 
	 * @param queryObj
	 * @return
	 */
	List<Customer> selectByParam(CustomerQuery queryObj) throws Exception;
	
	/**
	 * 根据条件分页查询
	 * 
	 * @param queryObj
	 * @return
	 */
	PageVo<Customer> findByParam(CustomerQuery queryObj) throws Exception;
}
