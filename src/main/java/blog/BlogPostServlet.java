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

public class BlogPostServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// send response to ofyguestbook.jsp
		resp.sendRedirect("/post.jsp");
	}
	
	static {
		ObjectifyService.register(BlogPost.class);
	}
}
