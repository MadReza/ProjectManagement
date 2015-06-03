package model;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

import net.proteanit.sql.DbUtils;


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
				"CREATE TABLE IF NOT EXISTS Members (Name TEXT PRIMARY KEY NOT NULL, Email TEXT UNIQUE, "
						+ "Username TEXT UNIQUE, Password TEXT);" ,

				"CREATE TABLE IF NOT EXISTS Projects (ID INTEGER PRIMARY KEY AUTOINCREMENT, ManagerUsername TEXT NOT NULL, Name TEXT NOT NULL UNIQUE, Description TEXT, "
						+ "Status TEXT, Budget DOUBLE, StartDate TEXT, EndDate TEXT);" ,

				"CREATE TABLE IF NOT EXISTS Activities (ID INTEGER PRIMARY KEY AUTOINCREMENT,projectID INTEGER NOT NULL, Name TEXT UNIQUE,"
						+ "Description TEXT, StartDate TEXT, EndDate TEXT, Status TEXT);",
/*
				" CREATE TABLE IF NOT EXISTS pmProjects (Username TEXT NOT NULL, projectID INTEGER NOT NULL,"
						+ " FOREIGN KEY(Username) REFERENCES Members(Username) ON DELETE CASCADE, FOREIGN KEY(projectID) REFERENCES Projects(ID) ON DELETE CASCADE);",
*/
				"CREATE TABLE IF NOT EXISTS ProjectActivities (projectID INTEGER NOT NULL, "
						+ "activityID INTEGER NOT NULL, FOREIGN KEY(projectID) REFERENCES Projects(ID) ON DELETE CASCADE,"
						+ " FOREIGN KEY(activityID) REFERENCES Activities(ID) ON DELETE CASCADE);",

				"CREATE TABLE IF NOT EXISTS PreReqActivities (activityID INTEGER NOT NULL, preReqID INTEGER NOT NULL, FOREIGN KEY(activityID)"
						+ " REFERENCES Activities(ID) ON DELETE CASCADE, FOREIGN KEY(preReqID) REFERENCES Activities(ID) ON DELETE CASCADE);",

			/*	"CREATE TRIGGER IF NOT EXISTS DeleteProject BEFORE DELETE ON Projects BEGIN DELETE FROM Activities WHERE ID IN "
						+ "(SELECT activityID FROM Activities WHERE ParentProjectID = OLD.ID); END;" */
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
				statement.close(); // clearing the statement necessary?
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1);
			}
		}
		return 0;
	}

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
	 * Inserts Member-Entry into the Members table in the database.
	 * @param name
	 * @param role
	 * @param username
	 * @param password
	 */
	protected void addMember(String name, String email, String username, String password) {

		try {
			String query = "INSERT INTO Members (Name,Email,UserName,Password) VALUES (?,?,?,?);";
			prepStatement = connection.prepareStatement(query);
			prepStatement.setString(1, name);
			prepStatement.setString(2, email);
			prepStatement.setString(3, username);
			prepStatement.setString(4, password);

			boolean result = prepStatement.execute();

			System.out.println("Result set for member insertion = " + result);

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
				prepStatement.close();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1);
			}
		}

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

		//	associateProjectwithManager(user, ID);

		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);	
		}


	}


