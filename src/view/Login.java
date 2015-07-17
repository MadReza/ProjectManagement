package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

<<<<<<< HEAD
=======
import java.awt.event.ActionEvent;

import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
@SuppressWarnings("serial")
public class Login extends JFrame {

	private SignupPage signupFrame;
	private JTextField usernameTextField;
<<<<<<< HEAD
	private JTextField PasswordTextField;
	private JButton loginButton, registerBtn;
	private JPanel loginPanel;

	public Login(){
		// MainFrame setup (title, layout, dimensions)
		super("My Project Manager");							// calls super constructor in JFrame and assigning application title
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			// the application doesn't exit on closing by default
		setSize(500, 380);										// sets default size for card1
=======
	private JPasswordField passwordField;
	private JButton loginButton, signUpBtn;
	private JPanel loginPanel;

	public Login(){
		super("My Project Manager");							// calls super constructor in JFrame and assigning application title
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			// the application doesn't exit on closing by default
		setBounds(100, 100, 1200, 700);							// sets default size for card1					
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
		setVisible(true);										// JFrame is invisible by default
		setResizable(false);

		loginPanel = new JPanel();
<<<<<<< HEAD
		loginPanel.setBackground(Color.getHSBColor(2.43f, 0.8f, 0.7f));
		loginPanel.setLayout(null);
		
		
		//Login Panel Attributes
		JLabel usernameLabel = new JLabel("User Name");
		usernameLabel.setFont(new Font("Courier New Bold Italic", Font.BOLD, 18));
		usernameLabel.setBounds(30, 30, 200, 30);
		loginPanel.add(usernameLabel);

		usernameTextField = new JTextField(15);
		usernameTextField.setFont(new Font("Serif Bold", Font.BOLD, 18));
		usernameTextField.setBounds(250, 30, 200, 30);
		usernameTextField.setForeground(Color.getHSBColor(4.86f, 0.9f, 0.9f));
		usernameTextField.setText("a");				// for testing only .... remove
		loginPanel.add(usernameTextField);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Courier New Bold Italic", Font.BOLD, 18));
		passwordLabel.setBounds(30, 90, 200, 30);
		loginPanel.add(passwordLabel);

		PasswordTextField = new JTextField(15);
		PasswordTextField.setFont(new Font("Serif Bold", Font.BOLD, 18));
		PasswordTextField.setBounds(250, 90, 200, 30);
		PasswordTextField.setForeground(Color.getHSBColor(4.86f, 0.9f, 0.9f));
		PasswordTextField.setText("a");			// for testing only .... remove
		loginPanel.add(PasswordTextField);

		loginButton = new JButton("LOG IN");
		loginButton.setFont(new Font("Courier New Bold Italic", Font.BOLD, 18));
		loginButton.setBounds(30, 170, 200, 40);
//		loginButton.setBorder(BorderFactory.createRaisedBevelBorder());
//		loginButton.setBackground(Color.getHSBColor(2.1f, 0.9f, 0.9f));
		loginPanel.add(loginButton);

		signupFrame = new SignupPage();
		registerBtn = new JButton("REGISTER");
		registerBtn.setFont(new Font("Courier New Bold Italic", Font.BOLD, 18));
		registerBtn.setBounds(250, 170, 200, 40);
		loginPanel.add(registerBtn);
		
		getContentPane().add(loginPanel);
	}
	
=======
		loginPanel.setBackground(new Color(25, 25, 112));
		loginPanel.setLayout(null);
		//loginPanel.setBounds(0, 0, 1194, 671);

		JLabel lblNewLabel = new JLabel("Login or Signup to Manage Projects");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("High Tower Text", Font.ITALIC, 26));
		lblNewLabel.setBounds(409, 54, 375, 50);
		loginPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("High Tower Text", Font.PLAIN, 19));
		lblNewLabel_1.setBounds(421, 178, 89, 37);
		loginPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("High Tower Text", Font.PLAIN, 19));
		lblNewLabel_2.setBounds(421, 238, 89, 37);
		loginPanel.add(lblNewLabel_2);

		usernameTextField = new JTextField();
		usernameTextField.setFont(new Font("High Tower Text", Font.PLAIN, 19));
		usernameTextField.setBounds(577, 184, 200, 25);
		usernameTextField.setColumns(10);
		usernameTextField.setText("a");
		loginPanel.add(usernameTextField);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("High Tower Text", Font.PLAIN, 19));
		passwordField.setBounds(577, 244, 200, 25);
		loginPanel.add(passwordField);

		loginButton = new JButton("Login");
		loginButton.setFont(new Font("High Tower Text", Font.ITALIC, 16));
		loginButton.setBounds(577, 334, 90, 25);
		loginPanel.add(loginButton);

		JLabel lblNewLabel_3 = new JLabel("First time User? Sign up.");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setFont(new Font("High Tower Text", Font.PLAIN, 17));
		lblNewLabel_3.setBounds(577, 368, 200, 25);
		loginPanel.add(lblNewLabel_3);
		
		signupFrame = new SignupPage();
		signUpBtn = new JButton("Sign Up");
		signUpBtn.setFont(new Font("High Tower Text", Font.ITALIC, 16));
		signUpBtn.setBounds(687, 334, 90, 25);
		loginPanel.add(signUpBtn);
		getContentPane().add(loginPanel);
	}

>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
	public JButton getLoginBtn() {
		return loginButton;
	}

<<<<<<< HEAD
	public String getUsername(){
=======
	public String getUsernameTextField(){
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
		return usernameTextField.getText();
	}

	public String getPassword(){
<<<<<<< HEAD
		return PasswordTextField.getText();
=======
		return (new String (passwordField.getPassword()));
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
	}

	public SignupPage getSignupFrame(){
		return signupFrame;
	}
<<<<<<< HEAD
	
	public void clearLoginForm(){
		usernameTextField.setText("");
		PasswordTextField.setText("");
	}
	

	// adds an action listener to the login button
	public void addLoginListener(ActionListener aListener){

		loginButton.addActionListener(aListener); 
	}
	
	public void addRegisterBtnListener(ActionListener aListener){
		registerBtn.addActionListener(aListener);
=======

	public void clearLoginForm(){
		usernameTextField.setText("");
		passwordField.setText("");
	}

	// adds an action listener to the login button
	public void addLoginListener(ActionListener aListener){
		loginButton.addActionListener(aListener); 
	}

	public void addRegisterBtnListener(ActionListener aListener){
		signUpBtn.addActionListener(aListener);
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
	}

}
