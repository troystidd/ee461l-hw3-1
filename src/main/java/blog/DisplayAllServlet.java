package blog;

import java.io.IOException;
import java.util.Date;

import com.googlecode.objectify.ObjectifyService;
import static com.googlecode.objectify.ObjectifyService.ofy;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class DisplayAllServlet extends HttpServlet {
	
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
		resp.sendRedirect("/post.jsp");
	}
	
	static {
		ObjectifyService.register(BlogPost.class);
	}

}
