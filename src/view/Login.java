package view;
import javax.swing.*;

import java.sql.*;
import java.awt.event.*;
import java.awt.*;

import model.Database;
import model.DatabaseConnection;

public class Login {

	static String currentUser;
	Connection connection = null;
	private JFrame frmProjectManager;
	private JTextField usernameTextField;
	private JPasswordField passwordField;

	/**
	 * Create the application.
	 * Establish a connection with the database.
	 */
	public Login() {
		initialize();
		connection = DatabaseConnection.dbConnector(); 
	}

	/**
	 * Set username for the current session.
	 * @param username retrieved when login is successful
	 */
	private void setCurrentUser(String username) {
		currentUser = username;
	}
	
	/**
	 * Returns username for the current session.
	 * @return currentUser
	 */
	public static String getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * Initialise the contents of the Main login frame.
	 */
	private void initialize() {

		frmProjectManager = new JFrame();
		frmProjectManager.setResizable(false);
		frmProjectManager.getContentPane().setBackground(new Color(25, 25, 112));
		frmProjectManager.setTitle("Project Manager");
		frmProjectManager.setVisible(true);
		frmProjectManager.setBounds(100, 100, 1200, 700);
		frmProjectManager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProjectManager.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Login or Signup to Manage Projects");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("High Tower Text", Font.ITALIC, 26));
		lblNewLabel.setBounds(409, 54, 375, 50);
		frmProjectManager.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("High Tower Text", Font.PLAIN, 19));
		lblNewLabel_1.setBounds(421, 178, 89, 37);
		frmProjectManager.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("High Tower Text", Font.PLAIN, 19));
		lblNewLabel_2.setBounds(421, 238, 89, 37);
		frmProjectManager.getContentPane().add(lblNewLabel_2);

		usernameTextField = new JTextField();
		usernameTextField.setFont(new Font("High Tower Text", Font.PLAIN, 12));
		usernameTextField.setBounds(577, 184, 200, 25);
		frmProjectManager.getContentPane().add(usernameTextField);
		usernameTextField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("High Tower Text", Font.PLAIN, 12));
		passwordField.setBounds(577, 244, 200, 25);
		frmProjectManager.getContentPane().add(passwordField);

		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Database database = new Database();
				boolean loginStatus = database.validateLoginInformation(usernameTextField.getText(), 
						passwordField.getText());

				//If user info matches with data in db -> loginStatus = true -> application continues.
				if(loginStatus) {
					setCurrentUser(usernameTextField.getText());
					frmProjectManager.dispose();
					ProjectManagerUI managerUI = new ProjectManagerUI();
					managerUI.setVisible(true);	
				}
			}
		});
		loginButton.setFont(new Font("High Tower Text", Font.ITALIC, 16));
		loginButton.setBounds(577, 334, 90, 25);
		frmProjectManager.getContentPane().add(loginButton);


		JButton signupButton = new JButton("Sign up");
		signupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmProjectManager.dispose();
				Signup form = new Signup();
				form.setVisible(true);
			}
		});
		signupButton.setFont(new Font("High Tower Text", Font.ITALIC, 16));
		signupButton.setBounds(687, 334, 90, 25);
		frmProjectManager.getContentPane().add(signupButton);

		JLabel lblNewLabel_3 = new JLabel("First time User? Sign up.");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setFont(new Font("High Tower Text", Font.PLAIN, 17));
		lblNewLabel_3.setBounds(577, 368, 200, 25);
		frmProjectManager.getContentPane().add(lblNewLabel_3);

	}
}
