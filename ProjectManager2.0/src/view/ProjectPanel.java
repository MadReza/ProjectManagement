package view;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;






import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import model.Activity;
import model.Project;

@SuppressWarnings("serial")
public class ProjectPanel extends JPanel {
	
	private JPanel projectTabPanel, projectForm;
	private ActivityFrame activityFrame;
	private JFrame prereqSelectionFrame;
	
	private JTextField nameTextField, budgetTextField;
	private DisplayPanel displayPanel;
	private JTextArea descriptionArea;
	private JComboBox<String> statusCombo;
	private JButton addActivityButton, deleteProjectButton, editProjectButton, choosePrereqsButton,savePrereqsButton, addMemberButton, 
			addGantChartButton, evaluateCPAButton, pertButton, earnedValueButton,editActivityButton,deleteActivityButton ;
	
	private JList<Activity> availableActivities, chosenPrereqs;
	private DefaultListModel<Activity> availableModel, chosenModel;
	
		
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
		
		choosePrereqsButton = new JButton("Choose Prereqs");
		choosePrereqsButton.setBounds(20, 288, 89, 23);
		projectTabPanel.add(choosePrereqsButton);

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
	
	public JList<Activity> getAvailableActivitiesList() {
		return availableActivities;
	}
	
	public JList<Activity> getChosenActivitiesList() {
		return chosenPrereqs;
	}
	
	public ArrayList<Activity> getChosenPrereqs() {
		ArrayList<Activity> prereqs = new ArrayList<Activity>();
		if(!chosenModel.isEmpty()) {
			for(int i = 0; i < chosenModel.size(); i++) {
				prereqs.add(chosenModel.getElementAt(i));
				System.out.println("chosen : " + chosenModel.getElementAt(i).getName());
			}
		}
		else
			JOptionPane.showMessageDialog(null,"Please select at least one prerequisite.");
		return prereqs;
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
		
		prereqSelectionFrame = new JFrame();
		prereqSelectionFrame.setTitle("Choose prerequistes");
		prereqSelectionFrame.setBounds(100, 100, 465, 356);
		prereqSelectionFrame.getContentPane().setLayout(null);
		prereqSelectionFrame.setVisible(true);
		prereqSelectionFrame.setResizable(false);
		
		JScrollPane scrollPaneActs = new JScrollPane();
		scrollPaneActs.setBounds(20, 37, 154, 224);
		prereqSelectionFrame.getContentPane().add(scrollPaneActs);
		
		availableActivities = new JList<Activity>();
		availableActivities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneActs.setViewportView(availableActivities);
		
		JScrollPane scrollPanePrereqs = new JScrollPane();
		scrollPanePrereqs.setBounds(268, 37, 154, 224);
		prereqSelectionFrame.getContentPane().add(scrollPanePrereqs);
		
		chosenPrereqs = new JList<Activity>();
		chosenPrereqs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPanePrereqs.setViewportView(chosenPrereqs);
		
		JButton addPrerqsButton = new JButton(">>");
		addPrerqsButton.setFont(new Font("High Tower Text", Font.PLAIN, 15));
		addPrerqsButton.setBounds(196, 87, 51, 35);
		addPrerqsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				addToList(chosenModel,chosenPrereqs,availableActivities.getSelectedValue()); //add selection to chosen prereqs
				removeFromList(availableModel,availableActivities,availableActivities.getSelectedValue());	//remove selection from availabilities	
			}
		});
		prereqSelectionFrame.getContentPane().add(addPrerqsButton);
		
		JButton RemovePrereqsButton = new JButton("<<");
		RemovePrereqsButton.setFont(new Font("High Tower Text", Font.PLAIN, 15));
		RemovePrereqsButton.setBounds(196, 147, 51, 35);
		RemovePrereqsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				addToList(availableModel,availableActivities, chosenPrereqs.getSelectedValue()); //add selection to availabilities
				removeFromList(chosenModel,chosenPrereqs,chosenPrereqs.getSelectedValue()); //remove selection from chosen prereqs		
			}
		});
		prereqSelectionFrame.getContentPane().add(RemovePrereqsButton);
		
		JLabel lblNewLabel = new JLabel("Available Activities");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("High Tower Text", Font.PLAIN, 16));
		lblNewLabel.setBounds(22, 11, 152, 21);
		prereqSelectionFrame.getContentPane().add(lblNewLabel);
		
		JLabel lblSelectedPrerequistes = new JLabel("Selected Prerequistes");
		lblSelectedPrerequistes.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectedPrerequistes.setFont(new Font("High Tower Text", Font.PLAIN, 16));
		lblSelectedPrerequistes.setBounds(272, 11, 152, 21);
		prereqSelectionFrame.getContentPane().add(lblSelectedPrerequistes);
		
		savePrereqsButton = new JButton("Save");
		savePrereqsButton.setFont(new Font("High Tower Text", Font.PLAIN, 15));
		savePrereqsButton.setBounds(121, 292, 89, 23);
		prereqSelectionFrame.getContentPane().add(savePrereqsButton);
		
		JButton cancelPrereqsButton = new JButton("Cancel");
		cancelPrereqsButton.setFont(new Font("High Tower Text", Font.PLAIN, 15));
		cancelPrereqsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				prereqSelectionFrame.dispose();
			}
		});
		cancelPrereqsButton.setBounds(227, 292, 89, 23);
		prereqSelectionFrame.getContentPane().add(cancelPrereqsButton);		
	}
	
	private void addToList(DefaultListModel<Activity> model, JList<Activity> list, Activity activity) {
		model.addElement(activity);
		list.setModel(model);	
	}
	
	private void removeFromList(DefaultListModel<Activity> model, JList<Activity> list, Activity activity) {
		model.removeElement(activity);
		list.setModel(model);
	}
	
	public void updateAvailableListEntries(ArrayList<Activity> activities) {
		
		availableModel = new DefaultListModel<Activity>();
		
		if (!activities.isEmpty()) {	
			for(Activity activity : activities) {
				availableModel.addElement(activity);
			}
			availableActivities.setModel(availableModel);
			availableActivities.setCellRenderer(new PreReqListCellRenderer());	
		}
	}

	public void updateChosenPrereqEntries(ArrayList<Activity> activities) {
		
		chosenModel = new DefaultListModel<Activity>();
		
		if (!activities.isEmpty()) {
			for(Activity activity : activities) {
				chosenModel.addElement(activity);
			}
			chosenPrereqs.setModel(chosenModel);
			chosenPrereqs.setCellRenderer(new PreReqListCellRenderer());	
		}
	}
	
	
	//-------------  L I S T E N E R S ------------------------------
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
	
	public void addSavePrereqsListener(ActionListener listener) {
		savePrereqsButton.addActionListener(listener);
	}

	
	public class PreReqListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList<?> list,
				Object value,
				int index,
				boolean isSelected,
				boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof Activity) {
				Activity activity = (Activity)value;
				setText(activity.getName());
			}
			return this;
		}
	}
	 
	
}



