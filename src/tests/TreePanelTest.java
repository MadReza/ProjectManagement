/*package tests;

import java.util.ArrayList;

import model.MainModel;
import model.Project;
import model.Status;
import view.MainView;
import view.TreePanel;

public class TreePanelTest {

	@Test
	public void testRefreshTree() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTree() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	public void testGetManagerProjects() throws Exception {
		MainModel model = MainModel.getInstance();
		MainView view = new MainView(model);
		TreePanel tree = new TreePanel(view);
		
		Project project = new Project(1, "ProjectName", "ProjectDescription", 100000000.0, "Mon Jun 08 12:00:00 EDT 2015", "Mon Jun 25 12:00:00 EDT 2015", Status.LOCKED);
		Project _project = new Project(2, "ProjectName2", "ProjectDescription2", 100000000.0, "Mon Jun 08 12:00:00 EDT 2015", "Mon Jun 25 12:00:00 EDT 2015", Status.LOCKED);
		
		ArrayList<Project> managerProjects = new ArrayList<Project>();
		managerProjects.add(project);
		managerProjects.add(_project);
		
		tree.setManagerProjects(managerProjects);
		
		assertEquals("Project managers should be equal", managerProjects, tree.getManagerProjects());
	}

	@Test
	public void testSetManagerProjects() throws Exception {
		MainModel model = MainModel.getInstance();
		MainView view = new MainView(model);
		TreePanel tree = new TreePanel(view);
		
		Project project = new Project(1, "ProjectName", "ProjectDescription", 100000000.0, "Mon Jun 08 12:00:00 EDT 2015", "Mon Jun 25 12:00:00 EDT 2015", Status.LOCKED);
		Project _project = new Project(2, "ProjectName2", "ProjectDescription2", 100000000.0, "Mon Jun 08 12:00:00 EDT 2015", "Mon Jun 25 12:00:00 EDT 2015", Status.LOCKED);
		
		ArrayList<Project> managerProjects = new ArrayList<Project>();
		managerProjects.add(project);
		managerProjects.add(_project);
		
		tree.setManagerProjects(managerProjects);
		
		assertEquals("Project managers should be equal", managerProjects, tree.getManagerProjects());
	}

}
*/