package com.quickshear.common.aop.datasource.dynamic;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 */
public class QuickShearDynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return QuickShearJdbcContextHolder.getCustomerType();
	}
}
