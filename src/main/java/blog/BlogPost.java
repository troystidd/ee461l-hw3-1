package blog;

import java.util.Date;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Entity
public class BlogPost implements Comparable<BlogPost> {

	@Parent Key<Blog> blogName;
    @Id Long id;
    @Index User user;
    @Index String title;
    @Index String content;
    @Index Date date;
    
    private BlogPost() {}
    
    public BlogPost(User user, String title, String content, String blogName) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.blogName = Key.create(Blog.class, blogName);
        date = new Date();
    }
    
    public User getUser() {
        return user;
    }
    
    public String getTitle() {
    		return title;
    }
    
    public String getContent() {
        return content;
    }
    
    public String getFormattedDate() {
    		SimpleDateFormat parser = new SimpleDateFormat("MMM dd yyyy"); //ex: Feb 28 2018
    		TimeZone tz = TimeZone.getTimeZone("CST"); //set timezone to central 
    		parser.setTimeZone(tz);
    		return parser.format(date);
    }
    
    public Date getDate() {
    		return date;
    }

    @Override
    public int compareTo(BlogPost other) {
        if (date.after(other.date)) {
            return 1;
        } 
        else if (date.before(other.date)) {
            return -1;
        }
        return 0;
    }
}
