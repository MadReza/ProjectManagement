package model;

// Members exist in the database

public class Member {

	private String name, email, role, username, userPassword;
	private int userID;

	protected Member(String name, String email,String role, String username, String userPassword) {
		this.name = name;
		this.email = email;
		this.role = role;
		this.username = username;
		this.userPassword = userPassword;
	}

	protected String getName() {
		return name;
	}

	protected String getEmail() {
		return email;
	}
	
	protected String getRole() {
		return role;
	}


	protected String getUsername() {
		return username;
	}

	protected String getUserPassword() {
		return userPassword;
	}

	protected int getUserID() {
		return userID;
	}

	protected void setUserID(int userID) {
		this.userID = userID;
	}

}
