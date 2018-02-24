<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.googlecode.objectify.*" %>
<%@ page import="blog.BlogPost" %>
<%@ page import="java.util.Collections" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <header>
  	<link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
	<div class="banner">
		<div class="blogTitle" style="font-family:Helvetica; font-size: 30px"><b>EE 461L Blog</b></div>
		<div class="authors" style="font-family:Verdana; font-size:15px">Cynthia & Veronica</div>
	</div>
  </header>
  
  <body>
<%
    String blogName = request.getParameter("blogName");
	if (blogName == null) {
		blogName = "default";
	}
	
	pageContext.setAttribute("blogName", blogName);
	UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    
    if (user != null) {
      pageContext.setAttribute("user", user);
%>

<form action="/post/createpost" method="post">
	<p>Title</p>
	<div><textarea name="title" rows="1" cols="30"></textarea></div>
	<p>Content</p>
	<div><textarea name="content" rows="3" cols="60"></textarea></div>
	<br>
	<div><input type="submit" value="Publish" class="button"/></div>
	<input type="hidden" name="blogName" value="${fn:escapeXml(blogName)}"/>
</form>

<p><a href="http://ee461l-hw3.appspot.com/" class="button">Back</a></p>
<p><a href="<%= userService.createLogoutURL(request.getRequestURI()) %>" class="button">Log out</a></p>

<%
    } else {
%>

<p><a href="<%= userService.createLoginURL(request.getRequestURI()) %>" class="button">Login</a></p>

<%
    }
%>

  </body>
</html>