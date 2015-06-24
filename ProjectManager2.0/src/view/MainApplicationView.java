package view;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import model.Project;
import model.Status;

@SuppressWarnings("serial")
public class MainApplicationView extends JPanel {


	private ProjectPanel projectPanel;
	private JTabbedPane tabbedPane;
	private JPanel homeParentPanel, welcomePanel, homePanel, projectFormPanel, viewPanel;
	private CardLayout homeTabLayout;

	private JButton newProjectButton, viewProjectsButton, saveProjectButton, cancelProjectButton, openButton, logoutButton;
	private JTextField textFieldName, budgetTextField, textStartDate, textEndDate;
	private JTextArea textArea;
	private JLabel projectFormLabel, currentUserLabel;
	private Choice choice;
	private JTable projectTable;

	private JDatePickerImpl startDatePicker ;
	private JDatePickerImpl endDatePicker ;
	private JDatePanelImpl startDatePanel;
	private JDatePanelImpl endDatePanel;
	private UtilDateModel startDateModel ;
	private UtilDateModel endDateModel;

	private String currentUser;
	private Project currentProject;
	private boolean editMode = false;
	private JScrollPane scrollPane;


	/**
	 * Create the panel.
	 */
	public MainApplicationView() {
		createProjectForm();
		viewAllProjectsPanel();
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
		double budget = -1.0;

		try {
			budget = Double.parseDouble(budgetTextField.getText());
			return (new Double(budgetTextField.getText()));
		}
		catch(NumberFormatException e) {
			return budget; // if the code gets to here, it wasn't recognized as a double.
		}
	}

	public void setBudget(JTextField budgetTextField) {
		this.budgetTextField = budgetTextField;
	}
	
	public String getStartDate() {
		return  startDateModel.getValue().toString();
	}

