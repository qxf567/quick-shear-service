package com.quickshear.service.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickshear.common.vo.BaseQuery;
import com.quickshear.common.vo.PageVo;
import com.quickshear.domain.Customer;
import com.quickshear.domain.CustomerExample;
import com.quickshear.domain.CustomerExample.Criteria;
import com.quickshear.domain.query.CustomerQuery;
import com.quickshear.mapper.CustomerMapper;
import com.quickshear.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = Logger.getLogger(CustomerServiceImpl.class);
	@Autowired
	private CustomerMapper customerMapper;
	
	public Customer findbyid(Long id) throws Exception {
		if(id == null || id <= 0){
			return null ;
		}
		return customerMapper.selectByPrimaryKey(id) ;
	}

	public int save(Customer t) throws Exception {
		if (t == null) {
			return -1;
		}
		try {
			t.setcTime(Calendar.getInstance().getTime());
			t.setmTime(Calendar.getInstance().getTime());
			return customerMapper.insertSelective(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save 失败", e);
		}
		return 0;
	}

	public int update(Customer t) throws Exception {
		if (t == null) {
			return -1;
		}
		try {
			t.setmTime(Calendar.getInstance().getTime());
			return customerMapper.updateByPrimaryKeySelective(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update 失败", e);
		}
		return 0;
	}

	public List<Customer> selectByParam(CustomerQuery queryObj) throws Exception {
		if (queryObj == null) {
			return null;
		}
		CustomerExample example = new CustomerExample();
		Criteria criteria = example.createCriteria();
		if(queryObj.getPhoneNumber() != null){
			criteria.andPhoneNumberEqualTo(queryObj.getPhoneNumber());
		}
		if(queryObj.getWechatOpenId() != null){
			criteria.andWechatOpenIdEqualTo(queryObj.getWechatOpenId());
		}
		return customerMapper.selectByExample(example);
	}

	public PageVo<Customer> findByParam(CustomerQuery queryObj) throws Exception {
		PageVo<Customer> result = new PageVo<Customer>();
		try {
			CustomerExample example = this.queryObj2Example(queryObj);
			List<Customer> list = customerMapper.selectByExample(example);

			this.clearSortAndPagenation(example);
			int count = customerMapper.countByExample(example);

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
	private void clearSortAndPagenation(CustomerExample example) {
		example.setOrderByClause(null);
		example.setLimitStart(-1);
		example.setLimitEnd(-1);
	}

	/**
	 * QueryObj转换为Example
	 */
	private CustomerExample queryObj2Example(CustomerQuery queryObj) {
		CustomerExample example = new CustomerExample();

		Criteria criteria = example.createCriteria();
		if(queryObj.getPhoneNumber() != null){
			criteria.andPhoneNumberEqualTo(queryObj.getPhoneNumber());
		}
		if(queryObj.getWechatOpenId() != null){
			criteria.andWechatOpenIdEqualTo(queryObj.getWechatOpenId());
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

    public Customer findbyOpenId(String openId) throws Exception {
	if (openId == null) {
	    return null;
	}
	CustomerExample example = new CustomerExample();
	Criteria criteria = example.createCriteria();
	criteria.andWechatOpenIdEqualTo(openId);
	List<Customer> list = customerMapper.selectByExample(example);
	if (list.size() > 0) {
	    return list.get(0);
	}
	return null;
	}

}
