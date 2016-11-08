package com.quickshear.service.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickshear.common.vo.BaseQuery;
import com.quickshear.common.vo.PageVo;
import com.quickshear.domain.OrderEvaluate;
import com.quickshear.domain.OrderEvaluateExample;
import com.quickshear.domain.OrderEvaluateExample.Criteria;
import com.quickshear.domain.query.OrderEvaluateQuery;
import com.quickshear.mapper.OrderEvaluateMapper;
import com.quickshear.service.OrderEvaluateService;

@Service
public class OrderEvaluateServiceImpl implements OrderEvaluateService {

	private static final Logger logger = Logger.getLogger(OrderEvaluateServiceImpl.class);
	@Autowired
	private OrderEvaluateMapper orderEvaluateMapper;
	
	public OrderEvaluate findbyid(Long id) throws Exception {
		if(id == null || id <= 0){
			return null ;
		}
		return orderEvaluateMapper.selectByPrimaryKey(id) ;
	}

	public int save(OrderEvaluate t) throws Exception {
		if (t == null) {
			return -1;
		}
		try {
			t.setcTime(Calendar.getInstance().getTime());
			t.setmTime(Calendar.getInstance().getTime());
			return orderEvaluateMapper.insertSelective(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save 失败", e);
		}
		return 0;
	}

	public int update(OrderEvaluate t) throws Exception {
		if (t == null) {
			return -1;
		}
		try {
			t.setmTime(Calendar.getInstance().getTime());
			return orderEvaluateMapper.updateByPrimaryKeySelective(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update 失败", e);
		}
		return 0;
	}

	public List<OrderEvaluate> selectByParam(OrderEvaluateQuery queryObj) throws Exception {
		if (queryObj == null) {
			return null;
		}
		OrderEvaluateExample example = new OrderEvaluateExample();
		Criteria criteria = example.createCriteria();
		if(queryObj.getCustomerId() != null){
			criteria.andCustomerIdEqualTo(queryObj.getCustomerId());
		}
		if(queryObj.getHairdresserId() != null){
			criteria.andHairdresserIdEqualTo(queryObj.getHairdresserId());
		}
		if(queryObj.getShopId() != null){
			criteria.andShopIdEqualTo(queryObj.getShopId());
		}
		if(queryObj.getOrderId() != null){
			criteria.andOrderIdEqualTo(queryObj.getOrderId());
		}
		if(queryObj.getIsValid() != null){
			criteria.andIsValidEqualTo(queryObj.getIsValid());
		}
		return orderEvaluateMapper.selectByExample(example);
	}

	public PageVo<OrderEvaluate> findByParam(OrderEvaluateQuery queryObj) throws Exception {
		PageVo<OrderEvaluate> result = new PageVo<OrderEvaluate>();
		try {
			OrderEvaluateExample example = this.queryObj2Example(queryObj);
			List<OrderEvaluate> list = orderEvaluateMapper.selectByExample(example);

			this.clearSortAndPagenation(example);
			int count = orderEvaluateMapper.countByExample(example);

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
	private void clearSortAndPagenation(OrderEvaluateExample example) {
		example.setOrderByClause(null);
		example.setLimitStart(-1);
		example.setLimitEnd(-1);
	}

	/**
	 * QueryObj转换为Example
	 */
	private OrderEvaluateExample queryObj2Example(OrderEvaluateQuery queryObj) {

		OrderEvaluateExample example = new OrderEvaluateExample();
		Criteria criteria = example.createCriteria();
		if(queryObj.getCustomerId() != null){
			criteria.andCustomerIdEqualTo(queryObj.getCustomerId());
		}
		if(queryObj.getHairdresserId() != null){
			criteria.andHairdresserIdEqualTo(queryObj.getHairdresserId());
		}
		if(queryObj.getShopId() != null){
			criteria.andShopIdEqualTo(queryObj.getShopId());
		}
		if(queryObj.getOrderId() != null){
			criteria.andOrderIdEqualTo(queryObj.getOrderId());
		}
		if(queryObj.getIsValid() != null){
			criteria.andIsValidEqualTo(queryObj.getIsValid());
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
