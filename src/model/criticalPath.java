package model;


import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JOptionPane;

import view.criticalPathView;
public class criticalPath {

	
	public criticalPath(){
		criticalpaths = new ArrayList<List<Activity>>();
	}
		
	List<List<Activity>> criticalpaths = null;
	
	SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.CANADA);
	
	
	//this method prints all the critical paths
	public void printAllCriticalPaths(Project p) throws ParseException{
		
		List<Activity> currentList = new ArrayList<Activity>();
		ArrayList<Activity> list = p.getProjectActivities();
		
		//Calls the method to get the critical paths with all the final activities
		for(Activity x : list)
			if(format.parse(x.getFinishDate()).equals(format.parse(p.getFinishDate())))
				getAllCriticalPaths(p,x,currentList);
				
		
		
		//prints all the paths
		int i = 1;
		for(List<Activity> x : criticalpaths){
			System.out.println("Critical path #"+ i);
			for(Activity y : x){
				System.out.println(y.getName());
			}
			i++;
		}
		
		if(criticalpaths.size() == 0)
			JOptionPane.showMessageDialog(null, "The project you selected doesn't have a critical path");
		else{
			criticalPathView criticalView = new criticalPathView(criticalpaths);
			criticalView.setVisible(true);
			criticalView.setSize(500,500);
		}//print it with gui here 
		
		
		
		
		
		
		
		
	}
	
	//this method gets all the critical paths, works with recursion
	public List<List<Activity>> getAllCriticalPaths(Project p, Activity finalActivity, List<Activity> currentList)throws ParseException{
		
		List<Activity> temporary = new ArrayList<Activity>();
		for(Activity acts : currentList)
			temporary.add(clone(acts));
		
		temporary.add(clone(finalActivity));
		//gets all the prereq of the activity that was in the method call
		//the first call is made in the method printAllCriticalPaths with the final activities
		ArrayList<Activity> preReq = finalActivity.getPreReq();
		
		
		//If the activity is a starting activity, adds the currentlist to the List<List<Activity>> then clears it
		if((format.parse(finalActivity.getStartDate())).equals(format.parse(p.getStartDate()))){
			criticalpaths.add(temporary);
			}	
		
		//while the activity has prereq call the method with the prereq as the activity and add that activity to the list
		for(Activity pre : preReq){
			if((format.parse(pre.getFinishDate())).equals(format.parse(finalActivity.getStartDate()))){
				getAllCriticalPaths(p, p.getActivityByName(p,pre.getName()),temporary );
			}	
		}
		return criticalpaths;
			
	}


	//helper methods
	public List<Activity> addToList(List<Activity> x, Activity a){
		x.add(a);
		return x;
	}

	public Activity clone(Activity a){
		try {
			Activity temp = new Activity(a.getParentProjectID(),a.getName(), a.getDescription(), a.getBudget(), a.getStartDate(), a.getFinishDate(), a.getStatus());
			return temp;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	




}

