/*	
 * DISREGARD FOR NOW. Addded the "ManagerUsername" Attribute to a Project.
 * *//**
	 * Associate a project with the project manager who created it.
	 * @param username
	 * @param projectID
	 * @throws SQLException
	 *//*
	private void associateProjectwithManager(String username, int projectID) throws SQLException {

		String query = "INSERT INTO pmProjects (Username, projectID) VALUES (?,?);";
		prepStatement = connection.prepareStatement(query);
		prepStatement.setString(1, username);
		prepStatement.setInt(2, projectID);

		int isAssociated = prepStatement.executeUpdate();
		System.out.println("Associated :" + isAssociated);
		prepStatement.close();

	}
*/


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
	protected void updateProject(int ID, String managerUsername,String name, String description, String status, double budget, String startDate, String endDate) {
		try {
			//	statement = connection.createStatement();
			String aQuery = " UPDATE Projects SET ManagerUsername = '" + managerUsername
					+ "', Name = '"                 + name
					+ "', Description = '" 			+ description
					+ "', Status = '" 				+ status
					+ "', Budget = '"				+ budget
					+ "', StartDate = '"			+ startDate
					+ "', EndDate = '"				+ endDate
					+ "' WHERE ID = "				+ ID + "; ";
			statement.executeUpdate(aQuery);
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


	protected void deleteProject(Project project) {
		int ID = project.getID();
		String [] queries = { "DELETE FROM Projects where ID = '" + ID + "';" , 
				"DELETE FROM pmProjects where projectID = '" + ID + "';" } ;
		try {
			statement = connection.createStatement();
			for (String query : queries) {
				statement.addBatch(query);
			}
			statement.executeBatch();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);	
		}
		finally {
			try {
				statement.close();
				JOptionPane.showMessageDialog(null, "Project Deleted!");
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1);
			}
		}
	}


	/**
	 * Retrieve all projects for the current user (manager) from the database.
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

	



	protected void addActivity(int projectID, String name, String description, String startDate, String endDate, String status) {

		try {
			String query = "INSERT INTO Activities (projectID, Name,Description,StartDate,EndDate,Status) VALUES (?,?,?,?,?,?);"; 
			prepStatement = connection.prepareStatement(query);
			prepStatement.setInt(1, projectID);
			prepStatement.setString(2, name);
			prepStatement.setString(3, description);
			prepStatement.setString(4,startDate);
			prepStatement.setString(5,endDate);
			prepStatement.setString(6, status);			

			boolean result = prepStatement.execute();
			System.out.println("Activity insertion = " + result);
		//	associateActivityWithProject(projectID, activityID);

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
	 * Associate a Activity with the project manager who created it.
	 * @param username
	 * @param projectID
	 * @throws SQLException
	 */
	private void associateActivityWithProject(int projectID, int activityID){
		try{
			String query = " INSERT INTO ProjectActivities (projectID, activityID) VALUES (?,?);";
			prepStatement = connection.prepareStatement(query);
			prepStatement.setInt(1, projectID);
			prepStatement.setInt(2, activityID);

			int isAssociated = prepStatement.executeUpdate();
			System.out.println("Associated :" + isAssociated);
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

/*
	public ArrayList<Activity> getAllActivities()throws Exception {

		String aQuery = " SELECT * FROM Activities, ProjectActivities WHERE ID = activityID;";
		results = statement.executeQuery(aQuery);
		ArrayList<Activity> activityList = new ArrayList<Activity>();

		while (results.next()) {

			int parentProjectID = results.getInt("ID");
			
			String name = results.getString("Name");
			String description = results.getString("Description");
			String startDate = results.getString("StartDate");
			String endDate = results.getString("EndDate");
			Status status = Status.values()[results.getInt("Status")];
			Activity activity = new Activity(parentProjectID,name, description, startDate, endDate, status);
			activityList.add(activity);
		}
		return activityList;
	}*/
	
	/**
	 * Retrieve all activities for the current project from the database.
	 */
	protected ArrayList<Activity> getAllActivities(int projectID) throws Exception {
		String query1 = "SELECT * FROM Activities WHERE projectID = '" + projectID + "' ;" ;
		results = statement.executeQuery(query1);

		ArrayList <Activity> activities = new ArrayList<Activity>();
		while(results.next()) {
			int ID = results.getInt("ID");
			int pID = results.getInt("projectID");
			String name = results.getString("Name");
			String description = results.getString("Description");
			String startDate = results.getString("StartDate");
			String endDate = results.getString("EndDate");
			Status status = Status.values()[results.getInt("Status")];

			Activity activity = new Activity(ID, pID, name, description,startDate, endDate, status);
			activities.add(activity);
		}
		System.out.println("Got Activities for project " + projectID);
		results.close();
		statement.close();


		return activities;
	
	}
/*	
	// returns an ArrayList of activities associated with a particular project
	protected ArrayList<Activity> getProjectActivities(int parentID) throws Exception {

		String aQuery = "SELECT * FROM Activities, ProjectActivities WHERE projectID = " + parentID + " AND ID = activityID;";
		results = statement.executeQuery(aQuery);
		ArrayList<Activity> projectActivityList = new ArrayList<Activity>();

		while (results.next()) {

			int parentProjectID = results.getInt("ID");
			String name = results.getString("Name");
			String description = results.getString("Description");
			String startDate = results.getString("StartDate");
			String endDate = results.getString("EndDate");
			Status status = Status.values()[results.getInt("Status")];
			Activity activity = new Activity(parentProjectID,name, description, startDate, endDate, status);
			projectActivityList.add(activity);
		}
		return projectActivityList;
	}
*/
	// update an existing activity
	protected void updateActivity(int ID, String name, String description, String startDate, String endDate, String status) throws SQLException {
		String aQuery = " UPDATE Activities SET Name = '"	+ name
				+ "', Description = '"				+ description
				+ ", startDate = '"					+ startDate
				+ "', finishDate = '"				+ endDate
				+ "', Status = "					+ status
				+ " WHERE ID = "					+ ID + "; ";
		prepStatement.executeUpdate(aQuery);
	}
	// get Last Activity ID
/*	protected Activity getLastActivity() throws Exception {

		String aQuery = "SELECT * FROM Activities WHERE ID = (SELECT MAX(ID) FROM Activities);";
		results = statement.executeQuery(aQuery);

		if (results.next()) {
			int parentProjectID = results.getInt("pmID");
			String name = results.getString("Name");
			String description = results.getString("Description");
			String startDate = results.getString("startDate");
			String endDate = results.getString("finishDate");
			Status status = Status.values()[results.getInt("Status")];
			Activity activity = new Activity(parentProjectID, name, description, startDate, endDate, status);
			return activity;
		} else {
			throw new Exception("No Existing Projeccts");
		}
	}
*/

	protected void updateActivityTable(JTable activityTable, int projectID) {
		try {
			String query = " SELECT Name FROM Activities WHERE projectID = '" + projectID + "';" ;
			prepStatement = connection.prepareStatement(query);
			results = prepStatement.executeQuery();
			activityTable.setModel(DbUtils.resultSetToTableModel(results));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	protected void updateProjectTable(JTable projectTable, String username) {
		try {
		String query = "SELECT Name FROM Projects WHERE ManagerUsername = '" + username + "' ;" ;
		prepStatement = connection.prepareStatement(query);
		results = prepStatement.executeQuery();
		projectTable.setModel(DbUtils.resultSetToTableModel(results));
		
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


}


