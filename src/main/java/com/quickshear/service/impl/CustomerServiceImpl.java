package com.quickshear.service.impl;

import org.springframework.stereotype.Service;

import com.quickshear.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

//  @Autowired
//  private CustomerMapper customersMapper;
//
//  @Override
//  public Customer findById(Integer id) {
//    if (id == null) {
//      return null;
//    }
//    return customersMapper.selectByPrimaryKey(id);
//  }
//
//  @Override
//  public int save(Customer t) {
//    if (t == null)
//      return 0;
//    return customersMapper.insertSelective(t);
//  }
//
//  @Override
//  public int update(Customer t) {
//    if (t == null)
//      return 0;
//    return customersMapper.updateByPrimaryKeySelective(t);
//  }
//
//  @Override
//  public int deleteById(Integer id) {
//    if (id == null)
//      return 0;
//    return customersMapper.deleteByPrimaryKey(id);
//  }
//
//  @Override
//  public Customer findByPhone(String phone) {
//    if (StringUtils.isBlank(phone)) {
//      return null;
//    }
//    CustomerExample example = new CustomerExample();
//    Criteria criteria = example.createCriteria();
//    criteria.andPhoneNumberEqualTo(phone);
//    List<Customer> r = customersMapper.selectByExample(example);
//    if (r == null || r.size() < 1) {
//      return null;
//    }
//    return r.get(0);
//  }
//
//  @Override
//  public Customer findByWechatOpenId(String openId) {
//    if (StringUtils.isBlank(openId)) {
//      return null;
//    }
//    CustomerExample example = new CustomerExample();
//    example.createCriteria().andOpenIdEqualTo(openId);
//    List<Customer> list = customersMapper.selectByExample(example);
//    if (list != null && list.size() > 0) {
//      return list.get(0);
//    }
//    return null;
//  }
//
//  @Override
//  public int bindWechat(Customer customer) {
//    if (customer == null || customer.getId() == null || StringUtils.isBlank(customer.getOpenId()))
//      return 0;
//    Customer newObj = new Customer();
//    newObj.setId(customer.getId());
//    newObj.setOpenId(customer.getOpenId());
//    newObj.setmTime(Calendar.getInstance().getTime());
//
//    CustomerExample example = new CustomerExample();
//    example.createCriteria().andIdEqualTo(customer.getId());
//    return customersMapper.updateByExampleSelective(newObj, example);
//  }
//
//  @Override
//  public int unBindWechat(Customer customer) {
//    if (customer == null || customer.getId() == null)
//      return 0;
//
//    Customer newObj = new Customer();
//    newObj.setId(customer.getId());
//    newObj.setOpenId("");
//    newObj.setmTime(Calendar.getInstance().getTime());
//
//    CustomerExample example = new CustomerExample();
//    example.createCriteria().andIdEqualTo(customer.getId());
//    return customersMapper.updateByExampleSelective(newObj, example);
//  }

}
