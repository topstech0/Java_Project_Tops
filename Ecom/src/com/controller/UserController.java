package com.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.User;
import com.bean.Wishlist;
import com.dao.UserDao;
import com.dao.WishlistDao;
import com.service.Services;


@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;        

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		if(action.equalsIgnoreCase("register"))
		{
			boolean flag = UserDao.checkEmail(request.getParameter("email"));
			
			if(flag == true)
			{
				request.setAttribute("msg", "Email-id already registered.");
				request.getRequestDispatcher("register.jsp").forward(request, response);
			}
			else if(request.getParameter("password").equals(request.getParameter("cpassword")))
			{			
				User u = new User();
				u.setFname(request.getParameter("fname"));
				u.setLname(request.getParameter("lname"));
				u.setEmail(request.getParameter("email"));
				u.setPassword(request.getParameter("password"));
				u.setCpassword(request.getParameter("cpassword"));
				u.setMobile(Long.parseLong(request.getParameter("mobile")));
				u.setGender(request.getParameter("gender"));
				u.setAddress(request.getParameter("address"));
				u.setUsertype(request.getParameter("usertype"));
				UserDao.insertUser(u);
				request.setAttribute("msg", "User Registered Successfully.");
				request.getRequestDispatcher("register.jsp").forward(request, response);
			}
			else
			{
				request.setAttribute("msg", "Password and Confirm Password does not matched.");
				request.getRequestDispatcher("register.jsp").forward(request, response);
			}
		}
		else if(action.equalsIgnoreCase("login"))
		{
			User u = UserDao.checkLogin(request.getParameter("email"), request.getParameter("password"));
			if(u==null)
			{
				request.setAttribute("msg", "Invalid Email/Password");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
			else if(u.getUsertype().equals("user"))
			{
				HttpSession session = request.getSession();
				List<Wishlist> w_list = WishlistDao.getWishlistByUser(u.getUid());
				session.setAttribute("wishlist_count", w_list.size());
				session.setAttribute("u", u);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
			else
			{
				HttpSession session = request.getSession();
				session.setAttribute("u", u);
				request.getRequestDispatcher("seller_index.jsp").forward(request, response);
				
			}
			
		}
		else if(action.equalsIgnoreCase("change password"))
		{
			HttpSession session = request.getSession();
			User u = (User)session.getAttribute("u");
			if(u.getUsertype().equals("user"))
			{
				if(u.getPassword().equals(request.getParameter("oldpassword")))
				{
					if(request.getParameter("newpassword").equals(request.getParameter("cnewpassword")))
					{
						UserDao.changePassword(u.getEmail(), request.getParameter("newpassword"));
						response.sendRedirect("logout.jsp");
					}
					else
					{
						request.setAttribute("msg", "New and Confirm New Password is Incorrect");
						request.getRequestDispatcher("changepassword.jsp").forward(request, response);
					}
				}
				else
				{
					request.setAttribute("msg", "Old password is Incorrect.");
					request.getRequestDispatcher("changepassword.jsp").forward(request, response);
				}
			}
			else
			{
				if(u.getPassword().equals(request.getParameter("oldpassword")))
				{
					if(request.getParameter("newpassword").equals(request.getParameter("cnewpassword")))
					{
						UserDao.changePassword(u.getEmail(), request.getParameter("newpassword"));
						response.sendRedirect("logout.jsp");
					}
					else
					{
						request.setAttribute("msg", "New and Confirm New Password is Incorrect");
						request.getRequestDispatcher("seller_changepassword.jsp").forward(request, response);
					}
				}
				else
				{
					request.setAttribute("msg", "Old password is Incorrect.");
					request.getRequestDispatcher("seller_changepassword.jsp").forward(request, response);
				}
				
			}
			
			
			
			
			
		}
		else if(action.equalsIgnoreCase("update profile"))
		{
			User u = new User();
			u.setUid(Integer.parseInt(request.getParameter("uid")));
			u.setFname(request.getParameter("fname"));
			u.setLname(request.getParameter("lname"));
			u.setEmail(request.getParameter("email"));
			u.setMobile(Long.parseLong(request.getParameter("mobile")));
			u.setAddress(request.getParameter("address"));
			u.setUsertype(request.getParameter("usertype"));
			UserDao.updateProfile(u);
			HttpSession session = request.getSession();
			session.setAttribute("u", u);
			if(u.getUsertype().equals("user"))
			{
				request.setAttribute("msg", "Profile updated successfully.");
				request.getRequestDispatcher("profile.jsp").forward(request, response);
			}
			else
			{
				request.setAttribute("msg", "Profile updated successfully.");
				request.getRequestDispatcher("seller_profile.jsp").forward(request, response);
			}	
			
		}
		else if(action.equalsIgnoreCase("send otp"))
		{
			String email = request.getParameter("email");
			boolean flag = UserDao.checkEmail(email);
			
			if(flag==true)
			{
				int min = 1000;  
				int max = 9999;
				int otp =  (int)(Math.random()*(max-min+1)+min); 
				Services.sendMail(email, otp);
				request.setAttribute("email", email);
				request.setAttribute("otp", otp);
				request.getRequestDispatcher("otp.jsp").forward(request, response);
			}
			else
			{
				request.setAttribute("msg", "Email-Id not Registered.");
				request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
			}			
			
		}
		else if(action.equalsIgnoreCase("verify otp"))
		{
			String email = request.getParameter("email");
			int otp1 = Integer.parseInt(request.getParameter("otp1"));
			int otp2= Integer.parseInt(request.getParameter("otp2"));
			
			if(otp1==otp2)
			{
				request.setAttribute("email", email);
				request.getRequestDispatcher("new_password.jsp").forward(request, response);
			}
			else
			{
				request.setAttribute("email", email);
				request.setAttribute("otp", otp1);
				request.setAttribute("msg", "Invalid OTP");
				request.getRequestDispatcher("otp.jsp").forward(request, response);
			}
		}
		else if(action.equalsIgnoreCase("update password"))
		{
			String email = request.getParameter("email");
			String np = request.getParameter("newpassword");
			String cnp = request.getParameter("cnewpassword");
			
			if(np.equals(cnp))
			{
				UserDao.changePassword(email, np);
				response.sendRedirect("login.jsp");
			}
			else
			{
				request.setAttribute("email", email);
				request.setAttribute("msg", "New and Confirm New Password doesn't matched.");
				request.getRequestDispatcher("new_password.jsp").forward(request, response);
			}
		}
		
		
		
		
		
	}

}
