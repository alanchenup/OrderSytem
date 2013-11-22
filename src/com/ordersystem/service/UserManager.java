package com.ordersystem.service;

import java.util.List;

import com.ordersystem.common.model.Page;
import com.ordersystem.common.model.User;

public interface UserManager {
	
	public void data() throws Exception;
	
	public boolean login(User user) throws Exception ;

	public boolean regist(User user) throws Exception;

	public List<User> loadAll() throws Exception;

	public Boolean add(User user) throws Exception;

	public Boolean update(User user) throws Exception;

	public Boolean delete(int id) throws Exception;
}
