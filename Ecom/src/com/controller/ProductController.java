package com.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bean.Product;
import com.dao.ProductDao;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 512,//512MB
maxFileSize = 1024 * 1024 * 512, //512MB
maxRequestSize = 1024 * 1024 * 512) // 512MB
@WebServlet("/ProductController")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private String extractfilename(Part file) {
	    String cd = file.getHeader("content-disposition");
	    System.out.println(cd);
	    String[] items = cd.split(";");
	    for (String string : items) {
	        if (string.trim().startsWith("filename")) {
	            return string.substring(string.indexOf("=") + 2, string.length()-1);
	        }
	    }
	    return "";
	}
    	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String action =request.getParameter("action");
		
		if(action.equalsIgnoreCase("add product"))
		{
			String savePath = "D:\\Github\\JavaBatch15March\\Ecom\\WebContent\\Product_Images";
			File fileSaveDir=new File(savePath);
	        if(!fileSaveDir.exists()){
	            fileSaveDir.mkdir();
	        }
	        Part file1 = request.getPart("p_image");
		 	String fileName=extractfilename(file1);
		    file1.write(savePath + File.separator + fileName);
		    String filePath= savePath + File.separator + fileName ;
		    
		    Product p =new Product();
		    p.setUid(Integer.parseInt(request.getParameter("uid")));
		    p.setP_category(request.getParameter("p_category"));
		    p.setP_name(request.getParameter("p_name"));
		    p.setP_model(request.getParameter("p_model"));
		    p.setP_price(Integer.parseInt(request.getParameter("p_price")));
		    p.setP_image(fileName);
		    p.setP_desc(request.getParameter("p_desc"));
		    ProductDao.addProduct(p);
		    request.setAttribute("msg", "Product Added Successfully.");
		    request.getRequestDispatcher("add_product.jsp").forward(request, response);
		    
		}
		else if(action.equalsIgnoreCase("update product"))
		{
			Product p = new Product();
			p.setPid(Integer.parseInt(request.getParameter("pid")));
			p.setP_category(request.getParameter("p_category"));
			p.setP_name(request.getParameter("p_name"));
			p.setP_model(request.getParameter("p_model"));
			p.setP_price(Integer.parseInt(request.getParameter("p_price")));
			p.setP_desc(request.getParameter("p_desc"));
			ProductDao.updateProduct(p);
			request.setAttribute("msg", "Product updated successfully.");
			request.getRequestDispatcher("seller_view_product.jsp").forward(request, response);
		}
		
		
		
	}

}
