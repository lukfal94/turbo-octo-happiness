package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import com.fasterxml.jackson.core.JsonParseException;

import social.User;
import databaseComm.Registrar;
import databaseComm.ServerResponse;
import databaseComm.ServerResponse.ServerErrorMessage;

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
	
	private Color backgroundColor;
	
	private String logoPath = "/Users/lukefallon/Desktop/turbo-octo-happiness/Groop/img/groop_logo.png";
	private ImageIcon logoImage;
	private JLabel logoLabel;
	
	public LoginWindow() {
		initComponents();
	}
	
	private void initComponents() {
		registrar = new Registrar();
		backgroundColor = new Color(129, 205, 234);
		
		this.setTitle("Login or Sign Up");
		this.setSize(300, 400);
		this.setBackground(backgroundColor);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		// Center the frame in the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		gbLayout = new GridBagLayout();
		gLayout = new GridLayout(0, 1);
		c = new GridBagConstraints();
		
		
		
		loginPanel = new JPanel();
		loginPanel.setLayout(gLayout);
		loginPanel.setMinimumSize(new Dimension(300, 400));
		loginPanel.setBackground(backgroundColor);
		
		msgLabel = new JLabel();
		msgLabel.setForeground(Color.RED);
		
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
		
		logoImage = resizeImage(new ImageIcon(logoPath), 300, 200);
		System.out.println(logoImage.toString());
		logoLabel = new JLabel(logoImage);
		System.out.println(logoLabel.getBackground());
		logoLabel.setBackground(backgroundColor);
		System.out.println(logoLabel.getBackground());
		
		
		loginPanel.add(usernameLabel);
		loginPanel.add(usernameTextField);
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordTextField);
		loginPanel.add(loginButton);
		loginPanel.add(msgLabel);
		
		this.add(logoLabel, BorderLayout.NORTH);
		this.add(loginPanel);
		
		//this.getRootPane().setDefaultButton(loginButton);
		
		this.setVisible(true);
	}
	
    // Resizes an image
    private ImageIcon resizeImage(ImageIcon icon, int x, int y)
    {
        // TODO: Resize an image to a desired dimension.
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(x, y, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);
        return icon;
    }
    
	private class LoginButtonPress implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object registrarResponse = null;
			ServerResponse serverResponse= null;
			
			if(usernameTextField.getText().equals("")) {
				msgLabel.setText("Please enter a username");
				return;
			}
			try {
				registrarResponse = registrar.login(usernameTextField.getText(), String.valueOf(passwordTextField.getPassword()));
			} catch(Exception ex) {
				
				System.out.println(">>" + ex);
				return;
			}
			
			// Registrar.login() returns either a User.class or ServerResponse.class
			//
			if(registrarResponse.getClass().equals(ServerResponse.class)) {
				serverResponse = (ServerResponse) registrarResponse;
				
				// Username or Password is incorrect, reset password field
				if(serverResponse.getServerErrorMessage() == ServerErrorMessage.INCORRECT_PASSWORD) {
					msgLabel.setText("Incorrect Username or Password");
					passwordTextField.setText("");
					return;
				} 
				// Could not connect to database
				else if (serverResponse.getServerErrorMessage() == ServerErrorMessage.DATABASE_CONN) {
					msgLabel.setText("Failed to connect to server");
					return;
				} 
				// Username does not exist in database, launch a window to register.
				else if (serverResponse.getServerErrorMessage() == ServerErrorMessage.USER_NOT_FOUND) {
					RegisterWindow registerWindow = new RegisterWindow(usernameTextField.getText(), LoginWindow.this);
				}
			}
			else if(registrarResponse.getClass().equals(User.class)) {
				GroopMainInterface mainGUI = new GroopMainInterface((User) registrarResponse);
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
