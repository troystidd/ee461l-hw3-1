package blog;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
//import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.ObjectifyService;

public class BlogServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// get user from UserService
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		// get content from the request
		String blogName = req.getParameter("blogName");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		Date date = new Date();
		
		// create a new greeting
		BlogPost blogPost = new BlogPost(user, title, content, blogName);
		
		// chuck the greeting into Objectify
		ofy().save().entity(blogPost).now();
		
		// send response to ofyguestbook.jsp
		resp.sendRedirect("/blog.jsp");
	}
	
	static {
		ObjectifyService.register(BlogPost.class);
	}
	
}
