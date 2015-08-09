package model;

import java.text.DecimalFormat;
import java.util.Random;

public class ActivityOnArrow extends Activity{

	private float standardDeviation;
	private float expectedDuration;
	private DecimalFormat df = new DecimalFormat("0.00");
	//needed just for testing. TODO: remove
	private Random ran = new Random();
	
	public ActivityOnArrow(Activity act, float sd, float t) throws Exception
	{
		super(act.getParentProjectID(), act.getName(), act.getDescription(), act.getBudget(), act.getStartDate(), act.getFinishDate(), act.getStatus());
		
		setPreReq(act.getPreReq());
		setSuccessors(act.getSuccessors());
		setID(act.getID());
		standardDeviation = sd;
		expectedDuration = t;
	}
	
	public void calculateStandardDeviation()
	{
		//TODO: implement the base class methods being called here
		//standardDeviation = (getPessimisticTime() - getOptimisticTime()) / 6;
		
		//Placeholder
		standardDeviation = ((float)ran.nextInt(10) / 10f);
		System.out.println("DEIVIATION: " + standardDeviation);
	}
	public void calculateExpectedDuration()
	{
		//TODO: implement the base class methods being called here
		//expectedDuration = (getOptimisticTime() + (4*getMostLikelyTime()) + getPessimisticTime() ) / 6;
		
		//Placeholder
		expectedDuration = ((float)ran.nextInt(25) / 25f);
		System.out.println("EXP DATE: " + expectedDuration);
	}
	public float getStandardDeviation() {
		return standardDeviation;
	}
	public void setStandardDeviation(float standardDeviation) {
		this.standardDeviation = standardDeviation;
	}
	public float getExpectedDuration() {
		return expectedDuration;
	}
	public void setExpectedDuration(float expectedDuration) {
		this.expectedDuration = expectedDuration;
	}
	
	public String toString()
	{
		return (getName() + "\n" + df.format(expectedDuration) + "\n" + df.format(standardDeviation));
	}

}
