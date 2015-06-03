package view;

import java.awt.*;
import java.awt.event.*;


import javax.swing.*;
import javax.swing.border.*;


import model.Project;

@SuppressWarnings("serial")
public class MainApplicationView extends JPanel {

	private ProjectPanel projectPanel;
	private JTabbedPane tabbedPane;
	private JPanel welcomePanel, homePanel, projectFormPanel, viewPanel;

	private JButton newProjectButton, viewProjectsButton, saveProjectButton, cancelProjectButton, openButton;
	private JTextField textFieldName, budgetTextField, textStartDate, textEndDate;
	private JTextArea textArea;
	private JLabel projectFormLabel, currentUserLabel;
	private Choice choice;
	private JTable projectTable;

	private String currentUser;
	private Project currentProject;

	
	/**
	 * Create the panel.
	 */
	public MainApplicationView() {
		initialize();
		projectPanel = new ProjectPanel(tabbedPane);	
	}
	
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
		currentUserLabel.setText("Signed in as " + currentUser);
	}

	public void setCurrentProject(Project currentProject) {
		this.currentProject = currentProject;
		projectPanel.updateDisplayPanel(currentProject);
	}

	public String getName() {
		return textFieldName.getText();
	}

	public void setName(JTextField textFieldName) {
		this.textFieldName = textFieldName;
	}

	public Double getBudget() {
		if(budgetTextField.getText().isEmpty())
			return 0.0;
		return (new Double(budgetTextField.getText()));
	}

	public void setBudget(JTextField budgetTextField) {
		this.budgetTextField = budgetTextField;
	}

	public String getStartDate() {
		return textStartDate.getText();
	}

	public void setStartDate(JTextField textStartDate) {
		this.textStartDate = textStartDate;
	}

	public String getEndDate() {
		return textEndDate.getText();
	}

	public void setEndDate(JTextField textEndDate) {
		this.textEndDate = textEndDate;
	}

	public String getDescription() {
		return textArea.getText();
	}

	public void setDescription(JTextArea textArea) {
		this.textArea = textArea;
	}

	public String getChoice() {
		return choice.getSelectedItem();
	}

	public void setChoice(Choice choice) {
		this.choice = choice;
	}
	
	public String getSelectedProject() {
		int row = projectTable.getSelectedRow();
		int column = projectTable.getSelectedColumn();
		return projectTable.getValueAt(row,column).toString();
	}

	public  JPanel getProjectFormPanel() {
		return projectFormPanel;
	}

	public JPanel getViewPanel() {
		return viewPanel;
	}

	public ProjectPanel getProjectPanel() {
		return projectPanel;
	}

	public JPanel getWelcomePanel() {
		return welcomePanel;
	}

	public JTable getProjectTable() {
		return projectTable;
	}
	

	/**
	 * Creates the main view of the application.
	 */
	private void initialize() {

		setBackground(new Color(25, 25, 112));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) //Switch tabs on a single click.
					e.consume();
			}
		});
		tabbedPane.setBackground(new Color(192, 192, 192));
		tabbedPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		tabbedPane.setBounds(10, 38, 1172, 620);
		add(tabbedPane);

		homePanel = new JPanel();
		homePanel.setBackground(new Color(192, 192, 192));
		tabbedPane.addTab("Home", null, homePanel, null);
		tabbedPane.setBackgroundAt(0, new Color(192, 192, 192));
		tabbedPane.setEnabledAt(0, true);
		homePanel.setLayout(null);

		createProjectForm();
		homePanel.add(projectFormPanel);
		newProjectButton = new JButton(new ImageIcon("Images\\addProject.png"));
		newProjectButton.setToolTipText("Select to create a new project");
		newProjectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				welcomePanel.setVisible(false);
				clearForm();
				projectFormLabel.setText("New Project");
				projectFormPanel.setVisible(true);
			}
		});
		newProjectButton.setBounds(47, 63, 89, 84);
		homePanel.add(newProjectButton);

		welcomePanel = new JPanel();
		welcomePanel.setBackground(new Color(192, 192, 192));
		welcomePanel.setBounds(360, 29, 763, 513);
		welcomePanel.setVisible(true);
		welcomePanel.setLayout(null);
		homePanel.add(welcomePanel);

		JLabel lbl1 = new JLabel("Welcome");
		lbl1.setForeground(new Color(211,211,211));
		lbl1.setFont(new Font("High Tower text",Font.ITALIC,80));
		lbl1.setBounds(240,116,282,99);
		welcomePanel.add(lbl1);

		JLabel lbl2 = new JLabel("Create a new project or open an existing project");
		lbl2.setFont(new Font("High Tower Text", Font.ITALIC, 35));
		lbl2.setForeground(new Color(211,211,211));
		lbl2.setBounds(70, 231, 623, 74);
		welcomePanel.add(lbl2);

		viewAllProjectsPanel();
		homePanel.add(viewPanel);
		viewProjectsButton = new JButton(new ImageIcon("Images\\open_project.png"));
		viewProjectsButton.setToolTipText("Select to view the list of existing projects");
		viewProjectsButton.setBounds(179, 63, 89, 84);
		homePanel.add(viewProjectsButton);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(325, 0, 2, 611);
		homePanel.add(separator);


		currentUserLabel = new JLabel("Signed in as " + currentUser); //FIX THIS
		currentUserLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		currentUserLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		currentUserLabel.setForeground(new Color(255, 255, 255));
		currentUserLabel.setFont(new Font("High Tower Text", Font.PLAIN, 17));
		currentUserLabel.setBounds(945, 22, 216, 17);
		add(currentUserLabel);
		

	}



	/**
	 * Creates the form for a new Project.
	 * @return projectFormPanel
	 */
	private JPanel createProjectForm() {

		projectFormPanel = new JPanel();
		projectFormPanel.setBounds(360, 29, 763, 513);
		projectFormPanel.setLayout(null);
		projectFormPanel.setVisible(false);

		projectFormLabel = new JLabel("New Project");
		projectFormLabel.setFont(new Font("High Tower Text", Font.PLAIN, 30));
		projectFormLabel.setBounds(82, 34, 166, 35);
		projectFormPanel.add(projectFormLabel);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(82, 102, 94, 27);
		projectFormPanel.add(lblName);

		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(82, 160, 94, 27);
		projectFormPanel.add(lblDescription);

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(82, 251, 94, 27);
		projectFormPanel.add(lblStatus);

		textFieldName = new JTextField();
		textFieldName.setBounds(195, 102, 288, 27);
		textFieldName.setColumns(10);
		textFieldName.setText("q");
		projectFormPanel.add(textFieldName);

		JScrollPane descriptionScrollPane = new JScrollPane();
		descriptionScrollPane.setBounds(198, 161, 285, 66);
		projectFormPanel.add(descriptionScrollPane);

		textArea = new JTextArea();
		textArea.setText("q");
		textArea.setLineWrap(true);
		descriptionScrollPane.setViewportView(textArea);

		choice = new Choice();
		choice.setBounds(195, 258, 140, 35);
		choice.add("Unlocked");
		choice.add("In_progress");
		choice.add("Restricted");
		choice.add("Completed");
		projectFormPanel.add(choice);

		JLabel lblBudget = new JLabel("Budget");
		lblBudget.setBounds(82, 309, 94, 27);
		projectFormPanel.add(lblBudget);

		budgetTextField = new JTextField();
		budgetTextField.setBounds(195, 305, 122, 27);
		budgetTextField.setColumns(10);
		budgetTextField.setText("450");
		projectFormPanel.add(budgetTextField);

		JLabel lblStartdate = new JLabel("startDate");
		lblStartdate.setBounds(82, 361, 94, 27);
		projectFormPanel.add(lblStartdate);

		JLabel lblStartdate_1 = new JLabel("endDate");
		lblStartdate_1.setBounds(445, 361, 94, 27);
		projectFormPanel.add(lblStartdate_1);

		textStartDate = new JTextField();
		textStartDate.setText(" ");
		textStartDate.setBounds(195, 357, 180, 35);
		textStartDate.setColumns(10);
		textStartDate.setText("12-12-2015");
		projectFormPanel.add(textStartDate);

		textEndDate = new JTextField();
		textEndDate.setText(" ");
		textEndDate.setColumns(10);
		textEndDate.setBounds(535, 357, 180, 35);
		textEndDate.setText("13-12-2015");
		projectFormPanel.add(textEndDate);

		saveProjectButton = new JButton("Save");
		saveProjectButton.setBounds(514, 454, 106, 46); 
		saveProjectButton.setFont(new Font("High Tower Text", Font.PLAIN, 22));
		projectFormPanel.add(saveProjectButton);

		cancelProjectButton = new JButton("Cancel");
		cancelProjectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				clearForm();
				projectFormPanel.setVisible(false);	
				welcomePanel.setVisible(true);
			}
		});
		cancelProjectButton.setBounds(629, 454, 106, 46);
		cancelProjectButton.setFont(new Font("High Tower Text", Font.PLAIN, 22));
		projectFormPanel.add(cancelProjectButton);

		return projectFormPanel;
	}


	/**
	 * Creates components to view all Projects.
	 * @return viewPanel;
	 */
	private JPanel viewAllProjectsPanel() {

		viewPanel = new JPanel();
		viewPanel.setBackground(new Color(105, 105, 105));
		viewPanel.setBounds(360, 29, 763, 513);
		viewPanel.setLayout(null);
		viewPanel.setVisible(false);

		JLabel lblOpenAnExsiting = new JLabel("Open an existing project");
		lblOpenAnExsiting.setForeground(new Color(255, 255, 255));
		lblOpenAnExsiting.setFont(new Font("High Tower Text", Font.PLAIN, 30));
		lblOpenAnExsiting.setBounds(220, 43, 323, 35);
		viewPanel.add(lblOpenAnExsiting);

		JButton cancelViewBtn = new JButton("Cancel");
		cancelViewBtn.setFont(new Font("High Tower Text", Font.PLAIN, 22));
		cancelViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				viewPanel.setVisible(false);
				welcomePanel.setVisible(true);
			}
		});
		cancelViewBtn.setBounds(587, 432, 106, 46);
		viewPanel.add(cancelViewBtn);

		openButton = new JButton("Open");
		openButton.setFont(new Font("High Tower Text", Font.PLAIN, 22));
		openButton.setBounds(453, 432, 106, 46);
		viewPanel.add(openButton);
		
		projectTable = new JTable();
		projectTable.setBounds(220, 102, 323, 296);
		projectTable.setRowHeight(26);
		projectTable.setFont(new Font("High Tower Text", Font.PLAIN, 24));
		projectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		viewPanel.add(projectTable);
		setVisible(true);
		
		return viewPanel;
	}


	/**
	 * Resets all entries of the textFields in the Project form.
	 */
	public void clearForm() {
		textFieldName.setText(" ");
		textArea.setText(" ");
		budgetTextField.setText(" ");
		textStartDate.setText(" ");
		textEndDate.setText(" ");
	}


	/**
	 * Automatically switches to the ProjectTab when a project has been created or selected.	
	 */
	public void autoOpenProjectTab() {
		tabbedPane.setEnabledAt(1,true);
		tabbedPane.setSelectedIndex(1);
	}

	/**
	 * Automatically switches to the HomeTab when a project has been deleted.
	 */
	public void autoOpenHomeTab() {
		tabbedPane.setSelectedIndex(0);
		tabbedPane.setEnabledAt(1,false);
		
	}


	public void createEditProjectForm(Project project) {

		tabbedPane.setSelectedIndex(0);
		projectFormLabel.setText("Edit Project");
		textFieldName.setText(project.getName().toUpperCase());
		choice.select(project.getStatus().toString());
		textStartDate.setText(project.getStartDate());
		textEndDate.setText(project.getEndDate());
		textArea.setText(project.getDescription());
		budgetTextField.setText(String.valueOf(project.getBudget()));

		projectFormPanel.setVisible(true);

	}

	public void updateDisplayPanel() {
		projectPanel.updateDisplayPanel(currentProject);
	}

	public void addSaveProjectListener(ActionListener listener) {
		saveProjectButton.addActionListener(listener);
	}

	public void addViewProjectsListener(ActionListener listener) {
		viewProjectsButton.addActionListener(listener);
	}

	public void addOpenProjectListener(ActionListener listener) {
		openButton.addActionListener(listener);
	}


	
}


