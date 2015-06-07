package model;

/*import java.sql.Date;*/
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

public class MainModel {

	
	private static MainModel modelInstance = null; // Singleton Pattern
	private Database database; 
	private String currentUser;
	private Project currentProject;
	private Activity currentActivity;
	private ArrayList<Project> allProjects;
	private ArrayList<Activity> allProjectActivities;
	
	
	/**
	 * Private constructor that creates an instance of Database.
	 * Ensuring a single connection throughout the application.
	 * @throws Exception
	 */
	private MainModel() throws Exception {	
		database = new Database();
	}
	
	//Ensures only one instance (singleton) is created.
	public static MainModel getInstance() throws Exception {	
		if (modelInstance == null) {
			modelInstance = new MainModel();
		}
		return modelInstance;
	}
	
	public Project getCurrentProject() {
		return currentProject;
	}
	
	public void setCurrentProject(Project project) {
		currentProject = project;
	}
	
	public Activity getCurrentActivity() {
		return currentActivity;
	}

	public void setCurrentActivity(Activity activity) {
		currentActivity = activity;
	}
	
	/**
	 * Validates user login data.
	 * @param username
	 * @param password
	 */
	public boolean validateLoginInformation(String username, String password) {	
		if(database.verifyMember(username, password) == 1) {
			currentUser = username;
			return true;
		}
		return false;
	}
	
	/**
	 * Validates user input for a new user account.
	 * @param email
	 * @param username
	 * @return true if user input is valid, false otherwise.
	 */
	public int validateNewMemberInformation(String email, String username) {
		int signupStatus = database.validateNewMemberInformation(email, username);
		System.out.println("validate member login : " + signupStatus);
		return signupStatus;
	}
	
	/**
	 * Adds a new member to the database.
	 * @param name
	 * @param role
	 * @param username
	 * @param password
	 * @throws SQLException 
	 */
	public void addMemberToDatabase(String name, String email, String role,String username, String password) throws Exception {
		database.addMember(name, email, role, username, password);
		currentUser = username;	
	}

	
	public boolean isProjectNameValid(String currentUser, String projectName) {
		allProjects = getAllProjects(currentUser);
		for(Project project : allProjects) {
			if(project.getName().equals(projectName))
				return false;
		} return true;
	}
	
