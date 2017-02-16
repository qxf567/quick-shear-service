package com.quickshear.service;

import java.util.List;

import com.quickshear.common.vo.PageVo;
import com.quickshear.domain.User;
import com.quickshear.domain.query.UserQuery;


/**
 * 系统用户信息service
 */
public interface UserService {
	
	
	/**
	 * 根据主键查询
	 * @param id 主键
	 * @return 实体对象
	 */
	User findbyid(Long id) throws Exception;

	/**
	 * 添加
	 * @param t 实体对象
	 * @return 0：失败，1：成功
	 */
	int save(User t) throws Exception;

	/**
	 * 更新，更新实体对象
	 * @param t 实体对象
	 * @return 0:失败，1：成功
	 */
	int update(User t) throws Exception;
	
	/**
	 * 更新，更新实体对象
	 * @param t 实体对象
	 * @return 0:失败，1：成功
	 */
	int update(User t,UserQuery queryObj) throws Exception;
	
	/**
	 * 根据条件查询
	 * 
	 * @param queryObj
	 * @return
	 */
	List<User> selectByParam(UserQuery queryObj) throws Exception;
	
	/**
	 * 根据条件分页查询
	 * 
	 * @param queryObj
	 * @return
	 */
	PageVo<User> findByParam(UserQuery queryObj) throws Exception;
}
