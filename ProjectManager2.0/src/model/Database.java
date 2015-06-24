package model;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

//import net.proteanit.sql.DbUtils;


public class Database {

	private String driver;
	private String url;
	private Connection connection;
	private Statement statement;
	private PreparedStatement prepStatement;
	private ResultSet results;
	int statementTimeout;


	public Database() throws SQLException {

		driver = "org.sqlite.JDBC";
		url = "jdbc:sqlite:database.sqlite";
		statementTimeout = 20;

		connect();
		setStatement();
		//statement.executeUpdate("PRAGMA foreign_keys = ON;");
		createTables();

	}

	/**
	 * Creates a database if it does not already exist and establishes a connection with the database.
	 * @return dbConnection - connection to the database.
	 */
	public void connect() {

		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url);
			//	JOptionPane.showMessageDialog(null, "Connection successful!");
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	
	/**
	 * Disconnects the database.
	 * @throws SQLException
	 */
	public void disconnect() throws SQLException {

		if(connection != null) {
			connection.close();
		}
		if(statement != null) {
			statement.close();
		}
	}

	
	private void setStatement() throws SQLException {

		if(connection == null) {
			connect();
		}

		statement = connection.createStatement();
		statement.setQueryTimeout(statementTimeout);
	}


	/**
	 * Creates required tables in the database.
	 * @throws SQLException 
	 */
	private void createTables() throws SQLException {	

		String [] tableQueries = {
				"CREATE TABLE IF NOT EXISTS Members (Name TEXT PRIMARY KEY NOT NULL, Email TEXT UNIQUE, Role TEXT,"
						+ "Username TEXT UNIQUE, Password TEXT);" ,

				"CREATE TABLE IF NOT EXISTS Projects (ID INTEGER PRIMARY KEY AUTOINCREMENT, ManagerUsername TEXT NOT NULL, Name TEXT NOT NULL, Description TEXT, "
						+ "Status TEXT, Budget DOUBLE, StartDate TEXT, EndDate TEXT);" ,


				"CREATE TABLE IF NOT EXISTS Activities (ID INTEGER PRIMARY KEY AUTOINCREMENT, ProjectID INTEGER NOT NULL, Name TEXT,"
						+ "Description TEXT, Budget DOUBLE, Duration INTEGER, EarliestStart INTEGER, EarliestFinish INTEGER, LatestStart INTEGER, LatestFinish INTEGER, Status TEXT);",

				"CREATE TABLE IF NOT EXISTS PrereqActivities (ActivityID INTEGER NOT NULL, PrereqID INTEGER NOT NULL, FOREIGN KEY(activityID)"
						+ " REFERENCES Activities(ID) ON DELETE CASCADE, FOREIGN KEY(preReqID) REFERENCES Activities(ID) ON DELETE CASCADE);",

				"CREATE TRIGGER IF NOT EXISTS DeleteProject BEFORE DELETE ON Projects BEGIN DELETE FROM Activities WHERE ID IN "
						+ "(SELECT ID FROM Activities WHERE ProjectID = OLD.ID); END;" 
		};

		try {
			statement = connection.createStatement();
			for (String query : tableQueries) {
				statement.addBatch(query);
			}

			statement.executeBatch();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		finally {
			try {
				statement.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}

	//****************************************** Methods Related to Member Information ****************************************************
	/**
	 * Verifies and logs into the application if the member exists in the database.
	 * @param username - user input retrieved from usernameTextField
	 * @param password - password retrieved from passwordField
	 */
	protected int verifyMember(String username, String password) {
		try {
			String query = "SELECT COUNT(*) FROM Members WHERE Username = '" + username + "' and Password = '" + password +"' ;"; 
			results = statement.executeQuery(query);

			int count = 0;
			while (results.next()) {
				count = results.getInt(1);
			}
			return count;
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);	
		}
		finally {
			try {
				results.close();
				statement.close(); 
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1);
			}
		}
		return 0;
	}

	
	/**
	 * Inserts Member-Entry into the Members table in the database.
	 * @param name
	 * @param role
	 * @param username
	 * @param password
	 */
	protected boolean addMember(String name, String email, String role, String username, String password) throws SQLException {

		String query = "INSERT INTO Members (Name,Email,Role,Username,Password) VALUES (?,?,?,?,?);";
		prepStatement = connection.prepareStatement(query);
		prepStatement.setString(1, name);
		prepStatement.setString(2, email);
		prepStatement.setString(3, role);
		prepStatement.setString(4, username);
		prepStatement.setString(5, password);

		boolean result = prepStatement.execute();

		System.out.println("Result set for member insertion = " + result);
		return !result;
	}

	/**
	 * Validates member information during Signup.
	 * @param email
	 * @param username
	 * @return 0 - if username and email is not found, 
	 * 		   1 - email AND username is found (duplicate user)
	 * 		   2 - if email is found
	 *         3 - if username is found
	 */
	protected int validateNewMemberInformation(String email, String username) {
		try {
			String query = "SELECT * FROM Members WHERE Email = '" + email + "' OR Username = '" + username +"' ;"; 
			results = statement.executeQuery(query);

			int count = 0;
			while (results.next()) {
				if (email.equals(results.getString(2)) && username.equals(results.getString(4)))
					return 1;
				else if(email.equals(results.getString(2))) //Email is NOT unique
					return 2;
				else if (username.equals(results.getString(4))) //username is NOT unique
					return 3;

			}
			System.out.println(count);
			return count;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				results.close();
				statement.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return 0;
	}


	//****************************************** Methods Related to Project Information ****************************************************
	/**
	 * Returns the index of the last added project. (Max row ID in Projects Table)
	 * @return projectCounter - Max ID
	 */
	protected int getIndexOfLastAddedProject() {
		int projectCounter = 0;
		String query = "SELECT * FROM Projects WHERE ID = (SELECT MAX(ID) FROM Projects);"; 
		try {
			results = statement.executeQuery(query);

			while(results.next())
				projectCounter = results.getInt("ID");
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {

			try {
				results.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		System.out.println("Max index used :" + projectCounter);
		return projectCounter;
	}

	/**
	 * Checks if the user already has a project with the same name.
	 * @param name
	 * @return 0 - chosen name is unique, 1 - chosen name is unique
	 */
	protected int validateProjectName(String name) {

		try {
			String query = "SELECT * FROM Projects WHERE Name = '" + name +"' ;";
			results = statement.executeQuery(query);

			int count = 0;
			while (results.next()) {
				if(name.equals(results.getString(2))) //Name is NOT unique
					return 1;
			}
			return count;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				results.close();
				statement.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return 0;
	}


	/**
	 * Inserts the new project to the database.
	 * @param name
	 * @param budget
	 * @param startDate
	 * @param endDate
	 * @param status
	 */
	protected void addProject(String managerUsername,String name, String description, String status, double budget, String startDate, String endDate) {

		try {
			String query = "INSERT INTO Projects (ManagerUsername,Name,Description,Status,Budget,StartDate,EndDate) VALUES (?,?,?,?,?,?,?);"; 
			prepStatement = connection.prepareStatement(query);

			prepStatement.setString(1,managerUsername);
			prepStatement.setString(2, name);
			prepStatement.setString(3, description);
			prepStatement.setString(4, status);
			prepStatement.setDouble(5, budget);
			prepStatement.setString(6, startDate);
			prepStatement.setString(7, endDate);			

			boolean result = prepStatement.execute();
			System.out.println("Project insertion = " + result);

		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);	
		}
	}


	/**
	 * Updates an existing project in the database.
	 * @param ID
	 * @param name
	 * @param description
	 * @param status
	 * @param budget
	 * @param startDate
	 * @param endDate
	 */
	protected void updateProject(int ID, String managerUsername,String name, String description, String status,
			double budget, String startDate, String endDate) throws SQLException {
		String aQuery = " UPDATE Projects SET ManagerUsername = '" + managerUsername
				+ "', Name = '"                 + name
				+ "', Description = '" 			+ description
				+ "', Status = '" 				+ status
				+ "', Budget = '"				+ budget
				+ "', StartDate = '"			+ startDate
				+ "', EndDate = '"				+ endDate
				+ "' WHERE ID = "				+ ID + "; ";
		statement.executeUpdate(aQuery);
		statement.close();
	}


	/**
	 * Deletes a project from the database.
	 * @param project
	 */
	protected void deleteProject(Project project) {
		int ID = project.getID();
		String query = "DELETE FROM Projects where ID = '" + ID + "';" ; 
		try {
			statement.executeUpdate(query);

		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);	
		}
		finally {
			try {
				JOptionPane.showMessageDialog(null, "Project Deleted!");
				statement.close();		
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1);
			}
		}
	}


	/**
	 * Retrieves all projects for the current user (manager) from the database.
	 */
	protected ArrayList<Project> getAllProjects(String username) throws Exception {

		ArrayList<Project> projectList = new ArrayList<Project>();
		String query = "SELECT * FROM Projects WHERE ManagerUsername = '" + username + "' ;" ;
		results = statement.executeQuery(query);

		while(results.next()) {
			int id = results.getInt("ID");
			String name = results.getString("Name");
			String description = results.getString("Description");
			Status status = Status.values()[results.getInt("Status")];
			double budget = results.getDouble("Budget");
			String startDate = results.getString("StartDate");
			String endDate = results.getString("EndDate");

			Project project = new Project(id, username, name, description, status, budget, startDate, endDate);
			projectList.add(project);
		}
		return projectList;
	}

	protected ResultSet getResultSetOfAllProjectsForUser(String username) {
		try {
			String query = "SELECT Name FROM Projects WHERE ManagerUsername = '" + username + "' ;" ;
			prepStatement = connection.prepareStatement(query);
			results = prepStatement.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	//****************************************** Methods Related to Activity Information ****************************************************
	/**
	 * Returns the index of the last added activity. (Max row ID in Activity Table)
	 * @return
	 */
	protected int getIndexOfLastAddedActivity() {
		int activityCounter = 0;
		String query = "SELECT * FROM Activities WHERE ID = (SELECT MAX(ID) FROM Activities);"; 
		try {
			results = statement.executeQuery(query);

			while(results.next())
				activityCounter = results.getInt("ID");
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				results.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		System.out.println("Max index used :" + activityCounter);
		return activityCounter;
	}

	
	/**
	 * Adds an activity to a selected(current) project.
	 * @param projectID
	 * @param name
	 * @param description
	 * @param budget
	 * @param startDate
	 * @param endDate
	 * @param status
	 */
	protected void addActivity(int projectID, String name, String description, double budget, int duration, int earliestStart, int earliestFinish,
			int latestStart, int latestFinish, String status) {

		try {

			String query = "INSERT INTO Activities (ProjectID,Name,Description,Budget,Duration,EarliestStart,EarliestFinish,LatestStart,LatestFinish,Status) VALUES (?,?,?,?,?,?,?,?,?,?);"; 

			prepStatement = connection.prepareStatement(query);
			prepStatement.setInt(1, projectID);
			prepStatement.setString(2, name);
			prepStatement.setString(3, description);
			prepStatement.setDouble(4,budget);
			prepStatement.setInt(5, duration);
			prepStatement.setInt(6, earliestStart);
			prepStatement.setInt(7, earliestFinish);
			prepStatement.setInt(8, latestStart);
			prepStatement.setInt(9, latestFinish);
			prepStatement.setString(10, status);			

			boolean result = prepStatement.execute();
			System.out.println("Activity insertion = " + result);

		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);	
		}
		finally {
			try {
				prepStatement.close();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1);
			}
		}
	}

	
	/**
	 * Updates an existing activity.
	 * @param ID
	 * @param name
	 * @param description
	 * @param budget
	 * @param startDate
	 * @param endDate
	 * @param status
	 */
	protected void updateActivity(int ID, String name, String description, double budget, int duration, int ES, int EF, int LS, 
			int LF, String status) {
		try{
			String aQuery = " UPDATE Activities SET Name = '"	+ name
					+ "', Description = '"				+ description
					+ "', Budget =  '"					+ budget
					+ "', Duration = '"				    + duration
					+ "', EarliestStart = '"			+ ES
					+ "', EarliestFinish = '"		   	+ EF
					+ "', LatestStart = '"				+ LS
					+ "', LatestFinish = '"			   	+ LF
					+ "', Status = '"					+ status
					+ "' WHERE ID = "					+ ID + "; ";
			statement.executeUpdate(aQuery);
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(null,e);
		}
		finally{
			try{
				statement.close();
			}catch (SQLException e1){
				JOptionPane.showMessageDialog(null, e1);
			}
		}
	}

	
	/**
	 * Deletes an activity from the project.
	 * @param activity
	 */
	protected void deleteActivity(Activity activity) {
		int ID = activity.getID();
		String query = "DELETE FROM Activities WHERE ID = '" + ID + "';";
		try {
			statement.executeUpdate(query);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);	
		}
		finally {
			try {
				statement.close();
				JOptionPane.showMessageDialog(null, "Activity Deleted!");
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1);
			}
		}
	}

	protected void associateActivityWithPrerequisites(int selectedActivityID, int prereqID) {
			try {
					String query = "INSERT INTO PrereqActivities (ActivityID, PrereqID) VALUES (?,?);";
					prepStatement = connection.prepareStatement(query);
					prepStatement.setInt(1, selectedActivityID);
					prepStatement.setInt(2, prereqID);

					prepStatement.executeUpdate();
				}
				catch (SQLException e) {
				e.printStackTrace();
			} 
		}
	
	protected void deleteAssociatedPrerequisites(int selectedActivityID) {
		String query = "DELETE FROM PrereqActivities WHERE ActivityID = " +  selectedActivityID + ";";
		try {
			statement.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves all activities for the current project from the database.
	 */
	protected ArrayList<Activity> getAllProjectActivities(int projectID) throws Exception {
		String query = "SELECT * FROM Activities WHERE ProjectID = '" + projectID + "' ;" ;
		results = statement.executeQuery(query);

		ArrayList <Activity> activities = new ArrayList<Activity>();
		while(results.next()) {
			int ID = results.getInt("ID");
			int pID = results.getInt("ProjectID");
			String name = results.getString("Name");
			String description = results.getString("Description");
			double budget = results.getDouble("Budget");
			int duration = results.getInt("Duration");
			int ES = results.getInt("EarliestStart");
			int EF = results.getInt("EarliestFinish");
			int LS = results.getInt("LatestStart");
			int LF = results.getInt("LatestFinish");
			/*String startDate = results.getString("StartDate");
			String endDate = results.getString("EndDate");*/
			Status status = Status.values()[results.getInt("Status")];

			Activity activity = new Activity(ID, pID, name, description, budget, duration, ES, EF, LS, LF, status);
			activities.add(activity);

		}

		System.out.println("Got Activities for project " + projectID);
		results.close();
		statement.close();
		return activities;

	}

	/**
	 * Returns the set of activities for a given project
	 * @param projectID
	 * @return result set of all activities that's associated with projectID
	 */
	protected ResultSet getResultSetForAllActivitiesOfProject(int projectID) {
		try {
			String query = " SELECT Name FROM Activities WHERE ProjectID = '" + projectID + "';" ;
			prepStatement = connection.prepareStatement(query);
			results = prepStatement.executeQuery();		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	
	/**
	 * Retrieves all activities that can be set as a prerequisite for the selected activity.
	 * @param projectID
	 * @param selectedActivityID
	 * @return an arraylist of available activity IDs
	 * @throws Exception
	 */
	protected ArrayList<Integer> getAvailableChoicesForPrerequisites(int projectID, int selectedActivityID) throws Exception {
		
		ArrayList<Integer> availableChoices = new ArrayList<Integer>();
		String query = " SELECT ID FROM Activities WHERE (ProjectID = " + projectID +
				" AND ID != " + selectedActivityID + ")" +
				" EXCEPT " +
				" SELECT PrereqID FROM PrereqActivities WHERE ActivityID = " + selectedActivityID + " ;" ;

		results = statement.executeQuery(query);

		while (results.next()) {
			availableChoices.add(results.getInt("ID")); 
		}
		return availableChoices;
	}

	
	/**
	 * Retrieves the activities that have been set as a prerequisite for the selected activity.
	 * @param selectedActivityID
	 * @return an arraylist of the IDs of the activities that have already been chosen as prereqs.
	 * @throws Exception
	 */
	protected ArrayList<Integer> getSelectedPrereqs(int selectedActivityID) throws Exception {
		
		ArrayList<Integer> selectedPrereqIDs = new ArrayList<Integer>();
		String query = "SELECT PreReqID FROM PrereqActivities WHERE ActivityID = '" + selectedActivityID + "';" ;
		
		results = statement.executeQuery(query);

		while(results.next()) {
			selectedPrereqIDs.add(results.getInt("PrereqID"));	
		}
		return selectedPrereqIDs;	
	}
}


