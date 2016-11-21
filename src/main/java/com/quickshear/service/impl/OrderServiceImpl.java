package com.quickshear.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickshear.common.util.DateUtil;
import com.quickshear.common.vo.BaseQuery;
import com.quickshear.common.vo.PageVo;
import com.quickshear.domain.Order;
import com.quickshear.domain.OrderExample;
import com.quickshear.domain.OrderExample.Criteria;
import com.quickshear.domain.query.OrderQuery;
import com.quickshear.mapper.OrderMapper;
import com.quickshear.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);
	@Autowired
	private OrderMapper orderMapper;
	
	public Order findbyid(Long id) throws Exception {
		if(id == null || id <= 0){
			return null ;
		}
		return orderMapper.selectByPrimaryKey(id) ;
	}

	public int save(Order t) throws Exception {
		if (t == null) {
			return -1;
		}
		try {
			t.setcTime(Calendar.getInstance().getTime());
			t.setmTime(Calendar.getInstance().getTime());
			return orderMapper.insertSelective(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save 失败", e);
		}
		return 0;
	}

	public int update(Order t) throws Exception {
		if (t == null) {
			return -1;
		}
		try {
			t.setmTime(Calendar.getInstance().getTime());
			return orderMapper.updateByPrimaryKeySelective(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update 失败", e);
		}
		return 0;
	}

	public List<Order> selectByParam(OrderQuery queryObj) throws Exception {
		if (queryObj == null) {
			return null;
		}
		OrderExample example = new OrderExample();
		Criteria criteria = example.createCriteria();
		if(queryObj.getOrderId() != null){
			criteria.andOrderIdEqualTo(queryObj.getOrderId());
		}
		if(queryObj.getCustomerId() != null){
			criteria.andCustomerIdEqualTo(queryObj.getCustomerId());
		}
		if(queryObj.getCustomerNumber() != null){
			criteria.andCustomerNumberEqualTo(queryObj.getCustomerNumber());
		}
		if(queryObj.getHairdresserId() != null){
			criteria.andHairdresserIdEqualTo(queryObj.getHairdresserId());
		}
		if(queryObj.getOrderStatus() != null){
			criteria.andOrderStatusEqualTo(queryObj.getOrderStatus());
		}
		if(queryObj.getAppointmentTime() != null){
		    Date start = queryObj.getAppointmentTime();
		    Date end = DateUtil.getMinuteAfterDay(30);
		    criteria.andAppointmentTimeBetween(start, end);
		}
		return orderMapper.selectByExample(example);
	}

	public PageVo<Order> findByParam(OrderQuery queryObj) throws Exception {
		PageVo<Order> result = new PageVo<Order>();
		try {
			OrderExample example = this.queryObj2Example(queryObj);
			List<Order> list = orderMapper.selectByExample(example);

			this.clearSortAndPagenation(example);
			int count = orderMapper.countByExample(example);

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
	private void clearSortAndPagenation(OrderExample example) {
		example.setOrderByClause(null);
		example.setLimitStart(-1);
		example.setLimitEnd(-1);
	}

	/**
	 * QueryObj转换为Example
	 */
	private OrderExample queryObj2Example(OrderQuery queryObj) {

		OrderExample example = new OrderExample();
		Criteria criteria = example.createCriteria();
		if(queryObj.getOrderId() != null){
			criteria.andOrderIdEqualTo(queryObj.getOrderId());
		}
		if(queryObj.getCustomerId() != null){
			criteria.andCustomerIdEqualTo(queryObj.getCustomerId());
		}
		if(queryObj.getCustomerNumber() != null){
			criteria.andCustomerNumberEqualTo(queryObj.getCustomerNumber());
		}
		if(queryObj.getHairdresserId() != null){
			criteria.andHairdresserIdEqualTo(queryObj.getHairdresserId());
		}
		if(queryObj.getOrderStatus() != null){
			criteria.andOrderStatusEqualTo(queryObj.getOrderStatus());
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
