package userInterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import databaseComm.ServerResponse;
import social.Group;
import userInterface.GroopMainInterface.GuiMode;
import managers.SessionManager;

public class GroupInfoPanel extends JPanel {
	private SessionManager sessionManager;

	private JLabel groupName;
	private JLabel groupDescription;
	
	// Layout
	private GridBagLayout gbLayout;
	private GridBagConstraints gbC;
	
	public enum GroupWindowMode {
		NEW_GROUP
	}
	
	public GroupInfoPanel(SessionManager sm) {
		this.sessionManager = sm;
		
		if(sessionManager.getGuiMode() == GuiMode.STANDARD)
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
	
	public void openGroupInfoWindow(GroupWindowMode mode) {
		GroupInfoWindow gWindow = new GroupInfoWindow(mode);
	}
	
	private class GroupInfoWindow extends JFrame {
		private GroupWindowMode mode;
		
		private JPanel windowPanel;
		
		private JLabel nameLabel;
		private JLabel descriptionLabel;
		
		private JTextField nameTextField;
		private JTextArea descriptionTextArea;
		
		private JButton createButton;
		
		public GroupInfoWindow(GroupWindowMode mode) {
			this.mode = mode;
			
			this.setSize(500, 400);
			
			// Center the frame in the screen
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

			initCommonComponents();
			initNewGroupComponents();
			
			this.setVisible(true);
		}
		
		private void initCommonComponents() {
			windowPanel = new JPanel();
			
			// Init layout tools
			gbLayout = new GridBagLayout();
			gbC = new GridBagConstraints();
			
			gbC.fill = GridBagConstraints.BOTH;
			
			windowPanel.setLayout(new GridLayout());
			
			nameLabel = new JLabel("Name");
			descriptionLabel= new JLabel("Description");
			
			nameTextField = new JTextField(20);
			descriptionTextArea = new JTextArea(3, 40);
			descriptionTextArea.setLineWrap(true);
			descriptionTextArea.setWrapStyleWord(true);
			
			createButton = new JButton("Create");
			createButton.addActionListener(new CreateButtonPress());
			
			windowPanel.add(nameLabel);
			windowPanel.add(nameTextField);
			windowPanel.add(descriptionLabel);
			windowPanel.add(descriptionTextArea);
			windowPanel.add(createButton);
			
//			GroupInfoPanel.this.addComponent(0, 0, 2, 1, gbC, windowPanel, nameLabel);
//			GroupInfoPanel.this.addComponent(2, 0, 5, 1, gbC, windowPanel, nameTextField);
//			GroupInfoPanel.this.addComponent(0, 1, 3, 1, gbC, windowPanel, descriptionLabel);
//			GroupInfoPanel.this.addComponent(3, 1, 5, 2, gbC, windowPanel, descriptionTextArea);
//			GroupInfoPanel.this.addComponent(1, 3, 3, 1, gbC, windowPanel, createButton);
			
			this.add(windowPanel);
			
		}
		
		private void initNewGroupComponents() {
			
		} 
		
		private class CreateButtonPress implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object response = null;
				ServerResponse sResponse = null;
				
				try {
					response = sessionManager.getGroupManager().createGroup(sessionManager.getActiveUser(),
							nameTextField.getText(), descriptionTextArea.getText());
				} catch(Exception ex) {
					System.out.println("Error: Could not create the group");
				}
				
				if(!(response == null)) {
					sessionManager.getGroupManager().addGroup((Group) response);
					sessionManager.switchGroup((Group) response);
					sessionManager.refreshSession();
					GroupInfoWindow.this.dispose();
				}
				
			}
		}
	}
	
	private void addComponent(int x, int y, int w, int h, GridBagConstraints c, Container aContainer, Component aComponent )  
	{  
	    c.gridx = x;  
	    c.gridy = y;  
	    c.gridwidth = w;  
	    c.gridheight = h;
	    gbLayout.setConstraints( aComponent, c );  
	    aContainer.add( aComponent );  
	}

	public void refresh() {
		// TODO Auto-generated method stub
		Group activeGroup = sessionManager.getActiveGroup();
		this.groupName.setText(activeGroup.getName());
		this.groupDescription.setText(activeGroup.getDescription());
	}
}

