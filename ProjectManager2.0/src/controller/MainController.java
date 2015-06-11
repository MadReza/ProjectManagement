package controller;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;
/*import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;*/

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.proteanit.sql.DbUtils;
import view.ActivityFrame;
import view.MainView;
import model.Activity;
import model.MainModel;
import model.Project;


//Handles user interaction with view
public class MainController {

	private MainModel mainModel; // Reference to the Model
	private MainView mainView; // Reference to the View

	Project currentProject;
	Activity currentActivity;
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

	/**
	 * Updates references to the currentUser in MainController and MainApplicationView.
	 * @param memberUsername - username used to log in to the system or username used
	 * 						to create a new user account.
	 */
	private void setCurrentUser(String memberUsername) {
		currentUser = memberUsername;
		mainView.getStartupView().getAppView().setCurrentUser(currentUser);
	}

	/**
	 * Updates references to the currentProject(project being viewed) in MainController and MainApplicationView.
	 * @param project - project created or updated.
	 */
	private void setCurrentProject(Project project) {
		currentProject = project;
		mainView.getStartupView().getAppView().setCurrentProject(project); //Update reference in MainApplicationView
	}

	private void setCurrentActivity(Activity activity) {
		currentActivity = activity;
		//	mainView.getStartupView().getAppView().setCurrentProject(project); //Update reference in MainApplicationView
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
		mainView.getStartupView().getAppView().getProjectPanel().addNewActivityListener(new NewActivityListener());
		mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().addSaveActivityListener(new SaveActivityListener());
		mainView.getStartupView().getAppView().getProjectPanel().addEditActivityListener(new EditActivityListener());
		mainView.getStartupView().getAppView().getProjectPanel().addDeleteActivityListener(new DeleteActivityListener());
	}

	/**
	 * Updates the project display panel which includes project and activity information.
	 */
	private void updateProjectDisplay() {

		updateProjectTable();
		updateActivityTable();
		mainView.getStartupView().getAppView().updateDisplayPanel();

	}

	private void updateProjectTable(){
		ResultSet results = mainModel.updateProjectTable(currentUser);
		getProjectTable().setModel(DbUtils.resultSetToTableModel(results));
	}

	private void updateActivityTable(){
		ResultSet results = mainModel.updateActivityTable(currentProject.getID());
		getActivityTable().setModel(DbUtils.resultSetToTableModel(results));
	}

	public boolean isFormReady() {
		int isReady = 0;
		if(!mainModel.isProjectNameValid(currentUser,mainView.getStartupView().getAppView().getName()))
			isReady = 5;
		else 
			isReady = mainView.getStartupView().getAppView().isJobFormReady();

		switch(isReady) {
		case 0:
			JOptionPane.showMessageDialog(null, "Fill all fields");
			return false;

		case 1:
			JOptionPane.showMessageDialog(null, "Finish Date can't precede Start Date");
			return false;

		case 2:
			JOptionPane.showMessageDialog(null, "Status can't be in progress while StartDate is yet to come, Change the Start Date or Change Status");
			return false;

		case 3: 	
			JOptionPane.showMessageDialog(null, "Status can't be Finished while Finish Date is yet to come, Change the Finish Date or Change Status");
			return false;
		case 4: 
			JOptionPane.showMessageDialog(null, "You entered an invalid budget");
			return false;
		case 5: 
			JOptionPane.showMessageDialog(null, "You are currenty managing another project with the same name! Choose a different name.");
			return false;
		}
		return true;
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
			String role = mainView.getStartupView().getRole();
			String username = mainView.getStartupView().getTextFieldUsername();
			String password = mainView.getStartupView().getPasswordField();
			String confirmPW = mainView.getStartupView().getConfirmPWField();

			if( name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPW.isEmpty() ) 							
				JOptionPane.showMessageDialog(null, "Complete all fields");	
			else if (!password.equals(confirmPW)) 
				JOptionPane.showMessageDialog(null, "Passwords do not match");	
			else {
				int signupStatus =  mainModel.validateNewMemberInformation(email, username);
				if(signupStatus == 0) {
					try {
						mainModel.addMemberToDatabase(name, email, role, username, password);
						setCurrentUser(username);
						mainView.getStartupView().switchToMainAppView();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Error : Member Account was not created! try again.");
						mainView.getStartupView().clearSignupForm();
					}
				}
				else {
					if(signupStatus == 1)
						JOptionPane.showMessageDialog(null, "This Member exists!");
					if(signupStatus == 2) 
						JOptionPane.showMessageDialog(null, "This E-mail is already used!");
					if(signupStatus == 3) 
						JOptionPane.showMessageDialog(null, "This username is taken!");
					mainView.getStartupView().clearSignupForm();
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

			if(isFormReady()) {
				System.out.println("Edit mode :" + mainView.getStartupView().getAppView().getProjectPanel().getEditProjectMode());
				String name = mainView.getStartupView().getAppView().getName();
				String description = mainView.getStartupView().getAppView().getDescription();
				String status = mainView.getStartupView().getAppView().getChoice();
				double budget = mainView.getStartupView().getAppView().getBudget();
				String startDate = mainView.getStartupView().getAppView().getStartDate();
				String endDate = mainView.getStartupView().getAppView().getEndDate();

				//Create and save a new Project  (Edit Mode = FALSE)
				boolean editMode = mainView.getStartupView().getAppView().getProjectPanel().getEditProjectMode();
				if (!editMode) {
					try {
						mainModel.addProjectToDatabase(currentUser, name, description, status, budget, startDate, endDate);

						setCurrentProject(mainModel.getCurrentProject());
						mainView.getStartupView().getAppView().autoOpenProjectTab(); 
						updateProjectDisplay();	
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				//Update and save an existing project (Edit Mode = TRUE)
				else {
					try {
						mainModel.updateProjectInDatabase(name, description, status, budget, startDate, endDate);
						mainView.getStartupView().getAppView().getProjectPanel().setEditProjectMode(false);	
						setCurrentProject(mainModel.getCurrentProject());
						mainView.getStartupView().getAppView().setEditMode(false);
						mainView.getStartupView().getAppView().autoOpenProjectTab(); 
						updateProjectDisplay();	
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Error Occured. Please Try Again!");
					}
				}
				mainView.getStartupView().getAppView().clearForm(); 
				mainView.getStartupView().getAppView().displayWelcomePanel();

			}
		}
	}


	/**
	 * Implements viewProjectsListener.
	 */
	private class ViewProjectsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				mainView.getStartupView().getAppView().displayViewPanel();
				updateProjectTable();
			} 
			catch (Exception e) {
				e.printStackTrace();
			} 			
		}
	}


