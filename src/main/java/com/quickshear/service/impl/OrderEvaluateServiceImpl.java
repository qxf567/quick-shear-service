package com.quickshear.service.impl;

import org.springframework.stereotype.Service;

import com.quickshear.service.OrderEvaluateService;

@Service
public class OrderEvaluateServiceImpl implements OrderEvaluateService {

//	@Autowired
//	private ShopMapper shopMapper;
//
//	@Override
//	public Shop findbyid(Integer id) {
//		return shopMapper.selectByPrimaryKey(id);
//	}
//
//	@Override
//	public boolean findCheckName(String name, Integer id) {
//		ShopExample example = new ShopExample();
//		ShopExample.Criteria mc = example.createCriteria();
//		mc.andShopNameEqualTo(name);
//
//		if (id != null && id > 0) {
//			mc.andShopIdNotEqualTo(id);
//		}
//
//		List<Shop> list = shopMapper.selectByExample(example);
//		if (list != null && !list.isEmpty()) {
//			return true;
//		}
//
//		return false;
//	}
//
//	@Override
//	public int save(Shop t) {
//		return shopMapper.insert(t);
//	}
//
//	@Override
//	public int update(Shop t) {
//		ShopExample example = new ShopExample();
//		ShopExample.Criteria sc = example.createCriteria();
//		sc.andShopIdEqualTo(t.getShopId());
//		sc.andMTimeEqualTo(t.getmTime());
//		t.setmTime(new Date());
//		return shopMapper.updateByExample(t, example);
//	}
//
//	@Override
//	public int deleteById(Integer id, Integer isValid) {
//		Shop record = new Shop();
//		record.setShopId(id);
//		record.setIsValid(isValid);
//		return shopMapper.updateByPrimaryKeySelective(record);
//	}
//
//	@Override
//	public void findByPage(Page<Shop> page, Shop t) {
//		ShopExample example = new ShopExample();
//		page2Exam(page, example);
//
//		if (t != null) {
//			ShopExample.Criteria sc = example.createCriteria();
//			if (t.getShopName() != null && !"".equals(t.getShopName())) {
//				sc.andShopNameLike("%" + t.getShopName() + "%");
//			}
//
//			if (t.getCityId() != null && t.getCityId() > 0) {
//				sc.andCityIdEqualTo(t.getCityId());
//			}
//			
//			if (t.getTownId() != null && t.getTownId() > 0) {
//				sc.andTownIdEqualTo(t.getTownId());
//			}
//		}
//
//		int total = shopMapper.countByExample(example);
//		List<Shop> list = shopMapper.selectByExample(example);
//
//		page.setTotalCount(total);
//		page.setResult(list);
//	}
//
//	/**
//	 * 分页对象组装分页查询条件
//	 * 
//	 * @param p
//	 *            分页对象
//	 * @param c
//	 *            查询条件
//	 */
//	private void page2Exam(Page<Shop> p, ShopExample c) {
//		if (p != null && c != null) {
//			c.setLimitEnd(p.getPageSize());
//			c.setLimitStart(p.getPageSize() * (p.getPageNo() - 1));
//
//			if (p.getOrderBy() != null && p.getOrderBy().length() < 20) {
//				c.setOrderByClause(StringEscapeUtils.escapeSql(p.getOrderBy()));
//			}
//		}
//	}
//
//	@Override
//	public List<Integer> findShopCityIds() {
//		return shopMapper.selectCityIds();
//	}
//
//	@Override
//	public List<Shop> findbycityId(Integer cityId) {
//		ShopExample example = new ShopExample();
//		ShopExample.Criteria mc = example.createCriteria();
//		mc.andCityIdEqualTo(cityId);
//		mc.andIsValidEqualTo(1);
//		return shopMapper.selectByExample(example);
//	}
//	
//	@Override
//	public List<Shop> findByOrg(Shop t){
//		ShopExample example = new ShopExample();
//		example.setOrderByClause("c_time desc");
//
//		if (t != null) {
//			ShopExample.Criteria sc = example.createCriteria();
//			if(t.getIsValid() != null){
//				sc.andIsValidEqualTo(t.getIsValid());
//			}
//			if (t.getShopName() != null && !"".equals(t.getShopName())) {
//				sc.andShopNameLike("%" + t.getShopName() + "%");
//			}
//			if (t.getCityId() != null && t.getCityId() > 0) {
//				sc.andCityIdEqualTo(t.getCityId());
//			}
//			if (t.getTownId() != null && t.getTownId() > 0) {
//				sc.andTownIdEqualTo(t.getTownId());
//			}
//		}
//
//		return shopMapper.selectByExample(example);
//	}
//	
//	@Override
//	public List<Shop> findByShopId(List<Integer> shopIds, Integer isValid){
//		ShopExample example = new ShopExample();
//		ShopExample.Criteria mc = example.createCriteria();
//		mc.andShopIdIn(shopIds) ;
//		if (null != isValid && isValid > 0) {
//			mc.andIsValidEqualTo(isValid);
//		}
//		return shopMapper.selectByExample(example);
//	}
}
