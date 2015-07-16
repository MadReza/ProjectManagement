package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class SignupPage extends JFrame {

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

	public JPanel getSignupPanel() {
		return signupPanel;
	}

	// Adds an action listener to the Sign up Button.
	public void addSignupListener(ActionListener alistener) {
		signupBtn.addActionListener(alistener);
	}

	public void addCancelSignUpListener(ActionListener alistener){
		cancelBtn.addActionListener(alistener);
	}
}
