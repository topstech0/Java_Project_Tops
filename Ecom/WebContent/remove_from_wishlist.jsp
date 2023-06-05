<%@page import="java.util.List"%>
<%@page import="com.dao.WishlistDao"%>
<%@page import="com.bean.Wishlist"%>
<%
int pid = Integer.parseInt(request.getParameter("pid"));
int uid = Integer.parseInt(request.getParameter("uid"));
Wishlist w = new Wishlist();
w.setPid(pid);
w.setUid(uid);
WishlistDao.removefromWishlist(w);
List<Wishlist> w_list = WishlistDao.getWishlistByUser(uid);
session.setAttribute("wishlist_count", w_list.size());
request.getRequestDispatcher("mywishlist.jsp").forward(request, response);

%>