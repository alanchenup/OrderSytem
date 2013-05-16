package com.ordersystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.pool.OracleDataSource;

public class DBHelper {
	 private static final String OPTION_FILE_NAME = "config_db"; 
	    private static OracleDataSource ds=null;
	    private static String drivers; 
	    private static String url; 
	    private static String user; 
	    private static String password; 
	    static { 
	        ResourceBundle res = ResourceBundle.getBundle(OPTION_FILE_NAME); 
	        drivers = res.getString("DRIVERS").trim(); 
	        url = res.getString("URL").trim(); 
	        user = res.getString("USER").trim(); 
	        password = res.getString("PASSWORD").trim(); 
	    } 
	    public static Connection getConnection() throws SQLException { 
	        Connection conn = null; 
	        try { 
	        	
	        	ds=new OracleDataSource();
				ds.setURL(url);
				conn=ds.getConnection(user, password);
//	            Class.forName(drivers).newInstance(); 
//	            conn = DriverManager.getConnection(url, user, password); 
	        } catch (Exception e) { 
	            e.printStackTrace(); 
	        } 
	        if (conn == null) { 
	            throw new SQLException("DBUtils: Cannot get connection."); 
	        } 
	        return conn; 
	    } 
	    
	    public static void close(Connection conn) { 
	        if (conn == null) 
	            return; 
	        try { 
	            conn.close(); 
	        } catch (SQLException e) { 
	            System.out.println("DBUtils: Cannot close connection."); 
	        } 
	    } 
	    public static void close(Statement stmt) { 
	        try { 
	            if (stmt != null) { 
	                stmt.close(); 
	            } 
	        } catch (SQLException e) { 
	            System.out.println("DBUtils: Cannot close statement."); 
	        } 
	    } 
	    public static void close(ResultSet rs) { 
	        try { 
	            if (rs != null) { 
	                rs.close(); 
	            } 
	        } catch (SQLException e) { 
	            System.out.println("DBUtils: Cannot close resultset."); 
	        } 
	    } 
	/***
	 * 创建
	 * @param sql
	 * @return
	 */
	public static OraclePreparedStatement prepareStatement(String sql) throws SQLException{
		Connection connection = getConnection();
		OraclePreparedStatement pstmt= (OraclePreparedStatement)connection.prepareStatement(sql);		
		return pstmt;
	}
	public static ResultSet read(String query,String[] vals) throws SQLException{
	OraclePreparedStatement	pstmt= prepareStatement(query);
		if(vals!=null&&vals.length>0){
			for(int i=0;i<vals.length;i++){
				pstmt.setString(i+1, vals[i]);
			}
		}
		ResultSet rs= pstmt.executeQuery();
		
		return rs;
	}
	public static int write(String query,String[] vals) throws SQLException{
		OraclePreparedStatement	pstmt= prepareStatement(query);
		if(vals!=null&&vals.length>0){
			for(int i=0;i<vals.length;i++){
				pstmt.setString(i+1, vals[i]);
			}
		}
		return pstmt.executeUpdate();
	}
	public static int write(String query,int id)throws SQLException{
		OraclePreparedStatement	pstmt= prepareStatement(query);
		pstmt.setInt(1, id);
		return pstmt.executeUpdate();
	}
	

}
