package com.ordersystem.view.listener;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ordersystem.commom.dao.Transaction;
import com.ordersystem.common.model.User;
import com.ordersystem.common.util.LogUtil;

public class StartListener implements ServletContextListener {

	@SuppressWarnings("rawtypes")
	private final static Class cls =(StartListener.class);
	private static final BlockingQueue queue = new LinkedBlockingQueue();
	private volatile Thread thread;

	public static void add(AsyncContext c) {
		queue.add(c);
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					AsyncContext context;						
					while ((context = (AsyncContext) queue.poll()) != null) {
						try {
							ServletRequest request=context.getRequest();
							ServletResponse response = context.getResponse();
							// 类名，方法名
							String theme = request.getParameter("theme");
							String method = request.getParameter("method");
							
							User user = null;
							HttpSession session = ((HttpServletRequest) request).getSession(true);
							if (session.getAttribute("user") != null) {
								user = (User) session.getAttribute("user");
								LogUtil.info(cls, "IP:"+request.getRemoteAddr()+" User:"+user.getUser_name()+" theme:"+theme+" method:"+method);
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
							
							
						} catch (Exception e) {
							throw new RuntimeException(e.getMessage(), e);
						} finally {
							context.complete();
						}
					}
				}
			}
		});
		thread.start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		thread.interrupt();
	}
}
