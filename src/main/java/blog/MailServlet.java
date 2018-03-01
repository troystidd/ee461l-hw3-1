package blog;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

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
		
		//register entities
		ObjectifyService.register(Subscriber.class);
		ObjectifyService.register(BlogPost.class);
		
		//get lists from datastore
		List<Subscriber> subscribers = ObjectifyService.ofy().load().type(Subscriber.class).list();
		List<BlogPost> blogPosts = ObjectifyService.ofy().load().type(BlogPost.class).list();
		
		//sort by most recent first
		Collections.sort(blogPosts, Collections.reverseOrder());
		
		//the date 24 hours prior
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, -1);
		date = c.getTime();
		
		boolean send = true; //if true, there was a new post added within 24 hours ago
		
		if (blogPosts.size() > 0) {
			if (blogPosts.get(0).getDate().before(date)) { //check if most recent post was from over 24 hrs ago
				send = false;
			}
		}
		
		if (send) {
			for (Subscriber sub : subscribers) { //for each subscriber in mailing list
				try {
					String strBody = "";
					
					String strTo = sub.getEmailId(); //get subscriber email address
					
					if (strTo.length() == 0) throw new Exception("To field cannot be empty.");
					
					//Call email service
					Properties props = new Properties();
					Session session = Session.getDefaultInstance(props, null);
					
					Message msg = new MimeMessage(session);
					
					//set message attributes
					msg.setFrom(new InternetAddress("update@ee461l-hw3.appspotmail.com", "EE461L-HW3 Update"));
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(strTo));
					msg.setSubject("Blog Updates");
		    			
					//append posts that were posted under 24 hrs ago
					for (BlogPost post : blogPosts) {
						if (post.getDate().after(date)) {
							strBody = strBody + post.getTitle() + "\n" + post.getContent() + "\n\n";
						}
					}
	
					strBody += "Thanks for subscribing.";
					
					msg.setText(strBody);
					
					Transport.send(msg); //send out the email
					strCallResult = "Success: " + "Email has been delivered.";
					resp.getWriter().println(strCallResult);
				}
				catch (Exception e) {
					strCallResult = "Fail: " + e.getMessage();
					resp.getWriter().println(strCallResult);
				}
			}
		}
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doPost(req, resp);
	}
}
