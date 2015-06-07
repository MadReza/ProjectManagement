package view;

import javax.swing.*;

import java.awt.Choice;
import java.awt.Component;
import java.awt.Font;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import model.Activity;
import model.Project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.ArrayList;
import java.awt.Color;


@SuppressWarnings("serial")
public class ActivityFrame extends JFrame {

	private JPanel activityPanel, editFrame;
	private JTextField nameTextField, startDateTextField, endDateTextField, budgetTextField;
	private JTextArea descriptionArea;
	private JButton saveActivityButton, cancelActivityButton;
	private Choice statusChoice;
	private JComboBox<Object> precedingActivityChoice;
	private JTable prereqsTable;
	
	private Activity currentActivity;
	
	

	
	/**
	 * Creates the New Activity Form.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public ActivityFrame()  {
		super("Activity Form");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 905, 700);
		getContentPane().setLayout(null);
		setVisible(false);
		setResizable(false);

		activityPanel = new JPanel();
		activityPanel.setBackground(new Color(25, 25, 112));
		activityPanel.setBounds(0, 0, 900, 671);
		activityPanel.setLayout(null); //Absolute Layout
		getContentPane().add(activityPanel);

		JLabel lblActivityName = new JLabel("Activity Name");
		lblActivityName.setForeground(new Color(255, 255, 255));
		lblActivityName.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
		lblActivityName.setBounds(208, 91, 138, 38);
		activityPanel.add(lblActivityName);

		JLabel lblDescription = new JLabel("Activity Description");
		lblDescription.setForeground(new Color(255, 255, 255));
		lblDescription.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
		lblDescription.setBounds(208, 188, 180, 38);
		activityPanel.add(lblDescription);

		JLabel lblStartDateField = new JLabel("Start Date");
		lblStartDateField.setForeground(new Color(255, 255, 255));
		lblStartDateField.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
		lblStartDateField.setBounds(208, 462, 104, 38);
		activityPanel.add(lblStartDateField);

		JLabel lblFinishDateField = new JLabel("End Date");
		lblFinishDateField.setForeground(new Color(255, 255, 255));
		lblFinishDateField.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
		lblFinishDateField.setBounds(208, 529, 104, 38);
		activityPanel.add(lblFinishDateField);

		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		nameTextField.setBounds(465, 95, 263, 30);
		//nameTextField.setText("abc");  //REMOVE LATER
		activityPanel.add(nameTextField);

		JLabel lblNewLabel = new JLabel("Activity Form");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("High Tower Text", Font.ITALIC, 26));
		lblNewLabel.setBounds(334, 20, 231, 49);
		activityPanel.add(lblNewLabel);

		saveActivityButton = new JButton("Save");
		saveActivityButton.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 22));
		saveActivityButton.setBounds(581, 596, 120, 38);
		activityPanel.add(saveActivityButton);

		cancelActivityButton = new JButton("Cancel");
		cancelActivityButton.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 21));
		cancelActivityButton.setBounds(748, 596, 120, 38);
		cancelActivityButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();					
			}
		});
		activityPanel.add(cancelActivityButton);

		activityPanel.add(cancelActivityButton);

		startDateTextField = new JTextField();
		startDateTextField.setColumns(10);
		startDateTextField.setBounds(465, 466, 143, 30);
		activityPanel.add(startDateTextField);

		endDateTextField = new JTextField();
		endDateTextField.setColumns(10);
		endDateTextField.setBounds(465, 533, 138, 30);
		activityPanel.add(endDateTextField);

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
		lblStatus.setBounds(208, 337, 104, 38);
		activityPanel.add(lblStatus);

		statusChoice = new Choice();
		statusChoice.setBounds(465, 346, 191, 30);
		statusChoice.add("Locked");
		statusChoice.add("Unlocked");
		activityPanel.add(statusChoice);


		JLabel lblPreceedingActivities = new JLabel("Preceeding Activities");
		lblPreceedingActivities.setForeground(Color.WHITE);
		lblPreceedingActivities.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
		lblPreceedingActivities.setBounds(208, 275, 180, 38);
		activityPanel.add(lblPreceedingActivities);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(465, 164, 263, 87);
		activityPanel.add(scrollPane);

		descriptionArea = new JTextArea();
		descriptionArea.setLocation(168, 0);
		scrollPane.setViewportView(descriptionArea);

		JLabel lblBudget = new JLabel("Budget");
		lblBudget.setForeground(Color.WHITE);
		lblBudget.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
		lblBudget.setBounds(208, 398, 104, 38);
		activityPanel.add(lblBudget);

		budgetTextField = new JTextField();
		budgetTextField.setText("0.0");
		budgetTextField.setBounds(465, 402, 143, 30);
		budgetTextField.setColumns(10);
		activityPanel.add(budgetTextField);


	}
	
	
	public void updatePossiblePrereqItems(ArrayList<Activity> allActivities) {
		if(allActivities != null) {
		precedingActivityChoice = new JComboBox<Object>(new DefaultComboBoxModel<Object>(allActivities.toArray()));
		precedingActivityChoice.setBounds(465, 284, 191, 20);
		precedingActivityChoice.setRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(
					JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if(value instanceof Activity) {
					Activity activity = (Activity) value;
					setText(activity.getName());
				}
				else
					setText("None");
				return this;
			}
		});
		precedingActivityChoice.setSelectedItem(null);
		activityPanel.add(precedingActivityChoice);
	}
	}
		
		  
	

	public void setEditActivityFrame(Activity activity)
	{
		nameTextField.setText(activity.getName());
		statusChoice.select(activity.getStatus().toString());
		startDateTextField.setText(activity.getStartDate());
		endDateTextField.setText(activity.getEndDate());
		Double budget = activity.getBudget();
		budgetTextField.setText(budget.toString());
		descriptionArea.setText(activity.getDescription());
		setVisible(true);
	}
	
	public JPanel getEditActivityFrame()
	{
		activityPanel.setVisible(true);
		return activityPanel;
		
	}
	// adding an action listener to the save activity button
	public void addSaveActivityListener(ActionListener saveActivityListener){
		saveActivityButton.addActionListener(saveActivityListener);
	}


	public void setActivityName(JTextField activityName) {
		this.nameTextField = activityName;
	}

	public String getActivityNameField(){
		return nameTextField.getText();
	}

	public void setActivityChoice(Choice choice) {
		this.statusChoice = choice;
	}
	public String getActivityChoice() {
		return 	statusChoice.getSelectedItem();
	}

	public void setStartDate(JTextField textStartDate) {
		this.startDateTextField = textStartDate;
	}

	public String getStartDate() {
		return startDateTextField.getText();
	}

	public void setEndDate(JTextField textEndDate) {
		this.endDateTextField = textEndDate;
	}
	public String getEndDate() {
		return endDateTextField.getText();
	}

	public void setActivityDescription(JTextArea activityDescription){
		this.descriptionArea = activityDescription;
	}
	
	public String getActivityDescription()
	{
		return descriptionArea.getText();
	}

	public double getBudget() {
		return Double.parseDouble(budgetTextField.getText());
	}

	// clears JobForm
	public void clearForm() {
		nameTextField.setText("");
		startDateTextField.setText("");
		endDateTextField.setText("");
		descriptionArea.setText("");
		budgetTextField.setText("");
		precedingActivityChoice.setSelectedItem(null);
	} 
	
	/*	
	//BROKEN - GOTTA FIX BUT TOO SLEEPY RIGHT NOW.
	public void addPrereqComboBoxListener(ItemListener listener) {
		precedingActivityChoice.addItemListener(listener);
	}*/

	
	 
}

