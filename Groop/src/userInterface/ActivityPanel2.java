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
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import databaseComm.Registrar;
import databaseComm.ServerResponse;
import managers.SessionManager;
import social.User;
import userInterface.ActivityPanel.ActivityWindowMode;
import userInterface.GroopMainInterface.GuiMode;
import util.Activity;
import util.Task;
import util.Date;
import util.Task.TaskPriority;

public class ActivityPanel2 extends JPanel{
//	private SessionManager sessionManager;
//	
//	private JLabel titleLabel;
//	private JPanel actsScrollPanel;
//	private JScrollPane scrollPane;
//	
//	private JButton createActButton;
//	
//	// private CalendarPanel calendarPanel = new CalendarPanel();
//	// Month Names (short)
//	private String[] shortMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
//		
//	public ActivityPanel2(SessionManager sm) {
//		this.sessionManager = sm;
//		initComponents();
//	}
//	
//	public void initComponents() {
////		this.setSize(400, 400);
//		this.setLayout(null);
//		
//		actsScrollPanel = new JPanel();
//		actsScrollPanel.setLayout(null);
//		actsScrollPanel.setBackground(Color.DARK_GRAY);
//		
//		scrollPane = new JScrollPane(actsScrollPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		scrollPane.setBounds(40, 60, 319, 300);
//
//		titleLabel = new JLabel("<html><h1 style='text-align: center;'>Activities</h1></html>");
//		titleLabel.setFont(UIVisual.titleFont);
//		
//		titleLabel.setSize(new Dimension(300, 50));
//		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		titleLabel.setBounds(50, 0, 300, 50);
//		
//		createActButton = new JButton("New");
//		createActButton.addActionListener(new ActivityWindowButtonClick());
//		createActButton.setBounds(170, 365, 60, 20);
//		createActButton.setName("createActButton");
//		
//		if(sessionManager.getGuiMode() == GuiMode.STANDARD) {
//			// Populate the activities
//			int i = 0;
//			for(Activity a : sessionManager.getActiveGroup().getActivityManager().getActivityLog()) {
//				ActivityElement ae = new ActivityElement(ae);
//				ae.setBounds(0, i * 100, 300, 100);
//				
//				ae.setBackground(UIVisual.completeActivityColor);
//				
//				actsScrollPanel.add(ae);
//				i++;
//			}
//			actsScrollPanel.setPreferredSize(new Dimension(300, 100 * i));
//		}
//		
//		this.setBackground(UIVisual.guiColor);
//		
//		this.add(titleLabel);
//		this.add(scrollPane);
//		this.add(createActButton);
//	}
//	
//	public enum ActivityWindowMode {
//		NEW_ACT, EDIT_ACT
//	}
//	
//	public void refresh() {
//		// TODO Auto-generated method stub
//		this.actsScrollPanel = new JPanel();
//		
//		actsScrollPanel.setLayout(null);
//		actsScrollPanel.setBackground(Color.DARK_GRAY);
//		
//		// Populate the activities
//		int i = 0;
//		for(Activity a : sessionManager.getActiveGroup().getActivityManager().getActivityLog()) {
//			ActivityElement ae = new ActivityElement(ae);
//			ae.setBounds(0, i * 100, 300, 100);
//			
//			ae.setBackground(UIVisual.completeActivityColor);
//			
//			actsScrollPanel.add(ae);
//			i++;
//		}
//		actsScrollPanel.setPreferredSize(new Dimension(300, 100 * i));
//		
//		scrollPane.setViewportView(actsScrollPanel);
//	}
//	
//	private class ActivityWindowButtonClick implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			if(e.getSource().equals(createActButton))
//				openActivityWindow(ActivityWindowMode.NEW_ACT);
////			else if(e.getSource().equals(editTaskButton))
////				openTaskWindow(TaskWindowMode.EDIT_TASK);
//		}
//	}
//	
//	public void openTaskWindow(TaskWindowMode mode) {
//		TaskWindow taskWindow = new TaskWindow(mode);
//	}
//	
//	private class TaskElement extends JPanel {
//		private Task task;
//		
//		private JLabel titleLabel;
//		private JLabel descriptionLabel;
//		private JLabel deadlineLabel;
//		private JLabel assignedToLabel;
//		private JComboBox<String> assignedToComboBox;
//		private JButton gotItButton;
//		private JButton logActivityButton;
//		private JButton completeButton;
//		
//		private JButton editButton;
//		
//		private Border border = BorderFactory.createLineBorder(Color.BLACK);
//		
//		private GridBagLayout gbLayout;
//		private GridBagConstraints gbC;
//		
//		public TaskElement(Task t) {
//			this.task = t;
//			
//			this.setSize(new Dimension(300, 100));
//			this.setLayout(null);
//			
//			initComponents();
//			this.setBorder(border);
//			this.setBackground(Color.GRAY);
//		}
//		
//		private void initComponents() {
//			
//			titleLabel = new JLabel(task.getTitle());
//			titleLabel.setBounds(5, 5, 150, 20);
//			
//			deadlineLabel = new JLabel(task.getDeadline().getDate().asString('/'));
//			deadlineLabel.setBounds(160, 5, 150, 20);
//			
//			descriptionLabel = new JLabel(String.format("<html><p style='width: %d;'>%s</p></html>", 250, task.getDescription()));
//			descriptionLabel.setBounds(5, 25, 295, 50);	
//			
//			assignedToLabel = new JLabel("Assigned to:");
//			assignedToLabel.setBounds(5, 75, 95, 25);
//			
//			assignedToComboBox = new JComboBox<String>();
//			assignedToComboBox.setBounds(105, 75, 100, 25);
//			
//			if(task.getAssignedTo() != null) {
//				for(User u : task.getAssignedTo()) {
//					assignedToComboBox.addItem(u.getUsername());
//				}	
//			}
//
//			gotItButton = new JButton("!");
//			gotItButton.setBounds(215, 77, 20, 20);
//			gotItButton.addActionListener(new TaskElementButtonPress(this));
//			
//			logActivityButton = new JButton("A");
//			logActivityButton.setBounds(237, 77, 20, 20);
//			logActivityButton.addActionListener(new TaskElementButtonPress(this));
//			
//			completeButton = new JButton("C");
//			completeButton.setBounds(259, 77, 20, 20);
//			completeButton.addActionListener(new TaskElementButtonPress(this));
//			
//			if(task.isCompleted()) {
//				completeButton.setEnabled(false);
//			}
//			
//			// I hate null pointers...
////			for(User u : task.getAssignedTo()) {
////				if(u == null) {
////					System.out.println("null2");
////				}
////				assignedToComboBox.addItem(u.getUsername());
////			}
//			
//			this.add(titleLabel);
//			this.add(deadlineLabel);
//			this.add(descriptionLabel);
//			this.add(assignedToLabel);
//			this.add(assignedToComboBox);
//			this.add(gotItButton);
//			this.add(logActivityButton);
//			this.add(completeButton);
//		}
//
//		public Task getTask() {
//			// TODO Auto-generated method stub
//			return this.task;
//		}
//	}
//	
//	private class TaskElementButtonPress implements ActionListener {
//		
//		private TaskElement te;
//		
//		public TaskElementButtonPress(TaskElement te) {
//			this.te = te;
//		}
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			if(e.getActionCommand().equals("!")) {
//				try {
//					sessionManager.getActiveGroup().getTaskManager().assignTask(te.getTask(), sessionManager.getActiveUser());	
//					te.assignedToComboBox.addItem(sessionManager.getActiveUser().getUsername());
//					System.out.println("Success");
//				} catch(Exception ex) {
//					System.out.println("Failed to assign user to task");
//				}
//				
//			} else if(e.getActionCommand().equals("A")) {
//				
//			} else if(e.getActionCommand().equals("C")) {
//				
//			}
//		}
//	}
//	private class ActivityWindow extends JFrame {
//		private JPanel activityWindowPanel;
//		private ActivityWindowMode currMode;
//		
//		// Common Labels
//		private JLabel selectTaskLabel;
//		private JLabel timeSpentLabel;
//		private JLabel descriptionLabel;
//		
//		// Input Elements
//		private JComboBox<Task> taskComboBox;
//		private JTextArea descriptionTextArea;
//		private JSpinner timeSpentSpinner;
//		private JCheckBox verifyCheckBox;
//		
//		// Action Buttons
//		private JButton submitButton;
//		private JButton cancelButton;
//		
//		private int activityInd;
//		
//		// Layout members
//		private BorderLayout borderLayout = new BorderLayout();
//		private GridBagLayout gbLayout = new GridBagLayout();
//		private GridBagConstraints gbC = new GridBagConstraints();
//		
//		public ActivityWindow(ActivityWindowMode mode) {
//			this.currMode = mode;
//			this.setLayout(borderLayout);
//			
//			activityInd = activityList.getSelectedIndex();
//			
//			// Init Common Elements
//			initCommon();
//			
//			// Init Elements Unique to Window Mode
//			switch(mode) {
//				case NEW_ACTIVITY:
//					initNewActivityWindow();
//					break;
//				case EDIT_ACTIVITY:
//					initEditActivityWindow();
//					break;
//			}
//			
//			this.setSize(500, 400);
//			
//			// Center the frame in the screen
//			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//			this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
//
//			this.setVisible(true);
//		}
//
//		private void initCommon() {
//			activityWindowPanel = new JPanel();
//			activityWindowPanel.setBackground(Color.GRAY);;
//			
//			// Init layout tools
//			gbLayout = new GridBagLayout();
//			gbC = new GridBagConstraints();
//			
//			gbC.fill = GridBagConstraints.HORIZONTAL;
//			
//			activityWindowPanel.setLayout(gbLayout);
//			
//			// Initialize field label
//			selectTaskLabel = new JLabel("Select a Task", SwingConstants.RIGHT);
//			timeSpentLabel = new JLabel("Time Spend", SwingConstants.RIGHT);
//			descriptionLabel = new JLabel("Description", SwingConstants.RIGHT);
//			verifyCheckBox = new JCheckBox("Verified");
//			
//			
//			// Init input fields
//			descriptionTextArea = new JTextArea(3, 30);
//			taskComboBox = new JComboBox<Task>();
//			for(Task t : sessionManager.getActiveUser().getAssignedTasks()) {
//				taskComboBox.addItem(t);
//			}
//			timeSpentSpinner = new JSpinner();
//			
//			submitButton = new JButton("Submit");
//			submitButton.addActionListener(new SubmitButtonPress());
//			
//			cancelButton = new JButton("Cancel");
//			cancelButton.addActionListener(new CancelButtonPress());
//			
//			descriptionTextArea.setLineWrap(true);
//			descriptionTextArea.setWrapStyleWord(true);
//			
//			
//			/*
//			 * How we'll parse date
//			String s = "03/24/2013 21:54";
//	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
//			
//	        try {
//	        	Date exDate = simpleDateFormat.parse(s);
//		        System.out.println(exDate);
//	        } catch(Exception ex) {
//	        	System.out.println("Ooops");
//	        }
//	        */
//	        
//			this.addComponent(0, 0, 3, 1, gbC, activityWindowPanel, selectTaskLabel);
//			this.addComponent(3, 0, 5, 1, gbC, activityWindowPanel, taskComboBox);
//			this.addComponent(0, 1, 3, 1, gbC, activityWindowPanel, descriptionLabel);
//			this.addComponent(3, 1, 6, 2, gbC, activityWindowPanel, descriptionTextArea);
//			this.addComponent(0, 2, 3, 1, gbC, activityWindowPanel, timeSpentLabel);
//			this.addComponent(3, 2, 5, 1, gbC, activityWindowPanel, timeSpentSpinner);
//			this.addComponent(0, 4, 2, 1, gbC, activityWindowPanel, cancelButton);
//			this.addComponent(2, 4, 3, 1, gbC, activityWindowPanel, submitButton);
//			
//			// admins have access to verify checkbox
//			if (sessionManager.getActiveGroup().getAdmins().contains(sessionManager.getActiveUser()))
//				this.addComponent(0, 3, 3, 1, gbC, activityWindowPanel, verifyCheckBox);
//			
//			this.add(activityWindowPanel, BorderLayout.CENTER);
//		}
//		
//		// Initializes the components for the "New Activity" window
//		private void initNewActivityWindow() {
//
//			this.setTitle("Create a New Activity");
//		 
//		}
//		
//		private void initEditActivityWindow() {
//			
//			this.setTitle("Edit an Activity");
//			
//			// when editing, initialize all fields to the current values
//			Activity act = sessionManager.getActiveGroup().getActivityManager().getActivityLog().get(activityList.getSelectedIndex());
//			
//			taskComboBox.setSelectedItem(act.getTask());
//			descriptionTextArea.setText(act.getDescription());
//			timeSpentSpinner.setValue(act.getTimeSpend());
//			verifyCheckBox.setSelected(act.isVerified());
//			
//		}
//
//		
//		private void addComponent(int x, int y, int w, int h, GridBagConstraints c, Container aContainer, Component aComponent )  
//		{  
//		    c.gridx = x;  
//		    c.gridy = y;  
//		    c.gridwidth = w;  
//		    c.gridheight = h;
//		    gbLayout.setConstraints( aComponent, c );  
//		    aContainer.add( aComponent );  
//		} 
//		
//		private class SubmitButtonPress implements ActionListener {
//			private Registrar registrar = new Registrar();
//			private SessionManager sm;
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				sm = ActivityPanel.this.sessionManager;
//				Object response = null;
//				if (taskComboBox.getSelectedIndex() == -1)
//					JOptionPane.showMessageDialog(null, "Please select a task.", "Task Not Selected", JOptionPane.INFORMATION_MESSAGE);
//					
//				else if (descriptionTextArea.getText().equals("")) {
//					JOptionPane.showMessageDialog(null, "Please give a description.", "No Description", JOptionPane.INFORMATION_MESSAGE);
//				}
//				
//				else if ((int) timeSpentSpinner.getValue() < 1) {
//					JOptionPane.showMessageDialog(null, "Please give an amount of time.", "No Time Spent", JOptionPane.INFORMATION_MESSAGE);
//				}
//				
//				else {
//
//					// Send the new activity to the DB.
//					try {
//						if (currMode == ActivityWindowMode.NEW_ACTIVITY)
//							response = registrar.addNewActivity();
//						// else
//							// TODO: add editActivity function to registrar
//							// response = registrar.editActivity();
//					} catch(Exception ex) {
//						System.out.println(ex);
//					}
//					
//					if(response.getClass().equals(ServerResponse.class)) {
//						ServerResponse servResponse = (ServerResponse)response;
//						// TODO Error handling
//					} else if(response.getClass().equals(Activity.class)) {
//						
//						// activity was created
//						if (currMode == ActivityWindowMode.NEW_ACTIVITY) {
//							Activity newActivity = (Activity) response;
//							newActivity.setTimeStamp(new Date());
//							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
//							sm.getActiveGroup().getActivityManager().addActivity(newActivity);
//							listModel.addElement(newActivity.getTask().getTitle() + "\n" + newActivity.getUser().getUsername() + " - " + dateFormat.format(newActivity.getTimeStamp()));
//					
//						}
//						
//						// activity was edited
//						else {
//							Activity editActivity = (Activity) response;
//							editActivity.setTimeStamp(new Date());
//							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
//							sm.getActiveGroup().getActivityManager().getActivityLog().set(activityInd, editActivity);
//							listModel.set(activityInd, editActivity.getTask().getTitle() + "\n" + editActivity.getUser().getUsername() + " - " + dateFormat.format(editActivity.getTimeStamp()));
//						}
//					}
//				}
//			}
//		}
}
