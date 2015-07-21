package testView;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import model.Activity;
import model.Project;
import model.Status;

import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;
import org.junit.Test;

public class GantViewTest {


	@Test
	public void testCreateDataset() throws Exception {

		//Creating a project and 2 activities.
		Project I2Project = new Project(1, "I2TestProject", "I2ProjectDescription", 1000.0, "Mon Jul 20 12:00:00 EDT 2015", "Fri Jul 31 12:00:00 EDT 2015", Status.LOCKED);
		Activity activity = new Activity(1, "ActivityName", "ActivityDescription", 250.0, "Mon Jul 20 11:00:00 EDT 2015", "Mon Jul 20 12:00:00 EDT 2015", Status.LOCKED);
		Activity _activity = new Activity(2, "ActivityName_2", "ActivityDescription_2", 150.0, "Tue Jul 21 10:00:00 EDT 2015", "Tue Jul 21 12:00:00 EDT 2015", Status.LOCKED);

		ArrayList <Activity> projectActivities = new ArrayList<Activity>();
		projectActivities.add(activity);
		projectActivities.add(_activity);
		I2Project.setProjectActivities(projectActivities);

		assertEquals("Activity 1 start date should be Mon Jul 20 11:00:00 EDT 2015", "Mon Jul 20 11:00:00 EDT 2015", I2Project.getProjectActivities().get(0).getStartDate());
		assertEquals("Activity 1 end date should be Mon Jul 20 12:00:00 EDT 2015", "Mon Jul 20 12:00:00 EDT 2015", I2Project.getProjectActivities().get(0).getFinishDate());

		assertEquals("Activity 2 start date should be Tue Jul 21 10:00:00 EDT 2015", "Tue Jul 21 10:00:00 EDT 2015", I2Project.getProjectActivities().get(1).getStartDate());
		assertEquals("Activity 2 end date should be Tue Jul 21 12:00:00 EDT 2015", "Tue Jul 21 12:00:00 EDT 2015", I2Project.getProjectActivities().get(1).getFinishDate());
	}

	//Last 2 tests are kind of a repeat...
	@Test
	public void testGetGantProj() throws Exception {
		Project project = new Project(1, "I2Project", "Test", 500.0, "Wed Jul 22 12:00:00 EDT 2015", "Fri Jul 31 12:00:00 EDT 2015", Status.LOCKED);
		assertEquals("Project name should be I2Project", "I2Project", project.getName());
	}

	//
	@Test
	public void testSetGantProj() throws Exception {
		Project project = new Project(1, "I2Project", "Test", 500.0, "Wed Jul 22 12:00:00 EDT 2015", "Fri Jul 31 12:00:00 EDT 2015", Status.LOCKED);
		project.setName("I2Project");
		assertEquals("Project name should be I2Project", "I2Project", project.getName());
	}

}
