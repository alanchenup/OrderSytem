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
	public boolean login(User user) {
		// TODO Auto-generated method stub
		final String method ="login";		
		return userDao.login(user);
	}

	@Override
	public boolean regist(User user) {
		// TODO Auto-generated method stub
		return userDao.regist(user) ;
	}

	@Override
	public List<User> loadAll(Page page, String orderby, String orderasc) {
		// TODO Auto-generated method stub
		return userDao.loadAll( page,  orderby,  orderasc);
	}

	@Override
	public Boolean add(User user) {
		// TODO Auto-generated method stub
		return userDao.add(user);
	}

	@Override
	public Boolean update(User user) {
		// TODO Auto-generated method stub
		return userDao.update(user);
	}

	@Override
	public Boolean delete(int id) {
		// TODO Auto-generated method stub
		return userDao.delete(id);
	}
	
}
