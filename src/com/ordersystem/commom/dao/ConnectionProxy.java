package com.ordersystem.commom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liaoyang.common.util.LogUtil;

/***
 * 数据库链接装饰类
 * @author chenchang
 *
 */
public class ConnectionProxy {
	@SuppressWarnings("rawtypes")private final static Class cls=(ConnectionProxy.class);
	public Connection connection;
	public List<Statement> statements;
	public Map<String,PreparedStatement> preparedstatements;
	public List<ResultSet> resultsets;
	
	/***
	 * 当前使用备注
	 */
	public String tag;
	/***
	 * 创建时间
	 */
	public long created;
	/***
	 * 最近调用时间
	 */
	public long runned;
	/***
	 * 被使用次数
	 */
	public int times;
	/***
	 * 是否自动提交
	 */
	public boolean auto;
	/***
	 * 是否处于忙碌状态
	 */
	public boolean busy;
	
	public ConnectionProxy(Connection connection) {
		this.connection = connection;
		this.statements=new ArrayList<Statement>();
		this.preparedstatements=new HashMap<String,PreparedStatement>();
		this.resultsets=new ArrayList<ResultSet>();
		this.reset();
	}
	private void reset(){
		this.auto=true;
		this.tag="";
		this.runned=0;
	}
	/***
	 * 创建
	 * @return
	 */
	public Statement createStatement() throws SQLException{
		Statement stmt= connection.createStatement();
		statements.add(stmt);
		return stmt;
	}
	/***
	 * 创建
	 * @param sql
	 * @return
	 */
	public PreparedStatement prepareStatement(String sql) throws SQLException{
		if(preparedstatements.containsKey(sql))return preparedstatements.get(sql);
		PreparedStatement stmt= connection.prepareStatement(sql);
		preparedstatements.put(sql,stmt);
		return stmt;
	}
	public ResultSet sql_query(String query,String[] vals) throws SQLException{
		PreparedStatement stmt = this.prepareStatement(query);
		if(vals!=null&&vals.length>0){
			for(int i=0;i<vals.length;i++){
				stmt.setString(i+1, vals[i]);
			}
		}
		ResultSet rs= stmt.executeQuery();
		resultsets.add(rs);
		return rs;
	}
	public ResultSet sql_query(String query, Object... p) throws SQLException{
		PreparedStatement stmt = this.prepareStatement(query);		
		if(p!=null){
		for (int i = 0; i < p.length; i++) {
			stmt.setObject(i + 1, p[i]);
		}
		}
		ResultSet rs= stmt.executeQuery();
		resultsets.add(rs);
		return rs;
	}
	public int sql_update(String query,String[] vals) throws SQLException{
		PreparedStatement stmt = this.prepareStatement(query);
		if(vals!=null&&vals.length>0){
			for(int i=0;i<vals.length;i++){
				stmt.setString(i+1, vals[i]);
			}
		}
		return stmt.executeUpdate();
	}
	public int sql_update(String query,Object... p) throws SQLException{
		PreparedStatement stmt = this.prepareStatement(query);
		if(p!=null){
			for (int i = 0; i < p.length; i++) {
				stmt.setObject(i + 1, p[i]);
			}
		}
		return stmt.executeUpdate();
	}
	
	/***
	 * 打开事务
	 * @return
	 */
	public boolean transaction() throws SQLException{
		connection.setAutoCommit(false);
		this.auto=false;
		return true;
	}
	/***
	 * 每次调用前调用<br>
	 * 粒度为每个DAO方法<br>
	 * 登记memo，用于整体监控
	 * @param memo
	 * @return
	 */
	public void before(String tag){
		this.tag=tag;
		this.runned=new Date().getTime();
		this.times++;
	}
	/***
	 * 每次调用后调用<br>
	 * 可以不调，主要为了调试运行时间，用memo进行跟踪
	 */
	public void end(){
		LogUtil.debug(cls, "end", this.tag+":"+(new Date().getTime()-this.runned));
	}
	public void commit(boolean success) throws SQLException{
		if(!this.auto){
			if(success)connection.commit();
			else {connection.rollback();}
		}
		connection.setAutoCommit(true);
	}
	/***
	 * 释放事务
	 */
	public void release() throws SQLException{
		for(PreparedStatement stmt:preparedstatements.values()){
			stmt.close();
			stmt=null;
		}
		for(Statement stmt:statements){
			stmt.close();
			stmt=null;
		}
		for(ResultSet rs:resultsets){
			rs.close();
			rs=null;
		}
		this.preparedstatements.clear();
		this.statements.clear();
		this.resultsets.clear();
		this.reset();
	}
}
