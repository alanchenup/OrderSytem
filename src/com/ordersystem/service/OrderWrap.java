/**
 * 
 */
package com.ordersystem.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ordersystem.common.model.Order;
import com.ordersystem.common.model.Page;
import com.ordersystem.common.model.User;
import com.ordersystem.common.util.JsonUtil;
import com.ordersystem.dao.DaoOrder;

/**
 * @author Administrator
 *
 */
public class OrderWrap {
	public static DaoOrder daoOrder = new DaoOrder();

	/**
	 * 载入订单
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
		Page page = new Page(pagesize, pageindex, daoOrder.getSum(null));

		List<Order> orderList = daoOrder.loadAll(page, orderby, orderasc);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", orderList);
		return JsonUtil.toJson(map);
	}

	/**
	 * 添加订单
	 * 
	 * @param session
	 * @param request
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public static String add(HttpSession session, HttpServletRequest request,
			User user) throws Exception {
		Order order = new Order();
		order.setUserId(Integer.parseInt(request.getParameter("userid")));
		
		order.setOrder_money(request.getParameter("order_money"));
		
		order.setOthers(request.getParameter("others"));
		
		Boolean flag = daoOrder.add(order);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("flag", flag);
		return JsonUtil.toJson(map);
	}

	/**
	 * 更新订单
	 * 
	 * @param session
	 * @param request
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public static String update(HttpSession session,
			HttpServletRequest request, User user) throws Exception {
		Order order = new Order();
		order.setId(Integer.parseInt(request.getParameter("id")));
		order.setUserId(Integer.parseInt(request.getParameter("userid")));		
		order.setOrder_money(request.getParameter("order_money"));		
		order.setOthers(request.getParameter("others"));		
		Boolean flag = daoOrder.update(order);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("flag", flag);
		return JsonUtil.toJson(map);
	}

	/**
	 * 删除订单
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
		Boolean flag = daoOrder.delete(id);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("flag", flag);
		return JsonUtil.toJson(map);
	}
}
