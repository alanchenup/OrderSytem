package com.ordersystem.common.util;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    public static Map<String,Object> caches=new HashMap<String,Object>();
	
    private Cache() {
    }
    public static <T> void put(Class<T> cls,Object  value){
    	caches.put(cls.getName(), value);
    }
    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> cls){
    	return (T)caches.get(cls.getName());
    }
    @SuppressWarnings("unchecked")
    public static <T> T get(String cls){
    	return (T)caches.get(cls);
    }
}
