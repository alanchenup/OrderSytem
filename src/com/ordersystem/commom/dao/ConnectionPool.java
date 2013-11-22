package com.ordersystem.commom.dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ordersystem.common.util.LogUtil;




public class ConnectionPool {
	@SuppressWarnings("rawtypes")private final static Class cls=(ConnectionPool.class);
	private String driver;
	private String url;
	private String name;
	private String password;
	public int initial;
	private int increment;
	private int max;
	public List<ConnectionProxy> connections = null;
	
	public ConnectionPool(String driver, String url,String name, String password, int initial,int increment, int max) {
		this.driver = driver;
		this.url = url;
		this.name = name;
		this.password = password;
		this.initial = initial;
		this.increment = increment;
		this.max = max;
		try {
			DriverManager.registerDriver((Driver) (Class.forName(this.driver).newInstance()));
		} catch (Exception e) {
        	LogUtil.warn(cls, "ConnectionPool", e);
		}
		this.connections = new ArrayList<ConnectionProxy>();
		this.createConnections(initial);
	}
	public  void freeConnection(ConnectionProxy cont) {
		synchronized(connections){
			cont.busy=false;
		}
	}
	public void closeConnection(ConnectionProxy cont){
		connections.remove(cont);
		try {
			cont.connection.close();
		} catch (SQLException e) {
        	LogUtil.warn(cls, "refreshConnection", e);
		}
	}
	public boolean refreshConnection(ConnectionProxy cont){
		synchronized(connections){
			try {
				cont.connection.close();
			} catch (SQLException e) {
	        	LogUtil.warn(cls, "refreshConnection", e);
			}
			try {
				cont.connection=newConnection();
				cont.busy=false;
			} catch (Exception e) {
	        	LogUtil.warn(cls, "refreshConnection", e);
				return false;
			}
			return true;
		}
	}
	public ConnectionProxy getConnection(){
		ConnectionProxy cont = getFreeConnection();
		while (cont == null) {
			try {
				wait(250);
			} catch (InterruptedException e) {
	        	LogUtil.warn(cls, "getConnection", e);
			}
			cont = getFreeConnection();
		}
		return cont;
	}
	private ConnectionProxy getFreeConnection(){
		ConnectionProxy cont = findFreeConnection();
		if (cont == null) {
			createConnections(increment);
			cont = findFreeConnection();
		}
		return cont;
	}
	private ConnectionProxy findFreeConnection(){
		synchronized(connections){
			for(ConnectionProxy cont:connections){
				if (!cont.busy) {
					cont.busy=true;
					return cont;
				}
			}
		}
		return null;
	}
	private void createConnections(int num){
		StringBuilder sb = new StringBuilder();
		sb.append("create_connections:").append(num).append(",now is ").append(this.connections.size())
		.append(",max is ").append(this.max);
		LogUtil.debug(cls, "createConnections",sb.toString());
		this.printConnection();
		int left=this.max-this.connections.size();
		if(left<=0){
			return;
		}
		if(num>left){
			LogUtil.warn(cls, "createConnections"+"max_connections false:"+num+"+"+this.connections.size()+">"+this.max);
			num=left;
		}
		synchronized(connections){
			for (int i = 0; i < num; i++) {
				try {
					connections.add(new ConnectionProxy(newConnection()));
				} catch (Exception e) {
		        	LogUtil.warn(cls, "openSession", e);
				}
			}
		}
	}
	private Connection newConnection(){
		Connection cont=null;
		try {
			cont = DriverManager.getConnection(url, name,password);
			if (connections.size() == 0) {
				int driver_max = cont.getMetaData().getMaxConnections();
				if (driver_max > 0 && this.max > driver_max) {
					this.max = driver_max;
				}
			}
		} catch (SQLException e) {
        	LogUtil.warn(cls, "newConnection", e);
		}
		return cont;
	}
	public void printConnection(){
		for(ConnectionProxy c:connections){
			if(!c.busy)continue;
			String time="";
			if(c.runned>0)time=(new  java.util.Date(c.runned)).toString();
			LogUtil.debug(cls, "printConnection", "create_connections:Run Times:"+c.times +" Last Fetched At:"+time+" Dao Tag is:"+c.tag);
		}
	}
}
