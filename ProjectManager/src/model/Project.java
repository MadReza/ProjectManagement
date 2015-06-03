package model;

public class Project {

	private String managerUsername, name, description;
	private double budget;
	private String startDate, endDate;		
	private Status status;									// ENUM class
	private int ID;	

	// project ID
	//	private int pmID;										// ID of project manager owns this project to be implemented later by getting the ID of the logged Project Manager from login page

	//	private ArrayList <Activity> projectActivities;			// Project Associated Activities

	// Constructor with no ID   ????????? 
	
	public Project(int ID, String managerUsername, String name, String description, Status status, double budget, String startDate, String finishDate) {
		
		this.ID = ID;
		this.managerUsername = managerUsername;
		this.name = name;
		this.description = description;
		this.budget = budget;
		this.startDate = startDate;
		this.endDate = finishDate;
		this.status = status;
		
	}

	public String getManagerUsername() {
		return managerUsername;
	}

	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	protected void setDescription(String description) {
		this.description = description;
	}

	public int getID() {
		return ID;
	}

	protected void setID(int iD) {
		ID = iD;
	}

	public double getBudget() {
		return budget;
	}

	protected void setBudget(double budget) {
		this.budget = budget;
	}

	public String getStartDate() {
		return startDate;
	}

	protected void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	protected void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Status getStatus() {
		return status;
	}

	protected void setStatus(Status status){
		this.status = status;
	}


	@Override
	public boolean equals(Object object) {	
		if (object == null || getClass() != object.getClass() )
			return false;
		if (this == object)
			return true;
		else {
			Project other = (Project) object;
			if ((ID == other.ID) && name.equals(other.name))
				return true;
		}
		return true;
	}

	public String toString() {
		return name;
	}

	/*	
	 * 	
	public int getDuration(){												// return duration in hours
		return (int) ((finishDate.getTime() - startDate.getTime())/60000);
	}
	 */
	/*	*//**
	 * @return the projectActivities
	 *//*
	public ArrayList <Activity> getProjectActivities() {
		return projectActivities;
	}

	  *//**
	  * @param projectActivities the projectActivities to set
	  *//*
	public void setProjectActivities(ArrayList <Activity> projectActivities) {
		this.projectActivities = projectActivities;
	}

	   *//**
	   * @return the pmID
	   *//*

	// associates an Activity with a project
	protected void addActivity(Activity newActivity){
		projectActivities.add(newActivity);
//		newActivity.setparentProjectID(this.getID());
//		newActivity.setID(activityCtr++);
	}

	protected void removeActivity(Activity deletedActivity){			// throws an exception if deletedActivity is not enlisted in projectActivities 
		projectActivities.remove(deletedActivity);
	}

	    *//**
	    * @return the sum of the project's activities budgets
	    *//*
	protected double getActivitiesTotalBudget() {

		double total = 0;
		for (Activity activity : projectActivities)
			total += activity.getBudget();
		return total;
	} */
}
