package userInterface;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import managers.SessionManager;

public class UserInfoPanel extends JPanel {	
	private SessionManager sessionManager;

	private JLabel fullnameLabel;
	private JLabel usernameLabel;
	private JLabel emailLabel;
	
	public UserInfoPanel(SessionManager sm) {
		this.sessionManager = sm;
		initComponents();
		
		this.setBackground(UIVisual.guiColor);
	}

	private void initComponents() {
		this.setLayout(null);
		
		fullnameLabel = new JLabel(sessionManager.getActiveUser().getFullname());
		fullnameLabel.setFont(UIVisual.titleFont);
		
		usernameLabel = new JLabel(sessionManager.getActiveUser().getUsername());
		usernameLabel.setFont(new Font("Sans-Serif", Font.ITALIC, 16));
		
		emailLabel = new JLabel(sessionManager.getActiveUser().getEmail());
		emailLabel.setFont(new Font("Sans-Sefif", Font.PLAIN, 16));
		
		fullnameLabel.setBounds(10, 10, 200, 25);
		usernameLabel.setBounds(10, 45, 200, 20);
		emailLabel.setBounds(10, 65, 200, 20);

		this.add(fullnameLabel);
		this.add(usernameLabel);
		this.add(emailLabel);
	}
}
