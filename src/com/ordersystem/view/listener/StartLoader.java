package com.ordersystem.view.listener;

import java.util.Properties;

import com.ordersystem.cache.impl.CacheManagerImpl;
import com.ordersystem.commom.dao.ConnectionBuffer;
import com.ordersystem.common.util.Cache;
import com.ordersystem.common.util.LogUtil;
import com.ordersystem.service.UserManager;
import com.ordersystem.service.impl.UserManagerImpl;




public class StartLoader {
	@SuppressWarnings("rawtypes")private final static Class cls=(StartLoader.class);
	public static void context(String context_path){
		LogUtil.debug(cls, "context", "context...........................");
       // Common.CONTEXT_PATH=context_path;
	}
	public static void database()throws Exception{
		LogUtil.debug(cls, "database", "database...........................");
        Properties prop = new Properties();
        prop.load(StartupListener.class.getClassLoader().getResourceAsStream("config.properties"));
		ConnectionBuffer.init((String) prop.getProperty("DB_DRIVER"),
				(String) prop.getProperty("DB_URL"), (String) prop.getProperty("DB_USERNAME"),
				(String) prop.getProperty("DB_PASSWORD"), Integer.parseInt((String) prop.getProperty("DB_ICONTS")), 
				Integer.parseInt((String) prop.getProperty("DB_CCONTS")), Integer.parseInt((String) prop.getProperty("DB_MCONTS")));
	}
	
	public static void instance()throws Exception{
		final String method="instance";        
		UserManagerImpl.getInstance();
		LogUtil.debug(cls, method, "user...........................");
		
	}
	public static void data() throws Exception{
		final String method="data";		
		Cache.get(UserManager.class).data();
		LogUtil.debug(cls, method, "user data...........................");		
	}
	
	public static void cache() throws Exception{
		final String method = "cache";
		CacheManagerImpl.getInstance().updateUserCache();
		LogUtil.debug(cls, method, "updateUserCache...........................");
		
	}
}
