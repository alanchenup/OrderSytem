package com.ordersystem.commom.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import oracle.jdbc.pool.OracleDataSource;

import com.ordersystem.common.util.LogUtil;

public class DBHelper {
	 private static final String OPTION_FILE_NAME = "config_db";

	public final static Class clazz = DBHelper.class;
	private static OracleDataSource ds = null;
	private static String drivers;
	private static String url;
	private static String user;
	private static String password;
	private static int initial=10;
	private static int increment=2;
	private static int max=16;
	
	static {
		ResourceBundle res = ResourceBundle.getBundle(OPTION_FILE_NAME);
		drivers = res.getString("DRIVERS").trim();
		url = res.getString("URL").trim();
		user = res.getString("USER").trim();
		password = res.getString("PASSWORD").trim();		
		ConnectionBuffer.init(drivers, url, user, password, initial, increment, max);
	}
	public static ResultSet read(String tag,String sql, String[] vals)throws SQLException {
		String methodName = "read";
		LogUtil.begin(clazz, methodName);
		ConnectionProxy cont=Transaction.getConnection(tag);
		if(cont==null)return null;
		LogUtil.end(clazz, methodName);
		return cont.sql_query(sql, vals);
	}

	public static ResultSet readO(String tag,String sql, Object... p) throws SQLException{
		String methodName = "readO";
		LogUtil.begin(clazz, methodName);
		ConnectionProxy cont=Transaction.getConnection(tag);
		if(cont==null)return null;
		LogUtil.end(clazz, methodName);
		return cont.sql_query(sql, p);
	}

	public static int write(String tag,String sql, String[] vals)throws SQLException {
		String methodName = "write";
		LogUtil.begin(clazz, methodName);
		ConnectionProxy cont=Transaction.getConnection(tag);
		if(cont==null)return -1;
		LogUtil.end(clazz, methodName);
		return cont.sql_update(sql, vals);
	}

	public static int writeO(String tag,String sql, Object... p)throws SQLException {
		String methodName = "writeO";
		LogUtil.begin(clazz, methodName);
		ConnectionProxy cont=Transaction.getConnection(tag);
		if(cont==null)return -1;
		LogUtil.end(clazz, methodName);
		return cont.sql_update(sql, p);
	}

}
