package view;

<<<<<<< HEAD
import java.awt.Color;
import java.awt.Font;
=======
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
<<<<<<< HEAD
import javax.swing.JTextField;
=======
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e

@SuppressWarnings("serial")
public class SignupPage extends JFrame {

<<<<<<< HEAD
	private JTextField signupUN,signupPW;
	private JButton cancelBtn, signupBtn;
	private JPanel signupPanel;
	private JTextField signupCPW;
	private JComboBox<String> comboBox;
	
	public SignupPage(){
		super("My Project Manager");							// calls super constructor in JFrame and assigning our Apps title
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);			// the application doesn't exit on closing by default
		setSize(500,380);										// sets default size for card1
		setVisible(false);										// JFrame is invisible by default
		setResizable(false);

		signupPanel = new JPanel();
		signupPanel.setBackground(Color.getHSBColor(2.5f, 0.8f, 0.7f));
		signupPanel.setLayout(null);


		JLabel signupUNLbl = new JLabel("User Name");
		signupUNLbl.setFont(new Font("Courier New Bold Italic", Font.BOLD, 18));
		signupUNLbl.setBounds(30, 30, 200, 30);
		signupPanel.add(signupUNLbl);

		signupUN = new JTextField(15);
		signupUN.setFont(new Font("Serif Bold", Font.BOLD, 18));
		signupUN.setBounds(250, 30, 200, 30);
		signupPanel.add(signupUN);

		JLabel lblRole = new JLabel("Role");
		lblRole.setFont(new Font("Courier New Bold Italic", Font.BOLD, 18));
		lblRole.setBounds(30, 90, 200, 30);
		signupPanel.add(lblRole);

		String [] roles = {"Project Manager","Team-Member"};
		comboBox = new JComboBox<String>(roles);
		comboBox.setFont(new Font("Serif Bold", Font.BOLD, 18));
		comboBox.setBounds(250, 90, 200, 30);
		signupPanel.add(comboBox);
		getContentPane().add(signupPanel);
		
		JLabel signupPWLbl = new JLabel("Password");
		signupPWLbl.setFont(new Font("Courier New Bold Italic", Font.BOLD, 18));
		signupPWLbl.setBounds(30, 150, 200, 30);
		signupPanel.add(signupPWLbl);

		signupPW = new JTextField(15);
		signupPW.setFont(new Font("Serif Bold", Font.BOLD, 18));
		signupPW.setBounds(250, 150, 200, 30);
		signupPanel.add(signupPW);

		JLabel signupCPWLbl = new JLabel("Confirm Password");
		signupCPWLbl.setFont(new Font("Courier New Bold Italic", Font.BOLD, 18));
		signupCPWLbl.setBounds(30, 210, 200, 30);
		signupPanel.add(signupCPWLbl);

		signupCPW = new JTextField(15);
		signupCPW.setFont(new Font("Serif Bold", Font.BOLD, 18));
		signupCPW.setBounds(250, 210, 200, 30);
		signupPanel.add(signupCPW);


		signupBtn = new JButton("SIGN UP");
		signupBtn.setFont(new Font("Courier New Bold Italic", Font.BOLD, 18));
		signupBtn.setBounds(30, 280, 200, 40);
		signupPanel.add(signupBtn);

		cancelBtn = new JButton("CANCEL");
		cancelBtn.setFont(new Font("Courier New Bold Italic", Font.BOLD, 18));
		cancelBtn.setBounds(250, 280, 200, 40);
		signupPanel.add(cancelBtn);

	}	
		
	public String getSignupPW() {
		return signupPW.getText();
	}

	public String getSignupCPW() {
		return signupCPW.getText();
	}
	
