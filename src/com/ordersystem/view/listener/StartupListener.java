package com.ordersystem.view.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ordersystem.commom.dao.Transaction;
import com.ordersystem.common.util.LogUtil;

public class StartupListener implements ServletContextListener {
    @SuppressWarnings("rawtypes")private final static Class cls =(StartupListener.class);
    public void contextInitialized(ServletContextEvent event) {
    	final String method ="contextInitialized";
        ServletContext context = event.getServletContext();
        LogUtil.debug(cls, method, "log...........................");
        this.execute(context.getRealPath("/"));
    }
    public void execute(String path){
    	final String method = "execute";
    	try {
            StartLoader.context(path);
            StartLoader.database();
           
            StartLoader.instance();
            StartLoader.data();
            StartLoader.cache();
        } catch (Exception e) {
			LogUtil.warn(cls, method, e);
        }
        Transaction.closeSession(true);
    }
    public void contextDestroyed(ServletContextEvent arg0) {
    }
}
