/**
 * 
 */
package com.ordersystem.common.model;

import oracle.sql.BLOB;


/**
 * @author Administrator
 *
 */
public class Pic {
	public final static String TABLE="PIC";
	private int id;
	private String pic_name;
	private BLOB pic_blob;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPic_name() {
		return pic_name;
	}
	public void setPic_name(String pic_name) {
		this.pic_name = pic_name;
	}
	public BLOB getPic_blob() {
		return pic_blob;
	}
	public void setPic_blob(BLOB pic_blob) {
		this.pic_blob = pic_blob;
	}
	

}
