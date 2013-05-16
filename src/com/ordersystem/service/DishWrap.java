/**
 * 
 */
package com.ordersystem.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ordersystem.common.model.Dish;
import com.ordersystem.common.model.Page;
import com.ordersystem.common.model.User;
import com.ordersystem.common.util.JsonUtil;
import com.ordersystem.dao.DaoDish;

/**
 * @author Administrator
 * 
 */
public class DishWrap {
	public static DaoDish daoDish = new DaoDish();

	/**
	 * 载入菜品
	 * 
	 * @param session
	 * @param request
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public static String loadAll(HttpSession session,
			HttpServletRequest request, User user) throws Exception {
		int pagesize = Integer.parseInt(request.getParameter("pagesize"));
		int pageindex = Integer.parseInt(request.getParameter("pageindex"));
		// 分页大小，分页下标，排序列，排序方式
		String orderby = request.getParameter("orderby");
		String orderasc = request.getParameter("orderasc");
		// 搜索关键字
		String keyword = request.getParameter("keyword");
		Page page = new Page(pagesize, pageindex, daoDish.getSum(null));

		List<Dish> dishList = daoDish.loadAll(page, orderby, orderasc);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", dishList);
		return JsonUtil.toJson(map);
	}

	/**
	 * 添加是的菜品到数据库
	 * 
	 * @param session
	 * @param request
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public static String add(HttpSession session, HttpServletRequest request,
			User user) throws Exception {
		Dish dish = new Dish();
		dish.setDish_name(request.getParameter("dish_name"));
		dish.setDish_pic(request.getParameter("dish_pic"));
		dish.setDish_content(request.getParameter("dish_content"));
		dish.setDish_price(request.getParameter("dish_price"));
		dish.setDish_c_price(request.getParameter("dish_c_price"));
		Boolean flag = daoDish.add(dish);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("flag", flag);
		return JsonUtil.toJson(map);
	}

	/**
	 * 更新菜品
	 * 
	 * @param session
	 * @param request
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public static String update(HttpSession session,
			HttpServletRequest request, User user) throws Exception {
		Dish dish = new Dish();
		dish.setId(Integer.parseInt(request.getParameter("id")));
		dish.setDish_name(request.getParameter("dish_name"));
		dish.setDish_pic(request.getParameter("dish_pic"));
		dish.setDish_content(request.getParameter("dish_content"));
		dish.setDish_price(request.getParameter("dish_price"));
		dish.setDish_c_price(request.getParameter("dish_c_price"));
		Boolean flag = daoDish.update(dish);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("flag", flag);
		return JsonUtil.toJson(map);
	}

	/**
	 * 删除菜品
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
		Boolean flag = daoDish.delete(id);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("flag", flag);
		return JsonUtil.toJson(map);
	}
}
