package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import controller.MainController;

import model.MainModel;
import model.Project;

@SuppressWarnings("serial")
public class MainView extends JFrame {
	
	// Model reference
	private MainModel mainModel;
	
	// Controller reference
	private MainController mainController;	
	
	// LoginArea
	private Login loginPage;

	// MenuBar
	private JMenuBar menuBar;
	
	// ToolBar 
	private JToolBar toolbar;
	
	// Existing Projects are displayed in this ComboBox
	private JComboBox<Project> existingProjectsCombo;

	// Main Panels and Panes
	private JTabbedPane jobsTabbedPane;
	
	// FormPanels for Jobs
	private JobPanel jobPanel;
	private ProjectPanel projectPanel;
	private ActivityPanel activityPanel;
	private ReportPanel reportPanel;
	
	// JMenuItems
	JMenuItem newProjectItem , exitItem , gantItem, criticalItem, pertItem, earnedItem;

	// Layout Manager
	BorderLayout borderLayout;
	
	// Projects Tree
	private JScrollPane treePane;
	private TreePanel projectsTree;
	
	// Table Panel
	TablePanel tablePanel;
	
	// MainFram default constructor
	public MainView(MainModel mModel){
		
		// MainFrame setup (title, layout, dimensions)
		super("Project Manager Space");							// calls super constructor in JFrame and assigning our Apps title
		
		mainModel = mModel ;
		
		loginPage = new Login();

		// layout
		borderLayout = new BorderLayout();
		setLayout(borderLayout);								// divides the Main Frame into 5 regions NORTH, SOUTH, EAST, WEST and CENTER
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);			// the application doesn't exit on closing by default
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we)
			{ 
				String objectButtons[] = {"Yes","No"};
				int promptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Project Manager",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,objectButtons,objectButtons[1]);
				if(promptResult == JOptionPane.YES_OPTION)
				{
					System.exit(0);
				}
			}
		});
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
//		setSize(1200, 800);										// sets default size
		setMinimumSize(new Dimension(1200, 700));				// can't be resized less than these
		setVisible(false);										// JFrame is invisible by default
		
		// MenuBar
		menuBar = CreateMenuBar();
		setJMenuBar(menuBar);
		
		// ToolBar
		toolbar = createToolBar();
		add(toolbar, BorderLayout.NORTH);
		
		// creates and sets Jobs panels
		jobsTabbedPane = new JTabbedPane();
		jobsTabbedPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		jobsTabbedPane.setBackground(Color.yellow);
		add(jobsTabbedPane, BorderLayout.EAST);

		// report panel
		reportPanel = new ReportPanel();
		jobsTabbedPane.addTab("Main", reportPanel);
		
		// creates an NewProjectForm inside ProjectsTab
		projectPanel = new ProjectPanel();
		jobsTabbedPane.addTab("Project", projectPanel);
		
		// creates a NewActivityForm inside ActivitiesTab
		activityPanel = new ActivityPanel();
		jobsTabbedPane.addTab("Activity", activityPanel);		
		
		// Tree Panel
		projectsTree = new TreePanel();
		projectsTree.setBackground(Color.getHSBColor(1.57f, 0.6f, 0.8f));
		treePane = new JScrollPane(projectsTree);
		treePane.setViewportBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		add(treePane, BorderLayout.WEST);
		
		// Table Panel to display Data
		tablePanel = new TablePanel();
		add(tablePanel, BorderLayout.CENTER);
	}
	
	////////////////////////////////////////////////////////////////	Helper Methods	\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	// creates a menuBar and returns it
	public JMenuBar CreateMenuBar() {							
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.getHSBColor(1.57f, 0.6f, 0.8f));
		menuBar.setBorder(BorderFactory.createLineBorder(Color.orange, 4));
		
		JMenu newMenu = new JMenu("      New     ");							// creates a menu called "New"
		newMenu.setFont(new Font("Arial Black", Font.BOLD, 22));
		
		
		JMenu chartMenu = new JMenu("    Charts  ");							// creates a menu called "Charts"
		chartMenu.setFont(new Font("Arial Black", Font.BOLD, 22));
		
		JMenu analysisMenu = new JMenu("    Analysis   ");							// creates a menu called "Analysis"
		analysisMenu.setFont(new Font("Arial Black", Font.BOLD, 22));
		
		newProjectItem = new JMenuItem("      Project ");    				// creates a "Project" menu item to be added to "New" menu
		newProjectItem.setFont(new Font("Times New Roman", Font.BOLD, 20));
		
		exitItem = new JMenuItem("      Exit  ");							// creates an "Exit" menu item to be added to "New" menu
		exitItem.setFont(new Font("Times New Roman", Font.BOLD, 20));
		
		newMenu.add(newProjectItem);								// adds "Project" item to "New" menu
		newMenu.addSeparator();										// separates "Project" part from "exit" part
		newMenu.add(exitItem);										// adds "Exit" menu item to "New" menu 
		
		gantItem = new JMenuItem("      GANT       ");
		gantItem.setFont(new Font("Times New Roman", Font.BOLD, 20));
		chartMenu.add(gantItem);
		
		criticalItem = new JMenuItem("      Critical Path ");
		criticalItem.setFont(new Font("Times New Roman", Font.BOLD, 20));
		analysisMenu.add(criticalItem);
		
		pertItem = new JMenuItem("      PERT     ");
		pertItem.setFont(new Font("Times New Roman", Font.BOLD, 20));
		analysisMenu.add(pertItem);
		
		earnedItem = new JMenuItem("      Earned Value ");
		earnedItem.setFont(new Font("Times New Roman", Font.BOLD, 20));
		analysisMenu.add(earnedItem);
		
		newMenu.setMnemonic(KeyEvent.VK_N);							// ALT + N  opens "New" inside menuBar
		chartMenu.setMnemonic(KeyEvent.VK_C);						// ALT + C  opens "Charts" inside menuBar
		analysisMenu.setMnemonic(KeyEvent.VK_A);					// ALT + A  opens "Analysis" inside menuBar
		
		newProjectItem.setMnemonic(KeyEvent.VK_P);					// ALT + P  opens "Project" inside "New"
		
		exitItem.setMnemonic(KeyEvent.VK_X);						// ALT + X  invokes exit inside "New" and exits the Apps
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));							// CTRL + X exits 
		
		menuBar.add(newMenu);										// adds "New" menu to the menuBar
		menuBar.add(chartMenu);										// adds "Charts" menu to the menuBar
		menuBar.add(analysisMenu);									// adds "Analysis" menu to the menuBar
		
		return menuBar;
	}

	public JToolBar createToolBar(){
		
		JToolBar toolbar = new JToolBar();
		
		toolbar.setBackground(Color.getHSBColor(0.1f, 0.3f, 0.9f));
		toolbar.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(1.57f, 0.6f, 0.8f), 4));
		
		existingProjectsCombo = new JComboBox<Project>();
		existingProjectsCombo.insertItemAt(null, 0);
		existingProjectsCombo.setMinimumSize(new Dimension(30, 5));
		existingProjectsCombo.setEditable(false);
		existingProjectsCombo.setFont(new Font("Arial Black", Font.BOLD, 20));
		existingProjectsCombo.setBorder(BorderFactory.createRaisedSoftBevelBorder());

		
		toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel comboLabel = new JLabel("Existing Projects");
		comboLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		comboLabel.setForeground(Color.getHSBColor(1.04f, 0.9f, 0.5f));
		toolbar.add(comboLabel);
		toolbar.add(existingProjectsCombo);
		return toolbar;
	}
	
	public void ProjectPaneEnabled(boolean val) {
		jobsTabbedPane.setEnabledAt(1, val);
	}

	public void ActivityPaneEnabled(boolean val) {
		jobsTabbedPane.setEnabledAt(2, val);
	}
	
	////////////////////////////////////////////////////////////////	Listeners	\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	public void addComboListener(ItemListener aListener){
		existingProjectsCombo.addItemListener(aListener);
	}
	
	public void addNewProjectItemListener(ActionListener aListener){
		newProjectItem.addActionListener(aListener);
	}
	
	public void addExitItemListener(ActionListener aListener){
		exitItem.addActionListener(aListener);
	}
	
	public void addGantItemListener(ActionListener aListener){
		gantItem.addActionListener(aListener);
	}

	////////////////////////////////////////////////////////////////	Getters and Setters	\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	public JComboBox<Project> getExistingProjectsCombo() {
		return existingProjectsCombo;
	}
	
	public JTabbedPane getJobsTabbedPane() {
		return jobsTabbedPane;
	}	
	
	public ReportPanel getReportPanel() {
		return reportPanel;
	}
	
	public TreePanel getTreePanel() {
		return projectsTree;
	}
	
	// to be used in the controller
	public Login getLoginPage() {
		return loginPage;
	}
	
	public TablePanel getTablePanel() {
		return tablePanel;
	}

	public JobPanel getJobPanel() {
		return jobPanel;
	}

	public MainModel getMainModel(){
		return mainModel;
	}

	public JobPanel getProjectPanel() {
		return projectPanel;
	}
	
	public JobPanel getActivityPanel() {
		// TODO Auto-generated method stub
		return activityPanel;
	}

	public JMenuItem getNewProjectItem() {
		return newProjectItem;
	}

	public JMenuItem getExitItem() {
		return exitItem;
	}
	
	public JMenuBar getTheMenuBar() {
		return menuBar;
	}

	public JMenuItem getGantItem() {
		return gantItem;
	}

}