	/**
	 * Implements openProjectListener.
	 */
	private class OpenProjectListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {

			selectedProjectName = mainView.getStartupView().getAppView().getSelectedProject(); //get selected item from projectTable.
			mainView.getStartupView().getAppView().displayWelcomePanel();

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
			mainView.getStartupView().getAppView().displayFormPanel();
			mainView.getStartupView().getAppView().setEditMode(true);
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
			}		
		}	
	}


	private ActivityFrame getActivityFrame() {
		return mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame();
	}


	/*	private class PrereqComboBoxListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			Activity activity = (Activity) e.getItem();
			System.out.println("Selected prereq :" + activity.getID() + " - " + activity.getName());
		}
	}
	 */


	/**
	 * Implements newActivityListener.
	 * Creates the New Activity form.
	 *
	 */
	private class NewActivityListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				ArrayList<Activity> allActivities = mainModel.getAllActivities();
				allActivities.add(0, null);  
				getActivityFrame().updatePossiblePrereqItems(allActivities);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().setVisible(true);
			mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().clearForm();
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
				double budget = mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().getBudget();
				String duration = mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().getActivityDuration();
				String status = mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().getActivityChoice();
				/*String startDate = mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().getStartDate();
				String endDate = mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().getEndDate();*/
				


				if( name.isEmpty() || description.isEmpty() || duration.isEmpty()) {		
					JOptionPane.showMessageDialog(null, "Complete all fields");
				}

				boolean editMode = mainView.getStartupView().getAppView().getProjectPanel().getEditActivityMode();
				if (!editMode) {
					try {
						mainModel.addActivityToDatabase(pID, name, description,budget, duration, status, getActivityTable());
						JOptionPane.showMessageDialog(null, "Activity created sucessfully");
						mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().dispose();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e);
					}
				}

				//Update and save an existing activity (Edit Mode = TRUE)
				else {
					try {
						mainModel.updateActivityInDatabase(name, description, budget, duration,status);
						mainView.getStartupView().getAppView().getProjectPanel().setEditActivityMode(false);	
						mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().dispose();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
			updateProjectDisplay();
		}
	}


	/**
	 * Implements the EditActivityListener.
	 */
	private class EditActivityListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			mainView.getStartupView().getAppView().getProjectPanel().setEditActivityMode(true); //set editMode ON
			selectedActivityName = mainView.getStartupView().getAppView().getProjectPanel().getDisplayPanel().getSelectedActivity(); //get selected item from activityTable.

			try {
				setCurrentActivity(mainModel.getActivityByName(selectedActivityName)); //Update currentActivity (used for display).
				mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().setEditActivityFrame(currentActivity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().getEditActivityFrame().setVisible(true);
			updateProjectDisplay();
		}

	}


	/**
	 * Implements the DeleteActivityListener.
	 */
	private class DeleteActivityListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			selectedActivityName = mainView.getStartupView().getAppView().getProjectPanel().getDisplayPanel().getSelectedActivity(); //get selected item from activityTable.
			setCurrentActivity(mainModel.getActivityByName(selectedActivityName)); //Update currentActivity (used for display).
			int confirm = JOptionPane.showConfirmDialog(null, "Confirm Delete.", "Delete", JOptionPane.OK_CANCEL_OPTION);
			if (confirm == 0) {
				mainModel.deleteActivityFromDatabase(currentActivity);
				updateProjectDisplay();	
			}
		}
	}



}
