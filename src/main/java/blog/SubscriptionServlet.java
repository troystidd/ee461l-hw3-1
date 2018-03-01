package blog;

import java.io.IOException;

import com.googlecode.objectify.ObjectifyService;
import static com.googlecode.objectify.ObjectifyService.ofy;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubscriptionServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		resp.sendRedirect("/subscription.jsp");
	}
	
	static {
		ObjectifyService.register(Subscriber.class);
	}
}
