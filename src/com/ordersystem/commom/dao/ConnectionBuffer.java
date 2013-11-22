package com.ordersystem.commom.dao;

import com.ordersystem.common.util.LogUtil;


/***
 * 数据库链接池类<br>
 * 1.启动时加载<br>
 * 2.动态递增<br>
 * 3.自我监控<br>
 * @author chenchang
 *
 */
public class ConnectionBuffer {
	
	public static ConnectionPool pool;
	public static Class cls= ConnectionBuffer.class;
	public static void init(String driver, String url,String name, String password, int initial,int increment, int max) {
		pool=new ConnectionPool(driver, url,name, password, initial,increment, max);
		new Thread(new ConnectionHold()).start();
	}
	
	/***
	 * 维护链接池的线程
	 * 1.5小时重连一次，以防过期
	 * 2.1小时判断一次，求出increment，求出空闲数，如果空闲数大于increment，则关闭increment/2，需要increment/2要大于1
	 * @author Administrator
	 *
	 */
	static class ConnectionHold implements Runnable{
		@Override
		public void run() {
			while(true){
				try{
					Thread.sleep(1*60*60*1000);
					
					int increment =pool.connections.size()-pool.initial;
					int available=0;
					synchronized(pool.connections){
						for(ConnectionProxy cont:pool.connections){
							if (!cont.busy) {
								available++;
							}
						}
					}
					if(available>increment){
						increment=increment/2;
						if(increment>1){
							synchronized(pool.connections){
								for(ConnectionProxy cont:pool.connections){
									if (!cont.busy) {
										pool.closeConnection(cont);
									}
								}
							}
						}
					}
					
					Thread.sleep(4*60*60*1000);
					synchronized(pool.connections){
						for(ConnectionProxy cont:pool.connections){
							if (!cont.busy) {
								cont.busy=true;
								cont.sql_query("select 1 ",null);
								cont.release();
								cont.busy=false;
							}
						}
					}
				}
				catch(Exception e){
					LogUtil.error(cls, "run:"+e.getMessage());
				}
			}
		}
	}
	
	/***
	 * 获取一个空闲链接
	 * @return
	 */
	public static ConnectionProxy getConnection(){
		return pool.getConnection();
	}
	/***
	 * 释放一个链接
	 * @param cont
	 */
	public static void freeConnection(ConnectionProxy cont){
		pool.freeConnection(cont);
	}
	/***
	 * 计算链接数量
	 * @param all =ture 全部  =false 空闲
	 * @return
	 */
	public static void printConnection(){
		pool.printConnection();
	}
	/***
	 * 关闭一个脏链接,新建一个新链接
	 * @param cont
	 */
	public static boolean refreshConnection(ConnectionProxy cont){
		return pool.refreshConnection(cont);
	}
}
