package controller;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JList;
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

	//*********************************************************Setters*****************************************************************
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
	}

	//*******************************************************Getters***********************************************************
	/**
	 * Returns the project Table from MainApplicationView.
	 * @return
	 */
	private JTable getProjectTable() {
		return mainView.getStartupView().getAppView().getProjectTable();
	}
	
	/**
	 * Returns the activity Frame from the ProjectPanel
	 * @return
	 */
	private ActivityFrame getActivityFrame() {
		return mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame();
	}
	

	/**
	 * Returns the activity Table from DisplayPanel.
	 * @return
	 */
	private JTable getActivityTable() {
		return mainView.getStartupView().getAppView().getProjectPanel().getDisplayPanel().getActivityTable();
	}

	/**
	 * Returns the name of the activity selected from the table in the DisplayPanel.
	 * @return selected activity name
	 */
	private String getSelectedActivityName() {
		return mainView.getStartupView().getAppView().getProjectPanel().getDisplayPanel().getSelectedActivity();
	}

	private JList<Activity> getAvailableActivitiesTable() {
		return mainView.getStartupView().getAppView().getProjectPanel().getAvailableActivitiesList();
	}


	//*************************************************Registering all listeners******************************************

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
		mainView.getStartupView().getAppView().getProjectPanel().addChoosePrereqsListener(new ChoosePrereqsListener());
		mainView.getStartupView().getAppView().getProjectPanel().addSavePrereqsListener(new SavePrereqsListener());
	}


	//***************************************************Update Methods************************************************************

	/**
	 * To check if an item has been selected before editing or deleting. 
	 * @return
	 */

	private void updateProjectDisplay() {

		updateProjectTable();
		updateActivityTable();
		mainView.getStartupView().getAppView().updateDisplayPanel();

	}

	private void updateProjectTable(){
		ResultSet results = mainModel.getResultSetOfAllProjectsForUser(currentUser);
		getProjectTable().setModel(DbUtils.resultSetToTableModel(results));
	}

	private void updateActivityTable(){
		ResultSet results = mainModel.getResultSetForAllActivitiesOfProject(currentProject.getID());
		getActivityTable().setModel(DbUtils.resultSetToTableModel(results));
	}

	private void updatePrereqTable(String selectedActivity) {

		ArrayList<Activity> availableChoices, selectedPrereqs;
		try {
			availableChoices = mainModel.getAvailableActivities(currentProject.getID(), selectedActivity);
			selectedPrereqs = mainModel.getSelectedPrerequisties(selectedActivity);
			mainView.getStartupView().getAppView().getProjectPanel().updateAvailableListEntries(availableChoices);
			mainView.getStartupView().getAppView().getProjectPanel().updateChosenPrereqEntries(selectedPrereqs);
		} catch (Exception e) {
			e.printStackTrace();  
			JOptionPane.showMessageDialog(null, "No available prereqs!");
		}
	}


	//***************************************************Helper Methods************************************************************

	private boolean isAnythingSelected()
	{
		if (selectedActivityName == null) //This is always null ...need to change the condition.
			return false;
		return true;
	}

	/**
	 * Before submitting the form, checking if form is completed correctly.
	 * @param classType 1-project form, 2- activity form.
	 * @return
	 */
	public boolean isFormReady(int classType){
		int isReady = -1;

		if(classType == 1){
			if(!mainModel.isProjectNameValid(currentUser,mainView.getStartupView().getAppView().getName()))
				isReady = 5;
			else
				isReady = mainView.getStartupView().getAppView().isJobFormReady();
		}

		if(classType == 2){
			try {
				if(!mainModel.isActivityNameValid(currentProject.getName(),getActivityFrame().getActivityNameField()))
					isReady = 6;
				else
					isReady = getActivityFrame().isJobFormReady() ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		switch(isReady) {
		case 0:
			JOptionPane.showMessageDialog(null, "Complete all fields");
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
		case 6: 
			JOptionPane.showMessageDialog(null, "You already have an activity with the same name! Choose a different name.");
			return false;
		case 7: 
			JOptionPane.showMessageDialog(null, "You entered an invalid format for one or more of the following fields:"
					+ " Duration, ES, EF, LS and/or LF");
			return false;
		case 8: 
			JOptionPane.showMessageDialog(null, "The duration of this activity cannot excede the duration for the whole project");
			return false;

		}
		return true;
	}

	//***************************************************Listeners for Login/Sign up***********************************************
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


	//***************************************************Listeners related to Projects*****************************************************
	/**
	 * Implements saveprojectListener.
	 */
	private class SaveProjectListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			boolean formReady = isFormReady(1);
			try {
				if(formReady) {
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
			} catch (HeadlessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
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
		public void actionPerformed(ActionEvent event) {
			int confirm = JOptionPane.showConfirmDialog(null, "Confirm Delete.", "Delete", JOptionPane.OK_CANCEL_OPTION);
			if (confirm == 0) {
				mainModel.deleteProjectFromDatabase(currentProject);			
				mainView.getStartupView().getAppView().autoOpenHomeTab();
			}		
		}	
	}


	//***************************************************Listeners related to Activities*****************************************************
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
			boolean formReady = isFormReady(2);
			if(formReady) {
				try{
					int pID = mainModel.getCurrentProject().getID();
					String name = mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().getActivityNameField();
					String description = mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().getActivityDescription();
					double budget = mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().getBudget();
					int duration = mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().getActivityDuration();
					int ES = mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().getActivityES();
					int EF = duration + ES;
					int LS = mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().getActivityLS();
					int LF = mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().getActivityLF();
					String status = mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().getActivityChoice();

					boolean editMode = mainView.getStartupView().getAppView().getProjectPanel().getEditActivityMode();
					if (!editMode) {
						try {
							mainModel.addActivityToDatabase(pID, name, description,budget, duration, ES, EF, LS, LF, status, getActivityTable());
							JOptionPane.showMessageDialog(null, "Activity created sucessfully");
							mainView.getStartupView().getAppView().getProjectPanel().getActivityFrame().dispose();
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, e);
						}
					}

					//Update and save an existing activity (Edit Mode = TRUE)
					else {
						try {
							mainModel.updateActivityInDatabase(name, description, budget, duration, ES, EF, LS, LF, status);
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
	}

	/**
	 * Implements the EditActivityListener.
	 */
	private class EditActivityListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {

			if(isAnythingSelected()){
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
				selectedActivityName = null;
			}

			else
				JOptionPane.showMessageDialog(null,"No Item was selected. Select an activity to edit.");
		}
	}

	/**
	 * Implements the DeleteActivityListener.
	 */
	private class DeleteActivityListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			selectedActivityName = mainView.getStartupView().getAppView().getProjectPanel().getDisplayPanel().getSelectedActivity(); //get selected item from activityTable.
			setCurrentActivity(mainModel.getActivityByName(selectedActivityName)); //Update currentActivity (used for display).
			int confirm = JOptionPane.showConfirmDialog(null, "Confirm Delete.", "Delete", JOptionPane.OK_CANCEL_OPTION);
			if (confirm == 0) {
				mainModel.deleteActivityFromDatabase(currentActivity); 
				selectedActivityName = null; //To make sure that the selection is "empty"
				updateProjectDisplay();	
			}
		}
	}

	/**
	 * Implements a listener to choose prerequisites for a selected activity.
	 */
	private class ChoosePrereqsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			selectedActivityName = mainView.getStartupView().getAppView().getProjectPanel().getDisplayPanel().getSelectedActivity(); 
			mainView.getStartupView().getAppView().getProjectPanel().displayPrereqsFrame();	
			updatePrereqTable(selectedActivityName); 
		}		
	}

	
	private class SavePrereqsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			ArrayList<Activity> selectedPrereqs = mainView.getStartupView().getAppView().getProjectPanel().getSelectedPrereqs();
			mainModel.associateActivityWithPrerequisites(getSelectedActivityName(), selectedPrereqs);
			mainView.getStartupView().getAppView().getProjectPanel().disposePrereqsFrame();
			
		}
		
	}


}




