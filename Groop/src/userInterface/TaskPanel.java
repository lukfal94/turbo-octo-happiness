package userInterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.acl.Group;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import social.User;

public class TaskPanel extends JPanel{
	private User activeUser;
	private Group activeGroup;
	
	private JButton createTaskButton;
	
	public TaskPanel() {
		initComponents();
	}
	
	public void initComponents() {
		this.setSize(400, 400);
		
		createTaskButton = new JButton("New");
		createTaskButton.setSize(100, 50);
		createTaskButton.addActionListener(new TaskWindowButtonClick());
		createTaskButton.setName("createTaskButton");
		
		this.add(createTaskButton);
	}
	public enum TaskWindowMode {
		NEW_TASK, EDIT_TASK
	}
	

	
	private class TaskWindowButtonClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(createTaskButton))
				openTaskWindow(TaskWindowMode.NEW_TASK);
//			else if(e.getSource().equals(editTaskButton))
//				openTaskWindow(TaskWindowMode.EDIT_TASK);
		}
		
		private void openTaskWindow(TaskWindowMode mode) {
			TaskWindow taskWindow = new TaskWindow(mode);
		}
	}
	public User getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(User activeUser) {
		this.activeUser = activeUser;
	}

	public Group getActiveGroup() {
		return activeGroup;
	}

	public void setActiveGroup(Group activeGroup) {
		this.activeGroup = activeGroup;
	}

	private class TaskWindow extends JFrame {
		private JPanel taskWindowPanel;
		private TaskWindowMode currMode;
		
		// Common Labels
		private JLabel titleLabel;
		private JLabel descriptionLabel;
		private JLabel deadlineLabel;
		private JLabel priorityLabel;
		
		private JTextField titleTextField;
		private JTextArea descriptionTextArea;
		
		// Layout members
		private GridBagLayout gbLayout = new GridBagLayout();
		private GridBagConstraints gbC = new GridBagConstraints();
		
		public TaskWindow(TaskWindowMode mode) {
			this.currMode = mode;
			
			// Init Common Elements
			initCommon();
			
			// Init Elements Unique to Window Mode
			switch(mode) {
				case NEW_TASK:
					initNewTaskWindow();
					break;
				case EDIT_TASK:
					initEditTaskWindow();
					break;
			}
			
			this.setSize(500, 400);
			
			// Center the frame in the screen
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

			this.setVisible(true);
		}

		private void initCommon() {
			taskWindowPanel = new JPanel();

			// Init layout tools
			gbLayout = new GridBagLayout();
			gbC = new GridBagConstraints();
			
			taskWindowPanel.setLayout(gbLayout);
			
			// Initialize label fields
			titleLabel = new JLabel("Title:");
			descriptionLabel = new JLabel("Description:");
			deadlineLabel = new JLabel("Deadline:");
			priorityLabel = new JLabel("Priority:");
			
			titleTextField = new JTextField(20);
			descriptionTextArea = new JTextArea(4, 30);
			descriptionTextArea.setLineWrap(true);
			
			this.addComponent(0, 0, 1, 1, gbC, taskWindowPanel, titleLabel);
			this.addComponent(1, 0, 5, 1, gbC, taskWindowPanel, titleTextField);
			this.addComponent(0, 1, 3, 1, gbC, taskWindowPanel, descriptionLabel);
			this.addComponent(3, 1, 6, 3, gbC, taskWindowPanel, descriptionTextArea);
			this.addComponent(0, 6, 3, 1, gbC, taskWindowPanel, deadlineLabel);
			this.addComponent(0, 7, 1, 1, gbC, taskWindowPanel, priorityLabel);

			this.add(taskWindowPanel);
			
			
		}
		// Initializes the components for the "New Task" window
		private void initNewTaskWindow() {

			this.setTitle("Create a New Task");
		 
		}
		private void initEditTaskWindow() {
			// TODO Auto-generated method stub

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
		
		private void addComponent(int x, int y, int w, int h, int padx, int pady, Insets inset, double weightx, double weighty,
				GridBagConstraints c, Container aContainer, Component aComponent )  
		{  
		    c.gridx = x;  
		    c.gridy = y;  
		    c.gridwidth = w;  
		    c.gridheight = h;
		    c.ipadx = padx;
		    c.ipady = pady;
		    c.insets = inset;
		    c.weightx = weightx;
		    c.weighty = weighty;
		    gbLayout.setConstraints( aComponent, c );  
		    aContainer.add( aComponent );  
		} 
	}
	
}
