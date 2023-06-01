package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.bean.User;
import com.util.EcomUtil;

public class UserDao {
	
	public static void insertUser(User u)
	{
		try {
			Connection conn = EcomUtil.createConnection();
			String sql="insert into user(fname,lname,email,password,cpassword,mobile,gender,address,usertype) values (?,?,?,?,?,?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, u.getFname());
			pst.setString(2, u.getLname());
			pst.setString(3, u.getEmail());
			pst.setString(4, u.getPassword());
			pst.setString(5, u.getCpassword());
			pst.setLong(6, u.getMobile());
			pst.setString(7, u.getGender());
			pst.setString(8, u.getAddress());
			pst.setString(9, u.getUsertype());
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean checkEmail(String email)
	{
		boolean flag = false;
		
		try {
			Connection conn = EcomUtil.createConnection();
			String sql = "select * from user where email=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, email);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
			{
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public static User checkLogin(String email,String password)
	{
		User u = null;
		try {
			Connection conn = EcomUtil.createConnection();
			String sql = "select * from user where email=? and password=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, email);
			pst.setString(2, password);
			ResultSet rs = pst.executeQuery();
			if(rs.next())
			{
				u =  new User();
				u.setUid(rs.getInt("uid"));
				u.setFname(rs.getString("fname"));
				u.setLname(rs.getString("lname"));
				u.setEmail(rs.getString("email"));
				u.setPassword(rs.getString("password"));
				u.setCpassword(rs.getString("cpassword"));
				u.setMobile(rs.getLong("mobile"));
				u.setGender(rs.getString("gender"));
				u.setAddress(rs.getString("address"));
				u.setUsertype(rs.getString("usertype"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}
	
	public static void changePassword(String email,String password)
	{
		try {
			Connection conn = EcomUtil.createConnection();
			String sql = "update user set password=? where email=?";
			PreparedStatement pst  = conn.prepareStatement(sql);
			pst.setString(1, password);
			pst.setString(2, email);
			pst.executeUpdate();
		} catch (Exception e) {
			
		}
	}
	
	public static void updateProfile(User u)
	{
		try {
			Connection conn = EcomUtil.createConnection();
			String sql="update user set fname=?,lname=?,mobile=?,address=?,usertype=? where email=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, u.getFname());
			pst.setString(2, u.getLname());			
			pst.setLong(3, u.getMobile());			
			pst.setString(4, u.getAddress());
			pst.setString(5, u.getUsertype());
			pst.setString(6, u.getEmail());
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