	public void setStartDate(String startDate) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
		try {
			cal.setTime(sdf.parse(startDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}// all done
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		startDatePicker.getModel().setDate(year, month, day);
	}

	public String getEndDate() {
		return  endDateModel.getValue().toString();
	}

	public void setEndDate(String endDate) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
		try {
			cal.setTime(sdf.parse(endDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		endDatePicker.getModel().setDate(year, month, day);
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

	public void setEditMode(boolean state) {
		this.editMode = state;
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

		welcomePanel = new JPanel();
		welcomePanel.setBackground(new Color(192, 192, 192));
		welcomePanel.setBounds(360, 29, 763, 513);
		welcomePanel.setVisible(true);
		welcomePanel.setLayout(null);

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

		homeTabLayout = new CardLayout();
		homeParentPanel = new JPanel();
		homeParentPanel.setBackground(new Color(192, 192, 192));
		homeParentPanel.setBounds(360, 29, 763, 513);
		homeParentPanel.setVisible(true);
		homeParentPanel.setLayout(homeTabLayout);
		homeParentPanel.add(welcomePanel, "1");
		homeParentPanel.add(projectFormPanel, "2");
		homeParentPanel.add(viewPanel,"3");
		homePanel.add(homeParentPanel);
		homeTabLayout.show(homeParentPanel,"1");

		newProjectButton = new JButton(new ImageIcon("Images\\addProject.png"));
		newProjectButton.setToolTipText("Select to create a new project");
		newProjectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				clearForm();
				projectFormLabel.setText("New Project");
				homeTabLayout.show(homeParentPanel,"2");
			}
		});
		newProjectButton.setBounds(47, 63, 89, 84);
		homePanel.add(newProjectButton);

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
		currentUserLabel.setBounds(855, 22, 216, 17);
		add(currentUserLabel);

		logoutButton = new JButton("Logout");
		logoutButton.setFont(new Font("High Tower Text", Font.PLAIN, 15));
		logoutButton.setBounds(1100, 15, 80, 30);
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				StartupView.cardLayout.show(StartupView.parentPanel, "1");
			}
		});
		add(logoutButton);
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
		projectFormPanel.add(textFieldName);

		JScrollPane descriptionScrollPane = new JScrollPane();
		descriptionScrollPane.setBounds(198, 161, 285, 66);
		projectFormPanel.add(descriptionScrollPane);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		descriptionScrollPane.setViewportView(textArea);

		choice = new Choice();
		choice.setBounds(195, 258, 140, 35);
		choice.add("Unlocked");
		choice.add("In_progress");
		choice.add("Locked");
		choice.add("Completed");
		projectFormPanel.add(choice);

		JLabel lblBudget = new JLabel("Budget");
		lblBudget.setBounds(82, 309, 94, 27);
		projectFormPanel.add(lblBudget);

		budgetTextField = new JTextField();
		budgetTextField.setBounds(195, 305, 122, 27);
		budgetTextField.setColumns(10);
		projectFormPanel.add(budgetTextField);

		JLabel lblStartdate = new JLabel("startDate");
		lblStartdate.setBounds(82, 361, 94, 27);
		projectFormPanel.add(lblStartdate);

		JLabel lblStartdate_1 = new JLabel("endDate");
		lblStartdate_1.setBounds(445, 361, 94, 27);
		projectFormPanel.add(lblStartdate_1);

		startDateModel = new UtilDateModel();
		startDateModel.setSelected(true);
		endDateModel = new UtilDateModel();
		endDateModel.setSelected(true);

		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");

		startDatePanel = new JDatePanelImpl(startDateModel, p);
		endDatePanel = new JDatePanelImpl(endDateModel, p);

		startDatePicker = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());
		startDatePicker.setBounds(195, 357, 180, 35);
		projectFormPanel.add(startDatePicker);

		endDatePicker = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());
		endDatePicker.setBounds(535, 357, 180, 35);
		projectFormPanel.add(endDatePicker);
		
		saveProjectButton = new JButton("Save");
		saveProjectButton.setBounds(514, 454, 106, 46); 
		saveProjectButton.setFont(new Font("High Tower Text", Font.PLAIN, 22));
		projectFormPanel.add(saveProjectButton);

		cancelProjectButton = new JButton("Cancel");
		cancelProjectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				clearForm();
				if(editMode) 			
					autoOpenProjectTab();
				homeTabLayout.show(homeParentPanel,"1");
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
				homeTabLayout.show(homeParentPanel,"1");
			}
		});
		cancelViewBtn.setBounds(587, 432, 106, 46);
		viewPanel.add(cancelViewBtn);

		openButton = new JButton("Open");
		openButton.setFont(new Font("High Tower Text", Font.PLAIN, 22));
		openButton.setBounds(453, 432, 106, 46);
		viewPanel.add(openButton);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(220, 102, 323, 296);
		viewPanel.add(scrollPane);

		projectTable = new JTable();
		scrollPane.setViewportView(projectTable);
		projectTable.setSurrendersFocusOnKeystroke(true);
		projectTable.setFillsViewportHeight(true);
		projectTable.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		projectTable.setAutoCreateRowSorter(true);
		projectTable.setRowHeight(26);
		projectTable.setFont(new Font("High Tower Text", Font.PLAIN, 24));
		projectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setVisible(true);

		return viewPanel;
	}


	/**
	 * Resets all entries of the textFields in the Project form.
	 */
	public void clearForm() {
		textFieldName.setText("");
		textArea.setText("");
		budgetTextField.setText("");
		Calendar calendar = Calendar.getInstance();
		startDateModel.setValue(calendar.getTime());
		endDateModel.setValue(calendar.getTime());
	}

	/**
	 * Switches to the viewPanel associated with the viewButton.
	 */
	public void displayViewPanel() {
		homeTabLayout.show(homeParentPanel,"3");
	}

	/**
	 * Switches to the panel with the form.
	 */
	public void displayFormPanel() {
		homeTabLayout.show(homeParentPanel,"2");
	}

	/**
	 * Switches to the panel with the welcome msg.
	 */
	public void displayWelcomePanel(){
		homeTabLayout.show(homeParentPanel, "1");
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
		getStartDate();
		getEndDate();
		textArea.setText(project.getDescription());
		budgetTextField.setText(String.valueOf(project.getBudget()));

		projectFormPanel.setVisible(true);

	}

	public int isJobFormReady(){

		Calendar cal = Calendar.getInstance();
		if(getName().equals("") || getBudget().equals("") || getDescription().equals("")){
			return 0;
		}

		if( startDateModel.getValue().after(endDateModel.getValue())){
			return 1;
		}

		if(getChoice().equals(Status.IN_PROGRESS)  &&  startDateModel.getValue().after(cal.getTime()) ){
			return 2;
		}

		if(getChoice().equals(Status.COMPLETED)  &&  endDateModel.getValue().after(cal.getTime())){
			return 3;
		}

		if (getBudget() == -1.0)
		{
			return 4;
		}
		return -1;	
	}

	public boolean startInPast(){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date); 
		cal.add(Calendar.DATE, -1);
		date = cal.getTime();
		if( startDateModel.getValue().before(cal.getTime()) ){
			return true;
		}
		return false;
	}

	public class DateLabelFormatter extends AbstractFormatter {

		private static final long serialVersionUID = 1L;
		private String datePattern = "yyyy-MM-dd";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}

			return "";
		}
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


