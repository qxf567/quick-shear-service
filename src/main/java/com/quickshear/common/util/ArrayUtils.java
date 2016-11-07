package com.quickshear.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayUtils extends org.apache.commons.lang.ArrayUtils {
	
    public static <T extends Object> boolean isNullOrEmpty(T[] array) {
        return (array == null || array.length == 0);
    }

    public static String[] trim(String[] array) {
        if (array == null) {
            return null;
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].trim();
        }
        return array;
    }

    public static String[] asArray(List<String> list) {
        if (list == null) return null;
        return list.toArray(new String[0]);
    }
    
    public static Long[] toLong(String[] array) {
		if(array == null || array.length == 0)
			return new Long[0];
		Long[] value = new Long[array.length];
		for(int i = 0; i < array.length; i++)
			value[i] = NumberUtil.parseLong(array[i], 0L);
		return value;
	}
    
    public static Integer[] toInteger(String[] array) {
		if(array == null || array.length == 0)
			return new Integer[0];
		Integer[] value = new Integer[array.length];
		for(int i = 0; i < array.length; i++)
			value[i] = NumberUtil.parseInteger(array[i], 0);
		return value;
	}
    
    public static List<Integer> toIntegerList(String[] array) {
    	if(array == null || array.length == 0){
    		return Collections.emptyList() ;
    	}
    	List<Integer> value = new ArrayList<Integer>() ;
    	for(int i = 0; i < array.length; i++)
    		value.add(NumberUtil.parseInteger(array[i], 0));
		return value;
    }
}
