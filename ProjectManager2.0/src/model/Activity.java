package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Activity {

	private String name, description;
	private int ID,parentProjectID, duration, earliestStart,earliestFinish,latestStart,latestFinish;
	private double budget;
	private String finishDate, startDate;	
	private Status status;							// ENUM class
	private ArrayList <Activity> preReq;			// list of prerequisite activities
	private ArrayList <Activity> successors;		// list of Activities that depend on this Activity
	//	private ArrayList <Member> activityTeam;		// members who are assigned to this activity        			next Iteration

	protected Activity (int ID,int parentProjectID, String name, String description, double budget, int duration, int ES, int EF,
			int LS, int LF, Status status) throws Exception {
		this.ID = ID;
		this.parentProjectID = parentProjectID;
		setName(name);
		setDescription(description);
		setDuration(duration);
		setEarliestStart(ES);
		setEarliestFinish(EF);
		setLatestStart(LS);
		setLatestFinish(LF);
		setBudget(budget);
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

	public void setBudget(double budget) throws Exception{
		if (budget < 0){
			throw new Exception("Invalid Budget");
		}
		this.budget = budget;
	}

	public double getBudget()
	{
		return budget;
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
	
	public void setDuration(int duration) throws Exception
	{
		if (duration < 0){
			throw new Exception("Invalid Duration");
		}
		this.duration = duration;
	}

	public int getDuration(){
		return duration;
	}

	public int getEarliestStart() {
		return earliestStart;
	}

	public void setEarliestStart(int earliestStart) throws Exception {
		if (earliestStart < 0){
			throw new Exception("Invalid Input For Earliest Start");
		}

		this.earliestStart = earliestStart;
	}

	public int getEarliestFinish() {
		return earliestFinish;
	}

	public void setEarliestFinish(int earliestFinish) throws Exception {
		if (earliestFinish < 0){
			throw new Exception("Invalid Input For Earliest Finish");
		}
		this.earliestFinish = earliestStart + this.duration;
	}

	public int getLatestStart() {
		return latestStart;
	}

	public void setLatestStart(int latestStart) throws Exception{
		if (latestStart < 0){
			throw new Exception("Invalid Input For Latest Start");
		}
		this.latestStart = latestStart;
	}

	public int getLatestFinish() {
		return latestFinish;
	}

	public void setLatestFinish(int latestFinish) throws Exception {
		if (latestFinish < 0){
			throw new Exception("Invalid Input For Latest Finish");
		}
		this.latestFinish = latestFinish;
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

	public String toString() {
		return name;
	}
}