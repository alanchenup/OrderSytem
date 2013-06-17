/**
 * 
 */
package com.ordersystem.common.model;

import java.sql.Date;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import com.ordersystem.common.util.JsonUtil;

/**
 * @author Administrator
 *
 */
public class Order {
	public static final String TABLE="DORDER";
	private  int id;
	private int userId;
	private List<OrderDish> orderDishList;
	private Date order_date;
	private String order_money;
	private String others;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
			this.others = others;
			this.orderDishList=JsonUtil.foJson(others, new TypeReference<List<OrderDish>>(){});
	}
	public Date getOrder_date() {
		//返回一个可变对象的引用要用到克隆，防止破坏类的封装性
		return (Date)order_date.clone();
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public String getOrder_money() {
		return order_money;
	}
	public void setOrder_money(String order_money) {
		this.order_money = order_money;
	}

}
