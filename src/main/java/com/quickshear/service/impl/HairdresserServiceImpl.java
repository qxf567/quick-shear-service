package com.quickshear.service.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickshear.common.vo.BaseQuery;
import com.quickshear.common.vo.PageVo;
import com.quickshear.domain.Hairdresser;
import com.quickshear.domain.HairdresserExample;
import com.quickshear.domain.HairdresserExample.Criteria;
import com.quickshear.domain.query.HairdresserQuery;
import com.quickshear.mapper.HairdresserMapper;
import com.quickshear.service.HairdresserService;

@Service
public class HairdresserServiceImpl implements HairdresserService {

	private static final Logger logger = Logger.getLogger(HairdresserServiceImpl.class);
	@Autowired
	private HairdresserMapper hairdresserMapper;
	
	public Hairdresser findbyid(Long id) throws Exception {
		if(id == null || id <= 0){
			return null ;
		}
		return hairdresserMapper.selectByPrimaryKey(id) ;
	}

	public int save(Hairdresser t) throws Exception {
		if (t == null) {
			return -1;
		}
		try {
			t.setcTime(Calendar.getInstance().getTime());
			t.setmTime(Calendar.getInstance().getTime());
			return hairdresserMapper.insertSelective(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save 失败", e);
		}
		return 0;
	}

	public int update(Hairdresser t) throws Exception {
		if (t == null) {
			return -1;
		}
		try {
			t.setmTime(Calendar.getInstance().getTime());
			return hairdresserMapper.updateByPrimaryKeySelective(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update 失败", e);
		}
		return 0;
	}

	public List<Hairdresser> selectByParam(HairdresserQuery queryObj) throws Exception {
		if (queryObj == null) {
			return null;
		}
		HairdresserExample example = new HairdresserExample();
		Criteria criteria = example.createCriteria();
		if(queryObj.getStatus() != null){
			criteria.andStatusEqualTo(queryObj.getStatus());
		}
		if(queryObj.getPhoneNumber() != null){
			criteria.andPhoneNumberEqualTo(queryObj.getPhoneNumber());
		}
		if(queryObj.getShopId() !=null){
			criteria.andShopIdEqualTo(queryObj.getShopId());
		}
		return hairdresserMapper.selectByExample(example);
	}

	public PageVo<Hairdresser> findByParam(HairdresserQuery queryObj) throws Exception {
		PageVo<Hairdresser> result = new PageVo<Hairdresser>();
		try {
			HairdresserExample example = this.queryObj2Example(queryObj);
			List<Hairdresser> list = hairdresserMapper.selectByExample(example);

			this.clearSortAndPagenation(example);
			int count = hairdresserMapper.countByExample(example);

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
	private void clearSortAndPagenation(HairdresserExample example) {
		example.setOrderByClause(null);
		example.setLimitStart(-1);
		example.setLimitEnd(-1);
	}

	/**
	 * QueryObj转换为Example
	 */
	private HairdresserExample queryObj2Example(HairdresserQuery queryObj) {
		HairdresserExample example = new HairdresserExample();

		Criteria criteria = example.createCriteria();
		if(queryObj.getStatus() != null){
			criteria.andStatusEqualTo(queryObj.getStatus());
		}
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
