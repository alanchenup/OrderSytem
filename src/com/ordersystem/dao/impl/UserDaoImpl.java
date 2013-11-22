package com.ordersystem.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import com.ordersystem.commom.dao.DBHelper;
import com.ordersystem.commom.dao.SQLHelper;
import com.ordersystem.common.model.User;
import com.ordersystem.dao.UserDao;

public class UserDaoImpl implements UserDao{
	@Override
	public List<User> loadAll() throws SQLException{
		String method="loadAll";
		String sql = SQLHelper.row_select(User.TABLE,new String[]{"id","user_name","user_niname","user_password","user_email"},null,null);
		ResultSet rs = DBHelper.read( method,sql, null);
		List<User> userList = new  ArrayList<User>();
		while(rs.next()){
			User user = new User();
			user.setId(rs.getInt(1));
			user.setUser_name(rs.getString(2));	
		    
			user.setUser_niname(rs.getString(3));
			user.setUser_password(rs.getString(4));
					
			user.setUser_email(rs.getString(5));			
			userList.add(user);		
		}
		
		return userList;		
	}
	
	public int getSum(String[]conditions ) throws SQLException{
		String method ="getSum";
		String sqlSum=SQLHelper.row_select(User.TABLE, new String[]{"count(id)"},conditions,null);
		ResultSet rsSum = DBHelper.read(method,sqlSum, null);
		int sum=0;
		while(rsSum.next()){
			sum= rsSum.getInt(1);
		}
		return sum;
	}
	@Override
	public Boolean add(User user)throws SQLException {
		// TODO Auto-generated method stub
		String method="add";
		String sql=SQLHelper.row_insert(User.TABLE, new String[]{"user_name","user_niname","user_pic","user_price","user_c_price","m_mate","v_mate"}, null);
		int flag=DBHelper.writeO(method, sql, new String[]{user.getUser_name(),user.getUser_niname(),user.getUser_password(),user.getUser_email()});
		return flag==1?true:false;
	}
	@Override
	public Boolean update(User user)throws SQLException {
		// TODO Auto-generated method stub
		String method="update";
		String sql =SQLHelper.row_update(User.TABLE, new String[]{"user_name","user_content","user_pic","user_price","user_c_price","m_mate","v_mate"}, new String[]{"id"}, null);		
		int flag=DBHelper.writeO(method, sql, new String []{});
	   
		return flag==1?true:false;
	}
	@Override
	public Boolean delete(int id) throws SQLException{
		// TODO Auto-generated method stub
		String method="delete";
		String sql =SQLHelper.row_delete(User.TABLE, new String[]{"id"}, null);
	
	
		int flag=DBHelper.writeO(method, sql, id);

		return flag==1?true:false;
	}
	@Override
	public boolean login(User user) throws SQLException{
		// TODO Auto-generated method stub
		String method="login";
		String sql=SQLHelper.row_select(User.TABLE, new String[]{"id","user_niname","user_email"}, new String[]{"user_name","user_password","user_type"}, null);
		ResultSet rs=DBHelper.read(method,sql, new String[]{user.getUser_name(),user.getUser_password(),user.getUser_type()});
		if(rs.next()){
			user.setId(rs.getInt(1));
			user.setUser_niname(rs.getString(2));
			user.setUser_email(rs.getString(3));
		}else {
			return false;
		}		
		return true;
	}
	@Override
	public boolean regist(User user) throws SQLException{
		// TODO Auto-generated method stub
		String method="regist";
		String sql=SQLHelper.row_insert(User.TABLE, new String[]{"user_name","user_niname","user_password","user_email","user_type"}, null);
		int flag=DBHelper.write(method,sql, new String[]{user.getUser_name(),user.getUser_niname(),user.getUser_password(),user.getUser_email(),user.getUser_type()});
		return flag==1?true:false;
	}
	
}
