/**
 *  chenchang 2013-4-4
 */
package com.ordersystem.common.model;

/**
 * 食材
 * @author chenchang  2013-4-4
 *
 */
public class Material {
	public  final static  String TABLE="material";
	private int id;
	private String mate_name;
	private String mate_content;
	private int mate_pic;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMate_name() {
		return mate_name;
	}
	public void setMate_name(String mate_name) {
		this.mate_name = mate_name;
	}
	public String getMate_content() {
		return mate_content;
	}
	public void setMate_content(String mate_content) {
		this.mate_content = mate_content;
	}
	public int getMate_pic() {
		return mate_pic;
	}
	public void setMate_pic(int mate_pic) {
		this.mate_pic = mate_pic;
	}	
	
}
