package com.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.Contact;
import com.dao.ContactDao;


@WebServlet("/ContactController")
public class ContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action=request.getParameter("action");
		
		if(action.equalsIgnoreCase("submit"))
		{
			Contact c = new Contact();
			c.setFname(request.getParameter("fname"));
			c.setLname(request.getParameter("lname"));
			c.setEmail(request.getParameter("email"));
			c.setAddress(request.getParameter("address"));
			ContactDao.insertContact(c);
			request.setAttribute("msg", "Contact Saved Successfully.");
			//response.sendRedirect("contact.jsp");
			request.getRequestDispatcher("contact.jsp").forward(request, response);
		}
		
		
	}

}
