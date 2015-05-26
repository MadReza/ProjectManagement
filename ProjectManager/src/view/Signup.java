package view;

import java.awt.BorderLayout;
//import java.awt.EventQueue;



import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

/*import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;

import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.List;
import java.awt.Choice;
 */


import javax.swing.JOptionPane;

import model.Database;
import model.DatabaseConnection;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.Color;

@SuppressWarnings("serial")
public class Signup extends JFrame {

	Connection connection = null;
	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField emailTextField;
	private JTextField usernameTextField;
	private JPasswordField passwordField;
	private JPasswordField confirmPWfield;

	/**
	 * Create the Registration/Sign up form.
	 */
	public Signup() {
		super("Project Manager");
		setResizable(false);

		connection = DatabaseConnection.dbConnector();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(25, 25, 112));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setBackground(new Color(25, 25, 112));
		lblNewLabel_4.setForeground(new Color(255, 255, 255));
		lblNewLabel_4.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 26));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel_4, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(25, 25, 112));
		panel.setForeground(new Color(255, 255, 255));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblName = new JLabel("Name");
		lblName.setForeground(new Color(255, 255, 255));
		lblName.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
		lblName.setBounds(337, 80, 97, 38);
		panel.add(lblName);

		JLabel lblEmail = new JLabel("E-mail ");
		lblEmail.setForeground(new Color(255, 255, 255));
		lblEmail.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
		lblEmail.setBounds(337, 160, 97, 38);
		panel.add(lblEmail);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
		lblUsername.setBounds(337, 240, 138, 38);
		panel.add(lblUsername);

		JLabel lblCreatePassword = new JLabel("Create Password");
		lblCreatePassword.setForeground(new Color(255, 255, 255));
		lblCreatePassword.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
		lblCreatePassword.setBounds(337, 320, 170, 38);
		panel.add(lblCreatePassword);

		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setForeground(new Color(255, 255, 255));
		lblConfirmPassword.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
		lblConfirmPassword.setBounds(337, 400, 180, 38);
		panel.add(lblConfirmPassword);

		nameTextField = new JTextField();
		lblName.setLabelFor(nameTextField);
		nameTextField.setBounds(600, 80, 263, 30);
		panel.add(nameTextField);
		nameTextField.setColumns(10);

		emailTextField = new JTextField();
		emailTextField.setColumns(10);
		emailTextField.setBounds(600, 159, 263, 30);
		panel.add(emailTextField);

		usernameTextField = new JTextField();
		usernameTextField.setColumns(10);
		usernameTextField.setBounds(600, 239, 263, 30);
		panel.add(usernameTextField);

		passwordField = new JPasswordField();
		passwordField.setBounds(600, 319, 263, 30);
		panel.add(passwordField);

		confirmPWfield = new JPasswordField();
		confirmPWfield.setBounds(600, 400, 263, 30);
		panel.add(confirmPWfield);

		JButton signupButton = new JButton("Sign Up");
		signupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(passwordField.getText().equals(confirmPWfield.getText())) {
					try {
						Database database = new Database(); 
						boolean regStatus = database.addMembertoDatabase(nameTextField.getText(), emailTextField.getText(),
								usernameTextField.getText(), passwordField.getText());
						if(regStatus){
							dispose();
							ProjectManagerUI managerUI = new ProjectManagerUI();
							managerUI.setVisible(true);
						}
					}
					catch (Exception e) {
						JOptionPane.showMessageDialog(signupButton, e);	
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Passwords do not match");	
			}
	});
		signupButton.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 22));
		signupButton.setBounds(445, 509, 120, 38);
		panel.add(signupButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dispose();
				new Login();
			}
		});
		cancelButton.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 22));
		cancelButton.setBounds(635, 509, 120, 38);
		panel.add(cancelButton);

		JLabel lblNewLabel = new JLabel("Member Registration");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("High Tower Text", Font.ITALIC, 26));
		lblNewLabel.setBounds(476, 20, 231, 49);
		panel.add(lblNewLabel);


}
}
