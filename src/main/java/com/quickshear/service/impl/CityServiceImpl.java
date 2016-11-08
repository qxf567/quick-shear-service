package com.quickshear.service.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickshear.common.vo.BaseQuery;
import com.quickshear.common.vo.PageVo;
import com.quickshear.domain.City;
import com.quickshear.domain.CityExample;
import com.quickshear.domain.CityExample.Criteria;
import com.quickshear.domain.query.CityQuery;
import com.quickshear.mapper.CityMapper;
import com.quickshear.service.CityService;

@Service
public class CityServiceImpl implements CityService {
	
	private static final Logger logger = Logger.getLogger(CityServiceImpl.class);
	@Autowired
	private CityMapper cityMapper;
	
	public City findbyid(Long id) throws Exception {
		if(id == null || id <= 0){
			return null ;
		}
		return cityMapper.selectByPrimaryKey(id) ;
	}

	public int save(City t) throws Exception {
		if (t == null) {
			return -1;
		}
		try {
			t.setcTime(Calendar.getInstance().getTime());
			t.setmTime(Calendar.getInstance().getTime());
			return cityMapper.insertSelective(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save 失败", e);
		}
		return 0;
	}

	public int update(City t) throws Exception {
		if (t == null) {
			return -1;
		}
		try {
			t.setmTime(Calendar.getInstance().getTime());
			return cityMapper.updateByPrimaryKeySelective(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update 失败", e);
		}
		return 0;
	}

	public List<City> selectByParam(CityQuery queryObj) throws Exception {
		if (queryObj == null) {
			return null;
		}
		CityExample example = new CityExample();
		Criteria criteria = example.createCriteria();
		if(queryObj.getStatus() != null){
			criteria.andStatusEqualTo(queryObj.getStatus());
		}
		return cityMapper.selectByExample(example);
	}

	public PageVo<City> findByParam(CityQuery queryObj) throws Exception {
		PageVo<City> result = new PageVo<City>();
		try {
			CityExample example = this.queryObj2Example(queryObj);
			List<City> list = cityMapper.selectByExample(example);

			this.clearSortAndPagenation(example);
			int count = cityMapper.countByExample(example);

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
	private void clearSortAndPagenation(CityExample example) {
		example.setOrderByClause(null);
		example.setLimitStart(-1);
		example.setLimitEnd(-1);
	}

	/**
	 * QueryObj转换为Example
	 */
	private CityExample queryObj2Example(CityQuery queryObj) {
		CityExample example = new CityExample();

		Criteria criteria = example.createCriteria();
		if(queryObj.getStatus() != null){
			criteria.andStatusEqualTo(queryObj.getStatus());
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
