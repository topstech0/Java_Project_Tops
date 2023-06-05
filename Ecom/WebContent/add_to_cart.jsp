<%@page import="com.dao.CartDao"%>
<%@page import="com.bean.Cart"%>
<%@page import="com.dao.ProductDao"%>
<%@page import="com.bean.Product"%>
<%@page import="java.util.List"%>
<%@page import="com.dao.WishlistDao"%>
<%@page import="com.bean.Wishlist"%>
<%
int pid = Integer.parseInt(request.getParameter("pid"));
int uid = Integer.parseInt(request.getParameter("uid"));
Product p  =ProductDao.getProductById(pid);
Cart c = new Cart();
c.setPid(pid);
c.setUid(uid);
c.setP_price(p.getP_price());
c.setP_qty(1);
c.setTotal_price(p.getP_price());
CartDao.addToCart(c);
//List<Wishlist> w_list = WishlistDao.getWishlistByUser(uid);
//session.setAttribute("wishlist_count", w_list.size());
//request.getRequestDispatcher("mywishlist.jsp").forward(request, response);
response.sendRedirect("mycart.jsp");
%>