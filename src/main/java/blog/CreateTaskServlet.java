package blog;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

@SuppressWarnings("serial")
public class CreateTaskServlet extends HttpServlet {
	private static final Logger _logger = Logger.getLogger(CreateTaskServlet.class.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String strCallResult = "";
		resp.setContentType("text/plain");
		
		try {
			//extract out the To, subject, and body of email to be sent
			String strEmailId = req.getParameter("emailid");
			
			//do validations here. only basic ones
			if (strEmailId == null) throw new Exception("Email Id field cannot be empty.");
			
			//trim the stuff
			//strEmailId = strEmailId.trim();
			if(strEmailId.length() == 0) throw new Exception("Email Id field cannot be empty.");
			
			Queue queue = QueueFactory.getQueue("subscription-queue");
			queue.add(TaskOptions.Builder.withUrl("/ee461lsubscriber").param("emailid", strEmailId));
			strCallResult = "Successfully added you to the Subscription List";
			resp.getWriter().println(strCallResult);
			
			_logger.info("Cron Job has been executed");
		}
		catch (Exception ex) {
			strCallResult = "Fail: " + ex.getMessage();
			resp.getWriter().println(strCallResult);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
