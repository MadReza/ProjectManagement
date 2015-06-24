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
	//****************************************** Methods Related to Member Information ****************************************************	
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


	//****************************************** Methods Related to Project Information ****************************************************	
	/**
	 * Verifying if the name is unique so it doesn't get added twice to the database.
	 * @param currentUser
	 * @param projectName
	 * @return
	 */
	public boolean isProjectNameValid(String currentUser, String projectName) {
		allProjects = getAllProjects(currentUser);
		for(Project project : allProjects) {
			if(project.getName().equals(projectName))
				return false;
		} return true;
	}

	/*public boolean isNameValid(String currentUser, String projectName) {
		allProjects = getAllProjects(currentUser);
		for(Project project : allProjects) {
			if(project.getName().equals(projectName))
				return false;
		} return true;
	}*/

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

	/**
	 * To refresh the list of projects after adding/removing/editing a project.
	 * @param username
	 * @return
	 */
	public ResultSet getResultSetOfAllProjectsForUser(String username) {
		return database.getResultSetOfAllProjectsForUser(username);
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


	//****************************************** Methods Related to Activity Information ****************************************************
	/**
	 * Verifying if the activity we're adding is unique.
	 * @param currentProject
	 * @param activityName
	 * @return
	 * @throws Exception
	 */
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
	public void addActivityToDatabase(int parentID, String name, String description, double budget, int duration, int ES, int EF,
			int LS, int LF, String status, JTable table) throws Exception {
		int activityCounter = 0;
		Status statusEnum = Status.valueOf(status.toUpperCase());

		try {
			activityCounter = database.getIndexOfLastAddedActivity();
			database.addActivity(parentID, name, description, budget, duration, ES, EF, LS, LF, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int ID = activityCounter + 1;	
		currentActivity = new Activity(parentID, ID, name, description, budget, duration, ES, EF, LS, LF, statusEnum);

		System.out.println("created Project object.: Project ID" + currentProject.getID());

	}

	/**
	 * Updates the activity information in the database.
	 * @param name
	 * @param description
	 * @param budget
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @throws Exception
	 */
	public void updateActivityInDatabase(String name, String description, double budget,  int duration, int ES, int EF,
			int LS, int LF, String status) throws Exception {

		Status statusEnum = Status.valueOf(status.toUpperCase());
		int ID = currentActivity.getID();
		int parentProjectID = currentActivity.getParentProjectID();
		Activity activity = new Activity(ID, parentProjectID, name, description, budget, duration, ES, EF, LS, LS, statusEnum); //Set currentProject to the created project

		database.updateActivity(ID,name, description, budget, duration, ES, EF, LS, LS, status);
		System.out.println("Updated Activity.");
		setCurrentActivity(activity); //update currentActivity

	}

	/**
	 * Deletes a selected activity from the database.
	 * @param activity
	 */
	public void deleteActivityFromDatabase(Activity activity) {
		System.out.println("Deleting Activity : " + activity.getName());
		database.deleteActivity(activity);
		System.out.println("Activity deleted.");

		//Update references
		setCurrentActivity(null);
	}

	
	/**
	 * Associate a selected activity with chosen prerequisites.
	 * @param selectedActivity
	 * @param prereqs
	 */
	public void associateActivityWithPrerequisites(String selectedActivity, ArrayList<Activity> prereqs) {
		int selectedActivityID = getActivityByName(selectedActivity).getID();
		database.deleteAssociatedPrerequisites(selectedActivityID);
		if(!prereqs.isEmpty())
			database.associateActivityWithPrerequisites(selectedActivityID, prereqs);
		
	}

	/**
	 * Returns the set of all activities for the project currently being viewed.
	 * @param projectID
	 * @return result set of all activities of the current project.
	 */
	public ResultSet getResultSetForAllActivitiesOfProject(int projectID) {
		return database.getResultSetForAllActivitiesOfProject( projectID);
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

	
	/**
	 * Retrieving all activities associated to a project from the database.
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Activity> getAllActivities() {
		try {
			return database.getAllProjectActivities(currentProject.getID());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allProjectActivities;
	}

	
	/**
	 * Retrieving a specific activity by it's ID which is auto-incremented in the database.
	 * @param aID
	 * @return
	 * @throws Exception
	 */
	protected Activity getActivityByID(int activityID) {
		try {
			allProjectActivities = getAllActivities();
			for(Activity activity : allProjectActivities) {
				if (activity.getID() == activityID) {
					return activity;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	
	/**
	 * Construct an arraylist of activities associated with the activityIDs.
	 * @param activityIDs
	 * @return arraylist of activities.
	 */
	private ArrayList<Activity> constructArrayListOfActivities(ArrayList<Integer> activityIDs) {
		ArrayList<Activity> activities = new ArrayList<Activity>();
		if (!activityIDs.isEmpty()) {
			for(int activityID : activityIDs) {
				activities.add(getActivityByID(activityID));
			}	
		}
		return activities;	
	}

	
	/**
	 * Retrieves activites that can be set as prerequisites.
	 * @param id
	 * @param activityName
	 * @return arraylist of available activities.
	 * @throws Exception
	 */
	public ArrayList<Activity> getAvailableActivities(int id, String activityName) throws Exception {
		int selectedActivityID = getActivityByName(activityName).getID();
		ArrayList<Integer> availableChoices;
	
		availableChoices = database.getAvailableChoicesForPrerequisites(id, selectedActivityID);
		return constructArrayListOfActivities(availableChoices);
	}

	
	/**
	 * Retrieves activities that have been set as prerequisites.
	 * @param activityName
	 * @return arraylist of chosen activities
	 * @throws Exception
	 */
	public ArrayList<Activity> getSelectedPrerequisties(String activityName) throws Exception {
		int selectedActivityID = getActivityByName(activityName).getID();
		ArrayList<Integer> chosenPrereqIDs;
	
		chosenPrereqIDs = database.getSelectedPrereqs(selectedActivityID);
		return constructArrayListOfActivities(chosenPrereqIDs);
	}

}
