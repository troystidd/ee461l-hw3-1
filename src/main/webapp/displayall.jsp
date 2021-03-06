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
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
  %>
  
  <%
	ObjectifyService.register(BlogPost.class);
	List<BlogPost> blogPosts = ObjectifyService.ofy().load().type(BlogPost.class).list();
	
	Collections.sort(blogPosts, Collections.reverseOrder());
	
	if (blogPosts.isEmpty()) {
		%>
		
		<p>Blog '${fn:escapeXml(blogName)}' has no posts.</p>
		
		<%
	} else {
		%>
		<br>
		<%

		for (BlogPost blogPost : blogPosts) {
			
			pageContext.setAttribute("post_title", blogPost.getTitle());
			pageContext.setAttribute("post_user", blogPost.getUser());
			pageContext.setAttribute("post_date", blogPost.getFormattedDate());
			pageContext.setAttribute("post_content", blogPost.getContent());
			%>
			<div class="box">
				<h1>${fn:escapeXml(post_title)}</h1>
				<p>${fn:escapeXml(post_user)}</p>
				<p>${fn:escapeXml(post_date)}</p>
				<blockquote>${fn:escapeXml(post_content)}</blockquote>
			</div>
			<br>
			<%
		}
		
	}
%>
  <p><a href="http://ee461l-hw3.appspot.com/" class="button">Back</a></p>
  <p><a href="<%= userService.createLogoutURL(request.getRequestURI()) %>" class="button">Log out</a></p>
  
  </body>
</html>