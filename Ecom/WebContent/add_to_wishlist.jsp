<%@page import="com.dao.WishlistDao"%>
<%@page import="com.bean.Wishlist"%>
<%
int pid = Integer.parseInt(request.getParameter("pid"));
int uid = Integer.parseInt(request.getParameter("uid"));
Wishlist w = new Wishlist();
w.setPid(pid);
w.setUid(uid);
WishlistDao.addToWishlist(w);
response.sendRedirect("index.jsp");
%>