=======
	private JTextField signupUN;
	private JPasswordField signupPW, signupCPW;
	private JButton cancelBtn, signupBtn;
	private JPanel signupPanel;
	private JComboBox<String> comboBox;
	private JComboBox comboBox_1;

	public SignupPage(){
		super("My Project Manager");							// calls super constructor in JFrame and assigning our Apps title
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);			// the application doesn't exit on closing by default
		setVisible(false);										// JFrame is invisible by default
		setBounds(100, 100, 1200, 700);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setResizable(false);

		signupPanel = new JPanel();
		signupPanel.setBackground(new Color(25, 25, 112));
		signupPanel.setForeground(new Color(255, 255, 255));
		signupPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Member Registration");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("High Tower Text", Font.ITALIC, 26));
		lblNewLabel.setBounds(481, 20, 231, 49);
		signupPanel.add(lblNewLabel);

		JLabel signupUNLbl = new JLabel("Username");
		signupUNLbl.setForeground(Color.WHITE);
		signupUNLbl.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
		signupUNLbl.setBounds(333, 141, 200, 30);
		signupPanel.add(signupUNLbl);

		signupUN = new JTextField(15);
		signupUN.setFont(new Font("High Tower Text", Font.BOLD, 18));
		signupUN.setBounds(584, 141, 200, 30);
		signupPanel.add(signupUN);

		JLabel lblRole = new JLabel("Role");
		lblRole.setForeground(new Color(255, 255, 255));
		lblRole.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
		lblRole.setBounds(333, 211, 138, 30);
		signupPanel.add(lblRole);

		JLabel lblCreatePassword = new JLabel("Create Password");
		lblCreatePassword.setForeground(new Color(255, 255, 255));
		lblCreatePassword.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
		lblCreatePassword.setBounds(333, 281, 170, 30);
		signupPanel.add(lblCreatePassword);

		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setForeground(new Color(255, 255, 255));
		lblConfirmPassword.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 19));
		lblConfirmPassword.setBounds(333, 351, 180, 30);
		signupPanel.add(lblConfirmPassword);

		signupPW = new JPasswordField(15);
		signupPW.setBounds(584, 281, 200, 30);
		signupPanel.add(signupPW);

		signupCPW = new JPasswordField(15);
		signupCPW.setBounds(584, 351, 200, 30);
		signupPanel.add(signupCPW);


		signupBtn = new JButton("Sign Up");
		signupBtn.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 20));
		signupBtn.setBounds(346, 473, 200, 40);
		signupPanel.add(signupBtn);

		cancelBtn = new JButton("Cancel");
		cancelBtn.setFont(new Font("High Tower Text", Font.BOLD | Font.ITALIC, 20));
		cancelBtn.setBounds(609, 473, 200, 40);
		signupPanel.add(cancelBtn);

		String [] roles = {"Project Manager","Team-Member"};
		comboBox = new JComboBox<String>(roles);
		comboBox.setFont(new Font("High Tower Text", Font.BOLD, 16));
		comboBox.setBounds(584, 217, 200, 30);
		signupPanel.add(comboBox);
		getContentPane().add(signupPanel);

	}

	public String getSignupPW() {
		return (new String(signupPW.getPassword()));
	}

	public String getSignupCPW() {
		return (new String(signupCPW.getPassword()));
	}

>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
	public int getRole() {
		if (comboBox.getSelectedItem().toString().equals("Project Manager"))
			return 1;
		return 0;
	}

	public String getSignupUN() {
		return signupUN.getText();
	}

	public void clearSignupForm(){
		signupPW.setText("");
		signupCPW.setText("");
		signupUN.setText("");
	}
<<<<<<< HEAD
	
=======

>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
	public JPanel getSignupPanel() {
		return signupPanel;
	}

	// Adds an action listener to the Sign up Button.
	public void addSignupListener(ActionListener alistener) {
		signupBtn.addActionListener(alistener);
	}
<<<<<<< HEAD
	
	public void addCancelSignUpListener(ActionListener alistener){
		cancelBtn.addActionListener(alistener);
	}

=======

	public void addCancelSignUpListener(ActionListener alistener){
		cancelBtn.addActionListener(alistener);
	}
>>>>>>> 7335317999b66617b53caf2e07915a8d918b402e
}
