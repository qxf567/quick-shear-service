package com.quickshear.common.util;

public class PersentLike {
	/**
	 * 转义%
	 * 
	 * @param str
	 * @return
	 */
	public static String makeUpPersentLike(String str) {
		int r = str.indexOf("%");
		if (r >= 0) {
			str = str.replaceAll("%", "/%");
			str = "%" + str + "%" + " ESCAPE '/' ";
		} else {
			str = "%" + str + "%";
		}
		return str;
	}
}
