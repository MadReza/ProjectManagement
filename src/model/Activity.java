package model;

import java.util.ArrayList;

public class Activity extends Job{

	private int parentProjectID;
	private ArrayList <Activity> preReq;			// list of prerequisite activities
	private ArrayList <Activity> successors;		// list of Activities that depend on this Activity
	private ArrayList <Member> activityTeam;		// members who are assigned to this activity        			Iteration 2
	
	public Activity (int parentProjectID, String name, String description, double budget, String startDate, String finishDate, Status status) throws Exception {
		super(name, description, budget, startDate, finishDate, status);
		setParentProjectID(parentProjectID);
		preReq = new ArrayList <Activity>();
		successors = new ArrayList <Activity>();
		activityTeam = new ArrayList <Member>();
	}

	
	public ArrayList<Member> getActivityTeam() {
		return activityTeam;
	}

	public void setActivityTeam(ArrayList<Member> activityTeam) {
		this.activityTeam = activityTeam;
	}

	public void addMemberToTeam(Member member){
		this.activityTeam.add(member);
	}

	public void removeMemberFromTeam(Member member){
		this.activityTeam.remove(member);
	}
	
	public ArrayList<Activity> getPreReq() {
		return preReq;
	}

	public void setPreReq(ArrayList<Activity> preReq) {
		this.preReq = preReq;
	}
	
	public void addpreReq(Activity newActivity){
		preReq.add(newActivity);
	}
	
	public void removepreReq(Activity deletedActivity){
		preReq.remove(deletedActivity);
	}
	
	public ArrayList<Activity> getSuccessors() {
		return successors;
	}

	public void setSuccessors(ArrayList<Activity> successors) {
		this.successors = successors;
	}

	public int getParentProjectID() {
		return parentProjectID;
	}

	public void setParentProjectID(int parentProjectID) {
		this.parentProjectID = parentProjectID;
	}

	@Override
	// to compare activities by ID
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Activity))return false;
	    Activity otherActivity = (Activity)other;
	    if (this.getID() == otherActivity.getID()) return true;
		return false;
	}
		
	/*	
	public int getDuration(){												// return duration in hours
		return (int) ((finishDate.getTime() - startDate.getTime())/60000);
	}
*/
}
