package userInterface;

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

import managers.SessionManager;
import social.Group;
import social.User;
import userInterface.GroopMainInterface.GuiMode;
import userInterface.InviteUserWindow.CloseWindowAction;
import userInterface.InviteUserWindow.InviteButtonAction;
import databaseComm.ServerResponse;
import databaseComm.ServerResponse.ServerErrorMessage;

public class JoinGroupWindow extends JFrame {
	
	// For now, automatically add users to group. No confirmation on user end.
	private SessionManager sm;
	
	private JPanel containerPanel;
	private JLabel enterNameLabel;
	private JLabel msgLabel;
	
	private JTextField nameTextField;
	
	private JButton addUserButton;
	private JButton cancelButton;
	
	public JoinGroupWindow(SessionManager sm) {
		this.sm = sm;
		
		
		this.setTitle("Join a Group");
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
		
		enterNameLabel = new JLabel("Group Name:");
		nameTextField = new JTextField(15);
		
		msgLabel = new JLabel("");
		msgLabel.setPreferredSize(new Dimension(250, 25));
		msgLabel.setVisible(false);
		msgLabel.setForeground(Color.RED);
		
		addUserButton = new JButton("Join");
		addUserButton.addActionListener(new JoinButtonAction(sm.getActiveUser()));
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new CloseWindowAction());
		
		containerPanel.add(enterNameLabel);
		containerPanel.add(nameTextField);
		containerPanel.add(msgLabel);
		containerPanel.add(addUserButton);
		containerPanel.add(cancelButton);
		
		this.add(containerPanel);
	}
	
	public class JoinButtonAction implements ActionListener {
		private User activeUser;
		
		public JoinButtonAction(User au) {
			this.activeUser = au;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String msgText = "Joining " + nameTextField.getText() +"...";
			msgLabel.setText(msgText);
			msgLabel.setVisible(true);
			JoinGroupWindow.this.setSize(300, 125);
			
			Object response = null;
			
			try {
				response = sm.getGroupManager().addUser(activeUser, nameTextField.getText());
			} catch (Exception ex) {
				System.out.println(ex);
				msgText = "Error joining " + nameTextField.getText() + ". Try again later.";
				msgLabel.setText(msgText);
			}
			
			nameTextField.setText("");
			
			if(response != null) {
				// The addition was successful
				if(response.getClass().equals(ServerResponse.class)) {
					ServerResponse servResponse = (ServerResponse) response;
					if(servResponse.getServerErrorMessage().equals(ServerErrorMessage.GROUP_NOT_FOUND)) {
						System.out.println("Group not found!");
						msgText = "Sorry, that group was not found. Try again.";
					} else if(servResponse.getServerErrorMessage().equals(ServerErrorMessage.USER_IN_GROUP)) {
						msgText = "You're already in this group.";
					} else if(servResponse.getServerErrorMessage().equals(ServerErrorMessage.NO_ERROR)) {
						msgText = "Success!";
						try {
							sm.getGroupManager().syncGroups();
						} catch( Exception ex) {
							System.out.println("Could not sync groups");
						}
						sm.setActiveGroup(sm.getGroups().get(sm.getGroups().size() - 1));
						sm.refreshSession();
						sm.getMainGUI().refreshInterface();
					}
				}
				msgLabel.setText(msgText);
				JoinGroupWindow.this.dispose();
			}
		}
	}
	
	public class CloseWindowAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JoinGroupWindow.this.dispose();
		}
	}
}
