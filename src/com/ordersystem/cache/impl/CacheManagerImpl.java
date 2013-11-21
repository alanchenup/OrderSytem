package com.ordersystem.cache.impl;

import java.util.List;

import com.ordersystem.cache.DishManager;
import com.ordersystem.cache.UserManager;
import com.ordersystem.common.model.User;
import com.ordersystem.common.util.Cache;
import com.ordersystem.dao.UserDao;
import com.ordersystem.dao.impl.UserDaoImpl;

public class CacheManagerImpl implements UserManager,DishManager{

	static class CacheManagerImplHolder {
		static CacheManagerImpl instance = new CacheManagerImpl();
		}
	public static CacheManagerImpl getInstance(){
		return CacheManagerImplHolder.instance;
		}
	
	private UserDao userDao;
	
	private CacheManagerImpl(){
		Cache.put(UserManager.class, this);
		
		userDao = new UserDaoImpl();
	}
	private List<User> users;
	
	
	@Override
	public void updateUserCache() throws Exception {
		// TODO Auto-generated method stub
		users = userDao.loadAll();
	}

	@Override
	public List<User> getCacheUsers() {
		// TODO Auto-generated method stub
		return users;
	}

	@Override
	public User getCacheUser(User user) {
		// TODO Auto-generated method stub
		int idx = users.indexOf(user);
		if (idx > -1) {
			return users.get(idx);
		}
		return null;
	}
}
