package view;

import javax.swing.*;

import java.awt.Choice;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import model.Activity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.Color;


@SuppressWarnings("serial")
public class ActivityFrame extends JFrame {

	private JTextField nameTextField,startDateField, finishDateField,StartDateTextField,EndDateTextField;
	private JTextArea descriptionArea;
	private JButton saveActivityButton, cancelActivityButton;
	private Choice statusChoice,preecedingActivityChoice;
	private JPanel activityPanel,editFrame;
	private Activity activity;

	/**
	 * Create the Registration/Sign up form.
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
		lblStartDateField.setBounds(208, 430, 104, 38);
		activityPanel.add(lblStartDateField);

		JLabel lblFinishDateField = new JLabel("End Date");
		lblFinishDateField.setForeground(new Color(255, 255, 255));
		lblFinishDateField.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
		lblFinishDateField.setBounds(208, 511, 104, 38);
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

		StartDateTextField = new JTextField();
		StartDateTextField.setColumns(10);
		StartDateTextField.setBounds(465, 434, 143, 30);
		activityPanel.add(StartDateTextField);

		EndDateTextField = new JTextField();
		EndDateTextField.setColumns(10);
		EndDateTextField.setBounds(465, 515, 138, 30);
		activityPanel.add(EndDateTextField);

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
		lblStatus.setBounds(208, 344, 104, 38);
		activityPanel.add(lblStatus);

		statusChoice = new Choice();
		statusChoice.setBounds(465, 348, 191, 30);
		statusChoice.add("Locked");
		statusChoice.add("Unlocked");
		activityPanel.add(statusChoice);

		JLabel lblPreceedingActivities = new JLabel("Preceeding Activities");
		lblPreceedingActivities.setForeground(Color.WHITE);
		lblPreceedingActivities.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
		lblPreceedingActivities.setBounds(208, 275, 180, 38);
		activityPanel.add(lblPreceedingActivities);

		preecedingActivityChoice = new Choice();
		preecedingActivityChoice.setBounds(465, 284, 191, 20);
		preecedingActivityChoice.add("Not Applicable");
		activityPanel.add(preecedingActivityChoice);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(465, 164, 263, 87);
		activityPanel.add(scrollPane);

		descriptionArea = new JTextArea();
		descriptionArea.setLocation(168, 0);
		scrollPane.setViewportView(descriptionArea);


	}

	public void setEditActivityFrame(Activity activity)
	{
		nameTextField.setText(activity.getName());
		statusChoice.select(activity.getStatus().toString());
		StartDateTextField.setText(activity.getStartDate());
		EndDateTextField.setText(activity.getEndDate());
		descriptionArea.setText(activity.getDescription());
		setVisible(true);
	}
	
	public JPanel getEditActivityFrame()
	{
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
		this.StartDateTextField = textStartDate;
	}

	public String getStartDate() {
		return StartDateTextField.getText();
	}

	public void setEndDate(JTextField textEndDate) {
		this.EndDateTextField = textEndDate;
	}
	public String getEndDate() {
		return EndDateTextField.getText();
	}

	public void setActivityDescription(JTextArea activityDescription){
		this.descriptionArea = activityDescription;
	}
	public String getActivityDescription()
	{
		return descriptionArea.getText();
	}

	// clears JobForm
	public void clearForm(){
		nameTextField.setText("");
		startDateField.setText("");
		finishDateField.setText("");
		descriptionArea.setText("");		
	} 
}

