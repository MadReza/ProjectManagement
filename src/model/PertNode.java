package model;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;


public class PertNode implements Comparable{
	private int ID;
	private float expectedDate;
	private float targetDate;
	private float standardDeviation;
	private ArrayList<ActivityOnArrow> inActivity;
	private ArrayList<ActivityOnArrow> outActivity;
	private  DecimalFormat df = new DecimalFormat("0.00");
	
	public PertNode(int ID)
	{
		this.ID = ID;
		this.expectedDate = 0.0f;
		this.targetDate = 0.0f;
		this.standardDeviation = 0.0f;
		inActivity = new ArrayList<ActivityOnArrow>();
		outActivity = new ArrayList<ActivityOnArrow>();
	}
	public PertNode(int ID, int expectedDate, int targetDate, float standardDeviation, ActivityOnArrow in, ActivityOnArrow out)
	{
		this.ID = ID;
		this.expectedDate = expectedDate;
		this.targetDate = targetDate;
		this.standardDeviation = standardDeviation;
		inActivity = new ArrayList<ActivityOnArrow>();
		outActivity = new ArrayList<ActivityOnArrow>();
		if(in != null)
			inActivity.add(in);
		if(out != null)
			outActivity.add(out);
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public float getExpectedDate() {
		return expectedDate;
	}
	public void setExpectedDate(float expectedDate) {
		this.expectedDate = expectedDate;
	}
	public float getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(float targetDate) {
		this.targetDate = targetDate;
	}
	public float getStandardDeviation() {
		return standardDeviation;
	}
	public void setStandardDeviation(float standardDeviation) {
		this.standardDeviation = standardDeviation;
	}
	public void addInActivity(ActivityOnArrow in)
	{
		inActivity.add(in);
	}
	public void addOutActivity(ActivityOnArrow out)
	{
		outActivity.add(out);
	}
	public boolean containsInActivity(ActivityOnArrow in)
	{
		return inActivity.contains(in);
	}
	public boolean containsOutActivity(ActivityOnArrow out)
	{
		return outActivity.contains(out);
	}
	public ArrayList<ActivityOnArrow> getInActivity() {
		return inActivity;
	}
	public void setInActivity(ArrayList<ActivityOnArrow> inActivity) {
		this.inActivity = inActivity;
	}
	public ArrayList<ActivityOnArrow> getOutActivity() {
		return outActivity;
	}
	public void setOutActivity(ArrayList<ActivityOnArrow> outActivity) {
		this.outActivity = outActivity;
	}
	public String toString()
	{
		return (ID + "    " + df.format(targetDate) + "\n" + df.format(expectedDate) + "     " + df.format(standardDeviation));
		/*String str = "" + ID + "\n";
		if(inActivity.size() > 0)
			str += "IN:" + inActivity.get(0) + "\n";
		if(outActivity.size() > 0)
			str += "OUT:" + outActivity.get(0);
		return str;*/
	}
	@Override
	public int compareTo(Object obj) {
		// TODO Auto-generated method stub
		PertNode objNode = (PertNode)obj;
		
		if(this.getID() < objNode.getID())
			return -1;
		if(this.getID() > objNode.getID())
			return 1;
		return 0;
	}
}
