package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.bean.Wishlist;
import com.util.EcomUtil;

public class WishlistDao {
	
	public static void addToWishlist(Wishlist w)
	{
		try {
			Connection conn = EcomUtil.createConnection();
			String sql = "insert into wishlist(pid,uid) values (?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, w.getPid());
			pst.setInt(2, w.getUid());
			pst.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
