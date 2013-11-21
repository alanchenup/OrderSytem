package com.ordersystem.view.listener;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ordersystem.commom.dao.Transaction;
import com.ordersystem.common.model.User;
import com.ordersystem.common.util.LogUtil;

public class DoAjaxServlet extends HttpServlet {
	public final static  Class clazz = DoAjaxServlet.class;
	/**
	 * Constructor of the object.
	 */
	public DoAjaxServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 类名，方法名
		String theme = request.getParameter("theme");
		String method = request.getParameter("method");
		
		User user = null;
		HttpSession session = request.getSession(true);
		if (session.getAttribute("user") != null) {
			user = (User) session.getAttribute("user");
			LogUtil.info(clazz, "IP:"+request.getRemoteAddr()+" User:"+user.getUser_name()+" theme:"+theme+" method:"+method);
		} else {
			user = new User();
		}
		// 利用反射调用后端类和方法
		String res = null;
		Class cls = null;
		Class[] parameterTypes = null;
		Method methodName = null;
		try {
			cls = Class.forName("com.liaoyang.service." + theme + "Wrap");
			parameterTypes = new Class[] { HttpSession.class,
					HttpServletRequest.class, User.class };
			methodName = cls.getMethod(method, parameterTypes);
			if(methodName==null){
				LogUtil.info(cls, "NO Method:"+theme+"."+method);
			}else{
			res = (String) methodName.invoke(null, new Object[] { session,
					request, user });
			}
		  if(res==null) res="[]";			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtil.error(cls, "res:"+res+"  exception:"+e.getMessage());
			res="[]";
			e.printStackTrace();
		}
		finally{
			Transaction.closeSession(true);
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		OutputStream out = response.getOutputStream();
		out.write(res.getBytes("UTF-8"));
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
