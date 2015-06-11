package view;

import javax.swing.*;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.Activity;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


@SuppressWarnings("serial")
public class ActivityFrame extends JFrame {

	private JPanel activityPanel;
	private JTextField activityNameTextField, activityBudgetTextField, activityDurationTextField,
	earliestStartTextField, earliestFinishTextField, latestStartTextField, latestFinishTextField;
	private JTextArea activityDescription;
	private JComboBox<Object> precedingActivityChoice;
	private JTable prereqsTable;
	private Choice statusChoice;
	private Activity currentActivity;
	private JButton saveActivityButton, cancelActivityButton;

	/**
	 * Creates the New Activity Form.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public ActivityFrame()  {
		super("Activity Form");
		getContentPane().setBackground(new Color(25, 25, 112));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 658, 420);
		setVisible(false);
		setResizable(false);
		getContentPane().setLayout(null);

		activityPanel = new JPanel();
		activityPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		activityPanel.setBounds(96, 79, 215, 55);
		getContentPane().add(activityPanel);
		activityPanel.setLayout(null);

		activityNameTextField = new JTextField();
		activityNameTextField.setText("Activity Name");
		/*	
		activityNameTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(activityNameTextField.getText().equals("Activity Name"))
					activityNameTextField.setText(" ");
				else
					activityNameTextField.setText(" ");

			}
			@Override
			public void focusLost(FocusEvent arg0) {
				if(activityNameTextField.getText().trim().equals(""))
					activityNameTextField.setText("Activity Name");
				else
					activityNameTextField.setText(" ");
			}
		})*/;


		activityNameTextField.setBounds(35, 11, 144, 33);
		activityPanel.add(activityNameTextField);
		activityNameTextField.setColumns(10);

		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_4.setBounds(96, 260, 215, 55);
		getContentPane().add(panel_4);

		activityBudgetTextField = new JTextField();
		activityBudgetTextField.setText("Budget");
		/*activityBudgetTextField.addFocusListener(new FocusAdapter() {
		@Override
		public void focusGained(FocusEvent arg0) {
			if(activityBudgetTextField.getText().trim().equals("Budget"))
				activityBudgetTextField.setText("");
			else
				activityBudgetTextField.setText("");

		}
		@Override
		public void focusLost(FocusEvent arg0) {
			if(activityBudgetTextField.getText().trim().equals(""))
				activityBudgetTextField.setText("Budget");
			else
				activityBudgetTextField.setText("Budget");
		}
	});*/
		activityBudgetTextField.setColumns(10);
		activityBudgetTextField.setBounds(35, 11, 144, 33);
		panel_4.add(activityBudgetTextField);

		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_5.setBounds(313, 260, 225, 55);
		getContentPane().add(panel_5);

		statusChoice = new Choice();
		statusChoice.setBounds(40, 17, 144, 35);
		statusChoice.add("Locked");
		statusChoice.add("Unlocked");
		panel_5.add(statusChoice);

		JPanel panel_6 = new JPanel();
		panel_6.setLayout(null);
		panel_6.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_6.setBounds(313, 79, 225, 55);
		getContentPane().add(panel_6);

		activityDurationTextField = new JTextField();
		/*activityDurationTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(activityDurationTextField.getText().trim().equals("Duration"))
					activityDurationTextField.setText("");
				else
					activityDurationTextField.setText("");

			}
			@Override
			public void focusLost(FocusEvent arg0) {
				if(activityDurationTextField.getText().trim().equals(""))
					activityDurationTextField.setText("Duration");
				else
					activityDurationTextField.setText("Duration");
			}
		});*/
		activityDurationTextField.setColumns(10);
		activityDurationTextField.setBounds(40, 11, 144, 33);
		panel_6.add(activityDurationTextField);

		activityDescription = new JTextArea();
		/*activityDescription.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(activityDescription.getText().trim().equals("Activity Description"))
					activityDescription.setText("");
				else
					activityDescription.setText("");

			}
			@Override
			public void focusLost(FocusEvent arg0) {
				if(activityDescription.getText().trim().equals(""))
					activityDescription.setText("Activity Description");
				else
					activityDescription.setText("Activity Description");
			}
		});*/
		activityDescription.setBounds(177, 135, 279, 124);
		getContentPane().add(activityDescription);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(96, 135, 81, 66);
		getContentPane().add(panel_1);

		earliestStartTextField = new JTextField();
		/*earliestStartTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(earliestStartTextField.getText().trim().equals("ES"))
					earliestStartTextField.setText("");
				else
					earliestStartTextField.setText("");

			}
			@Override
			public void focusLost(FocusEvent arg0) {
				if(earliestStartTextField.getText().trim().equals(""))
					earliestStartTextField.setText("ES");
				else
					earliestStartTextField.setText("ES");
			}
		});*/
		earliestStartTextField.setColumns(10);
		earliestStartTextField.setBounds(8, 16, 65, 33);
		panel_1.add(earliestStartTextField);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_2.setBounds(96, 201, 81, 58);
		getContentPane().add(panel_2);

