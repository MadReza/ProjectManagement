package model;

import java.util.ArrayList;

public class Project extends Job{
	
	private int pmID;										// ID of project manager owns this project to be implemented by getting the ID of the logged in Project Manager from login page
	private ArrayList <Activity> projectActivities;			// Project Associated Activities
	
	public Project(int pmID, String name, String description, double budget, String startDate, String finishDate, Status status) throws Exception {
		super( name, description, budget, startDate, finishDate, status);
		setPmID(pmID);
		projectActivities = new ArrayList <Activity>();
	}

	/**
	 * @return the projectActivities
	 */
	public ArrayList <Activity> getProjectActivities() {
		return projectActivities;
	}

	/**
	 * @param projectActivities the projectActivities to set
	 */
	public void setProjectActivities(ArrayList <Activity> arrayList) {
		this.projectActivities = arrayList;
	}

	/**
	 * @return the pmID
	 */
	
	// associates an Activity with a project
	public void addActivity(Activity newActivity){
		projectActivities.add(newActivity);
	}
	
	public void removeActivity(Activity deletedActivity){			// throws an exception if deletedActivity is not enlisted in projectActivities 
		projectActivities.remove(deletedActivity);
	}
	
	/**
	 * @return the sum of the project's activities budgets
	 */
	public double getActivitiesTotalBudget() {
		
		double total = 0;
		
		if(!getProjectActivities().isEmpty()){
			for (Activity activity : getProjectActivities())
				total += activity.getBudget();
		}
		return total;
	}

	/**
	 * @return the pmID
	 */
	public int getPmID() {
		return pmID;
	}

	/**
	 * @param pmID the pmID to set
	 */
	public void setPmID(int pmID) {
		this.pmID = pmID;
	}	
	
	@Override
	// to compare projects by ID
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Project))return false;
	    Project otherProject = (Project)other;
	    if (this.getID() == otherProject.getID()) return true;
		return false;
	}
	
}
