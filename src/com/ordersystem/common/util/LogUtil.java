package com.ordersystem.common.util;

import org.apache.log4j.Logger;


public class LogUtil {
	public static Logger log = null;
	public static Logger getLogger(Class clazz){
		return Logger.getLogger(clazz);
	}
	public static void begin(Class clazz,String str){
		log=getLogger(clazz);
	    log.info("in::  "+str);
	}
	public static void end(Class clazz,String str){
		log=getLogger(clazz);
	    log.info("out::  "+str);
	}
	public static void info(Class clazz,String str){
		log=getLogger(clazz);
	    log.info(str);
	}
	public static void warn(Class clazz, String str) {
		log=getLogger(clazz);
		log.warn(str);
		
	}
	public static void warn(Class clazz,String str,Exception e){
		log=getLogger(clazz);
		log.warn("method:"+str+"  exception:"+e.getMessage());
	}
	public static void error(Class clazz,String str){
		log=getLogger(clazz);
		log.error(str);
	}
	public static void debug(Class clazz, String method, String str) {
		// TODO Auto-generated method stub
		log=getLogger(clazz);
		log.debug("method:"+method+" "+str);
	}
	

	
}
