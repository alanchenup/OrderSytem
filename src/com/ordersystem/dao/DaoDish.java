package com.ordersystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ordersystem.common.model.Dish;
import com.ordersystem.common.model.Page;

public class DaoDish {
	
	public List<Dish> loadAll(Page page,String orderby,String orderasc)throws SQLException{
//		String sql = SQLHelper.row_select(Dish.TABLE,new String[]{"dish_id","dish_name","dish_content","dish_pic","dish_price","dish_c_price","m_mate","v_mate","dish_sales"},null, null);
		String sql = SQLHelper.page(Dish.TABLE,orderby,orderasc);
		
//		ResultSet rs = DB.read( sql, new String[]{account,user_name,password});
		ResultSet rs = DBHelper.read( sql, new String[]{""+page.getPagesize()*page.getPageindex(),""+page.getC_pagesize()});
//		ResultSet rs = DBHelper.read(sql, null);
		List<Dish> dishList = new  ArrayList<Dish>();
		while(rs.next()){
			Dish dish = new Dish();
			dish.setId(rs.getInt(1));
			dish.setDish_name(rs.getString(2));	
		    dish.setDish_pic(rs.getString(3));
			dish.setDish_price(rs.getString(4));
			dish.setDish_c_price(rs.getString(5));
			dish.setM_mate(rs.getInt(6));
			dish.setV_mate(rs.getInt(7));			
			dish.setDish_sales(rs.getString(8));
			dish.setDish_content(rs.getString(9));
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
		int flag=DBHelper.write(sql, new String[]{dish.getDish_name(),dish.getDish_content(),dish.getDish_pic(),dish.getDish_price(),dish.getDish_c_price(),""+dish.getM_mate(),""+dish.getV_mate()});
		return flag==1?true:false;
	}
	public Boolean update(Dish dish)throws SQLException {
		// TODO Auto-generated method stub
		String sql =SQLHelper.row_update(Dish.TABLE, new String[]{"dish_name","dish_content","dish_pic","dish_price","dish_c_price","m_mate","v_mate"}, new String[]{"id"}, null);
		int flag=DBHelper.write(sql, new String[]{dish.getDish_name(),dish.getDish_content(),dish.getDish_pic(),dish.getDish_price(),dish.getDish_c_price(),""+dish.getM_mate(),""+dish.getV_mate(),""+dish.getId()});		
		return flag==1?true:false;
	}
	public Boolean delete(int id) throws SQLException{
		// TODO Auto-generated method stub
		String sql =SQLHelper.row_delete(Dish.TABLE, new String[]{"id"}, null);
		int flag=DBHelper.write(sql, id);
		return flag==1?true:false;
	}
	
	
	
	
	

}
