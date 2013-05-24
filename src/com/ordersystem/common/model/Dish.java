/**
 * 
 */
package com.ordersystem.common.model;

import java.util.List;

/**
 * Dish model contain the information of dish.
 * @author chenchang  2013-4-4
 *
 */
public class Dish {

	public final static String TABLE="dish";
	private int id;
	private String dish_name;
	private String dish_content="dishcontents";
	private int dish_pic;
	
	private String dish_price ;
	private String dish_c_price;
	private int m_mate;
	private int v_mate;
	private List<Material> lm_mate;
	private List<Material> lv_mate;
	private String dish_sales;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setM_mate(int m_mate) {
		this.m_mate = m_mate;
	}
	public void setV_mate(int v_mate) {
		this.v_mate = v_mate;
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
		if(dish_content==null) return;
		this.dish_content = dish_content;
	}
	public int getDish_pic() {
		return dish_pic;
	}
	public void setDish_pic(int dish_pic) {
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

	public int getV_mate() {
		return v_mate;
	}

	public String getDish_sales() {
		return dish_sales;
	}
	public void setDish_sales(String dish_sales) {
		this.dish_sales = dish_sales;
	}
	
	
}
