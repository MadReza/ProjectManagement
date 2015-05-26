package model;

import java.sql.*;

import javax.swing.*;

public class Database {

	Connection connect = null;
	/**
	 *  Default constructor for the Database.
	 *  Establishes a connection with the database.
	 */
	public Database() {
		connect = DatabaseConnection.dbConnector();
	}

	/**
	 * Validate user login data.
	 * @param username
	 * @param password
	 */
	public boolean validateLoginInformation(String username, String password) {
		return verifyMember(username, password);
	}


	/**
	 * Verifies and logs into the application if the member exists in the database.
	 * @param username - user input retrieved from usernameTextField
	 * @param password - password retrieved from passwordField
	 */
	private boolean verifyMember(String username, String password) {
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			String query = "select count(*) from Members where username=? and password=?"; 
			statement = connect.prepareStatement(query);
			statement.setString(1, username);
			statement.setString(2, password);

			result = statement.executeQuery();

			int count = 0;
			if (result.next()) {
				count = result.getInt(1);
			}
			if(count ==1)
				return true;

			if(count != 1)
				JOptionPane.showMessageDialog(null, "Username and/or password is incorrect!");
			return false;
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);	
		}
		finally {
			try {
				result.close();
				statement.close();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1);
			}
		}
		return false;
	}


	/**
	 * Add a new member to the database.
	 * @param name
	 * @param role
	 * @param username
	 * @param password
	 */
	public boolean addMembertoDatabase(String name, String email, String username, String password) {
		return insertMember(name, email, username, password);
	}


	/**
	 * Inserts Member-Entry into the Members table in the database.
	 * @param name
	 * @param role
	 * @param username
	 * @param password
	 */
	private boolean insertMember(String name, String email, String username, String password) {
		PreparedStatement statement = null;

		try {
			String query = "insert into Members (Name,Email,UserName,Password) values (?,?,?,?)"; 
			statement = connect.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, email);
			statement.setString(3, username);
			statement.setString(4, password);

			boolean status = statement.execute();	
			return status;
		}
		catch (SQLException e) {
			if(e.getMessage().contains("Username"))
				JOptionPane.showMessageDialog(null, "The username is already taken!Try Again.");
			if(e.getMessage().contains("Email"))
				JOptionPane.showMessageDialog(null, "Existing Member! Cancel and Login.");	
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);	
		}
		finally {
			try {
				statement.close();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1);
			}
		}
		return false;
	}

	/**
	 * Adds a new project to the database.
	 * @param name
	 * @param budget
	 * @param startDate
	 * @param endDate
	 * @param status
	 */
	public void addProjecttoDatabase(String name, double budget, Date startDate, Date endDate, String status) {
		insertProject(name, budget, startDate, endDate, status);
	}

	/**
	 * Insert the new project to the database.
	 * @param name
	 * @param budget
	 * @param startDate
	 * @param endDate
	 * @param status
	 */
	//Also create project object from controller?
	private void insertProject(String name, double budget, Date startDate, Date endDate, String status) {
		PreparedStatement statement = null;

		try {
			String query = "insert into Projects (Name,Budget,startDate,endDate,status) values (?,?,?,?,?)"; 
			statement = connect.prepareStatement(query);
			statement.setString(1, name);
			statement.setDouble(2, budget);
			statement.setDate(3, startDate);
			statement.setDate(4, endDate);
			statement.setString(5, status);

			statement.execute();	
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);	
		}
		finally {
			try {
				statement.close();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1);
			}
		}
	}

}
