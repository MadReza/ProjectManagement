package controller;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
/*import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;*/

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import view.MainView;
import model.MainModel;
import model.Project;


//Handles user interaction with view
public class MainController {

	private MainModel mainModel; // Reference to the Model
	private MainView mainView; // Reference to the View

	Project currentProject;
	String selectedProjectName, selectedActivityName;
	String currentUser;
	
	/**
	 * Parametrised constructor.
	 * @param aModel
	 * @param aView
	 */
	public MainController (MainModel aModel, MainView aView) {
		mainModel = aModel;
		mainView = aView;

		addListeners();
	}
	
	//Update currentUser and currentProject references.
	private void setCurrentUser(String memberUsername) {
		currentUser = memberUsername;
		mainView.getStartupView().getAppView().setCurrentUser(currentUser);
	}

	private void setCurrentProject(Project project) {
		currentProject = project;
		mainView.getStartupView().getAppView().setCurrentProject(project); //Update reference in MainApplicationView
	}
	
/*	private void setCurrentActivity(Activity activity) {
		currentActivity = activity;
		mainView.getStartupView().getAppView().setCurrentProject(project); //Update reference in MainApplicationView
	}*/

	/**
	 * Contains all listeners for the application.
	 */
	private void addListeners() {

		mainView.getStartupView().getLoginPage().addLoginListener(new LoginListener());
		mainView.getStartupView().getSignupPage().addSignupListener(new SignupListener());
		mainView.getStartupView().getAppView().addSaveProjectListener(new SaveProjectListener());
		mainView.getStartupView().getAppView().addViewProjectsListener(new ViewProjectsListener());
		mainView.getStartupView().getAppView().addOpenProjectListener(new OpenProjectListener());
		mainView.getStartupView().getAppView().getProjectPanel().addEditProjectListener(new EditProjectListener());
		mainView.getStartupView().getAppView().getProjectPanel().addDeleteProjectListener(new DeleteProjectListener());
		mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().addSaveActivityListener(new SaveActivityListener());
		mainView.getStartupView().getAppView().getProjectPanel().addEditActivityListener(new EditActivityListener());
		/*mainView.getStartupView().getAppView().getProjectPanel().addDeleteActivityListener(new DeleteActivityListener());*/
	}

	/**
	 * Updates the project display panel which includes project and activity information.
	 */
	private void updateProjectDisplay() {

		mainModel.updateProjectTable(getProjectTable(), currentUser);
		mainModel.updateActivityTable(getActivityTable(), currentProject.getID());
		mainView.getStartupView().getAppView().updateDisplayPanel();
		
	}
	
	
	
	/**
	 * Returns the activity Table from DisplayPanel.
	 * @return
	 */
	private JTable getActivityTable() {
		return mainView.getStartupView().getAppView().getProjectPanel().getDisplayPanel().getActivityTable();
	}
	
	/**
	 * Returns the project Table from MainApplicationView.
	 * @return
	 */
	private JTable getProjectTable() {
		return mainView.getStartupView().getAppView().getProjectTable();
	}

