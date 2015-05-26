package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import java.awt.SystemColor;

import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSplitPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Choice;

import javax.swing.JMenuItem;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.awt.Button;

@SuppressWarnings("serial")
public class ProjectManagerUI extends JFrame {

	private JPanel contentPane, homeTabPanel, projectTabPanel, projectTemplate, activityTemplate;
	private JTextField textField;
	private JTextField budgetTextField;
	private JTextField txtStartdate;
	private JTextField txtEnddate;
	
	/**
	 * Create the frame.
	 */
	public ProjectManagerUI() {
		super("Project Manager");
		setBackground(new Color(25, 25, 112));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(25, 25, 112));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1)
					e.consume();
			}
		});
		tabbedPane.setBackground(new Color(192, 192, 192));
		tabbedPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		tabbedPane.setBounds(10, 38, 1164, 597);
		contentPane.add(tabbedPane);
		
		
		homeTabPanel = new JPanel();
		homeTabPanel.setBackground(new Color(192, 192, 192));
		tabbedPane.addTab("Home", null, homeTabPanel, null);
		tabbedPane.setBackgroundAt(0, new Color(192, 192, 192));
		tabbedPane.setEnabledAt(0, true);
		homeTabPanel.setLayout(null);
		
		JButton existingProjectsButton = new JButton("View");
		existingProjectsButton.setToolTipText("Select to view the list of existing projects");
		existingProjectsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		existingProjectsButton.setBounds(179, 196, 89, 84);
		homeTabPanel.add(existingProjectsButton);
		
		projectTemplate = createTemplate();
		homeTabPanel.add(projectTemplate);
		JButton newButton = new JButton(new ImageIcon("Images\\addProject.png"));
		newButton.setToolTipText("Select to create a new project");
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			//	homeTabPanel.add(projectTemplate);
				projectTemplate.setVisible(true);
				placeAdditionalComponentsForNewProject();
			
				
			}
		});
		newButton.setBounds(47, 63, 89, 84);
		homeTabPanel.add(newButton);
		
		//TEMPLATE
		/*Button saveButton = new Button("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(saveButton, " Saved Data.");
			}
		});
		saveButton.setBounds(514, 454, 86, 27);
		projectTemplate.add(saveButton);
		*/
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(325, 0, 2, 611);
		homeTabPanel.add(separator);
		
		JButton editButton = new JButton("Edit");
		editButton.setToolTipText("Edit the selected project");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		editButton.setBounds(47, 196, 89, 84);
		homeTabPanel.add(editButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setToolTipText("Delete the selected project");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		deleteButton.setBounds(179, 63, 89, 84);
		homeTabPanel.add(deleteButton);
		
		
		projectTabPanel = new JPanel();
		projectTabPanel.setBackground(SystemColor.inactiveCaption);
		tabbedPane.addTab("Project", null, projectTabPanel, null);
		projectTabPanel.setLayout(null);
		
		JButton button = new JButton((Icon) null);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button.setBounds(29, 44, 62, 60);
		projectTabPanel.add(button);
		
		JButton button_1 = new JButton("Delete");
		button_1.setBounds(119, 44, 62, 60);
		projectTabPanel.add(button_1);
		
		JButton button_2 = new JButton("Edit");
		button_2.setBounds(203, 44, 62, 60);
		projectTabPanel.add(button_2);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(306, 0, 2, 611);
		projectTabPanel.add(separator_1);
		tabbedPane.setBackgroundAt(1, SystemColor.inactiveCaption);
		
		JLabel currentUserLabel = new JLabel("Signed in as " + Login.getCurrentUser());
		currentUserLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		currentUserLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		currentUserLabel.setForeground(new Color(255, 255, 255));
		currentUserLabel.setFont(new Font("High Tower Text", Font.PLAIN, 17));
		currentUserLabel.setBounds(945, 22, 216, 17);
		contentPane.add(currentUserLabel);
	
	}
	
	
	private JPanel createTemplate() {
		

		JPanel templatePanel = new JPanel();
		templatePanel.setBounds(360, 29, 763, 513);
		templatePanel.setLayout(null);
		templatePanel.setVisible(false);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(82, 102, 94, 27);
		templatePanel.add(lblName);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(82, 160, 94, 27);
		templatePanel.add(lblDescription);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(82, 251, 94, 27);
		templatePanel.add(lblStatus);
		
		textField = new JTextField();
		textField.setBounds(195, 102, 288, 27);
		templatePanel.add(textField);
		textField.setColumns(10);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(198, 161, 285, 66);
		templatePanel.add(textArea);
		
		Choice choice = new Choice();
		choice.setBounds(195, 258, 140, 35);
		choice.add("Unlocked");
		choice.add("In-progress");
		choice.add("Restricted");
		choice.add("Completed");
		templatePanel.add(choice);
		
		//TAKE IT OFF AFTER ACTIVITY IS DONE
	/*	Button cancelButton = new Button("Cancel");
		cancelButton.setBounds(629, 454, 86, 27);
		templatePanel.add(cancelButton);
		*/
		return templatePanel;
	}
	
	private void placeAdditionalComponentsForNewProject() {
		
		JLabel lblNewProject = new JLabel("New Project");
		lblNewProject.setFont(new Font("High Tower Text", Font.PLAIN, 18));
		lblNewProject.setBounds(82, 34, 134, 35);
		projectTemplate.add(lblNewProject);
		
		JLabel lblBudget = new JLabel("Budget");
		lblBudget.setBounds(82, 309, 94, 27);
		projectTemplate.add(lblBudget);
		
		budgetTextField = new JTextField();
		budgetTextField.setBounds(195, 305, 122, 27);
		projectTemplate.add(budgetTextField);
		budgetTextField.setColumns(10);
		
		JLabel lblStartdate = new JLabel("startDate");
		lblStartdate.setBounds(82, 361, 94, 27);
		projectTemplate.add(lblStartdate);
		
		JLabel lblStartdate_1 = new JLabel("endDate");
		lblStartdate_1.setBounds(445, 361, 94, 27);
		projectTemplate.add(lblStartdate_1);
		
		txtStartdate = new JTextField();
		txtStartdate.setText(" ");
		txtStartdate.setBounds(195, 357, 180, 35);
		projectTemplate.add(txtStartdate);
		txtStartdate.setColumns(10);
		
		txtEnddate = new JTextField();
		txtEnddate.setText(" ");
		txtEnddate.setColumns(10);
		txtEnddate.setBounds(535, 357, 180, 35);
		projectTemplate.add(txtEnddate);
		
		Button saveProjButton = new Button("Save");
		saveProjButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(saveProjButton, "Saved Data.");
			}
		});
		saveProjButton.setBounds(514, 454, 86, 27);
		projectTemplate.add(saveProjButton);
		
		Button cancelProjButton = new Button("Cancel");
		cancelProjButton.setBounds(629, 454, 86, 27);
		projectTemplate.add(cancelProjButton);
	}
	
}
