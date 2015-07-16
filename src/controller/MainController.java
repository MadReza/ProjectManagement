package controller;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import model.Activity;
import model.Job;
import model.MainModel;
import model.Member;
import model.Project;
import model.Status;

import org.jfree.ui.RefineryUtilities;

import view.ActivityPanel;
import view.EditableList;
import view.GanttView;
import view.MainView;
import view.MemberListView;
import view.ProjectPanel;

// Handles the interaction between the View and the Model
public class MainController {
	
	private MainModel mainModel;
	
	private MainView mainView;
    	
	private Member currentMember;
	
	private ArrayList<Project> allMemberProjects; 
	
	private ArrayList<Activity> allMemberActivities;
	
	private EditableList<Activity> prereqEditable;
	
	private EditableList<Member> memberEditable;
	private MemberListView<Member> activityTeam;

	// the Project selected in the ExistingProjectsCombo box if any
	private Project selectedProject; 
	
	// the Activity selected in the ProjectActivitiesCombo box if any
	private Activity selectedActivity;
	
	// reference to the ExistingProjects combo box in the main tool bar 
	private JComboBox<Project> projCombo ;
	
	// reference to the combo box displaying activities of each project in projectActivities combo box in the Project panel
	private JComboBox<Activity> actCombo ;
	
	// Default Constructor for Main Controller
	// Default Constructor
	public MainController(MainModel aModel, MainView aView){

		mainModel = aModel;
		mainView = aView;

		// registers all listeners to the view
		registerListeners();
		
		allMemberProjects = new ArrayList<Project>();
		allMemberActivities = new ArrayList<Activity>();
		
		// to eliminate long expressions redundancy
		projCombo = mainView.getExistingProjectsCombo();
		actCombo = mainView.getProjectPanel().getProjectActivitiesCombo();
	}
	
	///////////////////////////////////////////////////////////////////	Listeners	\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	//////////////////////// Private Classes for Listeners
	
