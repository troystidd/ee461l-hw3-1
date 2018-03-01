package blog;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Subscriber {
	@Parent Key<Blog> blogName;
	@Id Long id;
    @Index String emailid;
    @Index String name;
	
    private Subscriber() {}
	
	public Subscriber(String emailid, String name) {
		this.emailid = emailid;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmailId() {
		return emailid;
	}
	
}
