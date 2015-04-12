package userInterface;

import java.awt.BorderLayout;
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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingConstants;

import databaseComm.Registrar;
import databaseComm.ServerResponse;
import managers.SessionManager;
import social.User;
import util.Task;
import util.Task.TaskPriority;

public class TaskPanel extends JPanel{
	private SessionManager sessionManager;
	
	private JButton createTaskButton;
	
	public TaskPanel(SessionManager sm) {
		this.sessionManager = sm;
		initComponents();
	}
	
	public void initComponents() {
		this.setSize(400, 400);
		
		createTaskButton = new JButton("New");
		createTaskButton.setSize(100, 50);
		createTaskButton.addActionListener(new TaskWindowButtonClick());
		createTaskButton.setName("createTaskButton");
		
		this.setBackground(Color.CYAN);
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
	}
	
	public void openTaskWindow(TaskWindowMode mode) {
		TaskWindow taskWindow = new TaskWindow(mode);
	}

	private class TaskWindow extends JFrame {
		private JPanel taskWindowPanel;
		private TaskWindowMode currMode;
		
		// Common Labels
		private JLabel titleLabel;
		private JLabel descriptionLabel;
		private JLabel deadlineLabel;
		private JLabel priorityLabel;
		
		// Input Elements
		private JTextField titleTextField;
		private JTextArea descriptionTextArea;
		private JFormattedTextField deadlineTextField;
		private JComboBox priorityComboBox;
		
		// Action Buttons
		private JButton submitButton;
		
		// Layout members
		private BorderLayout borderLayout = new BorderLayout();
		private GridBagLayout gbLayout = new GridBagLayout();
		private GridBagConstraints gbC = new GridBagConstraints();
		
		public TaskWindow(TaskWindowMode mode) {
			this.currMode = mode;
			this.setLayout(borderLayout);
			
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
			taskWindowPanel.setBackground(Color.GRAY);;
			
			// Init layout tools
			gbLayout = new GridBagLayout();
			gbC = new GridBagConstraints();
			
			gbC.fill = GridBagConstraints.HORIZONTAL;
			
			taskWindowPanel.setLayout(gbLayout);
			
			// Initialize field label
			titleLabel = new JLabel("Title", SwingConstants.RIGHT);
			descriptionLabel = new JLabel("Description", SwingConstants.RIGHT);
			deadlineLabel =	new JLabel("Deadline", SwingConstants.RIGHT);
			priorityLabel = new JLabel("Priority", SwingConstants.RIGHT);
			
			// Init input fields
			titleTextField = new JTextField(20);
			descriptionTextArea = new JTextArea(3, 30);
			deadlineTextField = new JFormattedTextField(new SimpleDateFormat("MM/dd/yyyy HH:mm"));
			priorityComboBox = new JComboBox(TaskPriority.values());
			
			submitButton = new JButton("Submit");
			submitButton.addActionListener(new SubmitButtonPress());
			descriptionTextArea.setLineWrap(true);
			descriptionTextArea.setWrapStyleWord(true);
			deadlineTextField.setText("mm/dd/yyyy hh:mm");
			
			
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
	        
			this.addComponent(0, 0, 3, 1, gbC, taskWindowPanel, titleLabel);
			this.addComponent(3, 0, 5, 1, gbC, taskWindowPanel, titleTextField);
			this.addComponent(0, 1, 3, 1, gbC, taskWindowPanel, descriptionLabel);
			this.addComponent(3, 1, 6, 2, gbC, taskWindowPanel, descriptionTextArea);
			this.addComponent(0, 7, 3, 1, gbC, taskWindowPanel, deadlineLabel);
			this.addComponent(3, 7, 5, 1, gbC, taskWindowPanel, deadlineTextField);
			this.addComponent(0, 8, 3, 1, gbC, taskWindowPanel, priorityLabel);
			this.addComponent(3, 8, 5, 1, gbC, taskWindowPanel, priorityComboBox);
			this.addComponent(2, 9, 3, 1, gbC, taskWindowPanel, submitButton);
			
			this.add(taskWindowPanel, BorderLayout.CENTER);
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
		
		private class SubmitButtonPress implements ActionListener {
			private Registrar registrar = new Registrar();
			private SessionManager sm;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sm = TaskPanel.this.sessionManager;
				Object response = null;

				// Send the new task to the DB.
				try {
					response = registrar.addNewTask(sm.getActiveGroup(), sm.getActiveUser(), titleTextField.getText(),
						descriptionTextArea.getText(), new Date(), priorityComboBox.getSelectedIndex());
				} catch(Exception ex) {
					System.out.println(ex);
				}
				
				if(response.getClass().equals(ServerResponse.class)) {
					ServerResponse servResponse = (ServerResponse)response;
					// TODO Error handling
				} else if(response.getClass().equals(Task.class)) {
					Task newTask = (Task)response;
					sm.getActiveGroup().getTaskManager().addTask(newTask);
				}
				
				System.out.println("Title: "+titleTextField.getText());
				System.out.println("Descr: "+descriptionTextArea.getText());
				System.out.println("Deadl: "+deadlineTextField.getText());
				System.out.println("Prior: "+TaskPriority.values()[priorityComboBox.getSelectedIndex()]);
			}
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