	// inner class to implement loginListener
	private class LoginListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent ev) {
			String user = mainView.getLoginPage().getUsernameTextField();
			String pass = mainView.getLoginPage().getPassword();

			int[] couple = {-1, -1};
			
			try {
				couple = mainModel.validateLogin(user, pass);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
				try {
					currentMember = new Member(user, pass);
					currentMember.setUserID(couple[1]);
					currentMember.setManager(couple[0] == 1);
					mainView.getLoginPage().setVisible(false);
					mainView.setVisible(true);

					if (currentMember.isManager()) {
						linkAll();
						refreshAll();
						setInitialPosition();
//						JOptionPane.showMessageDialog(null, "Project Manager Login Successful!");	
					}
					else if( ! currentMember.isManager()){
//						JOptionPane.showMessageDialog(null, "Member Login Successful! ... Member Space implemented later");
						linkAll();
						refreshAll();
						setInitialPosition();
						disableManagerFeatures();
					}
					else {
							JOptionPane.showMessageDialog(null, "Error! Wrong Username and/or Password.");
							mainView.getLoginPage().clearLoginForm();
						}
				} catch (HeadlessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	
	// inner class to implement registerBtnListener
	private class RegisterBtnListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			mainView.getLoginPage().setVisible(false);
			mainView.getLoginPage().getSignupFrame().setVisible(true);
		}
	
	}

	
	// inner class to implement sign up a new member or project manager
	private class SignupListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			
			String user = mainView.getLoginPage().getSignupFrame().getSignupUN();
			int role = mainView.getLoginPage().getSignupFrame().getRole();
			String pass = mainView.getLoginPage().getSignupFrame().getSignupPW();
			String confirm = mainView.getLoginPage().getSignupFrame().getSignupCPW();
			
			if( user.isEmpty() || pass.isEmpty() || confirm.isEmpty() ) 							
				JOptionPane.showMessageDialog(null, "Complete all fields");	
			else if (!pass.equals(confirm)) 
				JOptionPane.showMessageDialog(null, "Passwords do not match");	
			else {

				try {

					mainModel.addMemberToDatabase(user, pass, role);
					mainView.getLoginPage().getSignupFrame().dispose();
					mainView.setVisible(true);
					//mainView.getLoginPage().setVisible(true);

				} catch (SQLException e) {
					if(e.getMessage().contains("UNIQUE")) {
						JOptionPane.showMessageDialog(null, "This username is already taken!");	
						mainView.getLoginPage().getSignupFrame().clearSignupForm();
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error : Member Account was not created! try again.");
					mainView.getLoginPage().getSignupFrame().clearSignupForm();
				}
			}
		} 				
	}
	
	
	// inner class to implement cancel sign up in register form
	private class CancelSignUpListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
				mainView.getLoginPage().getSignupFrame().dispose();
				mainView.getLoginPage().setVisible(true);
			}		
	}
	
	
	// inner class to implement Save/Edit/Modify Project
	private class SaveProjectListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ev) {
			
			boolean formReady = isFormReady(1);
			
			if(formReady){
				// get Project data from Project Panel Form
				JButton saveBtn = (JButton) ev.getSource();
				String name = mainView.getProjectPanel().getName();
				String description = mainView.getProjectPanel().getDescription();
				double budget = mainView.getProjectPanel().getBudget();
				String startDate = mainView.getProjectPanel().getStartDate();
				String finishDate = mainView.getProjectPanel().getFinishDate();
				Status status = mainView.getProjectPanel().getStatus();

					try {
						Project updatedProject = new Project(currentMember.getUserID(), name, description, budget, startDate, finishDate, status);
						
						// new project
						if(saveBtn.getText().equalsIgnoreCase("Save")){
							
							if(mainView.getProjectPanel().startInPast()){
								JOptionPane.showMessageDialog(null, "Start in the past !!!!");
							}
														
							else{
								mainModel.addProjectToDb(updatedProject);
								JOptionPane.showMessageDialog(null, "Project created sucessfully");
								
								linkAll();
								refreshAll();
								
								saveBtn.setText("Edit");
								mainView.getProjectPanel().getDeleteBtn().setText("Delete");
								
								mainView.getProjectPanel().getDeleteBtn().setEnabled(true);
								mainView.getProjectPanel().getNewActivityBtn().setEnabled(false);
								mainView.getNewProjectItem().setEnabled(true);

								mainView.getProjectPanel().setEnabled(true);
								mainView.getProjectPanel().enableFieldsEdit(false);

								projCombo.setEnabled(true);
								mainView.getTreePanel().getTree().setEnabled(true);
								mainView.getTablePanel().getTable().setEnabled(true);
								
								selectedProject = mainModel.getLastProject();
								projCombo.setSelectedItem(selectedProject);

							}
						}
						
						
						else if(saveBtn.getText().equalsIgnoreCase("Edit")){
							
							mainView.getProjectPanel().enableFieldsEdit(true);
							saveBtn.setText("Update");
							mainView.getProjectPanel().getDeleteBtn().setText("Cancel");
							mainView.getJobsTabbedPane().setEnabledAt(2, false);
						}
						
						else if(saveBtn.getText().equalsIgnoreCase("Update")){
							
								mainModel.updateProject(selectedProject, updatedProject);
								JOptionPane.showMessageDialog(null, "Project updated sucessfully");
								
								saveBtn.setText("Edit");
								mainView.getProjectPanel().getDeleteBtn().setText("Delete");
								actCombo.setEnabled(true);
								linkAll();
								refreshAll();
								projCombo.setSelectedItem(selectedProject);
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
		}
	}
	
	
	// inner class to implement Save/Edit/Modify Activity
	private class SaveActivityListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent ev) {
			
			boolean formReady = isFormReady(2);
			
			if(formReady){
				
			JButton saveBtn = (JButton) ev.getSource();
			String name = mainView.getActivityPanel().getName();
			String description = mainView.getActivityPanel().getDescription();
			double budget = mainView.getActivityPanel().getBudget();
			String startDate = mainView.getActivityPanel().getStartDate();
			String finishDate = mainView.getActivityPanel().getFinishDate();
			Status status = mainView.getActivityPanel().getStatus();
			int pID = selectedProject.getID();

				try {
					Activity updatedActivity = new Activity(pID, name, description, budget, startDate, finishDate, status);

							try {
									if(saveBtn.getText().equalsIgnoreCase("Save")){
										
										int suitsParent = mainModel.activityDatesSuitsParent(updatedActivity, selectedProject);
										
										if( mainView.getActivityPanel().startInPast()){
											JOptionPane.showMessageDialog(null, "Start in the past !!!!");
										}
										
										else if( ! mainModel.activityBudgetSuitsParent(updatedActivity, selectedProject)){
											JOptionPane.showMessageDialog(null, "Adjust: The Sum of a Project Activities budgets can't exceed their parent project ");
										}
										
										else if(suitsParent == 1) {
											JOptionPane.showMessageDialog(null, "Adjust: the Activity start date can't precede its parent project start date");
										}
										
										else if(suitsParent == 2) {
											JOptionPane.showMessageDialog(null, "Adjust: the Activity Finish date can't follow its parent project Finish date");
										}
										
										
										else{
											
											if(selectedProject.getStatus().equals(Status.LOCKED) && !mainView.getActivityPanel().getStatus().equals(Status.LOCKED) ){
												mainView.getActivityPanel().setStatus(Status.LOCKED);
												updatedActivity.setStatus(Status.LOCKED);
												JOptionPane.showMessageDialog(null, "Activity Status was set to Locked like its parent");
											}
											
											mainModel.addActivityToDb(updatedActivity);
											JOptionPane.showMessageDialog(null, "Activity created sucessfully");
										
											saveBtn.setText("Edit");
											mainView.getActivityPanel().getDeleteBtn().setText("Delete");
											saveBtn.setEnabled(true);
											mainView.getActivityPanel().getDeleteBtn().setEnabled(true);
											mainView.getActivityPanel().enableFieldsEdit(false);
											
											saveBtn.setText("Edit");
											mainView.getProjectPanel().getDeleteBtn().setText("Delete");
											mainView.getProjectPanel().getSaveBtn().setEnabled(true);
											mainView.getProjectPanel().getDeleteBtn().setEnabled(true);
											mainView.getProjectPanel().enableFieldsEdit(false);
											
											mainView.getJobsTabbedPane().setEnabledAt(1, true);
											mainView.getJobsTabbedPane().setEnabledAt(2, true);
											mainView.getJobsTabbedPane().setSelectedIndex(2);
											
											linkAll();
											refreshTree();
											refreshActivitiesCombo(selectedProject.getID());
											actCombo.setSelectedItem(mainModel.getLastActivity());
										}

									}
								
								
									else if(saveBtn.getText().equalsIgnoreCase("Edit")){
										
										mainView.getActivityPanel().enableFieldsEdit(true);
										mainView.getActivityPanel().getSaveBtn().setText("Update");
										mainView.getActivityPanel().getDeleteBtn().setText("Cancel");
									}
								
									else if(saveBtn.getText().equalsIgnoreCase("Update")){
										
										int suitsParent = mainModel.activityDatesSuitsParent(updatedActivity, selectedProject);
										
										if( ! mainModel.activityBudgetSuitsParent(updatedActivity, selectedProject)){
											JOptionPane.showMessageDialog(null, "Adjust: The Sum of a Project Activities budgets can't exceed their parent project ");
										}
										
										else if(suitsParent == 1) {
											JOptionPane.showMessageDialog(null, "Adjust: the Activity start date can't precede its parent project start date");
										}
										
										else if(suitsParent == 2) {
											JOptionPane.showMessageDialog(null, "Adjust: the Activity Finish date can't follow its parent project Finish date");
										}
										
										else if( ! mainModel.activityDatesSuitsAllPrereqs(selectedActivity, updatedActivity) ){
											JOptionPane.showMessageDialog(null, "Adjust: the Activity doesn't suit its prerequisites");
										}
										
										else if( ! mainModel.activityDatesSuitsAllSuccessors(selectedActivity, updatedActivity) ){
											JOptionPane.showMessageDialog(null, "Adjust: the Activity doesn't suit its successors");
										}
										
										else{
											
											if(selectedProject.getStatus().equals(Status.LOCKED) && !mainView.getActivityPanel().getStatus().equals(Status.LOCKED) ){
												mainView.getActivityPanel().setStatus(Status.LOCKED);
												updatedActivity.setStatus(Status.LOCKED);
												JOptionPane.showMessageDialog(null, "Activity Status was set to Locked like its parent");
											}
											updatedActivity.setPreReq(selectedActivity.getPreReq());
											mainModel.updateActivity(selectedActivity, updatedActivity);

											JOptionPane.showMessageDialog(null, "Activity updated sucessfully");
											mainView.getActivityPanel().getSaveBtn().setText("Edit");
											mainView.getActivityPanel().getDeleteBtn().setText("Delete");
											mainView.getActivityPanel().enableFieldsEdit(false);
											mainView.getJobsTabbedPane().setSelectedIndex(2);
											mainView.getJobsTabbedPane().setEnabledAt(1, true);
											mainView.getJobsTabbedPane().setEnabledAt(2, true);
											if(prereqEditable != null){
												prereqEditable.dispose();
											}
											linkAll();
											refreshActivitiesCombo(selectedProject.getID());
										}

									}		
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

					} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
		
	
	// inner class to implement Delete Project
	private class DeleteProjectListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ev) {
			// TODO Auto-generated method stub

			try {
				JButton delBtn = (JButton) ev.getSource();
				if(delBtn.getText().equalsIgnoreCase("Delete")){
					
					Project deletedProject = (Project)projCombo.getSelectedItem();
					mainModel.deleteProjectfromDb(deletedProject.getID());
					JOptionPane.showMessageDialog(null, "Project deleted sucessfully");
					setInitialPosition();
					linkAll();
					refreshAll();
				}
				
				else if(delBtn.getText().equalsIgnoreCase("Cancel")) {
					if(projCombo.getSelectedIndex() < 0) {				// in case of cancel creating a new project

						setInitialPosition();
						return;
					}
					else {												// in case of cancel editing an existing project
						mainView.getProjectPanel().getSaveBtn().setText("Edit");
						delBtn.setText("Delete");
						mainView.getNewProjectItem().setEnabled(true);
						mainView.getProjectPanel().enableFieldsEdit(false);
						mainView.getActivityPanel().setEnabled(true);
						projCombo.setEnabled(true);
						projCombo.setSelectedItem(selectedProject);
						refreshActivitiesCombo(selectedProject.getID());
						actCombo.setSelectedItem(selectedActivity);
						mainView.getTreePanel().getTree().setEnabled(true);
						mainView.getTablePanel().getTable().setEnabled(true);

					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	// inner class to implement Delete Activity
	private class DeleteActivityListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ev) {
			// TODO Auto-generated method stub
			
			JButton delBtn = (JButton) ev.getSource();
			
			try {
				if(delBtn.getText().equalsIgnoreCase("Delete")){
					Activity deletedActivity = selectedActivity;
					mainModel.deleteActivityfromDb(deletedActivity.getID());

					JOptionPane.showMessageDialog(null, "Activity deleted sucessfully");
					
					refreshActivitiesCombo(deletedActivity.getParentProjectID());
					linkAll();
					refreshTree();
				}
				
				else if(delBtn.getText().equalsIgnoreCase("Cancel")) {
					
					if(actCombo.getSelectedIndex() < 0) {								// case of cancel creating new Activity
						mainView.getActivityPanel().clearForm();
						mainView.getJobsTabbedPane().setSelectedIndex(1);
						mainView.getJobsTabbedPane().setEnabledAt(2,false);
					}
					else {																// case of cancel editing an existing Activity
						mainView.getJobsTabbedPane().setEnabledAt(2,true);
						mainView.getJobsTabbedPane().setSelectedIndex(2);
					}
					
					delBtn.setText("Delete");											// common Behaviour
					mainView.getActivityPanel().getSaveBtn().setText("Edit");
					mainView.getActivityPanel().getDeleteBtn().setText("Delete");
					mainView.getActivityPanel().enableFieldsEdit(false);
					mainView.getProjectPanel().getSaveBtn().setText("Edit");
					mainView.getProjectPanel().getSaveBtn().setEnabled(true);
					mainView.getProjectPanel().getDeleteBtn().setText("Delete");
					mainView.getProjectPanel().getDeleteBtn().setEnabled(true);
					mainView.getProjectPanel().getNewActivityBtn().setEnabled(true);
					mainView.getProjectPanel().setEnabled(true);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	// inner class to implement selecting a project from Existing Projects combo 
	private class ComboListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent ev) {
		    
		    selectedProject = (Project) ev.getItem();

		    if (ev.getStateChange() == ItemEvent.SELECTED) {
				mainView.getJobsTabbedPane().setSelectedIndex(1);
				mainView.getJobsTabbedPane().setEnabledAt(1, true);
				mainView.getJobsTabbedPane().setEnabledAt(2, false);
				fillProjectForm(selectedProject);
				
				@SuppressWarnings("unchecked")
				int index = ((JComboBox<Project>) ev.getSource()).getSelectedIndex();
				mainView.getTablePanel().getTable().setRowSelectionInterval(index-1, index-1);
				DefaultMutableTreeNode pNode = mainView.getTreePanel().searchNode(selectedProject);
				if(pNode != null){
					TreePath path = new TreePath(pNode.getPath());
					mainView.getTreePanel().getTree().getSelectionModel().addSelectionPath(path);

				}

		    } else if (ev.getStateChange() == ItemEvent.DESELECTED) {
		    	mainView.getJobsTabbedPane().setEnabledAt(1, false);
		    	mainView.getJobsTabbedPane().setEnabledAt(2, false);
				mainView.getJobsTabbedPane().setSelectedIndex(0);
				if(projCombo.getSelectedIndex() == -1){
					selectedProject = null;
					mainView.getTreePanel().getTree().setSelectionRow(0);
				}

		    }
		    
		}
	}	
	
	
	// inner class to implement selecting an Activity from Existing Projects combo 
	private class ActivitiesComboListener implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent ev) {
			
		    selectedActivity = (Activity) ev.getItem();	 

		    if (ev.getStateChange() == ItemEvent.SELECTED) {
		    	
		    	fillActivityForm(selectedActivity);
		    	mainView.getJobsTabbedPane().setEnabledAt(2, true);
		    	mainView.getJobsTabbedPane().setSelectedIndex(2);
		    	
				DefaultMutableTreeNode aNode = mainView.getTreePanel().searchNode(selectedActivity);
				if(aNode != null){
					TreePath path = new TreePath(aNode.getPath());
					mainView.getTreePanel().getTree().getSelectionModel().addSelectionPath(path);
				} 
				


		    } else if (ev.getStateChange() == ItemEvent.DESELECTED) {
		      // Item is no longer selected
		    	mainView.getJobsTabbedPane().setEnabledAt(2, false);
		    	mainView.getJobsTabbedPane().setSelectedIndex(1);
		    	
				if(actCombo.getSelectedIndex() == -1){
					selectedActivity = null;
					DefaultMutableTreeNode pNode = mainView.getTreePanel().searchNode(selectedProject);
					if(pNode != null){
						TreePath path = new TreePath(pNode.getPath());
						mainView.getTreePanel().getTree().getSelectionModel().addSelectionPath(path);

					}

				}
		    }
			
		}
	}
	
	
	// implements selecting a tree node (single selection only)
	private class SelectionListener implements TreeSelectionListener {

		@Override
		public void valueChanged(TreeSelectionEvent e) {
			
			DefaultMutableTreeNode node =  (DefaultMutableTreeNode) mainView.getTreePanel().getTree().getSelectionModel().getSelectionPath().getLastPathComponent();
		    if (node.isRoot()){
		    	mainView.getTablePanel().getTable().clearSelection();
		    	projCombo.setSelectedIndex(-1);
		    	actCombo.setSelectedIndex(-1);
		        return;
		    }

			Job treeSelectedJob = (Job) node.getUserObject();
			
			if(treeSelectedJob instanceof Project){
				projCombo.setSelectedItem((Project) treeSelectedJob);
		    	actCombo.setSelectedIndex(-1);
			}
			
			
			else if(treeSelectedJob instanceof Activity){
				Activity sAct = (Activity) treeSelectedJob;
				try {
					selectedProject = mainModel.getProjectByID(sAct.getParentProjectID());
					projCombo.setSelectedItem(selectedProject);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				actCombo.setSelectedItem(sAct);
			}
			
			else{
		    	mainView.getTablePanel().getTable().clearSelection();
		    	projCombo.setSelectedIndex(-1);
		    	actCombo.setSelectedIndex(-1);
			}
			
		}

	}
	
	
	// 
	private class TableDataChangedListener implements TableModelListener{

		@Override
		public void tableChanged(TableModelEvent e) {
		}
	}
	
	
	// implements adding a prerequisite activity in Activity form 
	private class PrereqListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(currentMember.isManager()){
//				JOptionPane.showMessageDialog(null, "Implemented Next Iteration");
				
				if(prereqEditable != null){			// prevents opening same window several times
					prereqEditable.dispose();
				}
				
				try {
					ArrayList<Activity> allActivities = mainModel.getProjectActivities(selectedProject.getID());
					ArrayList<Activity> prereqList;
					ArrayList<Activity> availableList = new ArrayList<Activity>();
					
					if(selectedActivity != null) {
						prereqList =  mainModel.getActivityPreReq(selectedActivity.getID());
						
						for(Activity activity: allActivities){
							// I had to override equals in Activity class to make the ArrayList.contains ... work !!!!!
							if( activity.getID() != selectedActivity.getID() && !prereqList.contains(activity)){
									availableList.add(activity);
							}
						}
						
					} else {		// not used unless we can add a prerequisite activity while creating a new activity ... not recommended 
						prereqList = new ArrayList<Activity>();
						availableList = allActivities;
					}
					
					prereqEditable = new EditableList<Activity>("Available", availableList, prereqList);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				prereqEditable.addButtonListener(new editableAddPrereqListener());
				prereqEditable.removeButtonListener(new editableRemovePrereqListener());
			} 
			// if a team member  //////////////////////////////////////// iteration 2
			else {
				
			}

		}	
	}
		
	
	// implements the add button in the Editable list of prerequisite in activity form
	private class editableAddPrereqListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(currentMember.isManager()){
				Activity elt = prereqEditable.getAvailableList().getSelectedValue();
				
				if(!prereqEditable.getAssignedListModel().contains(elt)){
					
					if(activitySuitsPrereq(selectedActivity, elt)){
						prereqEditable.getAssignedListModel().addElement(elt);
						selectedActivity.addpreReq(elt);
						try {
							mainModel.addActivityPrereq(elt.getID() , selectedActivity.getID() );
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						prereqEditable.getAvailableListModel().removeElement(elt);
					}
				}
			}
			// if a team member //////////////////////////////////////// iteration 2
			else {
				
			}
		}

	}
	
	
	// implements the remove button in the Editable list of prerequisite in activity form
	private class editableRemovePrereqListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Activity elt = prereqEditable.getAssignedList().getSelectedValue();
			
			prereqEditable.getAssignedListModel().removeElement(elt);
			selectedActivity.removepreReq(elt);
			try {
				mainModel.removeActivityPrereq(elt.getID() , selectedActivity.getID() );
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			if(!prereqEditable.getAvailableListModel().contains(elt)){
				prereqEditable.getAvailableListModel().addElement(elt);
			}
		}
	}
	
	
	// implements the add/remove member button in the activity form
	private class MemberListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
//			JOptionPane.showMessageDialog(null, "Implemented Next Iteration");
			if(currentMember.isManager()){

//				JOptionPane.showMessageDialog(null, "Implemented Next Iteration");
				
				if(memberEditable != null){			// prevents opening same window several times
					memberEditable.dispose();
				}
				
				try {
					ArrayList<Member> allMembers = mainModel.getAllTeamMembers();

					ArrayList<Member> membersList;
					ArrayList<Member> availableList = new ArrayList<Member>();
					
					if(selectedActivity != null) {
						membersList =  mainModel.getActivityMembers(selectedActivity.getID());
						for(Member member: allMembers){
							// I had to override equals in Member class to make the ArrayList.contains ... work !!!!!
							if( ! membersList.contains(member)){
									availableList.add(member);
							}
						}
						
					} else {			// not actually used unless we can add a Member to an activity while creating a new activity ... not recommended 
						membersList = new ArrayList<Member>();
						availableList = allMembers;
					}
					
					memberEditable = new EditableList<Member>("Available", availableList, membersList);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				memberEditable.addButtonListener(new editableAddMemberListener());
				memberEditable.removeButtonListener(new editableRemoveMemberListener());
			
			}
			else{
				try {
					ArrayList<Member> allMembers = mainModel.getAllTeamMembers();

					ArrayList<Member> membersList;
					ArrayList<Member> availableList = new ArrayList<Member>();
					
					if(selectedActivity != null) {
						membersList =  mainModel.getActivityMembers(selectedActivity.getID());
						for(Member member: allMembers){
							// I had to override equals in Member class to make the ArrayList.contains ... work !!!!!
							if( ! membersList.contains(member)){
									availableList.add(member);
							}
						}
						
					} else {			// not actually used unless we can add a Member to an activity while creating a new activity ... not recommended 
						membersList = new ArrayList<Member>();
						availableList = allMembers;
					}
					
					activityTeam = new MemberListView<Member>("Available", membersList);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			
		}
	}
	
	
	// implements the add button in the Editable list of prerequisite in activity form
	private class editableAddMemberListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Member elt = memberEditable.getAvailableList().getSelectedValue();
			
			if(!memberEditable.getAssignedListModel().contains(elt)){
				
				memberEditable.getAssignedListModel().addElement(elt);
				selectedActivity.addMemberToTeam(elt);
					try {
						mainModel.addMemberToActivity(elt.getUserID() , selectedActivity.getID() );
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					memberEditable.getAvailableListModel().removeElement(elt);
			}
		}
	}
	
	
	// implements the remove button in the Editable list of prerequisite in activity form
	private class editableRemoveMemberListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Member elt = memberEditable.getAssignedList().getSelectedValue();
			
			memberEditable.getAssignedListModel().removeElement(elt);
			selectedActivity.removeMemberFromTeam(elt);
			try {
				mainModel.removeMemberFromActivity(elt.getUserID() , selectedActivity.getID() );
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			if(!memberEditable.getAvailableListModel().contains(elt)){
				memberEditable.getAvailableListModel().addElement(elt);
			}
		}
	}

	
	// implements creating a new activity in the project form
	private class NewActivityBtnListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ev) {
			// TODO Auto-generated method stub
			
			JButton newActBtn = (JButton) ev.getSource();
			newActBtn.setEnabled(false);
			
			refreshActivitiesCombo(selectedProject.getID());
			mainView.getProjectPanel().getSaveBtn().setEnabled(false);
			mainView.getProjectPanel().getDeleteBtn().setEnabled(false);
			
			mainView.getActivityPanel().getSaveBtn().setText("Save");
			mainView.getActivityPanel().getDeleteBtn().setText("Cancel");
			mainView.getActivityPanel().enableFieldsEdit(true);
			mainView.getActivityPanel().getSaveBtn().setEnabled(true);
			mainView.getActivityPanel().getDeleteBtn().setEnabled(true);
			mainView.getActivityPanel().getPrereqBtn().setEnabled(false);
			mainView.getActivityPanel().getMemberBtn().setEnabled(false);
			mainView.getActivityPanel().clearForm();
			
			mainView.getJobsTabbedPane().setEnabledAt(2, true);
			mainView.getJobsTabbedPane().setSelectedIndex(2);	
		}
		
	}
	
	
	// implements creating a new project in the new menu item in the menu bar
	private class NewProjectItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				
			refreshAll();
			
			mainView.getNewProjectItem().setEnabled(false);
			projCombo.setEnabled(false);
			mainView.getTreePanel().getTree().setEnabled(false);
			mainView.getTablePanel().getTable().setEnabled(false);
						
			mainView.getJobsTabbedPane().setEnabledAt(1, true);
			mainView.getJobsTabbedPane().setSelectedIndex(1);
			mainView.getJobsTabbedPane().setEnabledAt(2, false);
			mainView.getProjectPanel().enableFieldsEdit(true);
			mainView.getActivityPanel().enableFieldsEdit(false);
			
			mainView.getProjectPanel().clearForm();
			mainView.getProjectPanel().getDeleteBtn().setText("Cancel");
			mainView.getProjectPanel().getDeleteBtn().setEnabled(true);
			mainView.getProjectPanel().getSaveBtn().setText("Save");
			mainView.getProjectPanel().getSaveBtn().setEnabled(true);
			mainView.getProjectPanel().getNewActivityBtn().setEnabled(false);

		}
		
	}
	
	
	// implements exit menu item in the menu bar
	private class ExitItemListener implements ActionListener {


		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			JOptionPane.showMessageDialog(null, "Confirm Exit");
			System.exit(0);
		
		}
		
	}
	
	
	// implements selecting a single row (No cell selection) from the table
	private class tableRowSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent ev) {

	        int[] selectedRow = mainView.getTablePanel().getTable().getSelectedRows();

	        	if(selectedRow.length > 0){
	        		projCombo.setSelectedIndex(selectedRow[0]+1);
	        	}
	        	else {
	        		projCombo.setSelectedIndex(-1);
	        	}
		}	
	}
	
	
	//Listener of menuy
	private class GantItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			try {
				if (selectedProject == null)
				{
					JOptionPane.showMessageDialog(null, "Please select a Project ");
					return;
				}
				
				
				ArrayList<Activity> projectActs = mainModel.getProjectActivities(selectedProject.getID());
				selectedProject.setProjectActivities(projectActs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(selectedProject.getProjectActivities());
			final GanttView demo = new GanttView( selectedProject.getName() +  "'s Gantt Chart", selectedProject);
			
	        demo.pack();
	        
	        RefineryUtilities.centerFrameOnScreen(demo);
	        demo.setVisible(true);
	        demo.createDataset();
		}
		
	}
	////////////////////// Helper Methods \\\\\\\\\\\\\\\\\\\\\\\\
	
	// checks if the project / activity form fields are suitable for creating project / activity
	public boolean isFormReady(int classType){
		
		int isReady = -1;
		// classType = 1   for a Project form
		if(classType == 1){
			isReady = mainView.getProjectPanel().isJobFormReady() ;
		}
		
		// classType = 1   for an Activity form
		if(classType == 2){
			isReady = mainView.getActivityPanel().isJobFormReady() ;
		}

		switch(isReady) {
		case 0:
			JOptionPane.showMessageDialog(null, "Fill all fields");
			return false;
			
		case 1:
			JOptionPane.showMessageDialog(null, "Finish Date can't percede Start Date");
			return false;
			
		case 2:
			JOptionPane.showMessageDialog(null, "Status can't be in progress while StartDate is yet to come, Change the Start Date or Change Status");
			return false;
			
		case 3: 	
			JOptionPane.showMessageDialog(null, "Status can't be Finished while Finish Date is yet to come, Change the Finish Date or Change Status");
			return false;

		}
		return true;
	}
	
	
	// loads a project fields from the database and displays it in the project form
	public void fillProjectForm(Project sProject){
	
		mainView.getProjectPanel().enableFieldsEdit(false);
		ProjectPanel form = (ProjectPanel) mainView.getProjectPanel();
		
		mainView.ProjectPaneEnabled(true);
		mainView.getJobsTabbedPane().setSelectedIndex(1);
		mainView.getProjectPanel().getDeleteBtn().setEnabled(true);
		mainView.getProjectPanel().getSaveBtn().setText("Edit");

		
		form.setNameFld(sProject.getName());
		form.setDescriptionArea(sProject.getDescription());
		form.setBudgetFld(sProject.getBudget());
		form.setStartDate(sProject.getStartDate());
		form.setFinishDate(sProject.getFinishDate());
		form.setStatus(sProject.getStatus());
		refreshActivitiesCombo(sProject.getID());
	}
	
	
	// loads an activity fields from the database and displays it in the activity form	
	public void fillActivityForm(Activity sActivity){
		
		mainView.getActivityPanel().enableFieldsEdit(false);
		ActivityPanel form = (ActivityPanel) mainView.getActivityPanel();
		
		mainView.ActivityPaneEnabled(true);
		mainView.getJobsTabbedPane().setSelectedIndex(2);
		mainView.getActivityPanel().getDeleteBtn().setEnabled(true);
		mainView.getActivityPanel().getSaveBtn().setText("Edit");
		
		form.setNameFld(sActivity.getName());
		form.setDescriptionArea(sActivity.getDescription());
		form.setBudgetFld(sActivity.getBudget());
		form.setStartDate(sActivity.getStartDate());
		form.setFinishDate(sActivity.getFinishDate());
		form.setStatus(sActivity.getStatus());
	}
	
	
	// loads projects that belongs to the current member(if assigned an activity in this project) /manager (if created by this manager) 
	public void setProjectsToMember(){

		if (currentMember.isManager()){
			try {
				allMemberProjects = mainModel.getManagerProjects(currentMember.getUserID());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else {
			try {
				allMemberProjects = mainModel.getMemberProjects(currentMember.getUserID());
//				System.out.println(allMemberProjects.get(0).getStartDate());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(!allMemberProjects.isEmpty()){
			currentMember.setMemberProjects(allMemberProjects);
		}
	}
	
	
	// loads the activities of a project (for a member only those activities whom he is assigned to are loaded)
	public void linkActivitiesToProjets() {
		
		if (currentMember.isManager()){ 
			for(Project proj: allMemberProjects){

				try {
					proj.setProjectActivities(mainModel.getProjectActivities(proj.getID()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		else {
			for(Project proj: allMemberProjects){
				try {
					ArrayList<Activity> projActivities = mainModel.getProjectActivities(proj.getID());
					ArrayList<Activity> memberActsOfProject = new ArrayList<Activity>();
					
					for(Activity act: projActivities){
						if( allMemberActivities.contains(act)){
							memberActsOfProject.add(act);
						}
					}

					proj.setProjectActivities(memberActsOfProject);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	
	
	public void linkPrereqsToActivities() {
		
			for(Activity act: allMemberActivities){
				try {
					act.setPreReq(mainModel.getActivityPreReq(act.getID()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	
	
	public void linkMemberstoActivites() {
		
	}

	// invokes the methods:	 setProjectsToMember, inkActivitiesToProjets , linkPrereqsToActivities , linkMemberstoActivites
	public void linkAll() {
		try {
			setProjectsToMember();
			setMemberActivities();
			linkActivitiesToProjets();
			linkPrereqsToActivities();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// enables initial default features for a manager
	public void setInitialPosition(){
		
		mainView.getTreePanel().getTree().setEnabled(true);
		mainView.getTablePanel().getTable().setEnabled(true);
		projCombo.setEnabled(true);
		projCombo.setSelectedIndex(-1);
		
		actCombo.setEnabled(true);
		actCombo.setSelectedIndex(-1);

		mainView.getProjectPanel().setEnabled(false);
		mainView.getProjectPanel().clearForm();
		
		mainView.getActivityPanel().setEnabled(false);
		mainView.getActivityPanel().clearForm();
		
		mainView.getJobsTabbedPane().setSelectedIndex(0);
		mainView.getJobsTabbedPane().setEnabledAt(1, false);
		mainView.getJobsTabbedPane().setEnabledAt(2, false);
		
		mainView.getNewProjectItem().setEnabled(true);
		
		mainView.getProjectPanel().getSaveBtn().setText("Save");
		mainView.getProjectPanel().getDeleteBtn().setText("Delete");
		
		mainView.getActivityPanel().getSaveBtn().setText("Save");
		mainView.getActivityPanel().getDeleteBtn().setText("Delete");
	}
	
	// loads updated member/manager projects into the existingProjects combo box in the main tool bar
	public void refreshProjectsCombo(){
		
		projCombo.removeAllItems();
		projCombo.insertItemAt(null, 0);
		
		ArrayList <Project> mProjects;
		try {
			if (currentMember.isManager()){
				mProjects = mainModel.getManagerProjects(currentMember.getUserID()) ;
			} else {
				mProjects = mainModel.getMemberProjects(currentMember.getUserID()) ;
			}

			for(Project proj: mProjects){
				projCombo.addItem(proj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	
	public void refreshTree(){
		mainView.getTreePanel().updateUI();
		mainView.getTreePanel().setmProjects(allMemberProjects);
		mainView.getTreePanel().refreshTree();
		mainView.getTreePanel().addTreeSelectionListener(new SelectionListener());
	}
	
	
	public void refreshTable(){
		
		try {
			mainView.getTablePanel().setData(allMemberProjects);  			///////////////////////////// fixed
			mainView.getTablePanel().refreshTable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void refreshAll(){
		
		refreshProjectsCombo();
		refreshTable();
		refreshTree();
		refreshReport();
	}
	
	
	public void refreshActivitiesCombo(int pID){
		actCombo.removeAllItems();
		actCombo.insertItemAt(null, 0);

		try {
			ArrayList<Activity> pActivities = new ArrayList<Activity>() ;
			for(Project proj: allMemberProjects){
				if(proj.equals(selectedProject)){
					pActivities = proj.getProjectActivities();
				}
			}
			
			if(!pActivities.isEmpty()){
				for(Activity act: pActivities) {
	
					actCombo.addItem(act);
				}
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	
	// give a report of the projects managed by a manager or activities assigned to a team member  /////////////// Iteration 2	
	public void refreshReport(){
		
		 mainView.getReportPanel().setReport("");
		 mainView.getReportPanel().setUserName(currentMember.getUsername());
		 mainView.getReportPanel().getUserNameFld().setEditable(false);
		 
		 ArrayList<Project> myProjects;
		try {
			myProjects = mainModel.getManagerProjects(currentMember.getUserID());

			 String Report = "You Manage: \n\n";
			 
			 if(!myProjects.isEmpty()){
				 for(Project pro: myProjects){
					 if(!myProjects.isEmpty()){
						 String name = pro.getName() + "\n";
						 Report +=  name;

					 }
					 mainView.getReportPanel().setReport(Report);
				 }
			 } else {
				 Report += "None";
			 }

			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	 mainView.getReportPanel().getReportArea().setEditable(false);		 
	}
		
	
	public Member getCurrentMember() {
		return currentMember;
	}
	
	
	// get all Member Activities
	public void setMemberActivities() {
		
		// if manager
		if (currentMember.isManager()){

			for(Project proj: allMemberProjects){
				try {
					allMemberActivities.clear();
					allMemberActivities.addAll(mainModel.getProjectActivities(proj.getID()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		else {
				try {
					allMemberActivities = mainModel.getMemberActivities(currentMember.getUserID());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

	}
	
	
	// used in prereqListener to check if a prerequisite activity suits a specific activity before adding it as a prerequisite
	public boolean activitySuitsPrereq(Activity originalActivity, Activity prereqActivity){
		
		// "Start date can't precede its prerequisite Finish date"

		if( ! mainModel.activityDatesSuitsPrereq(originalActivity , prereqActivity)) {	
			JOptionPane.showMessageDialog(null, "Activity start date can't precede its prerequisite Finish date");
			return false;
		}
		
		// check status
		else {
			if( ! prereqActivity.getStatus().equals(Status.FINISHED)){
				originalActivity.setStatus(Status.LOCKED);
				JOptionPane.showMessageDialog(null, "Activity is now Locked");
			}
			
			else {
				originalActivity.setStatus(Status.UNLOCKED);
				JOptionPane.showMessageDialog(null, "Activity is ready ");
			}
			return true;
		}
	}
	
	
	// registers all listeners
	public void registerListeners(){

		mainView.getLoginPage().addLoginListener(new LoginListener());
		mainView.getLoginPage().addRegisterBtnListener(new RegisterBtnListener());
		mainView.getLoginPage().getSignupFrame().addSignupListener(new SignupListener());
		mainView.getLoginPage().getSignupFrame().addCancelSignUpListener(new CancelSignUpListener());
		mainView.getProjectPanel().addSaveListener(new SaveProjectListener());
		mainView.getProjectPanel().addDeleteListener(new DeleteProjectListener());
		mainView.getActivityPanel().addSaveListener(new SaveActivityListener());
		mainView.getActivityPanel().addDeleteListener(new DeleteActivityListener());
		mainView.addComboListener(new ComboListener());
		mainView.getProjectPanel().addProjectActivitiesComboListener(new ActivitiesComboListener());
		mainView.getTreePanel().addTreeSelectionListener(new SelectionListener());
		mainView.getTablePanel().addTableListener(new TableDataChangedListener());
		mainView.getActivityPanel().addPrereqButtonListener(new PrereqListener());
		mainView.getActivityPanel().addMemberButtonListener(new MemberListener());				// Iteration 2
		mainView.getProjectPanel().addNewActivityBtnListener(new NewActivityBtnListener());
		mainView.addNewProjectItemListener(new NewProjectItemListener());
		mainView.addExitItemListener(new ExitItemListener());
		mainView.getTablePanel().addListSelectionListener(new tableRowSelectionListener());
		mainView.addGantItemListener(new GantItemListener());
	}
	
	
	// disable manager capabilities for a team member login
	public void disableManagerFeatures(){
		
		mainView.getNewProjectItem().setEnabled(false);
		mainView.getProjectPanel().enableFieldsEdit(false);
		mainView.getActivityPanel().enableFieldsEdit(false);
		mainView.getProjectPanel().getSaveBtn().setVisible(false);
		mainView.getActivityPanel().getSaveBtn().setVisible(false);
		mainView.getProjectPanel().getDeleteBtn().setVisible(false);
		mainView.getActivityPanel().getDeleteBtn().setVisible(false);
		mainView.getProjectPanel().getNewActivityBtn().setVisible(false);
		
		mainView.getActivityPanel().getPrereqBtn().setVisible(false);
		mainView.getActivityPanel().getMemberBtn().setText("Show Assigned Members");
		DefaultTreeModel model = mainView.getTreePanel().getModelTree();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		root.setUserObject("Member Tasks");
		model.nodeChanged(root);
		
		
	}
	

}

