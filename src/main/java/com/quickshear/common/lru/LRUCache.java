package com.quickshear.common.lru;

import java.util.Map;

public class LRUCache {

    private int capacity;
    private Map<String, String> cache;

    public LRUCache(int cap) {
        this.capacity = cap;
        this.cache = new java.util.LinkedHashMap<String, String> (capacity, 0.75f, true) {

	    /**
	     * 
	     */
	    private static final long serialVersionUID = 1L;

	    // 定义put后的移除规则，大于容量就删除eldest
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > capacity;
            }
        };
    }

    public String get(String key) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        } else
            return "";
    }

    public void set(String key, String value) {
        cache.put(key, value);
    }
}