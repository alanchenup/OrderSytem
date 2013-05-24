/**
 * 
 */
package com.ordersystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OraclePreparedStatement;

import com.ordersystem.common.model.Material;
import com.ordersystem.common.model.Page;

/**
 * @author Administrator
 * 
 */
public class DaoMaterial {

	public List<Material> loadAll(Page page, String orderby, String orderasc)
			throws SQLException {
		String sql = SQLHelper.page(Material.TABLE, orderby, orderasc);
		ResultSet rs = DBHelper.read(sql,
				new String[] { "" + page.getPagesize() * page.getPageindex(),
						"" + page.getC_pagesize() });
		List<Material> materialList = new ArrayList<Material>();
		while (rs.next()) {
			Material Material = new Material();
			Material.setId(rs.getInt(1));
			Material.setMate_name(rs.getString(2));

			Material.setMate_pic(rs.getInt(3));
			Material.setMate_content(rs.getString(4));
			materialList.add(Material);
		}

		return materialList;
	}

	public int getSum(String[] conditions) throws SQLException {
		String sqlSum = SQLHelper.row_select(Material.TABLE,
				new String[] { "count(id)" }, conditions, null);
		ResultSet rsSum = DBHelper.read(sqlSum, null);
		int sum = 0;
		while (rsSum.next()) {
			sum = rsSum.getInt(1);
		}
		return sum;
	}

	public Boolean add(Material Material) throws SQLException {
		// TODO Auto-generated method stub
		String sql = SQLHelper.row_insert(com.ordersystem.common.model.Material.TABLE, new String[] {
				"mate_name", "mate_content", "mate_pic" }, null);
		OraclePreparedStatement pstmt = DBHelper.prepareStatement(sql);
		pstmt.setString(1, Material.getMate_name());
		pstmt.setString(2, Material.getMate_content());
		pstmt.setInt(3, Material.getMate_pic());
		int flag = pstmt.executeUpdate();
		DBHelper.close(pstmt);
		return flag == 1 ? true : false;
	}

	public Boolean update(Material Material) throws SQLException {
		// TODO Auto-generated method stub
		String sql = SQLHelper.row_update(com.ordersystem.common.model.Material.TABLE, new String[] {
				"mate_name", "mate_content", "mate_pic" },
				new String[] { "id" }, null);

		OraclePreparedStatement pstmt = DBHelper.prepareStatement(sql);
		pstmt.setString(1, Material.getMate_name());
		pstmt.setString(2, Material.getMate_content());
		pstmt.setInt(3, Material.getMate_pic());
		int flag = pstmt.executeUpdate();
		DBHelper.close(pstmt);
		return flag == 1 ? true : false;
	}

	public Boolean delete(int id) throws SQLException {
		// TODO Auto-generated method stub
		String sql = SQLHelper.row_delete(Material.TABLE,
				new String[] { "id" }, null);
		OraclePreparedStatement pstmt = DBHelper.prepareStatement(sql);
		pstmt.setInt(1, id);
		int flag = pstmt.executeUpdate();
		DBHelper.close(pstmt);
		return flag == 1 ? true : false;
	}

}
