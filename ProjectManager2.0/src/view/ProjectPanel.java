package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;






import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import model.Project;

@SuppressWarnings("serial")
public class ProjectPanel extends JPanel {

	private JTextField nameTextField, budgetTextField;
	private JPanel projectTabPanel, projectForm;
	private DisplayPanel displayPanel;
	private JTextArea descriptionArea;
	private JComboBox<String> statusCombo;
	private JButton addActivityButton, deleteProjectButton, editProjectButton, addMemberButton, 
			addGantChartButton, evaluateCPAButton, pertButton, earnedValueButton,editActivityButton,deleteActivityButton ;

	private ActivityFrame activityFrame;
	
	private boolean editProjectMode = false;
	private boolean editActivitytMode = false;

	protected ProjectPanel(JTabbedPane tabbedPane) {
		
		projectTabPanel = new JPanel();
		projectTabPanel.setBackground(new Color(211, 211, 211));
		projectTabPanel.setLayout(null);
		tabbedPane.addTab("Project", null, projectTabPanel, null);
		tabbedPane.setEnabledAt(1,false);

		JLabel lblProjectOptions = new JLabel("Project Options");
		lblProjectOptions.setFont(new Font("High Tower Text", Font.ITALIC, 18));
		lblProjectOptions.setBounds(20, 26, 131, 30);
		projectTabPanel.add(lblProjectOptions);

		deleteProjectButton = new JButton(new ImageIcon("Images\\trash.png"));
		deleteProjectButton.setToolTipText("Delete current project");
		deleteProjectButton.setBounds(20, 80, 60, 60);
		projectTabPanel.add(deleteProjectButton);

		editProjectButton = new JButton(new ImageIcon("Images\\edit_project.png"));
		editProjectButton.setToolTipText("Edit project");
		editProjectButton.setBounds(117, 80, 60, 60);
		projectTabPanel.add(editProjectButton);

		addMemberButton = new JButton(new ImageIcon("Images\\add_members.png"));
		addMemberButton.setToolTipText("Add members to a project");
		addMemberButton.setBounds(214, 80, 60, 60);
		projectTabPanel.add(addMemberButton);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(306, 0, 2, 611);
		projectTabPanel.add(separator_1);

		JLabel lblActivityOptions = new JLabel("Activity Options");
		lblActivityOptions.setFont(new Font("High Tower Text", Font.ITALIC, 18));
		lblActivityOptions.setBounds(20, 179, 131, 30);
		projectTabPanel.add(lblActivityOptions);

		activityFrame = new ActivityFrame();
		addActivityButton = new JButton(new ImageIcon("Images\\add_activity.png"));
		addActivityButton.setToolTipText("Add new activity");
		addActivityButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				activityFrame.setVisible(true);
				activityFrame.clearForm();
			}
		});
		addActivityButton.setBounds(20, 228, 60, 60);
		projectTabPanel.add(addActivityButton);

		editActivityButton = new JButton(new ImageIcon("Images\\editActivity.png"));
		editActivityButton.setToolTipText("Edit an activity");
		editActivityButton.setBounds(117, 228, 60, 60);
		projectTabPanel.add(editActivityButton);

		deleteActivityButton = new JButton(new ImageIcon("Images\\deleteActivity.png"));
		deleteActivityButton.setToolTipText("Delete an activity");
		deleteActivityButton.setBounds(214, 228, 60, 60);
		projectTabPanel.add(deleteActivityButton);

		JLabel lblAdditionalFeatures = new JLabel("Additional Features");
		lblAdditionalFeatures.setFont(new Font("High Tower Text", Font.ITALIC, 18));
		lblAdditionalFeatures.setBounds(20, 336, 154, 30);
		projectTabPanel.add(lblAdditionalFeatures);

		addGantChartButton = new JButton(new ImageIcon("Images\\add_chart.png"));
		addGantChartButton.setToolTipText("Display Gant chart");
		addGantChartButton.setBounds(20, 383, 60, 60);
		projectTabPanel.add(addGantChartButton);

		evaluateCPAButton = new JButton(new ImageIcon("Images\\cpmicon.png"));
		evaluateCPAButton.setToolTipText("Evaluate a critical path");
		evaluateCPAButton.setBounds(117, 383, 60, 60);
		projectTabPanel.add(evaluateCPAButton);

		pertButton = new JButton(new ImageIcon("Images\\PERT.png"));
		pertButton.setToolTipText("Perform PERT analysis");
		pertButton.setBounds(214, 383, 60, 60);
		projectTabPanel.add(pertButton);

		earnedValueButton = new JButton(new ImageIcon("Images\\earned-value.png"));
		earnedValueButton.setToolTipText("Perform earned value analysis");
		earnedValueButton.setBounds(20, 471, 60, 60);
		projectTabPanel.add(earnedValueButton);

		displayPanel = new DisplayPanel();
		displayPanel.setBackground(new Color(169, 169, 169));
		projectTabPanel.add(displayPanel);

	}

	protected void setStatusDefault(int def) {
		statusCombo.setSelectedIndex(def);
	}
	

	public void setEditProjectMode(boolean mode) {
		editProjectMode = mode;
	}
	
	public boolean getEditProjectMode() {
		return editProjectMode;
	}
	
	public void setEditActivityMode(boolean mode) {
		editActivitytMode  = mode;
	}

	public boolean getEditActivityMode() {
		return editActivitytMode;
	}
	
	public String getName() {
		return nameTextField.getText();
	}

	public String getDescription() {
		return descriptionArea.getText();
	}

	public double getBudget(){
		return Double.parseDouble(budgetTextField.getText() );
	}

	public DisplayPanel getDisplayPanel() {
		return displayPanel;
	}
	
/*	public String getStartDate(){
		return  startDateCombo.getSelectedItem().toString();
	}

	public String getFinishDate(){
		return  finishDateCombo.getSelectedItem().toString();
	}
*/
	public int getStatus(){
		return statusCombo.getSelectedIndex();
	}

	public ActivityFrame getActivityFrame(){
		return this.activityFrame;
	}
	
	//Display details according to the project currently opened.
	public void updateDisplayPanel(Project project) {
		
		String name = project.getName().toUpperCase();
		String progress = project.getStatus().toString();
		String startDate = project.getStartDate();
		String endDate = project.getEndDate();
		String description = project.getDescription();
		String budget = String.valueOf(project.getBudget());
		
		displayPanel.updateProjectInfo(name, progress, startDate, endDate, description, budget);
	}
	
	//ADDED THIS : TO PASS the updated list of activities to the drop down prereq menu!
	public void addNewActivityListener(ActionListener listener) {
		addActivityButton.addActionListener(listener);
	}
	
	public void addEditProjectListener(ActionListener listener) {
		editProjectButton.addActionListener(listener);
	}
	
	public void addDeleteProjectListener(ActionListener listener) {
		deleteProjectButton.addActionListener(listener);
	}

	// adding an action listener to the edit activity button
	public void addEditActivityListener(ActionListener editActivityListener){
		editActivityButton.addActionListener(editActivityListener);
	}

	// adding an action listener to the delete activity button
	public void addDeleteActivityListener(ActionListener deleteActivityListener){
		deleteActivityButton.addActionListener(deleteActivityListener);
	}


}



