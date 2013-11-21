package com.ordersystem.service;

import java.util.List;

import com.ordersystem.common.model.Page;
import com.ordersystem.common.model.User;

public interface UserManager {
	public boolean login(User user);

	public boolean regist(User user);

	public List<User> loadAll(Page page, String orderby, String orderasc);

	public Boolean add(User user);

	public Boolean update(User user);

	public Boolean delete(int id);
}
