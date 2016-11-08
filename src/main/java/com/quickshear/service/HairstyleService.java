package com.quickshear.service;

import java.util.List;

import com.quickshear.common.vo.PageVo;
import com.quickshear.domain.Hairstyle;
import com.quickshear.domain.query.HairstyleQuery;


/**
 * 发型信息service
 */
public interface HairstyleService {
	
	
	/**
	 * 根据主键查询
	 * @param id 主键
	 * @return 实体对象
	 */
	Hairstyle findbyid(Long id) throws Exception;

	/**
	 * 添加
	 * @param t 实体对象
	 * @return 0：失败，1：成功
	 */
	int save(Hairstyle t) throws Exception;

	/**
	 * 更新，更新实体对象
	 * @param t 实体对象
	 * @return 0:失败，1：成功
	 */
	int update(Hairstyle t) throws Exception;
	
	/**
	 * 根据条件查询
	 * 
	 * @param queryObj
	 * @return
	 */
	List<Hairstyle> selectByParam(HairstyleQuery queryObj) throws Exception;
	
	/**
	 * 根据条件分页查询
	 * 
	 * @param queryObj
	 * @return
	 */
	PageVo<Hairstyle> findByParam(HairstyleQuery queryObj) throws Exception;
}
