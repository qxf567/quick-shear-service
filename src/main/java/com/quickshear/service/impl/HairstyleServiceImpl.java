package com.quickshear.service.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickshear.common.vo.BaseQuery;
import com.quickshear.common.vo.PageVo;
import com.quickshear.domain.Hairstyle;
import com.quickshear.domain.HairstyleExample;
import com.quickshear.domain.HairstyleExample.Criteria;
import com.quickshear.domain.query.HairstyleQuery;
import com.quickshear.mapper.HairstyleMapper;
import com.quickshear.service.HairstyleService;

@Service
public class HairstyleServiceImpl implements HairstyleService {

	private static final Logger logger = Logger.getLogger(HairstyleServiceImpl.class);
	@Autowired
	private HairstyleMapper hairstyleMapper;
	
	public Hairstyle findbyid(Long id) throws Exception {
		if(id == null || id <= 0){
			return null ;
		}
		return hairstyleMapper.selectByPrimaryKey(id) ;
	}

	public int save(Hairstyle t) throws Exception {
		if (t == null) {
			return -1;
		}
		try {
			t.setcTime(Calendar.getInstance().getTime());
			t.setmTime(Calendar.getInstance().getTime());
			return hairstyleMapper.insertSelective(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save 失败", e);
		}
		return 0;
	}

	public int update(Hairstyle t) throws Exception {
		if (t == null) {
			return -1;
		}
		try {
			t.setmTime(Calendar.getInstance().getTime());
			return hairstyleMapper.updateByPrimaryKeySelective(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update 失败", e);
		}
		return 0;
	}

	public List<Hairstyle> selectByParam(HairstyleQuery queryObj) throws Exception {
		if (queryObj == null) {
			return null;
		}
		HairstyleExample example = new HairstyleExample();
		Criteria criteria = example.createCriteria();
		if(queryObj.getStatus() != null){
			criteria.andStatusEqualTo(queryObj.getStatus());
		}
		return hairstyleMapper.selectByExample(example);
	}

	public PageVo<Hairstyle> findByParam(HairstyleQuery queryObj) throws Exception {
		PageVo<Hairstyle> result = new PageVo<Hairstyle>();
		try {
			HairstyleExample example = this.queryObj2Example(queryObj);
			List<Hairstyle> list = hairstyleMapper.selectByExample(example);

			this.clearSortAndPagenation(example);
			int count = hairstyleMapper.countByExample(example);

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
	private void clearSortAndPagenation(HairstyleExample example) {
		example.setOrderByClause(null);
		example.setLimitStart(-1);
		example.setLimitEnd(-1);
	}

	/**
	 * QueryObj转换为Example
	 */
	private HairstyleExample queryObj2Example(HairstyleQuery queryObj) {
		HairstyleExample example = new HairstyleExample();

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
