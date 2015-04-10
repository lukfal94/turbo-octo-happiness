package userInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import managers.SessionManager;

public class GroupInfoPanel extends JPanel {
	private SessionManager sessionManager;

	private JLabel groupName;
	private JLabel groupDescription;
	
	// Layout
	private GridBagLayout gbLayout;
	private GridBagConstraints gbConstraints;
	
	public GroupInfoPanel(SessionManager sm) {
		this.sessionManager = sm;
		
		initComponents();
		
		this.setBackground(Color.BLUE);
	}
	
	private void initComponents() {
		groupName = new JLabel(sessionManager.getActiveGroup().getName());
		groupName.setFont(new Font("Serif", Font.PLAIN, 24));
		
		groupDescription = new JLabel(sessionManager.getActiveGroup().getDescription());
		
		this.add(groupName);
		this.add(groupDescription);
	}
}

