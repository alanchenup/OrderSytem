/**
 * 
 */
package com.ordersystem.common.model;

import java.sql.Clob;

/**
 * Dish model contain the information of dish.
 * @author chenchang  2013-4-4
 *
 */
public class Dish {

	public final static String TABLE="dish";
	private int id;
	private String dish_name="没有名字";
	private String dish_content="没有描述";
	private String dish_pic="0.00";
	
	private String dish_price="0.00" ;
	private String dish_c_price="0.00";
	private int m_mate=0;
	private int v_mate=0;
	private String dish_sales="0";
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDish_name() {
		return dish_name;
	}
	public void setDish_name(String dish_name) {
		this.dish_name = dish_name;
	}
	public String getDish_content() {
		return dish_content;
	}
	public void setDish_content(String dish_content) {
		this.dish_content = dish_content;
	}
	public String getDish_pic() {
		return dish_pic;
	}
	public void setDish_pic(String dish_pic) {
		this.dish_pic = dish_pic;
	}
	public String getDish_price() {
		return dish_price;
	}
	public void setDish_price(String dish_price) {
		this.dish_price = dish_price;
	}
	public String getDish_c_price() {
		return dish_c_price;
	}
	public void setDish_c_price(String dish_c_price) {
		this.dish_c_price = dish_c_price;
	}
	public int getM_mate() {
		return m_mate;
	}
	public void setM_mate(int m_mate) {
		this.m_mate = m_mate;
	}
	public int getV_mate() {
		return v_mate;
	}
	public void setV_mate(int v_mate) {
		this.v_mate = v_mate;
	}
	public String getDish_sales() {
		return dish_sales;
	}
	public void setDish_sales(String dish_sales) {
		this.dish_sales = dish_sales;
	}
	
	
}
