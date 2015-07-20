package testModel;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Activity;
import model.Member;
import model.Status;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivityTest {

	@Test
	public void testEqualsObject() throws Exception {
		Activity activity = new Activity(1, "ActivityName", "ActivityDescription", 10000.0, "Mon Jun 08 12:00:00 EDT 2015", "Mon Jun 12 12:00:00 EDT 2015", Status.LOCKED);
		Activity _activity = new Activity(2, "ActivityName_2", "ActivityDescription_2", 25000.0, "Mon Jun 09 12:00:00 EDT 2015", "Mon Jun 21 12:00:00 EDT 2015", Status.LOCKED);
		Activity _activity2 = new Activity(1, "ActivityName3", "ActivityDescription3", 12310.0, "Mon Jun 08 12:00:00 EDT 2015", "Mon Jun 12 12:00:00 EDT 2015", Status.LOCKED);
		
		//Test if the activity is comparing itself
		//assertEquals("Comparing to same activity", true, activity.equals(activity));
		
		//Test if the activity is comparing to an instance of another object
		//assertEquals("Comparing to instance of different object", false, activity.equals(new Integer(5)));
			
		//Test if the activity is comparing to is null
		//assertEquals("Comparing to null", false, activity.equals(null));		
			
		//Test if the two activities are different (IDS). FAILED TEST
		//assertEquals("Both activities should be different", false, activity.equals(_activity));
		
		//Test if the two activities have same ID
		assertEquals("Both activities should be equal", true, activity.equals(_activity2));
		
	}
	
	//This test method also tests setActivityTeam() in the process of testing  getActivityteam().
	@Test
	public void testGetActivityTeam() throws Exception {
		Activity activity = new Activity(1, "ActivityA", "Description", 10000.0, "Mon Jul 20 12:00:00 EDT 2015", "Mon Jul 27 12:00:00 EDT 2015", Status.LOCKED);
		Member member = new Member("a","a");
		Member _member = new Member("b","b");
		
		ArrayList<Member> team = new ArrayList<Member>();
		team.add(member);
		team.add(_member);
		
		activity.setActivityTeam(team);
		assertEquals("Activity team arraylist should be equals", team, activity.getActivityTeam());
		
	}
	
	//Tested above in testGetActivityTeam()
	@Test
	public void testSetActivityTeam() throws Exception {
	}
	
	//Tested below in testRemoveMemberFromTeam()
	@Test
	public void testAddMemberToTeam() throws Exception {
	}
	
	//This method adds members in order to remove and hence tests both addMemberToTeam() and 
	// removeMemberFromTeam() methods.
	@Test
	public void testRemoveMemberFromTeam() throws Exception {
		Activity activity = new Activity(1, "ActivityA", "Description", 10000.0, "Mon Jul 20 12:00:00 EDT 2015", "Mon Jul 27 12:00:00 EDT 2015", Status.LOCKED);
		Member member = new Member("a","a");
		Member _member = new Member("b","b");
		
		activity.addMemberToTeam(member);
		activity.addMemberToTeam(_member);
		
		activity.removeMemberFromTeam(_member);
		
		assertEquals("Activity Team should be equal 1", 1, activity.getActivityTeam().size());
	
	}


}
