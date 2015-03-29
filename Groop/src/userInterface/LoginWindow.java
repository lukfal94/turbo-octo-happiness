package userInterface;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import com.fasterxml.jackson.core.JsonParseException;

import social.User;
import databaseComm.Registrar;

//
//	This is the user interface for the Login/Register Sequence
//
public class LoginWindow extends JFrame{
	private Registrar registrar;
	
	private JPanel loginPanel;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JLabel msgLabel;
	private JTextField usernameTextField;
	private JPasswordField passwordTextField;
	private JButton loginButton;
	
	private GridBagLayout gbLayout;
	private GridLayout gLayout;
	private GridBagConstraints c;
	
	public LoginWindow() {
		initComponents();
	}
	
	private void initComponents() {
		registrar = new Registrar();
		
		this.setTitle("Login or Sign Up");
		
		this.setSize(300, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Center the frame in the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		gbLayout = new GridBagLayout();
		gLayout = new GridLayout(0, 1);
		c = new GridBagConstraints();
		
		this.setLayout(gbLayout);
		
		loginPanel = new JPanel();
		loginPanel.setLayout(gLayout);
		
		msgLabel = new JLabel();
		
		usernameLabel = new JLabel("Username");
		passwordLabel = new JLabel("Password");
		usernameTextField = new JTextField();
		usernameTextField.setPreferredSize(new Dimension(100, 20));
		
		passwordTextField = new JPasswordField(20);
		passwordTextField.setPreferredSize(new Dimension(100, 20));
		
		loginButton = new JButton("Login/Register");
		loginButton.addActionListener(new LoginButtonPress());
		
// The commented code uses GridBagLayout
//		this.addComponent(0, 0, 2, 1, c, this, usernameLabel);
//		this.addComponent(0, 1, 2, 1, c, this, usernameTextField);
//		this.addComponent(0, 3, 2, 1, c, this, passwordLabel);
//		this.addComponent(0, 4, 2, 1, c, this, passwordTextField);
//		this.addComponent(0, 5, 2, 1, c, this, loginButton);
		
		loginPanel.add(usernameLabel);
		loginPanel.add(usernameTextField);
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordTextField);
		loginPanel.add(loginButton);
		loginPanel.add(msgLabel);
		
		this.add(loginPanel);
		
		this.setVisible(true);
	}
	
	private class LoginButtonPress implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			User currUser = null;
			try{
				currUser = registrar.login(usernameTextField.getText(), passwordTextField.getPassword().toString());
			} catch(JsonParseException jpEx) {
				// If there's a parse error, this is not the correct password
				System.out.println("Incorrect Username or Password");
				msgLabel.setText("Incorrect Username or Password");
				passwordTextField.setText("");
				return;
			} catch(IOException ioEx) {
				
			}
			if(currUser == null) {
				RegisterWindow registerWindow = new RegisterWindow(usernameTextField.getText());
			} else {
				GroopMainInterface mainGUI = new GroopMainInterface(currUser);
				LoginWindow.this.dispose();
			}
		}
	}
	
//	private void addComponent(int x, int y, int w, int h, GridBagConstraints c, Container aContainer, Component aComponent )  
//	{  
//	    c.gridx = x;  
//	    c.gridy = y;  
//	    c.gridwidth = w;  
//	    c.gridheight = h;  
//	    gbLayout.setConstraints( aComponent, c );  
//	    aContainer.add( aComponent );  
//	} 
}
