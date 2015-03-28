package userInterface;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import social.User;

import com.fasterxml.jackson.core.JsonParseException;

import databaseComm.Registrar;

public class RegisterWindow extends JFrame{
	/* User Info:
	 * username
	 * firstname
	 * lastname
	 * email
	 * organization
	 * phonenumber
	 * password
	 * confirmPassword 
	 */
	private JPanel registerPanel;
	
	private JLabel usernameLabel;
	private JLabel firstnameLabel;
	private JLabel lastnameLabel;
	private JLabel emailLabel;
	private JLabel organizationLabel;
	private JLabel phonenumberLabel;
	private JLabel passwordLabel;
	private JLabel confirmPasswordLabel;
	private JLabel msgLabel;
	
	private JTextField usernameTextField;
	private JTextField firstnameTextField;
	private JTextField lastnameTextField;
	private JTextField emailTextField;
	private JTextField organizationTextField;
	private JTextField phonenumberTextField;
	private JPasswordField passwordTextField;
	private JPasswordField confirmPasswordTextField;
	
	private JButton registerButton;
	
	private GridLayout gLayout;
	private GridBagLayout gbLayout;
	
	public RegisterWindow(String username) {
		initComponents(username);
	}
	
	private void initComponents(String username){
		
		this.setTitle("Create a new account");
		this.setSize(400, 600);
		
		gLayout = new GridLayout(0 , 1);
		gbLayout = new GridBagLayout();
		this.setLayout(gbLayout);
		
		// Center the frame in the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		registerPanel = new JPanel();
		registerPanel.setLayout(gLayout);
		
		initLabels();
		initFields(username);
		
		registerButton = new JButton("Register");
		registerButton.addActionListener(new RegisterButtonPress());
		
		registerPanel.add(usernameLabel);
		registerPanel.add(usernameTextField);
		registerPanel.add(firstnameLabel);
		registerPanel.add(firstnameTextField);
		registerPanel.add(lastnameLabel);
		registerPanel.add(lastnameTextField);
		registerPanel.add(emailLabel);
		registerPanel.add(emailTextField);
		registerPanel.add(organizationLabel);
		registerPanel.add(organizationTextField);
		registerPanel.add(phonenumberLabel);
		registerPanel.add(phonenumberTextField);
		registerPanel.add(passwordLabel);
		registerPanel.add(passwordTextField);
		registerPanel.add(confirmPasswordLabel);
		registerPanel.add(confirmPasswordTextField);
		registerPanel.add(registerButton);
		registerPanel.add(msgLabel);
		
		this.add(registerPanel);
		this.setVisible(true);
	}
	
	private void initLabels() {
		usernameLabel = new JLabel("Username");
		firstnameLabel = new JLabel("First Name");
		lastnameLabel = new JLabel("Last Name");
		emailLabel = new JLabel("Email");
		organizationLabel = new JLabel("Organization");
		phonenumberLabel = new JLabel("Phone Number");
		passwordLabel = new JLabel("Password");
		confirmPasswordLabel = new JLabel("Confirm Password");
		msgLabel = new JLabel();
	}
	
	private void initFields(String username) {
		usernameTextField = new JTextField(username);
		firstnameTextField = new JTextField();
		lastnameTextField = new JTextField();
		emailTextField = new JTextField();
		organizationTextField = new JTextField();
		phonenumberTextField = new JTextField();
		passwordTextField = new JPasswordField();
		confirmPasswordTextField = new JPasswordField();
	}
	
	private class RegisterButtonPress implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			if(confirmPassword()) {
				Registrar reg = new Registrar();
				try {
				reg.registerUser(usernameTextField.getText(), firstnameTextField.getText(),
						lastnameTextField.getText(), emailTextField.getText(), 
						organizationTextField.getText(), phonenumberTextField.getText(), 
						String.valueOf(passwordTextField.getPassword()));
				} catch(Exception ex) {
					System.out.println(ex);
				}
			}
			else {
				msgLabel.setText("Passwords do not match");
				passwordTextField.setText("");
				confirmPasswordTextField.setText("");
			}
		}

		private boolean confirmPassword() {
			// TODO Auto-generated method stub
			return Arrays.equals(passwordTextField.getPassword(), confirmPasswordTextField.getPassword());
		}
	}
}
