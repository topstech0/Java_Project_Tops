package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bean.Product;
import com.util.EcomUtil;

public class ProductDao {

	public static void addProduct(Product p)
	{
		try {
			Connection conn = EcomUtil.createConnection();
			String sql = "insert into product (uid,p_category,p_name,p_model,p_price,p_image,p_desc) values (?,?,?,?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, p.getUid());
			pst.setString(2, p.getP_category());
			pst.setString(3, p.getP_name());
			pst.setString(4, p.getP_model());
			pst.setInt(5, p.getP_price());
			pst.setString(6, p.getP_image());
			pst.setString(7, p.getP_desc());
			pst.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<Product> getAllProduct()
	{
		List<Product> list = new ArrayList<Product>();
		try {
			Connection conn = EcomUtil.createConnection();
			String sql = "select * from product";
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				Product p =new Product();
				p.setPid(rs.getInt("pid"));
				p.setUid(rs.getInt("uid"));
				p.setP_category(rs.getString("p_category"));
				p.setP_name(rs.getString("p_name"));
				p.setP_model(rs.getString("p_model"));
				p.setP_price(rs.getInt("p_price"));
				p.setP_image(rs.getString("p_image"));
				p.setP_desc(rs.getString("p_desc"));
				list.add(p);			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	public static Product getProductById(int pid)
	{
		Product p =null;
		try {
			Connection conn = EcomUtil.createConnection();
			String sql = "select * from product where pid=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, pid);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
			{
				p =new Product();
				p.setPid(rs.getInt("pid"));
				p.setUid(rs.getInt("uid"));
				p.setP_category(rs.getString("p_category"));
				p.setP_name(rs.getString("p_name"));
				p.setP_model(rs.getString("p_model"));
				p.setP_price(rs.getInt("p_price"));
				p.setP_image(rs.getString("p_image"));
				p.setP_desc(rs.getString("p_desc"));							
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return p;		
	}
	
	public static void updateProduct(Product p)
	{
		try {
			Connection conn = EcomUtil.createConnection();
			String sql = "update product set p_category=?,p_name=?,p_model=?,p_price=?,p_desc=? where pid=?";
			PreparedStatement pst = conn.prepareStatement(sql);			
			pst.setString(1, p.getP_category());
			pst.setString(2, p.getP_name());
			pst.setString(3, p.getP_model());
			pst.setInt(4, p.getP_price());			
			pst.setString(5, p.getP_desc());
			pst.setInt(6, p.getPid());
			pst.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteProduct(int pid)
	{
		try {
			
			Connection conn = EcomUtil.createConnection();
			String sql = "delete from product where pid=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, pid);
			pst.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<Product> getProductByCategory(String p_category)
	{
		List<Product> list = new ArrayList<Product>();
		try {
			Connection conn = EcomUtil.createConnection();
			String sql = "select * from product where p_category=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, p_category);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				Product p =new Product();
				p.setPid(rs.getInt("pid"));
				p.setUid(rs.getInt("uid"));
				p.setP_category(rs.getString("p_category"));
				p.setP_name(rs.getString("p_name"));
				p.setP_model(rs.getString("p_model"));
				p.setP_price(rs.getInt("p_price"));
				p.setP_image(rs.getString("p_image"));
				p.setP_desc(rs.getString("p_desc"));
				list.add(p);			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	
	
}
