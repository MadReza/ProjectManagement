package model;

// Members exist in the database

public class Member {

	private String name, email, username, userPassword;
	private int userID;

	protected Member(String name, String email,  String username, String userPassword) {
		this.name = name;
		this.email = email;
		this.username = username;
		this.userPassword = userPassword;
	}

	protected String getName() {
		return name;
	}

	protected String getEmail() {
		return email;
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
