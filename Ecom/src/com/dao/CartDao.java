package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bean.Cart;
import com.util.EcomUtil;

public class CartDao {
	
	public static void addToCart(Cart c)
	{
		try {
			
			Connection conn = EcomUtil.createConnection();
			String sql = "insert into cart(uid,pid,p_price,p_qty,total_price) values (?,?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, c.getUid());
			pst.setInt(2, c.getPid());
			pst.setInt(3, c.getP_price());
			pst.setInt(4, c.getP_qty());
			pst.setInt(5, c.getTotal_price());
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<Cart> getCartByUser(int uid)
	{
		List<Cart> list = new ArrayList<Cart>();
		try {
			Connection conn = EcomUtil.createConnection();
			String sql="select * from cart where uid=?";			
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, uid);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				Cart c = new Cart();
				c.setCid(rs.getInt("cid"));
				c.setUid(rs.getInt("uid"));
				c.setPid(rs.getInt("pid"));
				c.setP_price(rs.getInt("p_price"));
				c.setP_qty(rs.getInt("p_qty"));
				c.setTotal_price(rs.getInt("total_price"));
				list.add(c);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
