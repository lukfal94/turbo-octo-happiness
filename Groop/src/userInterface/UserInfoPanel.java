package userInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import managers.SessionManager;

public class UserInfoPanel extends JPanel {	
	private SessionManager sessionManager;

	private JLabel fullname;
	private JLabel username;
	
	// Layout
	private GridBagLayout gbLayout;
	private GridBagConstraints gbConstraints;
	
	public UserInfoPanel(SessionManager sm) {
		this.sessionManager = sm;
		initComponents();
		
		this.setBackground(Color.ORANGE);
	}
	
	private void initComponents() {
		fullname = new JLabel(sessionManager.getUser().getFullname());
		fullname.setFont(new Font("Sans-Serif", Font.BOLD, 24));
		
		username = new JLabel(sessionManager.getUser().getUsername());
		username.setFont(new Font("Sans-Serif", Font.ITALIC, 18));
		
		this.add(fullname);
		this.add(username);
	}
}
