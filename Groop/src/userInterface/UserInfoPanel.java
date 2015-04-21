package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import managers.SessionManager;

public class UserInfoPanel extends JPanel {	
	private SessionManager sessionManager;

	private JLabel fullname;
	private JLabel username;
	private JLabel email;
	private JLabel organization;
	private JLabel phone;
	
	// Layout
	private GridBagLayout gbLayout;
	private GridBagConstraints gbConstraints;
	
	
	public UserInfoPanel(SessionManager sm) {
		this.sessionManager = sm;
		initComponents();
		
		this.setBackground(Color.ORANGE);
	}
	
	private void initComponents() 
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		fullname = new JLabel(sessionManager.getActiveUser().getFullname());
		fullname.setFont(new Font("Sans-Serif", Font.BOLD, 24));
		
		username = new JLabel(sessionManager.getActiveUser().getUsername());
		username.setFont(new Font("Sans-Serif", Font.ITALIC, 18));
		
		organization = new JLabel(sessionManager.getActiveUser().getOrganization());
		organization.setFont(new Font("Sans-Serif", Font.ITALIC, 18));
		
		email = new JLabel(sessionManager.getActiveUser().getEmail());
		email.setFont(new Font("Sans-Serif", Font.ITALIC, 18));
		
		phone = new JLabel(sessionManager.getActiveUser().getPhone());
		phone.setFont(new Font("Sans-Serif", Font.ITALIC, 18));
		
		this.add(fullname);
		this.add(username);
		this.add(email);
		this.add(organization);
		
	}
}
