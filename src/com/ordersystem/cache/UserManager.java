package com.ordersystem.cache;

import java.util.List;

import com.ordersystem.common.model.User;

public interface UserManager {

	public void updateUserCache()throws Exception;//更新user缓存
	public List<User> getCacheUsers();//返回cache数据
	public User getCacheUser(User user);//从cache数据里面获取User对象
	
}
