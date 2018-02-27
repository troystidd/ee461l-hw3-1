package blog;

public class Subscriber {
	private String emailid;
	private String name;
	
	public Subscriber(String emailid, String name) {
		this.emailid = emailid;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmailId() {
		return emailid;
	}
	
}
