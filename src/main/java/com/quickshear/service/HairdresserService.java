package com.quickshear.service;

import java.util.List;

import com.quickshear.common.vo.PageVo;
import com.quickshear.domain.Hairdresser;
import com.quickshear.domain.query.HairdresserQuery;


/**
 * 发型师信息service
 */
public interface HairdresserService {
	
	
	/**
	 * 根据主键查询
	 * @param id 主键
	 * @return 实体对象
	 */
	Hairdresser findbyid(Long id) throws Exception;

	/**
	 * 添加
	 * @param t 实体对象
	 * @return 0：失败，1：成功
	 */
	int save(Hairdresser t) throws Exception;

	/**
	 * 更新，更新实体对象
	 * @param t 实体对象
	 * @return 0:失败，1：成功
	 */
	int update(Hairdresser t) throws Exception;
	
	/**
	 * 更新，更新实体对象
	 * @param t 实体对象
	 * @return 0:失败，1：成功
	 */
	int update(Hairdresser t,HairdresserQuery queryObj) throws Exception;
	
	/**
	 * 根据条件查询
	 * 
	 * @param queryObj
	 * @return
	 */
	List<Hairdresser> selectByParam(HairdresserQuery queryObj) throws Exception;
	
	/**
	 * 根据条件分页查询
	 * 
	 * @param queryObj
	 * @return
	 */
	PageVo<Hairdresser> findByParam(HairdresserQuery queryObj) throws Exception;
}
