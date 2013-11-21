package com.ordersystem.commom.dao;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.liaoyang.common.util.LogUtil;
import com.liaoyang.common.util.RandomStringUtil;


/***
 * 交易管理类
 * 
 * @author chenchang
 *
 */
public class Transaction {
	@SuppressWarnings("rawtypes")private final static Class cls=(Transaction.class);
	public enum Action{
		COMMIT,ROLLBACK
	}
	public static Map<Long,ConnectionProxy> connections;
	static{
		connections=new ConcurrentHashMap<Long,ConnectionProxy>();
	}
	/***
	 * 获取当前线程的链接<br>
	 * 1.从Map从取，存在则直接返回<br>
	 * 2.创建并放入Map中<br>
	 * 3.处理处理交易<br>
	 * @param tag 跟踪信息
	 * @return
	 */
	public static ConnectionProxy getConnection(String tag){
		Long key=Thread.currentThread().getId();
		ConnectionProxy connection=connections.get(key);
		if(connection==null){//肯定无事务
			connection=ConnectionBuffer.getConnection();
			connections.put(key, connection);
		}
		if(connection==null)return null;
		connection.before(tag);
		return connection;
	}
	public static String openSession(){
		openSession("");
		return transaction_id();
	}
	public static String transaction_id(){
		return RandomStringUtil.randomNumeric(10);
	}
	/***
	 * 打开事务<br>
	 * 1.先关闭之前的Session
	 * 2.获取链接，打开事务，如果失败，则刷新该链接，并且重新执行openSession
	 * 3.放入事务池
	 */
	public static Long openSession(String tag){
		closeSession(true);
		ConnectionProxy connection=ConnectionBuffer.getConnection();
		if(connection==null)return null;
		
		try {
			connection.transaction();
		} catch (Exception e) {
        	LogUtil.warn(cls, "openSession", e);
			if(!ConnectionBuffer.refreshConnection(connection))return null;
			return openSession(tag);
		}
		Long key=Thread.currentThread().getId();
		connections.put(key, connection);
		connection.before(tag);
		return key;
	}
	/***
	 * 释放当前线程的链接<br>
	 * 1.当前线程没使用链接，直接返回<br>
	 * 2.释放当前链接资源<br>
	 * 3.放回连接池<br>
	 * 4.退出事务池
	 */
	public static boolean closeSession(boolean success){
		Long key=Thread.currentThread().getId();
		return closeSession(key,success);
	}
	public static boolean closeSession(Long key,boolean success){
		ConnectionProxy connection=connections.get(key);
		if(connection==null)return true;
		closeConnection(connection,success);
		return connections.remove(key)!=null;
	}
	private static boolean closeConnection(ConnectionProxy connection,boolean success){
		boolean flag=true;
		try {
			connection.commit(success);
		} catch (Exception e) {//commit失败，则返回false，刷新链接
        	LogUtil.warn(cls, "closeConnection", e);
			flag=false;
			ConnectionBuffer.refreshConnection(connection);
		}
		try {
			connection.release();
		} catch (Exception e) {//释放失败，则返回true，刷新链接
        	LogUtil.warn(cls, "closeConnection", e);
			ConnectionBuffer.refreshConnection(connection);
		}
		ConnectionBuffer.freeConnection(connection);
		return flag;
	}
	
	public static class Proxy{
		private Object obj;
		private String method;
		private Object[] paras;
		
		public Proxy(Object obj,String method,Object[] paras){
			this.obj=obj;
			this.method=method;
			this.paras=paras;
		}
		public boolean execute(){
			Transaction.openSession();
			boolean flag=false;
			try{
				Method[] mths=obj.getClass().getMethods();
				for(Method mth:mths){
					if(mth.getName().equals(method)){
						flag=Boolean.parseBoolean(mth.invoke(obj, paras).toString());
						break;
					}
				}
			}
			catch(Exception e){
				LogUtil.warn(obj.getClass(), method, e);
				flag=false;
			}
			Transaction.closeSession(flag);
			return flag;
		}
	}
}
