package com.ordersystem.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.ordersystem.common.model.User;
import com.ordersystem.common.util.Cache;
import com.ordersystem.common.util.LogUtil;

@Path("/rest")
public class UserRest {
	@SuppressWarnings("rawtypes")
	private final static Class cls =(UserRest.class);
	@POST
	@Path("/users")
	@Produces({ "application/json", "application/jsonzip" })
	@Consumes({ "application/json", "application/jsonzip" })
	public List<User> users(){
		final String method = "users";	
		LogUtil.info(cls, method);
		return Cache.get(com.ordersystem.cache.UserManager.class).getCacheUsers();
	}
}
