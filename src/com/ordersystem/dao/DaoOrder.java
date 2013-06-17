/**
 * 
 */
package com.ordersystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OraclePreparedStatement;
import oracle.sql.DATE;

import com.ordersystem.common.model.Order;
import com.ordersystem.common.model.Page;

/**
 * @author Administrator
 *
 */
public class DaoOrder {
	public List<Order> loadAll(Page page,String orderby,String orderasc)throws SQLException{
		String sql = SQLHelper.page(Order.TABLE,orderby,orderasc);
		ResultSet rs = DBHelper.read( sql, new String[]{""+page.getPagesize()*page.getPageindex(),""+page.getC_pagesize()});
		List<Order> orderList = new  ArrayList<Order>();
		while(rs.next()){
			Order order = new Order();
			order.setId(rs.getInt(1));
			order.setUserId(rs.getInt(2));			    
			order.setOrder_date(rs.getDate(3));
			order.setOrder_money(rs.getString(4));
			order.setOthers(rs.getString(5));	
			orderList.add(order);		
		}
		
		return orderList;		
	}
	public int getSum(String[]conditions ) throws SQLException{
		String sqlSum=SQLHelper.row_select(Order.TABLE, new String[]{"count(id)"},conditions,null);
		ResultSet rsSum = DBHelper.read(sqlSum, null);
		int sum=0;
		while(rsSum.next()){
			sum= rsSum.getInt(1);
		}
		return sum;
	}
	public Boolean add(Order order)throws SQLException {
		// TODO Auto-generated method stub
		String sql=SQLHelper.row_insert(Order.TABLE, new String[]{"userid","order_money","order_date","others"}, null);
		OraclePreparedStatement pstmt = DBHelper.prepareStatement(sql);
		pstmt.setInt(1,order.getUserId());
		pstmt.setString(2,order.getOrder_money());
		
		pstmt.setDate(3,order.getOrder_date());
		
		pstmt.setString(4,order.getOthers());
	
		int flag=pstmt.executeUpdate();
		DBHelper.close(pstmt);
		return flag==1?true:false;
	}
	public Boolean update(Order order)throws SQLException {
		// TODO Auto-generated method stub
		String sql =SQLHelper.row_update(Order.TABLE, new String[]{"userid","order_money","order_date","others"}, new String[]{"id"}, null);
		
		OraclePreparedStatement pstmt = DBHelper.prepareStatement(sql);
		pstmt.setInt(1,order.getUserId());
		pstmt.setString(2,order.getOrder_money());
		pstmt.setDate(3,order.getOrder_date());
		pstmt.setString(4,order.getOthers());
		int flag=pstmt.executeUpdate();
	    DBHelper.close(pstmt);
		return flag==1?true:false;
	}
	public Boolean delete(int id) throws SQLException{
		// TODO Auto-generated method stub
		String sql =SQLHelper.row_delete(Order.TABLE, new String[]{"id"}, null);
		OraclePreparedStatement pstmt=DBHelper.prepareStatement(sql);
		pstmt.setInt(1, id);
		int flag=pstmt.executeUpdate();
		DBHelper.close(pstmt);
		return flag==1?true:false;
	}
	
}
