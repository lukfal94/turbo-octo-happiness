package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import managers.SessionManager;
import util.Activity;
import util.Task;
import util.Task.TaskPriority;
import databaseComm.Registrar;
import databaseComm.ServerResponse;

public class ActivityPanel extends JPanel{
	SessionManager sessionManager;
	
	JLabel testLabel;
	JButton createActivityButton;
	
	public ActivityPanel(SessionManager sm) {
		testLabel = new JLabel("Activity Panel");
		this.sessionManager = sm;
		
		createActivityButton = new JButton("New");
		createActivityButton.addActionListener(new ActivityWindowButtonClick());
		
		this.add(testLabel);
		this.add(createActivityButton);
		this.setBackground(Color.GREEN);
	}
	
	public enum ActivityWindowMode {
		NEW_ACTIVITY, EDIT_ACTIVITY
	}

	private class ActivityWindowButtonClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(createActivityButton))
				openActivityWindow(ActivityWindowMode.NEW_ACTIVITY);
//			else if(e.getSource().equals(editTaskButton))
//				openTaskWindow(TaskWindowMode.EDIT_TASK);
		}
	}
	
	public void openActivityWindow(ActivityWindowMode mode) {
		ActivityWindow aw = new ActivityWindow(mode);
	}

	private class ActivityWindow extends JFrame {
		private JPanel activityWindowPanel;
		private ActivityWindowMode currMode;
		
		// Common Labels
		private JLabel selectTaskLabel;
		private JLabel timeSpentLabel;
		private JLabel descriptionLabel;
		
		// Input Elements
		private JComboBox taskComboBox;
		private JTextArea descriptionTextArea;
		
		// Action Buttons
		private JButton submitButton;
		private JButton cancelButton;
		
		// Layout members
		private BorderLayout borderLayout = new BorderLayout();
		private GridBagLayout gbLayout = new GridBagLayout();
		private GridBagConstraints gbC = new GridBagConstraints();
		
		public ActivityWindow(ActivityWindowMode mode) {
			this.currMode = mode;
			this.setLayout(borderLayout);
			
			// Init Common Elements
			initCommon();
			
			// Init Elements Unique to Window Mode
			switch(mode) {
				case NEW_ACTIVITY:
					initNewTaskWindow();
					break;
				case EDIT_ACTIVITY:
					break;
			}
			
			this.setSize(500, 400);
			
			// Center the frame in the screen
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

			this.setVisible(true);
		}

		private void initCommon() {
			activityWindowPanel = new JPanel();
			activityWindowPanel.setBackground(Color.GRAY);;
			
			// Init layout tools
			gbLayout = new GridBagLayout();
			gbC = new GridBagConstraints();
			
			gbC.fill = GridBagConstraints.HORIZONTAL;
			
			activityWindowPanel.setLayout(gbLayout);
			
			// Initialize field label
			selectTaskLabel = new JLabel("Select a Task", SwingConstants.RIGHT);
			timeSpentLabel =	new JLabel("Time Spend", SwingConstants.RIGHT);
			descriptionLabel = new JLabel("Description", SwingConstants.RIGHT);
			
			
			// Init input fields
			descriptionTextArea = new JTextArea(3, 30);
			taskComboBox = new JComboBox<Task>();
			for(Task t : sessionManager.getActiveUser().getAssignedTasks()) {
				taskComboBox.addItem(t);
			}
			
			submitButton = new JButton("Submit");
			submitButton.addActionListener(new SubmitButtonPress());
			
			descriptionTextArea.setLineWrap(true);
			descriptionTextArea.setWrapStyleWord(true);
			
			
			/*
			 * How we'll parse date
			String s = "03/24/2013 21:54";
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			
	        try {
	        	Date exDate = simpleDateFormat.parse(s);
		        System.out.println(exDate);
	        } catch(Exception ex) {
	        	System.out.println("Ooops");
	        }
	        */
	        
			this.addComponent(0, 0, 3, 1, gbC, activityWindowPanel, selectTaskLabel);
			this.addComponent(3, 0, 5, 1, gbC, activityWindowPanel, taskComboBox);
			this.addComponent(0, 1, 3, 1, gbC, activityWindowPanel, descriptionLabel);
			this.addComponent(3, 1, 6, 2, gbC, activityWindowPanel, descriptionTextArea);
			this.addComponent(2, 2, 3, 1, gbC, activityWindowPanel, submitButton);
			
			this.add(activityWindowPanel, BorderLayout.CENTER);
		}
		
		// Initializes the components for the "New Activity" window
		private void initNewTaskWindow() {

			this.setTitle("Create a New Activity");
		 
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
		
		private class SubmitButtonPress implements ActionListener {
			private Registrar registrar = new Registrar();
			private SessionManager sm;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sm = ActivityPanel.this.sessionManager;
				Object response = null;

				// Send the new activity to the DB.
				try {
					response = registrar.addNewActivity();
				} catch(Exception ex) {
					System.out.println(ex);
				}
				
				if(response.getClass().equals(ServerResponse.class)) {
					ServerResponse servResponse = (ServerResponse)response;
					// TODO Error handling
				} else if(response.getClass().equals(Activity.class)) {
					Activity newActivity = (Activity)response;
					sm.getActiveGroup().getActivityManager().addActivity(newActivity);
				}
	
			}
		}
	}
}
