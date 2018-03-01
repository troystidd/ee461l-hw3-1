<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.googlecode.objectify.*" %>
<%@ page import="blog.Subscriber" %>
<%@ page import="java.util.Collections" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<html>
  <header>
  	<link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
	<div class="banner">
		<div class="blogTitle" style="font-family:Helvetica; font-size: 30px"><b>EE 461L Blog</b></div>
		<div class="authors" style="font-family:Verdana; font-size:15px">Cynthia & Veronica</div>
	</div>
	<img src="https://sdl-stickershop.line.naver.jp/products/0/0/1/1028369/android/stickers/1210842.png;compress=true">
  </header>
  
  <body>
<%
	ObjectifyService.register(Subscriber.class);    

	UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    
    List<Subscriber> subscribers = ObjectifyService.ofy().load().type(Subscriber.class).list();
    
    if (user != null) {
      pageContext.setAttribute("user", user);
      Subscriber s = null;
      for (Subscriber sub : subscribers) {
    	  	if (sub.getEmailId().equals(user.getEmail())) {
    	  		s = sub;
    	  	}
      }
      if (s != null) {
      	ObjectifyService.ofy().delete().type(Subscriber.class).id(Long.valueOf(s.getId())).now();
      	%>
      	<p>You've been removed from the subscription list. </p>
      	<%
      }
      else {
    	  %>
    	  <p>You are not subscribed.</p>
    	  <%
      }
    }
    
%>

<p><a href="http://ee461l-hw3.appspot.com/" class="button">Back</a></p>
  
  
  </body>
</html>