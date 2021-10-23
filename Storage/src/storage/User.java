package storage;

import java.util.HashMap;
import java.util.Map;
import storage.Permissions;

public class User {
	
	private String username, password;
	private HashMap<Permissions, Boolean> privileges;
	
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.privileges = new HashMap<Permissions, Boolean>();
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public HashMap<Permissions, Boolean> getPrivileges() {
		return privileges;
	}


	public void setPrivileges(HashMap<Permissions, Boolean> privileges) {
		this.privileges = privileges;
	}

	
}
