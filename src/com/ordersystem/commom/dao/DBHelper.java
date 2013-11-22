package com.ordersystem.commom.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import oracle.jdbc.pool.OracleDataSource;

import com.ordersystem.common.util.LogUtil;

public class DBHelper {

	@SuppressWarnings("rawtypes")
	private final static Class cls=(DBHelper.class);		
	public static ResultSet read(String tag,String sql, String[] vals)throws SQLException {
		String methodName = "read";
		LogUtil.begin(cls, methodName);
		ConnectionProxy cont=Transaction.getConnection(tag);
		if(cont==null)return null;
		LogUtil.end(cls, methodName);
		return cont.sql_query(sql, vals);
	}

	public static ResultSet readO(String tag,String sql, Object... p) throws SQLException{
		String methodName = "readO";
		LogUtil.begin(cls, methodName);
		ConnectionProxy cont=Transaction.getConnection(tag);
		if(cont==null)return null;
		LogUtil.end(cls, methodName);
		return cont.sql_query(sql, p);
	}

	public static int write(String tag,String sql, String[] vals)throws SQLException {
		String methodName = "write";
		LogUtil.begin(cls, methodName);
		ConnectionProxy cont=Transaction.getConnection(tag);
		if(cont==null)return -1;
		LogUtil.end(cls, methodName);
		return cont.sql_update(sql, vals);
	}

	public static int writeO(String tag,String sql, Object... p)throws SQLException {
		String methodName = "writeO";
		LogUtil.begin(cls, methodName);
		ConnectionProxy cont=Transaction.getConnection(tag);
		if(cont==null)return -1;
		LogUtil.end(cls, methodName);
		return cont.sql_update(sql, p);
	}

}
