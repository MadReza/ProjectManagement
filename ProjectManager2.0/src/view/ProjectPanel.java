package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;






import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import model.Project;

@SuppressWarnings("serial")
public class ProjectPanel extends JPanel {
	
	private JPanel projectTabPanel, projectForm;
	private JTextField nameTextField, budgetTextField;
	private DisplayPanel displayPanel;
	private JTextArea descriptionArea;
	private JComboBox<String> statusCombo;
	private JButton addActivityButton, deleteProjectButton, editProjectButton, choosePrereqsButton, addMemberButton, 
			addGantChartButton, evaluateCPAButton, pertButton, earnedValueButton,editActivityButton,deleteActivityButton ;
	private ActivityFrame activityFrame;
	private JFrame prereqSelectionFrame;
	private JTable availableActivities, chosenPrereqs;
		
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
		deleteProjectButton.setBounds(20, 67, 60, 60);
		projectTabPanel.add(deleteProjectButton);

		editProjectButton = new JButton(new ImageIcon("Images\\edit_project.png"));
		editProjectButton.setToolTipText("Edit project");
		editProjectButton.setBounds(117, 67, 60, 60);
		projectTabPanel.add(editProjectButton);

		addMemberButton = new JButton(new ImageIcon("Images\\add_members.png"));
		addMemberButton.setToolTipText("Add members to a project");
		addMemberButton.setBounds(214, 67, 60, 60);
		projectTabPanel.add(addMemberButton);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(306, 0, 2, 611);
		projectTabPanel.add(separator_1);

		JLabel lblActivityOptions = new JLabel("Activity Options");
		lblActivityOptions.setFont(new Font("High Tower Text", Font.ITALIC, 18));
		lblActivityOptions.setBounds(20, 153, 131, 30);
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
		addActivityButton.setBounds(20, 194, 60, 60);
		projectTabPanel.add(addActivityButton);

		editActivityButton = new JButton(new ImageIcon("Images\\editActivity.png"));
		editActivityButton.setToolTipText("Edit an activity");
		editActivityButton.setBounds(117, 194, 60, 60);
		projectTabPanel.add(editActivityButton);

		deleteActivityButton = new JButton(new ImageIcon("Images\\deleteActivity.png"));
		deleteActivityButton.setToolTipText("Delete an activity");
		deleteActivityButton.setBounds(214, 194, 60, 60);
		projectTabPanel.add(deleteActivityButton);
		
		choosePrereqsButton = new JButton(new ImageIcon("Images\\prerequisites.png"));
		choosePrereqsButton.setToolTipText("Add or remove prerequisite activities");
		choosePrereqsButton.setBounds(20, 282, 60, 60);
		projectTabPanel.add(choosePrereqsButton);

		JLabel lblAdditionalFeatures = new JLabel("Additional Features");
		lblAdditionalFeatures.setFont(new Font("High Tower Text", Font.ITALIC, 18));
		lblAdditionalFeatures.setBounds(20, 367, 154, 30);
		projectTabPanel.add(lblAdditionalFeatures);

		addGantChartButton = new JButton(new ImageIcon("Images\\add_chart.png"));
		addGantChartButton.setToolTipText("Display Gant chart");
		addGantChartButton.setBounds(20, 408, 60, 60);
		projectTabPanel.add(addGantChartButton);

		evaluateCPAButton = new JButton(new ImageIcon("Images\\cpmicon.png"));
		evaluateCPAButton.setToolTipText("Evaluate a critical path");
		evaluateCPAButton.setBounds(117, 408, 60, 60);
		projectTabPanel.add(evaluateCPAButton);

		pertButton = new JButton(new ImageIcon("Images\\PERT.png"));
		pertButton.setToolTipText("Perform PERT analysis");
		pertButton.setBounds(214, 408, 60, 60);
		projectTabPanel.add(pertButton);

		earnedValueButton = new JButton(new ImageIcon("Images\\earned-value.png"));
		earnedValueButton.setToolTipText("Perform earned value analysis");
		earnedValueButton.setBounds(20, 496, 60, 60);
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
	
	public void createPrereqsTable() {
		
		final JFrame selectionFrame = new JFrame();
		selectionFrame.setTitle("Choose prerequistes");
		selectionFrame.setBounds(100, 100, 465, 356);
		selectionFrame.getContentPane().setLayout(null);
		selectionFrame.setVisible(true);
		
		JScrollPane scrollPaneActs = new JScrollPane();
		scrollPaneActs.setBounds(20, 37, 154, 224);
		selectionFrame.getContentPane().add(scrollPaneActs);
		
		availableActivities = new JTable();
		scrollPaneActs.setViewportView(availableActivities);
		
		JScrollPane scrollPanePrereqs = new JScrollPane();
		scrollPanePrereqs.setBounds(268, 37, 154, 224);
		selectionFrame.getContentPane().add(scrollPanePrereqs);
		
		chosenPrereqs = new JTable();
		scrollPanePrereqs.setViewportView(chosenPrereqs);
		
		JButton addPrerqsButton = new JButton(">>");
		addPrerqsButton.setFont(new Font("High Tower Text", Font.PLAIN, 15));
		addPrerqsButton.setBounds(196, 87, 51, 35);
		selectionFrame.getContentPane().add(addPrerqsButton);
		
		JButton RemovePrereqsButton = new JButton("<<");
		RemovePrereqsButton.setFont(new Font("High Tower Text", Font.PLAIN, 15));
		RemovePrereqsButton.setBounds(196, 147, 51, 35);
		selectionFrame.getContentPane().add(RemovePrereqsButton);
		
		JLabel lblNewLabel = new JLabel("Available Activities");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("High Tower Text", Font.PLAIN, 16));
		lblNewLabel.setBounds(22, 11, 152, 21);
		selectionFrame.getContentPane().add(lblNewLabel);
		
		JLabel lblSelectedPrerequistes = new JLabel("Selected Prerequistes");
		lblSelectedPrerequistes.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectedPrerequistes.setFont(new Font("High Tower Text", Font.PLAIN, 16));
		lblSelectedPrerequistes.setBounds(272, 11, 152, 21);
		selectionFrame.getContentPane().add(lblSelectedPrerequistes);
		
		JButton savePrereqsButton = new JButton("Save");
		savePrereqsButton.setFont(new Font("High Tower Text", Font.PLAIN, 15));
		savePrereqsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		savePrereqsButton.setBounds(121, 292, 89, 23);
		selectionFrame.getContentPane().add(savePrereqsButton);
		
		JButton cancelPrereqsButton = new JButton("Cancel");
		cancelPrereqsButton.setFont(new Font("High Tower Text", Font.PLAIN, 15));
		cancelPrereqsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectionFrame.dispose();
			}
		});
		cancelPrereqsButton.setBounds(227, 292, 89, 23);
		selectionFrame.getContentPane().add(cancelPrereqsButton);		
	}
	
	public void addNewActivityListener(ActionListener listener) {
		addActivityButton.addActionListener(listener);
	}
	
	public void addEditProjectListener(ActionListener listener) {
		editProjectButton.addActionListener(listener);
	}
	
	public void addDeleteProjectListener(ActionListener listener) {
		deleteProjectButton.addActionListener(listener);
	}

	public void addEditActivityListener(ActionListener listener) {
		editActivityButton.addActionListener(listener);
	}

	public void addDeleteActivityListener(ActionListener listener) {
		deleteActivityButton.addActionListener(listener);
	}
	
	public void addChoosePrereqsListener(ActionListener listener) {
		choosePrereqsButton.addActionListener(listener);
	}
}



