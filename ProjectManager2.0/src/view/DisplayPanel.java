package view;

import javax.swing.*;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import java.awt.Font;

@SuppressWarnings({ "serial", "unused" })
public class DisplayPanel extends JPanel {

	private JLabel projectName, status, startDate, endDate;
	private JTextArea textArea;
	private JLabel lblCurrentBudget;
	private JLabel budget;
	private JTable activityTable;




	/**
	 * Create the main Display panel.
	 */
	public DisplayPanel() {

		setBackground(new Color(192, 192, 192));
		setBounds(340, 25, 800, 548);
		setLayout(null);
		setVisible(false);

		JPanel projectDisplayPanel = new JPanel();
		projectDisplayPanel.setBackground(new Color(128, 128, 128));
		projectDisplayPanel.setBounds(0, 0, 800, 189);
		add(projectDisplayPanel);
		projectDisplayPanel.setLayout(null);

		projectName = new JLabel("Project Name");
		projectName.setForeground(new Color(255, 255, 255));
		projectName.setFont(new Font("High Tower Text", Font.BOLD, 34));
		projectName.setBounds(23, 11, 373, 59);
		projectDisplayPanel.add(projectName);

		JLabel lblNewLabel = new JLabel("Starts on");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("High Tower Text", Font.PLAIN, 17));
		lblNewLabel.setBounds(23, 104, 69, 22);
		projectDisplayPanel.add(lblNewLabel);

		JLabel lblEndsOn = new JLabel("Ends on");
		lblEndsOn.setForeground(new Color(255, 255, 255));
		lblEndsOn.setFont(new Font("High Tower Text", Font.PLAIN, 17));
		lblEndsOn.setBounds(23, 122, 69, 22);
		projectDisplayPanel.add(lblEndsOn);

		status = new JLabel("Status");
		status.setForeground(new Color(255, 255, 255));
		status.setFont(new Font("High Tower Text", Font.PLAIN, 19));
		status.setBounds(23, 71, 137, 22);
		projectDisplayPanel.add(status);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(406, 11, 363, 159);
		projectDisplayPanel.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setFont(new Font("High Tower Text", Font.PLAIN, 20));
		textArea.setBackground(new Color(211, 211, 211));
		textArea.setLineWrap(true);
		textArea.setEditable(false);

		startDate = new JLabel("startDate");
		startDate.setForeground(Color.WHITE);
		startDate.setFont(new Font("High Tower Text", Font.PLAIN, 17));
		startDate.setBounds(91, 104, 150, 22);
		projectDisplayPanel.add(startDate);

		endDate = new JLabel("endDate");
		endDate.setForeground(Color.WHITE);
		endDate.setFont(new Font("High Tower Text", Font.PLAIN, 17));
		endDate.setBounds(91, 122, 150, 22);
		projectDisplayPanel.add(endDate);

		lblCurrentBudget = new JLabel("Current budget : $");
		lblCurrentBudget.setForeground(Color.WHITE);
		lblCurrentBudget.setFont(new Font("High Tower Text", Font.PLAIN, 17));
		lblCurrentBudget.setBounds(23, 155, 126, 22);
		projectDisplayPanel.add(lblCurrentBudget);

		budget = new JLabel("budget");
		budget.setForeground(Color.WHITE);
		budget.setFont(new Font("High Tower Text", Font.PLAIN, 20));
		budget.setBounds(152, 155, 69, 22);
		projectDisplayPanel.add(budget);

		JPanel activityDisplayPanel = new JPanel();
		activityDisplayPanel.setBackground(new Color(192, 192, 192));
		activityDisplayPanel.setBounds(0, 189, 800, 359);
		add(activityDisplayPanel);
		activityDisplayPanel.setLayout(null);

		activityTable = new JTable();
		activityTable.setBounds(53, 65, 200, 266);
		activityDisplayPanel.add(activityTable);
		activityTable.setRowHeight(26);
		activityTable.setFont(new Font("High Tower Text", Font.PLAIN, 22));
		activityTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		activityDisplayPanel.add(activityTable);

		JLabel lblActivityList = new JLabel("Activity List");
		lblActivityList.setForeground(Color.WHITE);
		lblActivityList.setFont(new Font("High Tower Text", Font.BOLD, 34));
		lblActivityList.setBounds(10, 11, 311, 45);
		activityDisplayPanel.add(lblActivityList);
		setVisible(true);
	}



	public JTable getActivityTable() {
		return activityTable;
	}


	public String getSelectedActivity() {
		int row = activityTable.getSelectedRow();
		int column = activityTable.getSelectedColumn();
		if (row < 0 || column < 0)
			{
			JOptionPane.showMessageDialog(null,"No Item was selected. Please choose an activity to perform this action.");
			return null;
			}
		return activityTable.getValueAt(row,column).toString();
	}


	/**
	 * Updates values of attributes according to the project currently viewed.
	 * @param name
	 * @param progress
	 * @param startDate
	 * @param endDate
	 * @param description
	 * @param budget
	 */
	protected void updateProjectInfo(String name, String progress, String startDate, String endDate, String description, String budget) {		

		projectName.setText(name);
		status.setText(progress);
		this.startDate.setText(startDate);
		this.endDate.setText(endDate);
		this.textArea.setText(description);
		this.budget.setText(budget);
	}
}
