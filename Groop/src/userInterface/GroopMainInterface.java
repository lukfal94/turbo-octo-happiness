package userInterface;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import social.User;

public class GroopMainInterface extends JFrame{
	User activeUser;
	
	public GroopMainInterface(User user) {
		activeUser = user;
		initComponents();
	}
	
	private void initComponents() {
		this.setTitle("Groop - " + activeUser.getUsername());

		this.setSize(1200, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Center the frame in the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		
		
		this.setVisible(true);
	}
}
