package com.ordersystem.dao;


import com.ordersystem.common.model.Page;
import com.ordersystem.common.util.StringUtils;

/***
 * SQL语句组装的辅助类
 * @author Administrator
 *
 */
public class SQLHelper {

	public final static String ID_LIMIT="LIMIT 1";
    public final static String DOT=",";
	/***
	 * 获取最近插入的ID
	 * @param table
	 * @return
	 */
	public static String last_id(String table){
		StringBuilder sb = new StringBuilder();
		sb.append("select last_insert_id() from ").append(table);
		return sb.toString();
	}
	/***
	 * 分页结尾
	 * @param pagesize
	 * @param pageindex
	 * @param order
	 * @param asc
	 * @return
	 */
	public static String row_page_after(int pagesize,int pageindex,String orderby, String asc){
		StringBuilder sb=new StringBuilder();
		sb.append(" order by ").append(orderby).append(" ").append(asc).append(" LIMIT ")
		.append((pageindex - 1) * pagesize).append(",").append(pagesize);
		return sb.toString();
	}
	/***
	 * 读取，用于构建PreparedStatement，执行时需要植入conditions对应的值
	 * @param table
	 * @param load_ziduans  为null的时候，select *
	 * @param conditions 条件，and相连，可以为null
	 * @param after 后缀，可以为null
	 * @return
	 */
	public static String row_select(String table,String load_ziduans[],String[] conditions,String after){
		StringBuilder sb=new StringBuilder();
		sb.append("select ");
		if(load_ziduans==null){
			sb.append("*");
		}
		else {
			sb.append(StringUtils.join(load_ziduans, ","));
		}
		sb.append(" from ").append(table);
		if(conditions!=null&&conditions.length>0){
			sb.append(" where 1 and ").append(StringUtils.join(conditions, "=? and ")).append("=?");
		}
		if(after!=null)sb.append(" ").append(after);
		return sb.toString();
	}
	public static String row_select(String table,String load_ziduans_dot,String conditions,String after){
		return row_select(table,StringUtils.Split(load_ziduans_dot, DOT),StringUtils.Split(conditions, DOT),after);
	}
	/***
	 * 删除，用于构建PreparedStatement，执行时需要植入ziduans对应的值
	 * @param table
	 * @param conditions 条件，and相连
	 * @param after
	 * @return
	 */
	public static String row_delete(String table,String[] conditions,String after){
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ");
		sb.append(table).append(" where 1 ");
		if(conditions!=null&&conditions.length>0){
			sb.append(" and ").append(StringUtils.join(conditions, "=? and ")).append("=?");
		}
		if(after!=null)sb.append(" ").append(after);
		return sb.toString();
	}
	/***
	 * 删除，用于构建Statement，不需要植入参数
	 * @param table
	 * @param key   in字段
	 * @param vals  in值列表
	 * @return
	 */
	public static String rows_delete(String table,String in_key,String[] in_vals,String after){
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ");
		sb.append(table).append(" where ").append(in_key).append(" in('").append(StringUtils.join(in_vals, "','")).append("')");
		if(after!=null)sb.append(" ").append(after);
		return sb.toString();
	}
	/***
	 * 插入，用于构建PreparedStatement，执行时需要植入insert_ziduans对应的值
	 * @param table
	 * @param insert_ziduans
	 * @param after
	 * @return
	 */
	public static String row_insert(String table,String[] insert_ziduans,String after){
		StringBuilder sb=new StringBuilder();
		sb.append("insert into ");
		sb.append(table);
		sb.append("(").append(StringUtils.join(insert_ziduans, ",")).append(") values(");
		int length=insert_ziduans.length;
		for(int i=0;i<length;i++){
			sb.append("?");
			if(i<length-1)sb.append(",");
		}
		sb.append(")");
		if(after!=null)sb.append(" ").append(after);
		return sb.toString();
	}
	public static String row_insert(String table,String insert_ziduans_dot,String after){
		return row_insert(table,StringUtils.Split(insert_ziduans_dot, DOT),after);
	}
	/***
	 * 插入，用于构建PreparedStatement，执行时需要植入insert_ziduans_length对应的值
	 * @param table
	 * @param insert_ziduans_length
	 * @param after
	 * @return
	 */
	public static String row_insert(String table,int insert_ziduans_length,String after){
		StringBuilder sb=new StringBuilder();
		sb.append("insert into ");
		sb.append(table).append(" values(");
		for(int i=0;i<insert_ziduans_length;i++){
			sb.append("?");
			if(i<insert_ziduans_length-1)sb.append(",");
		}
		sb.append(")");
		if(after!=null)sb.append(" ").append(after);
		return sb.toString();
	}
	/***
	 * 更新，用于构建PreparedStatement，执行时需要植入conditions对应的值
	 * @param table
	 * @param update_ziduans
	 * @param conditions
	 * @param after
	 * @return
	 */
	public static String row_update(String table,String[] update_ziduans,String[] conditions,String after){
		StringBuilder sb=new StringBuilder();
		sb.append("update ");
		sb.append(table);
		sb.append(" set ").append(StringUtils.join(update_ziduans, "=?,")).append("=?").append(" where 1 ");
		if(conditions!=null&&conditions.length>0){
			sb.append(" and ").append(StringUtils.join(conditions, "=? and ")).append("=?");
		}
		if(after!=null)sb.append(" ").append(after);
		return sb.toString();
	}
	public static String row_update(String table,String update_ziduans_dot,String conditions,String after){
		return row_update(table,StringUtils.Split(update_ziduans_dot, DOT),StringUtils.Split(conditions, DOT),after);
	}	
	public static String toBoolean(boolean bool){
		return bool?"1":"0";
	}
	
	public static String page(String table, String orderby,String orderasc){
		StringBuilder sb = new StringBuilder();
		sb.append("select b3.* from");
        sb.append("(select b1.id from ");
        sb.append("(select b.id from ");
        sb.append(table);
        sb.append(" b where rownum <= ? order by b.id desc) b1 ");
        sb.append("where rownum <= ? order by b1.id desc) b2, ");
        sb.append(table);
        sb.append(" b3 where b2.id = b3.id order by ");
        sb.append(orderby).append(" ").append(orderasc);		
		return sb.toString();
	
		
	}
}
