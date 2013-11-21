package com.ordersystem.dao;

import java.sql.SQLException;
import java.util.List;

import com.ordersystem.common.model.User;

public interface UserDao {

	public boolean login(User user) throws SQLException;

	public boolean regist(User user)  throws SQLException;

	public Boolean add(User user)  throws SQLException;

	public Boolean update(User user)  throws SQLException;

	public Boolean delete(int id)  throws SQLException;

	public List<User> loadAll() throws SQLException;

}
