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
import javax.swing.SwingConstants;

import databaseComm.ServerResponse;
import social.Group;
import userInterface.GroopMainInterface.GuiMode;
import managers.SessionManager;

public class GroupInfoPanel extends JPanel {
	private SessionManager sessionManager;

	private JLabel groupName;
	private JTextArea groupDescription;
	
	// Layout
	private GridBagLayout gbLayout;
	private GridBagConstraints gbC;
	
	public enum GroupWindowMode {
		NEW_GROUP
	}
	
	public GroupInfoPanel(SessionManager sm) {
		this.sessionManager = sm;
		this.setLayout(null);
		
		if(sessionManager.getGuiMode() == GuiMode.STANDARD)
			initComponents();
		else 
			initBlankComponents();
		
		this.setBackground(UIVisual.guiColor);
	}
	
	private void initBlankComponents() {
		// TODO Auto-generated method stub
		groupName = new JLabel("<html><p style='text-align: cetner;'>"+"</p></html>");
		groupName.setHorizontalAlignment(SwingConstants.CENTER);
		groupName.setFont(UIVisual.titleFont);
		
		groupDescription = new JTextArea();
		groupDescription.setEditable(false);
		groupDescription.setLineWrap(true);
		groupDescription.setWrapStyleWord(true);
		groupDescription.setBackground(UIVisual.guiColor);
		
		groupName.setBounds(125, 10, 250, 30);
		groupDescription.setBounds(50, 45, 400, 150);
		
		this.add(groupName);
		this.add(groupDescription);
	}

	private void initComponents() {
		groupName = new JLabel("<html><p style='text-align: cetner;'>"+sessionManager.getActiveGroup().getName()+"</p></html>");
		groupName.setHorizontalAlignment(SwingConstants.CENTER);
		groupName.setFont(UIVisual.titleFont);
		
		groupDescription = new JTextArea(sessionManager.getActiveGroup().getDescription());
		groupDescription.setEditable(false);
		groupDescription.setLineWrap(true);
		groupDescription.setWrapStyleWord(true);
		groupDescription.setBackground(UIVisual.guiColor);
		
		groupName.setBounds(125, 10, 250, 30);
		groupDescription.setBounds(50, 45, 400, 150);
		
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
			
			this.setSize(400, 400);
			
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
			
			windowPanel.setLayout(null);
			
			nameLabel = new JLabel("Name");
			descriptionLabel= new JLabel("Description");
			
			nameTextField = new JTextField(20);
			descriptionTextArea = new JTextArea(3, 40);
			descriptionTextArea.setLineWrap(true);
			descriptionTextArea.setWrapStyleWord(true);
			
			createButton = new JButton("Create");
			createButton.addActionListener(new CreateButtonPress());
			
			nameLabel.setBounds(15, 15, 100, 20);
			nameTextField.setBounds(120, 15, 200, 20);
			descriptionLabel.setBounds(15, 40, 100, 20);
			descriptionTextArea.setBounds(120, 65, 200, 150);
			createButton.setBounds(160, 275, 80, 30);
			
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
					sessionManager.getMainGUI().refreshInterface();
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

