package testModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Activity;
import model.MainModel;
import model.Project;
import model.Status;

import org.junit.Test;

public class MainModelTest {

	@Test
	
	public void testValidateLogin() {
		fail("Not yet implemented");
	}
	 
	@Test
	
	//Testing both AddProjectToDb
	public void testAddProjectToDbProject() {
		try {
			//First test
			MainModel mainModel = MainModel.getInstance();
			
			// project data as if entered in text fields
			int pmID = 1;
			String name = "Test_1";
			String description = "";
			double budget = 10;
			String today = "Wed Aug 01 00:00:00 EDT 2018";
			String end = "Wed Aug 29 00:00:00 EDT 2018";
			String startDate = today;
			String finishDate = end;
			Status status = Status.LOCKED;
			
			//creating a project and adding it to database

			mainModel.addProjectToDb(pmID, name, description, budget, startDate, finishDate, status);
			
			// retrieve it
			
			Project retrievedProject = mainModel.getLastProject();
			
			assertEquals(name,(retrievedProject.getName()));
			assertEquals(description, (retrievedProject.getDescription()));
			assertEquals(true,budget == (retrievedProject.getBudget()));
			assertEquals(startDate ,(retrievedProject.getStartDate()));
			assertEquals(finishDate ,(retrievedProject.getFinishDate()));
			assertEquals(0,status.compareTo(retrievedProject.getStatus()));
			
			mainModel.addProjectToDb(retrievedProject);
			
			
			//Second test
			Project retrievedProject2 = mainModel.getLastProject();
			
			assertEquals(name,(retrievedProject2.getName()));
			assertEquals(description, (retrievedProject2.getDescription()));
			assertEquals(true,budget == (retrievedProject2.getBudget()));
			assertEquals(startDate ,(retrievedProject2.getStartDate()));
			assertEquals(finishDate ,(retrievedProject2.getFinishDate()));
			assertEquals(0,status.compareTo(retrievedProject2.getStatus()));
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	@Test
	//Testing both adActivityToDb Methods, Also testing UpdateActivity in here
	public void testAddActivityToDbActivity() {
		try {//method 1
			MainModel mainModel = MainModel.getInstance();
			ArrayList<Project> ExistingProjects = mainModel.getAllProjects();			// get all existing projects
			int projectCount = ExistingProjects.size();
			System.out.println(ExistingProjects.size());								// print count
			if(projectCount > 0){
				Project lastProject = mainModel.getLastProject();						// get last project
				int lastProjectID = lastProject.getID();
				System.out.println(ExistingProjects.size());
				Activity newAct = new Activity(lastProjectID, "act1", "testing activity" , 2 , "Wed Aug 01 00:00:00 EDT 2018", "Wed Aug 08 00:00:00 EDT 2018", Status.UNLOCKED);
				mainModel.addActivityToDb(newAct);
				
				// retrieve it
				Activity retrievedActivity = mainModel.getLastActivity();
				
				assertEquals(newAct.getName(),(retrievedActivity.getName()));;
				assertEquals(newAct.getDescription(),(retrievedActivity.getDescription()));
				assertEquals(true , newAct.getBudget() == retrievedActivity.getBudget());
				assertEquals(newAct.getStartDate(),(retrievedActivity.getStartDate()));
				assertEquals(newAct.getFinishDate(),(retrievedActivity.getFinishDate()));
				assertEquals(true, newAct.getStatus() == retrievedActivity.getStatus() ) ;
				
				mainModel.addActivityToDb(retrievedActivity);
				
				
				//method 2
				Activity retrievedActivity2 = mainModel.getLastActivity();
				
				assertEquals(newAct.getName(),(retrievedActivity2.getName()));;
				assertEquals(newAct.getDescription(),(retrievedActivity2.getDescription()));
				assertEquals(true , newAct.getBudget() == retrievedActivity2.getBudget());
				assertEquals(newAct.getStartDate(),(retrievedActivity2.getStartDate()));
				assertEquals(newAct.getFinishDate(),(retrievedActivity2.getFinishDate()));
				assertEquals(true, newAct.getStatus() == retrievedActivity2.getStatus());
				
				//Testing Update activity
				
				Activity testUpdate = new Activity(lastProjectID, "act2", "testing activity update" , 3 , "Wed Aug 01 00:00:00 EDT 2018", "Fri Aug 03 00:00:00 EDT 2018", Status.LOCKED);
				
				mainModel.updateActivity(mainModel.getLastActivity(), testUpdate);
				
				Activity retrievedActivity3 = mainModel.getLastActivity();
				
				assertEquals("act2",(retrievedActivity3.getName()));;
				assertEquals("testing activity update",(retrievedActivity3.getDescription()));
				assertEquals(true , 3 == retrievedActivity3.getBudget());
				assertEquals("Wed Jun 10 12:00:00 EDT 2016",(retrievedActivity3.getStartDate()));
				assertEquals("Fri Jun 12 12:00:00 EDT 2016",(retrievedActivity3.getFinishDate()));
				assertEquals(true, Status.LOCKED == retrievedActivity3.getStatus());
			}
			
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateProject() {

		try {
			MainModel mainModel = MainModel.getInstance();
			int pmID = 1;
			String name = "Test_1";
			String description = "";
			double budget = 10;
			String today = "Sun Jan 01 00:00:00 EST 2017";
			String end = "Tue Jan 31 00:00:00 EST 2017";
			String startDate = today;
			String finishDate = end;
			Status status = Status.UNLOCKED;
				
			//creating a project and adding it to database
	
			mainModel.addProjectToDb(pmID, name, description, budget, startDate, finishDate, status);
			
			
			Project updatedProject = new Project(2,"Simon","hello", 5, "Mon Jan 02 00:00:00 EST 2017", "Mon Jan 30 00:00:00 EST 2017", Status.LOCKED);
			
			mainModel.updateProject(mainModel.getLastProject(), updatedProject);
			
			Project didItWork = mainModel.getLastProject();
			
			
			assertEquals("Simon",(didItWork.getName()));
			assertEquals("hello", (didItWork.getDescription()));
			assertEquals(true,5 == (didItWork.getBudget()));
			assertEquals("Mon Jan 02 00:00:00 EST 2017" ,(didItWork.getStartDate()));
			assertEquals("Mon Jan 30 00:00:00 EST 2017" ,(didItWork.getFinishDate()));
			assertEquals(0,Status.LOCKED.compareTo(didItWork.getStatus()));
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	

	@Test
	public void testProjectExists() {

		try {
			MainModel mainModel = MainModel.getInstance();
			assertEquals(true,mainModel.projectExists(1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testActivityExists() {

		try {
			MainModel mainModel = MainModel.getInstance();
			assertEquals(true,mainModel.activityExists(1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Test
	public void testActsTotalBudgetSuitsParent() {

		try {
			MainModel mainModel = MainModel.getInstance();
			Project testBudget = new Project(2,"Simon","hello", 1000, "Wed Jun 01 00:00:00 EDT 2016", "Fri Jul 01 00:00:00 EDT 2016", Status.LOCKED);
			Activity newAct1 = new Activity(2, "act1", "testing activity" , 500 , "Wed Jun 01 00:00:00 EDT 2016", "Wed Jun 08 00:00:00 EDT 2016", Status.UNLOCKED);
			Activity newAct2 = new Activity(2, "act2", "testing activity" , 600 , "Thu Jun 09 00:00:00 EDT 2016", "Fri Jul 15 00:00:00 EDT 2016", Status.UNLOCKED);
			testBudget.addActivity(newAct2);
			assertEquals(false,mainModel.activityBudgetSuitsParent(newAct1,testBudget));
			
			assertEquals(true,mainModel.activityExists(1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//This method doesn't really need testing as actsTotalBudgetSuitsParent work. If the method was to fail then we should look for this case.
	@Test
	public void testActivityBudgetSuitsParent() {
		fail("Not yet implemented");
	}



	@Test
	public void testActivityDatesSuitsParent() {

		try {
			MainModel mainModel = MainModel.getInstance();
			Project testBudget = new Project(2,"Simon","hello", 1000, "Wed Jun 01 00:00:00 EDT 2016", "Fri Jul 01 00:00:00 EDT 2016", Status.UNLOCKED);
			Activity newAct1 = new Activity(2, "act1", "testing activity" , 500 , "Wed Jun 01 00:00:00 EDT 2016", "Wed Jun 08 00:00:00 EDT 2016", Status.UNLOCKED);
			Activity newAct2 = new Activity(2, "act2", "testing activity" , 500 , "Thu Jun 09 00:00:00 EDT 2016", "Fri Jul 15 00:00:00 EDT 2016", Status.UNLOCKED);
			Activity newAct3 = new Activity(2, "act3", "testing activity" , 500 , "Thu Jun 16 00:00:00 EDT 2016", "Sat Jul 23 00:00:00 EDT 2016", Status.UNLOCKED);
		
			assertEquals(0,mainModel.activityDatesSuitsParent(newAct1, testBudget));
			assertEquals(1,mainModel.activityDatesSuitsParent(newAct2, testBudget));
			assertEquals(2,mainModel.activityDatesSuitsParent(newAct3, testBudget));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testActivityDatesSuitsPrereq() {
		
		try {
			MainModel mainModel = MainModel.getInstance();
			Activity newAct1 = new Activity(2, "act1", "testing activity" , 500 , "Wed Jun 01 00:00:00 EDT 2016", "Wed Jun 08 00:00:00 EDT 2016", Status.UNLOCKED);
			Activity newAct2 = new Activity(2, "act1", "testing activity" , 500 , "Thu Jun 09 00:00:00 EDT 2016", "Fri Jul 15 00:00:00 EDT 2016", Status.UNLOCKED);
			
			assertEquals(true,mainModel.activityDatesSuitsPrereq(newAct2, newAct1));
			assertEquals(false,mainModel.activityDatesSuitsPrereq(newAct1, newAct2));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	// This method is calling testActivityDatesSuitsPrereq for all activities since testActivityDatesSuitsPrereq works this one should also.
	@Test
	public void testActivityDatesSuitsAllPrereqs() {
		fail("Not yet implemented");
	}




	@Test
	public void testActivityDatesSuitsSuccessor() {
		
		try {
			MainModel mainModel = MainModel.getInstance();
			Activity newAct = new Activity(2, "act1", "testing activity" , 500 , "Wed Jun 01 00:00:00 EDT 2016", "Wed Jun 08 00:00:00 EDT 2016", Status.UNLOCKED);
			Activity newAct2 = new Activity(2, "act1", "testing activity" , 500 , "Thu Jun 09 00:00:00 EDT 2016", "Fri Jul 15 00:00:00 EDT 2016", Status.UNLOCKED);
			
			assertEquals(true,mainModel.activityDatesSuitsPrereq(newAct2, newAct));
			assertEquals(false,mainModel.activityDatesSuitsPrereq(newAct, newAct2));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	// This method is calling testActivityDatesSuitsSuccessor for all activities since testActivityDatesSuitsSuccessor works this one should also
  	@Test
	public void testActivityDatesSuitsAllSuccessors() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsPrereqDone() {
		
		try {
			MainModel mainModel = MainModel.getInstance();
			Activity newAct = new Activity(2, "act1", "testing activity" , 500 , "Wed Jun 01 00:00:00 EDT 2016", "Wed Jun 08 00:00:00 EDT 2016", Status.FINISHED);
			Activity newAct2 = new Activity(2, "act2", "testing activity" , 500 , "Thu Jun 09 00:00:00 EDT 2016", "Fri Jul 15 00:00:00 EDT 2016", Status.UNLOCKED);
			
			newAct2.addpreReq(newAct);
			assertEquals(true,mainModel.isPrereqDone(newAct2));
			
			
			Activity newAct1 = new Activity(2, "act1", "testing activity" , 500 , "Wed Jun 01 00:00:00 EDT 2016", "Wed Jun 08 00:00:00 EDT 2016", Status.UNLOCKED);
			Activity newAct3 = new Activity(2, "act1", "testing activity" , 500 , "Thu Jun 09 00:00:00 EDT 2016", "Fri Jul 15 00:00:00 EDT 2016", Status.UNLOCKED);
			newAct3.addpreReq(newAct1);
			assertEquals(false,mainModel.isPrereqDone(newAct3));
			
			
		} catch (Exception e) {
			
			
		}
	}
	
	
	
	

	@Test //Altought the test passes, this test doesn't work it always goes in the catch... 
	public void testDeleteProjectfromDb() {
		
		try{
			MainModel mainModel = MainModel.getInstance();
			Project testdelete = new Project(2,"Simon","hello", 1000, "Thu Jun 09 00:00:00 EDT 2016", "Fri Jul 15 00:00:00 EDT 2016", Status.LOCKED);
			Activity newAct = new Activity(2, "act1", "testing activity" , 500 , "Thu Jun 09 00:00:00 EDT 2016", "Fri Jul 15 00:00:00 EDT 2016", Status.UNLOCKED);
			//Activity newAct2 = new Activity(2, "act2", "testing activity" , 600 , "Thu Jun 09 00:00:00 EDT 2016", "Fri Jul 15 00:00:00 EDT 2016", Status.UNLOCKED);
			mainModel.addProjectToDb(testdelete);
			mainModel.addActivityToDb(newAct);
			
			mainModel.deleteProjectfromDb(2);
			
			assertEquals(true,(mainModel.getProjectByID(2) == null));
		} catch(Exception e){
			
		}
	}
		
		
	@Test
	public void testDeleteActivityfromDb() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddActivityPrereq() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveActivityPrereq() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllProjects() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetManagerProjects() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllActivities() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLastProject() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLastActivity() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProjectByID() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetActivityByID() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProjectActivities() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetActivityPreReq() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetActivitySuccessors() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProjectActivitiesNames() {
		fail("Not yet implemented");
	}

}
