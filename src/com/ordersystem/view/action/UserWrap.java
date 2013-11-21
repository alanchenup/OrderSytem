package com.ordersystem.view.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ordersystem.common.model.User;
import com.ordersystem.common.util.Cache;
import com.ordersystem.common.util.JsonUtil;
import com.ordersystem.common.util.StringUtils;
import com.ordersystem.dao.DaoUser;
import com.ordersystem.service.UserManager;

public class UserWrap {
	@SuppressWarnings("rawtypes")private final static Class cls =(UserWrap.class);
	public static DaoUser daoUser = new DaoUser();
	
	public static String login(HttpSession session, HttpServletRequest request,User user) throws Exception {
		
		String user_name=request.getParameter("user_name");
		String user_password=StringUtils.encodePassword(request.getParameter("user_password"));
		user.setUser_name(user_name);
		user.setUser_password(user_password);
		user.setUser_type(request.getParameter("user_type"));
		boolean flag=Cache.get(UserManager.class).login(user);
		if(flag){
			session.setAttribute("user", user);
		}
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("flag", flag);
		return JsonUtil.toJson(map);
	}
	public static String regist(HttpSession session, HttpServletRequest request,User user) throws Exception{
		String user_name=request.getParameter("user_name");
		String user_password=StringUtils.encodePassword(request.getParameter("user_password"));
		String user_email=request.getParameter("user_email");
		String user_niname=request.getParameter("user_niname");
		String user_type=request.getParameter("user_type");
		user.setUser_name(user_name);
		user.setUser_niname(user_niname);
		user.setUser_password(user_password);
		user.setUser_email(user_email);
		user.setUser_type(user_type);
		boolean flag=Cache.get(UserManager.class).regist(user);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("flag", flag);
		return JsonUtil.toJson(map);
	}
	
	
	/**
	 * 载入用户
	 * 
	 * @param session
	 * @param request
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public static String loadAll(HttpSession session,
			HttpServletRequest request, User user) throws Exception {
		return JsonUtil.toJson(Cache.get(com.ordersystem.cache.UserManager.class).getCacheUsers());		
	}

	/**
	 * 添加用户到数据库
	 * 
	 * @param session
	 * @param request
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public static String add(HttpSession session, HttpServletRequest request,
			User user) throws Exception {
		
		user.setUser_name(request.getParameter("user_name"));
		
		user.setUser_niname(request.getParameter("user_niname"));
		
		user.setUser_password(request.getParameter("user_password"));
		user.setUser_email(request.getParameter("user_email"));
		Boolean flag = Cache.get(UserManager.class).add(user);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("flag", flag);
		return JsonUtil.toJson(map);
	}

	/**
	 * 更新用户
	 * 
	 * @param session
	 * @param request
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public static String update(HttpSession session,
			HttpServletRequest request, User user) throws Exception {
		
		user.setId(Integer.parseInt(request.getParameter("id")));
		user.setUser_name(request.getParameter("user_name"));
		
		user.setUser_niname(request.getParameter("user_niname"));
		user.setUser_password(request.getParameter("user_password"));
		user.setUser_email(request.getParameter("user_email"));
		
		
		//获取image的路径 
		String picuri=request.getParameter("user_pic");
		
		
		
		Boolean flag = Cache.get(UserManager.class).update(user);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("flag", flag);
		return JsonUtil.toJson(map);
	}

	/**
	 * 删除用户
	 * 
	 * @param session
	 * @param request
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public static String delete(HttpSession session,
			HttpServletRequest request, User user) throws Exception {

		int id = Integer.parseInt(request.getParameter("id"));
		Boolean flag = Cache.get(UserManager.class).delete(id);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("flag", flag);
		return JsonUtil.toJson(map);
	}
}