	/**
	 * Adds a new project to the database.
	 * @param name
	 * @param budget
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @throws SQLException 
	 * @throws Exception 
	 */
	public void addProjectToDatabase(String currentUser, String name, String description,String status, double budget,  String startDate, String endDate) {
		int projectCounter = 0;
		int ID = 0;
		Status statusEnum = Status.valueOf(status.toUpperCase());
		
		try {
			projectCounter = database.getIndexOfLastAddedProject();
			ID = projectCounter + 1 ;
			database.addProject(currentUser, name, description, status, budget, startDate, endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Project project = new Project(ID,currentUser, name, description, statusEnum, budget, startDate, endDate);
		setCurrentProject(project); //Set currentProject to the new project created
		
		System.out.println("created Project object.: Project ID" + currentProject.getID());
	}
	
		
	public boolean isActivityNameValid(String currentProject, String activityName) throws Exception {
		allProjectActivities = getAllActivities();
		for(Activity activity : allProjectActivities) {
			if(activity.getName().equals(activityName))
				return false;
		} return true;
	}
	


		
	/**
	 * Adds a new activity to the database.
	 * @param name
	 * @param budget
	 * @param startDate
	 * @param endDate
	 * @param status
	 */
	public void addActivityToDatabase(int parentID, String name, String description,double budget, String startDate, String endDate,String status, JTable table) throws Exception {
		int activityCounter = 0;
		Status statusEnum = Status.valueOf(status.toUpperCase());

		try {
			activityCounter = database.getIndexOfLastAddedActivity();
			database.addActivity( parentID, name, description,budget, startDate, endDate,status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int ID = activityCounter + 1;	
		currentActivity = new Activity(parentID, ID, name, description, budget, startDate, endDate, statusEnum);

		System.out.println("created Project object.: Project ID" + currentProject.getID());
	
	}
	
	//-------------DO WE NEED THESE ? -------------------------------------//
		/*// set this activity ID which is handled by the database (auto increment)
		int lastActivityID = database.getLastActivity().getID();
		currentActivity.setID(lastActivityID);*/

		/*// check if Activity is suitable to be associated to a parent Project
		if( ! ActivitySuitsProject(currentActivity.getID(), parentID,  message) ){
			throw new Exception(message);*/
	
	//------------------------------------------------------------------------------//
		

	public void updateProjectInDatabase(String name, String description,String status, double budget,  String startDate, String endDate) throws SQLException {
		
		Status statusEnum = Status.valueOf(status.toUpperCase());
		int ID = currentProject.getID();
		String manager = currentProject.getManagerUsername();
		Project project = new Project(ID, manager, name, description, statusEnum, budget, startDate, endDate); //Set currentProject to the created project
		
		database.updateProject(ID,manager, name, description, status, budget, startDate, endDate);
		
		System.out.println("Updated project.");
		setCurrentProject(project); //update currentProject
	
	}
	
	public void deleteProjectFromDatabase(Project project) {
		System.out.println("Deleting Project : " + project.getName());
		database.deleteProject(project);
		System.out.println("Project deleted.");
		
		//Update references
		setCurrentProject(null);
	}
	
	public void updateActivityInDatabase(String name, String description, double budget,  String startDate, String endDate,String status) throws Exception {

		Status statusEnum = Status.valueOf(status.toUpperCase());
		int ID = currentActivity.getID();
		int parentProjectID = currentActivity.getParentProjectID();
		Activity activity = new Activity(ID, parentProjectID, name, description, budget, startDate, endDate, statusEnum); //Set currentProject to the created project

		database.updateActivity(ID,name, description, budget, startDate, endDate,status);
		System.out.println("Updated project.");
		setCurrentActivity(activity); //update currentProject

	}
	
	public void deleteActivityFromDatabase(Activity activity) {
		System.out.println("Deleting Activity : " + activity.getName());
		database.deleteActivity(activity);
		System.out.println("Activity deleted.");

		//Update references
		setCurrentActivity(null);

	}
	
	public boolean isNameValid(String currentUser, String projectName) {
		allProjects = getAllProjects(currentUser);
		for(Project project : allProjects) {
			if(project.getName().equals(projectName))
				return false;
		} return true;
	}
	
	public ResultSet updateActivityTable(int projectID) {
		return database.updateActivityTable( projectID);
	}
	
	public ResultSet updateProjectTable(String username) {
		return database.updateProjectTable(username);
	}
	
	
	public ArrayList<Project> getAllProjects(String user) {
			try {
				allProjects = database.getAllProjects(user);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
			return allProjects;
	}
	
	public Project getProjectByName(String currentUser, String selectedProject) {
		try {
		allProjects = getAllProjects(currentUser);
		for(Project project : allProjects) {
			if (project.getName().equals(selectedProject)) {
				currentProject = project; //update currentProject
				return project;
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Activity getActivityByName(String selectedActivity) {
		try {
			allProjectActivities = getAllActivities();
			for(Activity activity : allProjectActivities) {
				if (activity.getName().equals(selectedActivity)) {
					currentActivity = activity; //update currentProject
					return activity;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*public Project getProjectByID(int selectedProject) throws Exception {

		allProjects = getAllProjects();
		for(Project project : allProjects) {
			if (project.getID() == selectedProject) {
				currentProject = project; //update currentProject
				return project;
			}
		}
		return null;	
	}*/
	


		// associates the new Activity with the parentProject in the database table ProjectActivities
		//database.associateActivity(lastActivityID, parentID);
	
/*private boolean ActivitySuitsProject(int aID, int pID, String message) throws Exception {
		
		Activity activity = getActivityByID(aID);
		Project project = getProjectByID(pID);
		
		
		// checks prerequisites
		if (! activity.getPreReq().isEmpty() ){
			message = "Adjust: The activity has prerequisites";
			return false;
			}
		
		// checks successors
		 if(! activity.getSuccessors().isEmpty() ) {
			 message = "Adjust: The activity has successors";
			 return false;
		 }
		 
		// Check dates boundaries  already checked in the constructors
		if (activity.getStartDate().compareTo(project.getStartDate()) < 0) {
			message = "Adjust: the Activity can't precede its parent project";
			return false;
			}
		
		if (activity.getEndDate().compareTo(project.getEndDate()) > 0) {
			message = "Adjust: the Activity Finish date can't follow its parent project Finish date";
			return false;
			}
		// otherwise
		return true;
	}*/
	
	// 
		public ArrayList<Activity> getAllActivities() throws Exception {
			return database.getAllActivities(currentProject.getID());
		}

		protected Activity getActivityByID(int aID) throws Exception {
			return getAllActivities().get(aID);
		}



}
