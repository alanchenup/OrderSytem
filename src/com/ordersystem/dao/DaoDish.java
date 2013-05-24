package com.ordersystem.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OraclePreparedStatement;

import com.ordersystem.common.model.Dish;
import com.ordersystem.common.model.Page;

public class DaoDish {
	
	public List<Dish> loadAll(Page page,String orderby,String orderasc)throws SQLException{
		String sql = SQLHelper.page(Dish.TABLE,orderby,orderasc);
		ResultSet rs = DBHelper.read( sql, new String[]{""+page.getPagesize()*page.getPageindex(),""+page.getC_pagesize()});
		List<Dish> dishList = new  ArrayList<Dish>();
		while(rs.next()){
			Dish dish = new Dish();
			dish.setId(rs.getInt(1));
			dish.setDish_name(rs.getString(2));	
		    
			dish.setDish_price(rs.getString(3));
			dish.setDish_c_price(rs.getString(4));
			dish.setM_mate(rs.getInt(5));
			dish.setV_mate(rs.getInt(6));			
			dish.setDish_sales(rs.getString(7));
			dish.setDish_content(rs.getString(8));
			dish.setDish_pic(rs.getInt(9));
			dishList.add(dish);		
		}
		
		return dishList;		
	}
	public int getSum(String[]conditions ) throws SQLException{
		String sqlSum=SQLHelper.row_select(Dish.TABLE, new String[]{"count(id)"},conditions,null);
		ResultSet rsSum = DBHelper.read(sqlSum, null);
		int sum=0;
		while(rsSum.next()){
			sum= rsSum.getInt(1);
		}
		return sum;
	}
	public Boolean add(Dish dish)throws SQLException {
		// TODO Auto-generated method stub
		String sql=SQLHelper.row_insert(Dish.TABLE, new String[]{"dish_name","dish_content","dish_pic","dish_price","dish_c_price","m_mate","v_mate"}, null);
		OraclePreparedStatement pstmt = DBHelper.prepareStatement(sql);
		pstmt.setString(1,dish.getDish_name());
		pstmt.setString(2,dish.getDish_content());
		pstmt.setInt(3,dish.getDish_pic());
		pstmt.setString(4,dish.getDish_price());
		pstmt.setString(5,dish.getDish_c_price());
		pstmt.setInt(6,dish.getM_mate());
		pstmt.setInt(7,dish.getV_mate());
		
	
		int flag=pstmt.executeUpdate();
		DBHelper.close(pstmt);
		return flag==1?true:false;
	}
	public Boolean update(Dish dish)throws SQLException {
		// TODO Auto-generated method stub
		String sql =SQLHelper.row_update(Dish.TABLE, new String[]{"dish_name","dish_content","dish_pic","dish_price","dish_c_price","m_mate","v_mate"}, new String[]{"id"}, null);
		
		OraclePreparedStatement pstmt = DBHelper.prepareStatement(sql);
		pstmt.setString(1,dish.getDish_name());
		pstmt.setString(2,dish.getDish_content());
		pstmt.setInt(3,dish.getDish_pic());
		pstmt.setString(4,dish.getDish_price());
		pstmt.setString(5,dish.getDish_c_price());
		pstmt.setInt(6,dish.getM_mate());
		pstmt.setInt(7,dish.getV_mate());
		pstmt.setInt(8, dish.getId());
		int flag=pstmt.executeUpdate();
	    DBHelper.close(pstmt);
		return flag==1?true:false;
	}
	public Boolean delete(int id) throws SQLException{
		// TODO Auto-generated method stub
		String sql =SQLHelper.row_delete(Dish.TABLE, new String[]{"id"}, null);
		OraclePreparedStatement pstmt=DBHelper.prepareStatement(sql);
		pstmt.setInt(1, id);
		int flag=pstmt.executeUpdate();
		DBHelper.close(pstmt);
		return flag==1?true:false;
	}
	
	
	
	
	

}
