/**
 * 
 */
package com.ordersystem.view.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ordersystem.common.model.Material;
import com.ordersystem.common.model.Material;
import com.ordersystem.common.model.Page;
import com.ordersystem.common.model.User;
import com.ordersystem.common.util.JsonUtil;
import com.ordersystem.dao.DaoMaterial;


/**
 * @author Administrator
 *
 */
public class MaterialWrap {
	public static DaoMaterial dao= new DaoMaterial();

	/**
	 * 载入食材
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
		Page page = new Page(pagesize, pageindex, dao.getSum(null));

		List<Material> List = dao.loadAll(page, orderby, orderasc);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", List);
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
		Material mate = new Material();
		mate.setMate_name(request.getParameter("mate_name"));
		mate.setMate_pic(Integer.parseInt(request.getParameter("mate_pic")));
		mate.setMate_content(request.getParameter("mate_content"));

		Boolean flag = dao.add(mate);
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
		Material mate = new Material();
		mate.setId(Integer.parseInt(request.getParameter("id")));
		mate.setMate_name(request.getParameter("mate_name"));
		mate.setMate_pic(Integer.parseInt(request.getParameter("mate_pic")));
		mate.setMate_content(request.getParameter("mate_content"));
		
		
		
		Boolean flag = dao.update(mate);
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
		Boolean flag = dao.delete(id);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("flag", flag);
		return JsonUtil.toJson(map);
	}

}