	/**
	 * Implements LoginListener.
	 */
	private class LoginListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ev) {

			String user = mainView.getStartupView().getUsername();
			String pass = mainView.getStartupView().getPassword();

			try {
				if (mainModel.validateLoginInformation(user, pass)) {

					JOptionPane.showMessageDialog(null, "Project Manager Login Successful!");
					setCurrentUser(user);
					mainView.getStartupView().switchToMainAppView();
				}
				/*	else if(user.equals("m") && pass.equals("ok")){
						JOptionPane.showMessageDialog(null, "Member Login Successful! ... Member Space implemented later");
					}*/
				else {
					JOptionPane.showMessageDialog(null, "Error! Incorrect Username and/or Password.");
					mainView.getStartupView().getLoginPage().clearLoginForm();
				}
			} catch (HeadlessException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * Implements signupListener.
	 */
	private class SignupListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {

			String name = mainView.getStartupView().getTextFieldName();
			String email = mainView.getStartupView().getTextFieldEmail();
			String username = mainView.getStartupView().getTextFieldUsername();
			String password = mainView.getStartupView().getPasswordField();
			String confirmPW = mainView.getStartupView().getConfirmPWField();

			if( name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPW.isEmpty() ) 							
				JOptionPane.showMessageDialog(null, "Complete all fields");	
			else if (!password.equals(confirmPW)) 
				JOptionPane.showMessageDialog(null, "Passwords do not match");	
			else {
				try {
					mainModel.addMemberToDatabase(name, email, username, password);
					setCurrentUser(username);
					mainView.getStartupView().switchToMainAppView();
				} 
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			} 				
		}
	}
	

	/**
	 * Implements saveprojectListener.
	 */
	private class SaveProjectListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {

			System.out.println("Edit mode :" + mainView.getStartupView().getAppView().getProjectPanel().getEditProjectMode());
			String name = mainView.getStartupView().getAppView().getName();
			String description = mainView.getStartupView().getAppView().getDescription();
			String status = mainView.getStartupView().getAppView().getChoice();
			double budget = mainView.getStartupView().getAppView().getBudget();
			String startDate = mainView.getStartupView().getAppView().getStartDate();
			String endDate = mainView.getStartupView().getAppView().getEndDate();

			if( name.isEmpty() || description.isEmpty() || budget == 0 || startDate.isEmpty() || endDate.isEmpty() ) {							
				JOptionPane.showMessageDialog(null, "Complete all fields");
			}
			
			//Create and save a new Project  (Edit Mode = FALSE)
			boolean editMode = mainView.getStartupView().getAppView().getProjectPanel().getEditProjectMode();
			if (!editMode) {
				try {
					mainModel.addProjectToDatabase(currentUser, name, description, status, budget, startDate, endDate);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			//Update and save an existing project (Edit Mode = TRUE)
			else {
				try {
					mainModel.updateProjectInDatabase(name, description, status, budget, startDate, endDate);
					mainView.getStartupView().getAppView().getProjectPanel().setEditProjectMode(false);		
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			//Update currentProject (for display)
			setCurrentProject(mainModel.getCurrentProject());
	
			mainView.getStartupView().getAppView().clearForm(); 
			mainView.getStartupView().getAppView().getProjectFormPanel().setVisible(false);
			mainView.getStartupView().getAppView().autoOpenProjectTab(); 
			updateProjectDisplay();	
		}
	}


	/**
	 * Implements viewProjectsListener.
	 */
	private class ViewProjectsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				mainModel.updateProjectTable(getProjectTable(), currentUser);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}

			mainView.getStartupView().getAppView().getWelcomePanel().setVisible(false); 
			mainView.getStartupView().getAppView().getViewPanel().setVisible(true); 			
		}
	}

	
	/**
	 * Implements openProjectListener.
	 */
	private class OpenProjectListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {

			selectedProjectName = mainView.getStartupView().getAppView().getSelectedProject(); //get selected item from projectTable.
			mainView.getStartupView().getAppView().getViewPanel().setVisible(false); 
			
			try {
				setCurrentProject(mainModel.getProjectByName(currentUser,selectedProjectName)); //Update currentProject (used for display).
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Selected project : " + currentProject.getName());
			
			mainView.getStartupView().getAppView().autoOpenProjectTab(); 
			updateProjectDisplay();
		}
	}

	/**
	 * Implements EditProjectListener.
	 * Sets editMode = true.
	 * Displays the project form to edit.
	 */
	private class EditProjectListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			mainView.getStartupView().getAppView().getProjectPanel().setEditProjectMode(true); //set editMode ON
			mainView.getStartupView().getAppView().createEditProjectForm(currentProject);
		}

	}
	
	/**
	 * Implements DeleteProjectListener.
	 *
	 */
	private class DeleteProjectListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int confirm = JOptionPane.showConfirmDialog(null, "Confirm Delete.", "Delete", JOptionPane.OK_CANCEL_OPTION);
			if (confirm == 0) {
				mainModel.deleteProjectFromDatabase(currentProject);
				
				mainView.getStartupView().getAppView().autoOpenHomeTab();
				updateProjectDisplay();
			}		
		}	
	}


	/**
	 * Implements the SaveActivityListener.
	 */
	private class SaveActivityListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event){

			mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().setVisible(true);
			try{
				int pID = mainModel.getCurrentProject().getID();
				String name = mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().getActivityNameField();
				String description = mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().getActivityDescription();
				String status = mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().getActivityChoice();
				String startDate = mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().getStartDate();
				String endDate = mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().getEndDate();


				if( name.isEmpty() || description.isEmpty() || startDate.isEmpty()||endDate.isEmpty()) {							//   ... || budgetText.isEmpty() is not working ?????|| startDate.isEmpty() || endDate.isEmpty()
					JOptionPane.showMessageDialog(null, "Complete all fields");
				}

				else {
					try {
						mainModel.addActivityToDatabase(pID, name, description, startDate, endDate, status, getActivityTable());

						JOptionPane.showMessageDialog(null, "Activity created sucessfully");
						mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().dispose();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e);
					}
				}
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}


	/**
	 * Implements the EditActivityListener.
	 */
	private class EditActivityListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
		//	selectedActivityName =

		}


	}
	
	
	/*		public void actionPerformed(ActionEvent event) {
	System.out.println("Got to editactivit"); 
	try {

	//	mainView.getStartupView().getAppView().getProjectPanel().updateActivityView(mainModel.getAllactivities());
		System.out.println("showed the list");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
*/

	/**
	 * Implements the DeleteActivityListener.
	 */
	private class DeleteActivityListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {



		}
	}

}

