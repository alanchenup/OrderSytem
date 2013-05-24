/**
 * 
 */
package com.ordersystem.dao;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import oracle.sql.BLOB;

import com.ordersystem.common.model.Pic;

/**
 * @author Administrator
 *
 */
public class DaoPic {
	
	public Statement pstmt=null;
	public ResultSet rs=null;
	public BLOB blob=null;
	public int upload(String picName, BufferedInputStream in) throws Exception{


		Connection conn = DBHelper.getConnection();
		conn.setAutoCommit(false);
		int id=1;
		pstmt=conn.createStatement();
		rs=pstmt.executeQuery("select max(id) from pic ");
		if(rs.next()){
			id=rs.getInt(1)+1;
		}
		rs.close();
		pstmt.close();
		String sql="insert into pic(id,pic_name,pic_blob) values("+id+",'"+picName+"',empty_blob())";

		 pstmt =  conn.createStatement();
		 pstmt.executeUpdate(sql);
		 
		 String sqlblob = "select pic_blob from pic where id="+id+" for update";
		 rs = pstmt.executeQuery(sqlblob);
		    if (rs.next()) 
		    {
		        /* 取出此BLOB对象 */
		        blob = (oracle.sql.BLOB)rs.getBlob("pic_blob");
		        /* 向BLOB对象中写入数据 */
				OutputStream outputStream = blob.setBinaryStream(0);
		        int c;
		        while ((c=in.read())!=-1)
		        {
		           outputStream.write(c);
		        }
		        in.close();
		        outputStream.close();
		    }
		    rs.close();
		    pstmt.close();
            conn.commit();
            conn.close();  
		
	return id;	
	}
	public Pic getPic(int id) throws Exception {
		// TODO Auto-generated method stub
		Pic pic = new Pic();
		String sql = "select * from pic where id="+id;
		pstmt= DBHelper.getStatement();
		rs = pstmt.executeQuery(sql);
		while(rs.next()){
			pic.setId(rs.getInt(1));
			pic.setPic_name(rs.getString(2));
			pic.setPic_blob((BLOB)rs.getBlob(3));			
		}
		return pic;
	}

}
