package com.quickshear.service;

import java.util.List;

import com.quickshear.common.vo.PageVo;
import com.quickshear.domain.Order;
import com.quickshear.domain.query.OrderQuery;


/**
 * 订单信息service
 */
public interface OrderService {
	
	
	/**
	 * 根据主键查询
	 * @param id 主键
	 * @return 实体对象
	 */
	Order findbyid(Long id) throws Exception;

	/**
	 * 添加
	 * @param t 实体对象
	 * @return 0：失败，1：成功
	 */
	int save(Order t) throws Exception;

	/**
	 * 更新，更新实体对象
	 * @param t 实体对象
	 * @return 0:失败，1：成功
	 */
	int update(Order t) throws Exception;
	
	/**
	 * 更新，更新实体对象
	 * @param t 实体对象
	 * @return 0:失败，1：成功
	 */
	int updateByParam(Order t,OrderQuery queryObj) throws Exception;
	
	/**
	 * 根据条件查询
	 * 
	 * @param queryObj
	 * @return
	 */
	List<Order> selectByParam(OrderQuery queryObj) throws Exception;
	
	/**
	 * 根据条件分页查询
	 * 
	 * @param queryObj
	 * @return
	 */
	PageVo<Order> findByParam(OrderQuery queryObj) throws Exception;
}
