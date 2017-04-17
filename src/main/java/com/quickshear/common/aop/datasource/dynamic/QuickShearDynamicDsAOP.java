package com.quickshear.common.aop.datasource.dynamic;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 数据源切换拦截器
 */
@Component
@Aspect
public class QuickShearDynamicDsAOP {

	@Pointcut("execution(* com.lashou.x.mysql.repair.mapper..*.*(..))")
	public void inMapper() {

	}

	@Pointcut("execution(* com.lashou.x.mysql.service..*.*(..))	|| " + 
	          "execution(* com.lashou.x.order.service..*.*(..)) || " + 
	          "execution(* com.lashou.x.pb.service..*.*(..))")
	public void inService() {
		
	}

	/**
	 * 切换数据源,目标对象的方法执行前执行
	 * @param jp
	 */
	@Before("inService()")
	public void beforeInvoke(JoinPoint jp) {
//		String beanName = jp.getTarget().getClass().getName();
//		String methodName = jp.getSignature().getName();
//		Object[] args = jp.getArgs();
//		if (StringUtils.isNotBlank(methodName)) {
//			String n = methodName.toLowerCase();
//			if (n.startsWith("save") || n.startsWith("update")
//					|| n.startsWith("insert") || n.startsWith("del")
//					|| n.startsWith("remove")
//					|| n.startsWith("selectbyprimarykey")
//					|| n.startsWith("findbyid") || n.startsWith("add")) {
//				QuickShearJdbcContextHolder.setCustomerType(QuickShearJdbcContextHolder.master);
//			} else if ((n.startsWith("find") && !n.startsWith("findbyid"))
//					|| n.startsWith("count")
//					|| (n.startsWith("select") && !n.startsWith("selectbyprimarykey"))) {
//				QuickShearJdbcContextHolder.setCustomerType(QuickShearJdbcContextHolder.slave);
//			} else {
//				QuickShearJdbcContextHolder.clearCustomerType();
//			}
//			//分页查询时每页最大100条
//			if (args != null) {
//				for (Object obj : args) {
//					if (obj != null && obj instanceof Page) {
//						Page p = (Page) obj;
//						if (p != null && p.getPageSize() > 100) {
//							p.setPageSize(100);
//						}
//					}
//				}
//			}
//		}
//			if(logger.isDebugEnabled()){
//				logger.debug("datasource aop 拦截 ，前置通知:class={},method={},ds={}", beanName, methodName, QuickShearJdbcContextHolder.getCustomerType());
//			}
			
			QuickShearJdbcContextHolder.setCustomerType(QuickShearJdbcContextHolder.master);
		}

	@After("inService()")
	public void afterReturnInvoke() {
		QuickShearJdbcContextHolder.clearCustomerType();
	}
}
