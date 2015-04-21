package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import managers.SessionManager;
import social.User;
import util.Activity;
import util.Task;
import util.Task.TaskPriority;
import databaseComm.Registrar;
import databaseComm.ServerResponse;

public class ActivityPanel extends JPanel{
	SessionManager sessionManager;
	
	private JButton createActivityButton;
	private JButton editActivityButton;
	private JList<String> activityList;
	private JScrollPane activityListView;
	private DefaultListModel<String> listModel;
	private JTextArea summaryTextArea;
	private JLabel activityTitle;
	
	public ActivityPanel(SessionManager sm) {
		//this.setSize(400, 350);
		this.setLayout(null);
		this.setBackground(UIVisual.guiColor);
		
		this.sessionManager = sm;
		
		activityTitle = new JLabel("<html><h1 style='text-align: center;'>Activity</h1></html>");
		activityTitle.setFont(UIVisual.titleFont);
		activityTitle.setSize(new Dimension(400, 50));
		activityTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		createActivityButton = new JButton("New");
		createActivityButton.addActionListener(new ActivityWindowButtonClick());	
		
		editActivityButton = new JButton("Edit");
		editActivityButton.addActionListener(new ActivityWindowButtonClick());
		
		summaryTextArea = new JTextArea("");
		summaryTextArea.setEditable(false);
		
		// store list of activites in a JList to be displayed, but because we need to edit, they must be stored in a listmodel
		listModel = new DefaultListModel<String>();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		
		// iterate and add each activity to list
		if (sessionManager.getActiveGroup() != null) {
			for (Activity a : sessionManager.getActiveGroup().getActivityManager().getActivityLog()) {
				
				listModel.addElement(a.getTask().getTitle() + "\n" + a.getUser().getUsername() + " - " + dateFormat.format(a.getTimeStamp()));
			}
		}
		
		activityList = new JList<String>(listModel);
		activityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		activityList.addListSelectionListener(new ActivityListSelect());
		
		activityListView = new JScrollPane(activityList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		activityTitle.setBounds(0, 0, 400, 50);		
		activityListView.setBounds(10, 70, 180, 250);
		summaryTextArea.setBounds(200, 70, 170, 210);
		createActivityButton.setBounds(200, 290, 80, 30);
		editActivityButton.setBounds(290, 290, 80, 30);
		
		this.add(activityTitle);
		this.add(activityListView);
		this.add(summaryTextArea);
		this.add(createActivityButton);
		this.add(editActivityButton);
		//this.setBackground(Color.GREEN);
		// i'm assuming we don't want to keep it bright green
	}
	
	public enum ActivityWindowMode {
		NEW_ACTIVITY, EDIT_ACTIVITY
	}
	
	private class ActivityListSelect implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if (activityList.getSelectedIndex() != -1) {
				Activity act = sessionManager.getActiveGroup().getActivityManager().getActivityLog().get(activityList.getSelectedIndex());
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	
				summaryTextArea.setText(act.getTask().getTitle() + "\n" + dateFormat.format(act.getTimeStamp()) + " - " + act.getUser().getUsername() + "\n" + Integer.toString(act.getTimeSpend()) + " hours\n" + act.getDescription());
				
			}
			else
				summaryTextArea.setText("");
			
		}
		
	}

	private class ActivityWindowButtonClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// anyone can create an activity
			if(e.getSource().equals(createActivityButton))
				//openActivityWindow(ActivityWindowMode.NEW_ACTIVITY);
				System.out.printf("size is %d %d\n", ((JButton) e.getSource()).getWidth(), ((JButton) e.getSource()).getHeight());
				
			// edit a selected activity
			else if(e.getSource().equals(editActivityButton)) {
				
				// no selection - don't do anything except warn the user
				if (activityList.getSelectedIndex() == -1)
					JOptionPane.showMessageDialog(null, "Please select an activity to edit.", "No Selection", JOptionPane.INFORMATION_MESSAGE);
				
				// selected for editing
				else {
							
					// if user is admin or is the performer of the activity, editing is allowed (this is a horrendous line, i know)
					if (sessionManager.getActiveGroup().getAdmins().contains(sessionManager.getActiveUser()) || sessionManager.getActiveGroup().getActivityManager().getActivityLog().get(activityList.getSelectedIndex()).getUser().equals(sessionManager.getActiveUser()))
						openActivityWindow(ActivityWindowMode.EDIT_ACTIVITY);
						
					// user attempts to edit an activity that isn't his or hers
					else
						JOptionPane.showMessageDialog(null, "You do not have permission to edit that activity.", "Forbidden", JOptionPane.INFORMATION_MESSAGE);
				}
			}
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
		private JComboBox<Task> taskComboBox;
		private JTextArea descriptionTextArea;
		private JSpinner timeSpentSpinner;
		private JCheckBox verifyCheckBox;
		
		// Action Buttons
		private JButton submitButton;
		private JButton cancelButton;
		
		private int activityInd;
		
		// Layout members
		private BorderLayout borderLayout = new BorderLayout();
		private GridBagLayout gbLayout = new GridBagLayout();
		private GridBagConstraints gbC = new GridBagConstraints();
		
		public ActivityWindow(ActivityWindowMode mode) {
			this.currMode = mode;
			this.setLayout(borderLayout);
			
			activityInd = activityList.getSelectedIndex();
			
			// Init Common Elements
			initCommon();
			
			// Init Elements Unique to Window Mode
			switch(mode) {
				case NEW_ACTIVITY:
					initNewActivityWindow();
					break;
				case EDIT_ACTIVITY:
					initEditActivityWindow();
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
			timeSpentLabel = new JLabel("Time Spend", SwingConstants.RIGHT);
			descriptionLabel = new JLabel("Description", SwingConstants.RIGHT);
			verifyCheckBox = new JCheckBox("Verified");
			
			
			// Init input fields
			descriptionTextArea = new JTextArea(3, 30);
			taskComboBox = new JComboBox<Task>();
			for(Task t : sessionManager.getActiveUser().getAssignedTasks()) {
				taskComboBox.addItem(t);
			}
			timeSpentSpinner = new JSpinner();
			
			submitButton = new JButton("Submit");
			submitButton.addActionListener(new SubmitButtonPress());
			
			cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new CancelButtonPress());
			
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
			this.addComponent(0, 2, 3, 1, gbC, activityWindowPanel, timeSpentLabel);
			this.addComponent(3, 2, 5, 1, gbC, activityWindowPanel, timeSpentSpinner);
			this.addComponent(0, 4, 2, 1, gbC, activityWindowPanel, cancelButton);
			this.addComponent(2, 4, 3, 1, gbC, activityWindowPanel, submitButton);
			
			// admins have access to verify checkbox
			if (sessionManager.getActiveGroup().getAdmins().contains(sessionManager.getActiveUser()))
				this.addComponent(0, 3, 3, 1, gbC, activityWindowPanel, verifyCheckBox);
			
			this.add(activityWindowPanel, BorderLayout.CENTER);
		}
		
		// Initializes the components for the "New Activity" window
		private void initNewActivityWindow() {

			this.setTitle("Create a New Activity");
		 
		}
		
		private void initEditActivityWindow() {
			
			this.setTitle("Edit an Activity");
			
			// when editing, initialize all fields to the current values
			Activity act = sessionManager.getActiveGroup().getActivityManager().getActivityLog().get(activityList.getSelectedIndex());
			
			taskComboBox.setSelectedItem(act.getTask());
			descriptionTextArea.setText(act.getDescription());
			timeSpentSpinner.setValue(act.getTimeSpend());
			verifyCheckBox.setSelected(act.isVerified());
			
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
				if (taskComboBox.getSelectedIndex() == -1)
					JOptionPane.showMessageDialog(null, "Please select a task.", "Task Not Selected", JOptionPane.INFORMATION_MESSAGE);
					
				else if (descriptionTextArea.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please give a description.", "No Description", JOptionPane.INFORMATION_MESSAGE);
				}
				
				else if ((int) timeSpentSpinner.getValue() < 1) {
					JOptionPane.showMessageDialog(null, "Please give an amount of time.", "No Time Spent", JOptionPane.INFORMATION_MESSAGE);
				}
				
				else {

					// Send the new activity to the DB.
					try {
						if (currMode == ActivityWindowMode.NEW_ACTIVITY)
							response = registrar.addNewActivity();
						// else
							// TODO: add editActivity function to registrar
							// response = registrar.editActivity();
					} catch(Exception ex) {
						System.out.println(ex);
					}
					
					if(response.getClass().equals(ServerResponse.class)) {
						ServerResponse servResponse = (ServerResponse)response;
						// TODO Error handling
					} else if(response.getClass().equals(Activity.class)) {
						
						// activity was created
						if (currMode == ActivityWindowMode.NEW_ACTIVITY) {
							Activity newActivity = (Activity) response;
							newActivity.setTimeStamp(new Date());
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
							sm.getActiveGroup().getActivityManager().addActivity(newActivity);
							listModel.addElement(newActivity.getTask().getTitle() + "\n" + newActivity.getUser().getUsername() + " - " + dateFormat.format(newActivity.getTimeStamp()));
					
						}
						
						// activity was edited
						else {
							Activity editActivity = (Activity) response;
							editActivity.setTimeStamp(new Date());
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
							sm.getActiveGroup().getActivityManager().getActivityLog().set(activityInd, editActivity);
							listModel.set(activityInd, editActivity.getTask().getTitle() + "\n" + editActivity.getUser().getUsername() + " - " + dateFormat.format(editActivity.getTimeStamp()));
						}
					}
				}
			}
		}
		
		// cancel button should close the submission form
		private class CancelButtonPress implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				close();
			}
		}
		
		private void close() {
			this.dispose();
		}
	}
}
