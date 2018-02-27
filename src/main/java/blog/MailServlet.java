package blog;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.googlecode.objectify.ObjectifyService.ofy;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class MailServlet extends HttpServlet {
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String strCallResult = "";
		resp.setContentType("text/plain");
		
		ObjectifyService.register(Subscriber.class);
		List<Subscriber> subscribers = ObjectifyService.ofy().load().type(Subscriber.class).list();
		
		for (Subscriber sub : subscribers) {
			try {
				//String strTo = req.getParameter("email_to");
				//String strBody = req.getParameter("email_body");
				
				String strTo = sub.getEmailId();
				
				if (strTo.length() == 0) throw new Exception("To field cannot be empty.");
				
				//Call email service
				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);
				
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("update@ee461l-hw3.appspotmail.com", "EE461L-HW3 Update"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(strTo));
				msg.setSubject("Blog Updates");
				//msg.setText(strBody);
				msg.setText("There are new posts on the website");
				Transport.send(msg);
				strCallResult = "Success: " + "Email has been delivered.";
				resp.getWriter().println(strCallResult);
			}
			catch (Exception e) {
				strCallResult = "Fail: " + e.getMessage();
				resp.getWriter().println(strCallResult);
			}
		}
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doPost(req, resp);
	}
}
