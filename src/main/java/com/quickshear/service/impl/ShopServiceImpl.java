package com.quickshear.service.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickshear.common.vo.BaseQuery;
import com.quickshear.common.vo.PageVo;
import com.quickshear.domain.Shop;
import com.quickshear.domain.ShopExample;
import com.quickshear.domain.ShopExample.Criteria;
import com.quickshear.domain.query.ShopQuery;
import com.quickshear.mapper.ShopMapper;
import com.quickshear.service.ShopService;

@Service
public class ShopServiceImpl implements ShopService {

	private static final Logger logger = Logger.getLogger(ShopServiceImpl.class);
	@Autowired
	private ShopMapper shopMapper;
	
	public Shop findbyid(Long id) throws Exception {
		if(id == null || id <= 0){
			return null ;
		}
		return shopMapper.selectByPrimaryKey(id) ;
	}

	public int save(Shop t) throws Exception {
		if (t == null) {
			return -1;
		}
		try {
			t.setcTime(Calendar.getInstance().getTime());
			t.setmTime(Calendar.getInstance().getTime());
			return shopMapper.insertSelective(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save 失败", e);
		}
		return 0;
	}

	public int update(Shop t) throws Exception {
		if (t == null) {
			return -1;
		}
		try {
			t.setmTime(Calendar.getInstance().getTime());
			return shopMapper.updateByPrimaryKeySelective(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update 失败", e);
		}
		return 0;
	}

	public List<Shop> selectByParam(ShopQuery queryObj) throws Exception {
		if (queryObj == null) {
			return null;
		}
		ShopExample example = new ShopExample();
		Criteria criteria = example.createCriteria();
		if(queryObj.getCityId() != null){
			criteria.andCityIdEqualTo(queryObj.getCityId());
		}
		if(queryObj.getStatus() != null){
			criteria.andStatusEqualTo(queryObj.getStatus());
		}
		return shopMapper.selectByExample(example);
	}

	public PageVo<Shop> findByParam(ShopQuery queryObj) throws Exception {
		PageVo<Shop> result = new PageVo<Shop>();
		try {
			ShopExample example = this.queryObj2Example(queryObj);
			List<Shop> list = shopMapper.selectByExample(example);

			this.clearSortAndPagenation(example);
			int count = shopMapper.countByExample(example);

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
	private void clearSortAndPagenation(ShopExample example) {
		example.setOrderByClause(null);
		example.setLimitStart(-1);
		example.setLimitEnd(-1);
	}

	/**
	 * QueryObj转换为Example
	 */
	private ShopExample queryObj2Example(ShopQuery queryObj) {

		ShopExample example = new ShopExample();
		Criteria criteria = example.createCriteria();
		if(queryObj.getCityId() != null){
			criteria.andCityIdEqualTo(queryObj.getCityId());
		}
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
