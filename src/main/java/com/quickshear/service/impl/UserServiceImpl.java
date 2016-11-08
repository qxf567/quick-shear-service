package com.quickshear.service.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickshear.common.vo.BaseQuery;
import com.quickshear.common.vo.PageVo;
import com.quickshear.domain.User;
import com.quickshear.domain.UserExample;
import com.quickshear.domain.UserExample.Criteria;
import com.quickshear.domain.query.UserQuery;
import com.quickshear.mapper.UserMapper;
import com.quickshear.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	@Autowired
	private UserMapper userMapper;
	
	public User findbyid(Long id) throws Exception {
		if(id == null || id <= 0){
			return null ;
		}
		return userMapper.selectByPrimaryKey(id) ;
	}

	public int save(User t) throws Exception {
		if (t == null) {
			return -1;
		}
		try {
			t.setcTime(Calendar.getInstance().getTime());
			t.setmTime(Calendar.getInstance().getTime());
			return userMapper.insertSelective(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save 失败", e);
		}
		return 0;
	}

	public int update(User t) throws Exception {
		if (t == null) {
			return -1;
		}
		try {
			t.setmTime(Calendar.getInstance().getTime());
			return userMapper.updateByPrimaryKeySelective(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update 失败", e);
		}
		return 0;
	}

	public List<User> selectByParam(UserQuery queryObj) throws Exception {
		if (queryObj == null) {
			return null;
		}
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		if(queryObj.getPhoneNumber() != null){
			criteria.andPhoneNumberEqualTo(queryObj.getPhoneNumber());
		}
		return userMapper.selectByExample(example);
	}

	public PageVo<User> findByParam(UserQuery queryObj) throws Exception {
		PageVo<User> result = new PageVo<User>();
		try {
			UserExample example = this.queryObj2Example(queryObj);
			List<User> list = userMapper.selectByExample(example);

			this.clearSortAndPagenation(example);
			int count = userMapper.countByExample(example);

			result.setResult(list);
			result.setTotalCount(count);

			result.setPageNo(queryObj.getPageNo());
			result.setPageSize(queryObj.getPageSize());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("findByParam 失败", e);
		}
		return result;
	}
	
	/**
	 * 清除排序和分页条件
	 */
	private void clearSortAndPagenation(UserExample example) {
		example.setOrderByClause(null);
		example.setLimitStart(-1);
		example.setLimitEnd(-1);
	}

	/**
	 * QueryObj转换为Example
	 */
	private UserExample queryObj2Example(UserQuery queryObj) {

		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		if(queryObj.getPhoneNumber() != null){
			criteria.andPhoneNumberEqualTo(queryObj.getPhoneNumber());
		}

		// 排序
		if (StringUtils.isNotBlank(queryObj.getSort())) {
			String orderByClause = queryObj.getSort();
			if (BaseQuery.DESC.equalsIgnoreCase(queryObj.getSortType())) {
				orderByClause += " desc";
			} else {
				orderByClause += " asc";
			}
			example.setOrderByClause(orderByClause);
		}
		// 分页
		example.setLimitStart((queryObj.getPageNo() - 1) * queryObj.getPageSize());
		example.setLimitEnd(queryObj.getPageSize());
		return example;
	}
}
