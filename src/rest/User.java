/**
 * 
 */
package rest;

/**
 * @author Owner
 *
 */
public class User {
	public String username;
	public String userpassword;
	public String usertype;
	
	public User() {
		super();
	}

	public User(String username, String userpassword, String usertype) {
		super();
		this.username = username;
		this.userpassword = userpassword;
		this.usertype = usertype;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	

}
