package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;


public class StartupView {

	private JFrame mainFrame;
	private JButton loginButton, signupButton;
	private JTextField username, textFieldName, textFieldEmail, textFieldUsername;
	private JPasswordField password, passwordField, confirmPWField;
	private LoginPage loginPage;
	private SignupPage signupPage;
	private MainApplicationView appView;
	private JComboBox<String> roleChoice;
	
	static JPanel parentPanel;
	static CardLayout cardLayout;

	

	public StartupView() {

		loginPage = new LoginPage();
		signupPage = new SignupPage();
		appView = new MainApplicationView();
		cardLayout = new CardLayout();
		createMainFrame(loginPage);
	}


	/**
	 * Creates main frame of the application.
	 */
	private void createMainFrame(LoginPage loginPage) {

		mainFrame = new JFrame();
		mainFrame.setResizable(false);
		mainFrame.getContentPane().setBackground(new Color(25, 25, 112));
		mainFrame.setTitle("Project Manager");
		mainFrame.setVisible(true);
		mainFrame.setBounds(100, 100, 1200, 700);
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter() {
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
		mainFrame.getContentPane().setLayout(null);
		
		parentPanel = new JPanel();
		parentPanel.setBackground(new Color(25, 25, 112));
		parentPanel.setBounds(0, 0, 1194, 671);
		parentPanel.setLayout(cardLayout);
		parentPanel.add(loginPage, "1");
		parentPanel.add(signupPage, "2");
		parentPanel.add(appView, "3");
		mainFrame.getContentPane().add(parentPanel);
		
		cardLayout.show(parentPanel, "1");	

	}

	public LoginPage getLoginPage() {
		return loginPage;
	}
	
	public SignupPage getSignupPage() {
		return signupPage;
	}
	
	public MainApplicationView getAppView() {
		return appView;
	}
	
	//Accessor methods for Login information
	public String getUsername() {
		return username.getText();
	}
	
	public String getPassword() {
		return (new String (password.getPassword()));
	}
	
	//Accessor methods for signup information.
	public String getTextFieldName() {
		return textFieldName.getText();
	}

	public String getTextFieldEmail() {
		return textFieldEmail.getText();
	}

	public String getRole() {
		return roleChoice.getSelectedItem().toString();
	}

	public String getTextFieldUsername() {
		return textFieldUsername.getText();
	}

	public String getPasswordField() {
		return (new String(passwordField.getPassword()));
	}

	public String getConfirmPWField() {
		return (new String(confirmPWField.getPassword()));
	}

	public void switchToMainAppView() {
		cardLayout.show(parentPanel, "3");
	}

	public void clearSignupForm() {
		textFieldName.setText("");
		textFieldEmail.setText("");
		textFieldUsername.setText("");
		passwordField.setText("");
		confirmPWField.setText("");			
	}

	/**
	 * Places components for the Login page.
	 */
	@SuppressWarnings("serial")
	public class LoginPage extends JPanel {

		public LoginPage() {

			setBackground(new Color(25, 25, 112));
			setBounds(0, 0, 1194, 671);
			setLayout(null); //Absolute Layout

			JLabel lblNewLabel = new JLabel("Login or Signup to Manage Projects");
			lblNewLabel.setForeground(new Color(255, 255, 255));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("High Tower Text", Font.ITALIC, 26));
			lblNewLabel.setBounds(409, 54, 375, 50);
			add(lblNewLabel);

			JLabel lblNewLabel_1 = new JLabel("Username");
			lblNewLabel_1.setForeground(new Color(255, 255, 255));
			lblNewLabel_1.setFont(new Font("High Tower Text", Font.PLAIN, 19));
			lblNewLabel_1.setBounds(421, 178, 89, 37);
			add(lblNewLabel_1);

			JLabel lblNewLabel_2 = new JLabel("Password");
			lblNewLabel_2.setForeground(new Color(255, 255, 255));
			lblNewLabel_2.setFont(new Font("High Tower Text", Font.PLAIN, 19));
			lblNewLabel_2.setBounds(421, 238, 89, 37);
			add(lblNewLabel_2);

			username = new JTextField();
			username.setFont(new Font("High Tower Text", Font.PLAIN, 19));
			username.setBounds(577, 184, 200, 25);
			username.setColumns(10);
			username.setText("a");
			add(username);

			password = new JPasswordField();
			password.setFont(new Font("High Tower Text", Font.PLAIN, 19));
			password.setBounds(577, 244, 200, 25);
			add(password);

			loginButton = new JButton("Login");
			loginButton.setFont(new Font("High Tower Text", Font.ITALIC, 16));
			loginButton.setBounds(577, 334, 90, 25);
			add(loginButton);


			JButton signupBtn = new JButton("Sign up");
			signupBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardLayout.show(parentPanel, "2");
				}
			});
			signupBtn.setFont(new Font("High Tower Text", Font.ITALIC, 16));
			signupBtn.setBounds(687, 334, 90, 25);
			add(signupBtn);

			JLabel lblNewLabel_3 = new JLabel("First time User? Sign up.");
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_3.setForeground(new Color(255, 255, 255));
			lblNewLabel_3.setFont(new Font("High Tower Text", Font.PLAIN, 17));
			lblNewLabel_3.setBounds(577, 368, 200, 25);
			add(lblNewLabel_3);
			
		}
		
		public void clearLoginForm() {
			username.setText("");
			password.setText("");
		}
		
		//Adding an action listener to the Login button.
		public void addLoginListener(ActionListener loginListener) {
			
			loginButton.addActionListener(loginListener);
		}

	} // End of class Login.
	
	
	
	/**
	 * Place components for the Member-signup page.
	 */
	@SuppressWarnings("serial")
	public class SignupPage extends JPanel {
		
		public SignupPage() {
			setBounds(100, 100, 1200, 700);
			setBackground(new Color(25, 25, 112));
			setBorder(new EmptyBorder(5, 5, 5, 5));
			setLayout(new BorderLayout(0, 0));
		//	setVisible(false);

			JPanel panel = new JPanel();
			panel.setBackground(new Color(25, 25, 112));
			panel.setForeground(new Color(255, 255, 255));
			add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Member Registration");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setForeground(new Color(255, 255, 255));
			lblNewLabel.setFont(new Font("High Tower Text", Font.ITALIC, 26));
			lblNewLabel.setBounds(476, 20, 231, 49);
			panel.add(lblNewLabel);

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

			JLabel lblRole = new JLabel("Role");
			lblRole.setForeground(new Color(255, 255, 255));
			lblRole.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
			lblRole.setBounds(337, 240, 138, 38);
			panel.add(lblRole);
			
			JLabel lblUsername = new JLabel("Username");
			lblUsername.setForeground(new Color(255, 255, 255));
			lblUsername.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
			lblUsername.setBounds(337, 320, 138, 38);
			panel.add(lblUsername);

			JLabel lblCreatePassword = new JLabel("Create Password");
			lblCreatePassword.setForeground(new Color(255, 255, 255));
			lblCreatePassword.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
			lblCreatePassword.setBounds(337, 400, 170, 38);
			panel.add(lblCreatePassword);

			JLabel lblConfirmPassword = new JLabel("Confirm Password");
			lblConfirmPassword.setForeground(new Color(255, 255, 255));
			lblConfirmPassword.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
			lblConfirmPassword.setBounds(337, 472, 180, 38);
			panel.add(lblConfirmPassword);

			textFieldName = new JTextField();
			lblName.setLabelFor(textFieldName);
			textFieldName.setBounds(600, 80, 263, 30);
			textFieldName.setColumns(10);
			panel.add(textFieldName);

			textFieldEmail = new JTextField();
			textFieldEmail.setColumns(10);
			textFieldEmail.setBounds(600, 159, 263, 30);
			panel.add(textFieldEmail);
			
			String [] roles = {"Project Manager","Team-Member"};
			roleChoice = new JComboBox<String>(roles);
			roleChoice.setFont(new Font("High Tower Text", Font.PLAIN, 14));
			roleChoice.setBounds(600, 244, 263, 34);
			panel.add(roleChoice);
			
			textFieldUsername = new JTextField();
			textFieldUsername.setColumns(10);
			textFieldUsername.setBounds(600, 319, 263, 30);
			panel.add(textFieldUsername);
	
			passwordField = new JPasswordField();
			passwordField.setBounds(600, 400, 263, 30);
			panel.add(passwordField);

			confirmPWField = new JPasswordField();
			confirmPWField.setBounds(600, 472, 263, 30);
			panel.add(confirmPWField);
			
			signupButton = new JButton("Sign Up");
			signupButton.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 22));
			signupButton.setBounds(445, 599, 120, 38);
			panel.add(signupButton);

			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					clearSignupForm();
					cardLayout.show(parentPanel, "1");
				}
			});
			cancelButton.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 22));
			cancelButton.setBounds(635, 599, 120, 38);
			panel.add(cancelButton);
			
		}
		
		/**
		 * Adds an action listener to the Sign up Button.
		 * @param listener
		 */
		public void addSignupListener(ActionListener listener) {
			signupButton.addActionListener(listener);
		}
	}
} // End of class SignupView.
