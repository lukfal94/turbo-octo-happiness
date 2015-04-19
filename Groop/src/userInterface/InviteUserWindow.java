package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import databaseComm.ServerResponse;
import databaseComm.ServerResponse.ServerErrorMessage;
import social.Group;
import social.User;
import managers.SessionManager;

public class InviteUserWindow extends JFrame {
	
	// For now, automatically add users to group. No confirmation on user end.
	private SessionManager sm;
	
	private JPanel containerPanel;
	private JLabel enterNameLabel;
	private JLabel msgLabel;
	
	private JTextField nameTextField;
	
	private JButton addUserButton;
	private JButton cancelButton;
	
	public InviteUserWindow(SessionManager sm) {
		this.sm = sm;
		
		
		this.setTitle("Add a New Member");
		this.setSize(300, 100);
		
		// Center the frame in the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		initComponents();
		
		this.setVisible(true);
	}

	private void initComponents() {
		// TODO Auto-generated method stub
		containerPanel = new JPanel();
		
		enterNameLabel = new JLabel("Username:");
		nameTextField = new JTextField(15);
		
		msgLabel = new JLabel("");
		msgLabel.setPreferredSize(new Dimension(250, 25));
		msgLabel.setVisible(false);
		msgLabel.setForeground(Color.RED);
		
		addUserButton = new JButton("Add User");
		addUserButton.addActionListener(new InviteButtonAction(sm.getActiveGroup()));
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new CloseWindowAction());
		
		containerPanel.add(enterNameLabel);
		containerPanel.add(nameTextField);
		containerPanel.add(msgLabel);
		containerPanel.add(addUserButton);
		containerPanel.add(cancelButton);
		
		this.add(containerPanel);
	}
	
	public class InviteButtonAction implements ActionListener {
		private Group activeGroup;
		
		public InviteButtonAction(Group ag) {
			this.activeGroup = ag;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String msgText = "Adding user " + nameTextField.getText() + " to " + activeGroup.getName();
			msgLabel.setText(msgText);
			msgLabel.setVisible(true);
			InviteUserWindow.this.setSize(300, 125);
			
			Object response = null;
			
			try {
				response = activeGroup.addUser(nameTextField.getText());
			} catch (Exception ex) {
				System.out.println(ex);
				msgText = "Error adding " + nameTextField.getText() + ". Try again later.";
				msgLabel.setText(msgText);
			}
			
			nameTextField.setText("");
			
			if(response != null) {
				// The addition was successful
				if(response.getClass().equals(User.class)) {
					activeGroup.getMembers().add((User) response);
					msgText = "Successfully added " + ((User)response).getUsername();
				}
				// The addition was not successful, check error code
				else if(response.getClass().equals(ServerResponse.class)) {
					ServerResponse servResponse = (ServerResponse) response;
					if(servResponse.getServerErrorMessage().equals(ServerErrorMessage.USER_NOT_FOUND)) {
						msgText = "Sorry, that user was not found. Try again.";
					} else if(servResponse.getServerErrorMessage().equals(ServerErrorMessage.USER_IN_GROUP)) {
						msgText = "That user is already in this group.";
					}
				}
				msgLabel.setText(msgText);
			}
			
		}

	}
	
	public class CloseWindowAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			InviteUserWindow.this.dispose();
		}

	}
}
