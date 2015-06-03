package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Activity {

	private String name, description;
	private int ID,parentProjectID;
	private String finishDate, startDate;	
	private Status status;							// ENUM class
	private ArrayList <Activity> preReq;			// list of prerequisite activities
	private ArrayList <Activity> successors;		// list of Activities that depend on this Activity
	//	private ArrayList <Member> activityTeam;		// members who are assigned to this activity        			next Iteration

	protected Activity (int ID,int parentProjectID, String name, String description, String startDate, String finishDate, Status status) throws Exception {
		this.ID = ID;
		this.parentProjectID = parentProjectID;
		setName(name);
		setDescription(description);
		setStartDate(startDate);
		setFinishDate(finishDate);
		setStatus(Status.LOCKED);				// any activity is locked by default until checking its prerequisites are finished
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) throws Exception {
		if (iD < 0){
			throw new Exception("Invalid ID");
		}
		ID = iD;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) throws Exception {

		String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		if( startDate.compareTo(today) < 0) {
			throw new Exception("Can't start in the past");
		}

		this.startDate = startDate;
	}

	public String getEndDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) throws Exception {

		String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		if( finishDate.compareTo(today) < 0) {
			throw new Exception("Can't start in the past");
		}

		if( finishDate.compareTo(this.getStartDate()) < 0 ){
			throw new Exception("Can't finish before the Start date");
		}
		this.finishDate = finishDate;
	}


	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status){
		this.status = status;
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

	/**
	 * @param parentProjectID the parentProjectID to set
	 */
	public void setParentProjectID(int parentProjectID) {
		this.parentProjectID = parentProjectID;
	}
}