package com.ordersystem.service.impl;

import java.util.List;
import java.util.Map;

import com.ordersystem.common.model.Page;
import com.ordersystem.common.model.User;
import com.ordersystem.common.util.Cache;
import com.ordersystem.dao.UserDao;
import com.ordersystem.dao.impl.UserDaoImpl;
import com.ordersystem.service.UserManager;

public class UserManagerImpl implements UserManager{
	@SuppressWarnings("rawtypes")private final static Class cls =(UserManagerImpl.class);
	private UserDao userDao;
	private Map<String,User> sessions;
	
	
	static class UserManagerImplHolder {
		static UserManagerImpl instance = new UserManagerImpl();
	}

	public static UserManagerImpl getInstance() {
		return UserManagerImplHolder.instance;
	}
	
	private UserManagerImpl() {
		Cache.put(UserManager.class, this);
		userDao = new UserDaoImpl();
	}
	
	@Override
	public void data() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean login(User user)throws Exception {
		// TODO Auto-generated method stub
		final String method ="login";		
		return userDao.login(user);
	}

	@Override
	public boolean regist(User user)throws Exception {
		// TODO Auto-generated method stub
		return userDao.regist(user) ;
	}

	@Override
	public List<User> loadAll()throws Exception {
		// TODO Auto-generated method stub
		return userDao.loadAll();
	}

	@Override
	public Boolean add(User user) throws Exception{
		// TODO Auto-generated method stub
		return userDao.add(user);
	}

	@Override
	public Boolean update(User user)throws Exception {
		// TODO Auto-generated method stub
		return userDao.update(user);
	}

	@Override
	public Boolean delete(int id) throws Exception{
		// TODO Auto-generated method stub
		return userDao.delete(id);
	}


	
}
