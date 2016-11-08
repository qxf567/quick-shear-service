package com.quickshear.service;

import java.util.List;

import com.quickshear.common.vo.PageVo;
import com.quickshear.domain.OrderEvaluate;
import com.quickshear.domain.query.OrderEvaluateQuery;


/**
 * 评价信息service
 */
public interface OrderEvaluateService {
	
	
	/**
	 * 根据主键查询
	 * @param id 主键
	 * @return 实体对象
	 */
	OrderEvaluate findbyid(Long id) throws Exception;

	/**
	 * 添加
	 * @param t 实体对象
	 * @return 0：失败，1：成功
	 */
	int save(OrderEvaluate t) throws Exception;

	/**
	 * 更新，更新实体对象
	 * @param t 实体对象
	 * @return 0:失败，1：成功
	 */
	int update(OrderEvaluate t) throws Exception;
	
	/**
	 * 根据条件查询
	 * 
	 * @param queryObj
	 * @return
	 */
	List<OrderEvaluate> selectByParam(OrderEvaluateQuery queryObj) throws Exception;
	
	/**
	 * 根据条件分页查询
	 * 
	 * @param queryObj
	 * @return
	 */
	PageVo<OrderEvaluate> findByParam(OrderEvaluateQuery queryObj) throws Exception;
}
