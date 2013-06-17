/**
 *  chenchang 2013-4-4
 */
package com.ordersystem.common.model;

/**
 * 
 * @author chenchang  2013-4-4
 *
 */
public class User { 
public final static String TABLE="usertable";
	private int id ;
	private String user_name ;
	
	private String user_niname;
	private String user_password ;
	private String user_email;
	private String user_type;
	public User(){
		this.user_niname="昵称";
		this.user_email="ordersystem@gmail.com";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_niname() {
		return user_niname;
	}
	public void setUser_niname(String user_niname) {
		this.user_niname = user_niname;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	
}