		earliestFinishTextField = new JTextField();
		/*earliestFinishTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(earliestFinishTextField.getText().trim().equals("EF"))
					earliestFinishTextField.setText("");
				else
					earliestFinishTextField.setText("");

			}
			@Override
			public void focusLost(FocusEvent arg0) {
				if(earliestFinishTextField.getText().trim().equals(""))
					earliestFinishTextField.setText("EF");
				else
					earliestFinishTextField.setText("EF");
			}
		});*/
		earliestFinishTextField.setColumns(10);
		earliestFinishTextField.setBounds(8, 12, 65, 33);
		panel_2.add(earliestFinishTextField);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_3.setBounds(457, 135, 81, 66);
		getContentPane().add(panel_3);

		latestStartTextField = new JTextField();
		/*latestStartTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(latestStartTextField.getText().trim().equals("LS"))
					latestStartTextField.setText("");
				else
					latestStartTextField.setText("");

			}
			@Override
			public void focusLost(FocusEvent arg0) {
				if(latestStartTextField.getText().trim().equals(""))
					latestStartTextField.setText("LS");
				else
					latestStartTextField.setText("LS");
			}
		});*/
		latestStartTextField.setColumns(10);
		latestStartTextField.setBounds(8, 16, 65, 33);
		panel_3.add(latestStartTextField);

		JPanel panel_7 = new JPanel();
		panel_7.setLayout(null);
		panel_7.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_7.setBounds(457, 201, 81, 58);
		getContentPane().add(panel_7);

		latestFinishTextField = new JTextField();
		/*latestFinishTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(latestFinishTextField.getText().trim().equals("LF"))
					latestFinishTextField.setText("");
				else
					latestFinishTextField.setText("");

			}
			@Override
			public void focusLost(FocusEvent arg0) {
				if(latestFinishTextField.getText().trim().equals(""))
					latestFinishTextField.setText("LF");
				else
					latestFinishTextField.setText("LF");
			}
		});*/
		latestFinishTextField.setColumns(10);
		latestFinishTextField.setBounds(8, 12, 65, 33);
		panel_7.add(latestFinishTextField);

		JLabel lblActivityForm = new JLabel("Activity Form");
		lblActivityForm.setForeground(new Color(255, 255, 255));
		lblActivityForm.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 22));
		lblActivityForm.setBounds(257, 10, 137, 50);
		getContentPane().add(lblActivityForm);

		saveActivityButton = new JButton("Save");
		saveActivityButton.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 22));
		saveActivityButton.setBounds(350, 342, 120, 38);
		getContentPane().add(saveActivityButton);

		cancelActivityButton = new JButton("Cancel");
		cancelActivityButton.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 22));
		cancelActivityButton.setBounds(504, 342, 120, 38);
		cancelActivityButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();					
			}
		});
		getContentPane().add(cancelActivityButton);

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

	public void setCurrentActivity(Activity activity)
	{
		this.currentActivity = activity;
	}

	public void setEditActivityFrame(Activity activity)
	{
		activityNameTextField.setText(activity.getName());
		statusChoice.select(activity.getStatus().toString());
		Double budget = activity.getBudget();
		activityBudgetTextField.setText(budget.toString());
		activityDescription.setText(activity.getDescription());
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
		this.activityNameTextField = activityName;
	}

	public String getActivityNameField(){
		return activityNameTextField.getText();
	}

	public void setActivityDuration(JTextField duration) {
		this.activityDurationTextField = duration;
	}

	public String getActivityDuration(){
		return activityDurationTextField.getText();
	}

	public void setActivityChoice(Choice choice) {
		this.statusChoice = choice;
	}
	public String getActivityChoice() {
		return 	statusChoice.getSelectedItem();
	}

	/*public void setStartDate(JTextField textStartDate) {
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
	}*/

	public void setActivityDescription(JTextArea activityDescription){
		this.activityDescription = activityDescription;
	}

	public String getActivityDescription()
	{
		return activityDescription.getText();
	}

	public Double getBudget() {
		double budget = -1.0;

		try {
			budget = Double.parseDouble(activityBudgetTextField.getText());
			return (new Double(activityBudgetTextField.getText()));
		}
		catch(NumberFormatException e) {
			return budget; // if the code gets to here, it wasn't recognized as a double.
		}
	}

	// clears JobForm
	public void clearForm() {
		activityNameTextField.setText("");
		activityDescription.setText("");
		activityBudgetTextField.setText("");
		precedingActivityChoice.setSelectedItem(null);
	} 
}

