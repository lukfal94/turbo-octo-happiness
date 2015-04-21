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
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import userInterface.GroopMainInterface.GuiMode;
import util.Task;
import util.Date;
import util.Task.TaskPriority;

public class TaskPanel extends JPanel{
	private SessionManager sessionManager;
	
	private JLabel titleLabel;
	private JPanel taskScrollPanel;
	private JScrollPane scrollPane;
	
	private JButton createTaskButton;
	
	// private CalendarPanel calendarPanel = new CalendarPanel();
	// Month Names (short)
	private String[] shortMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		
	public TaskPanel(SessionManager sm) {
		this.sessionManager = sm;
		initComponents();
	}
	
	public void initComponents() {
//		this.setSize(400, 400);
		this.setLayout(null);
		
		taskScrollPanel = new JPanel();
		taskScrollPanel.setLayout(null);
		taskScrollPanel.setBackground(Color.GREEN);
		
		scrollPane = new JScrollPane(taskScrollPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(40, 60, 319, 300);

		titleLabel = new JLabel("<html><h1 style='text-align: center;'>Tasks</h1></html>");
		titleLabel.setFont(new Font("Sans serif", 0, 20));
		titleLabel.setBackground(Color.RED);
		titleLabel.setSize(new Dimension(300, 50));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(50, 0, 300, 50);
		
		createTaskButton = new JButton("New");
		createTaskButton.addActionListener(new TaskWindowButtonClick());
		createTaskButton.setBounds(170, 365, 60, 20);
		createTaskButton.setName("createTaskButton");
		
		if(sessionManager.getGuiMode() == GuiMode.STANDARD) {
			// Populate the tasks
			int i = 0;
			for(Task t : sessionManager.getActiveGroup().getTaskManager().getTasks()) {
				TaskElement te = new TaskElement(t);
				te.setBounds(0, i * 100, 300, 100);
				taskScrollPanel.add(te);
				System.out.println("Adding " + t);
				i++;
			}
			taskScrollPanel.setPreferredSize(new Dimension(300, 100 * i));
		}
		
		this.setBackground(Color.CYAN);
		
		this.add(titleLabel);
		this.add(scrollPane);
		this.add(createTaskButton);
	}
	
	public enum TaskWindowMode {
		NEW_TASK, EDIT_TASK
	}
	
	public void refresh() {
		// TODO Auto-generated method stub
		this.taskScrollPanel = new JPanel();
		
		taskScrollPanel.setLayout(null);
		taskScrollPanel.setBackground(Color.GREEN);
		
		// Populate the tasks		
		int i = 0;
		for(Task t : sessionManager.getActiveGroup().getTaskManager().getTasks()) {
			TaskElement te = new TaskElement(t);
			te.setBounds(0, i * 100, 300, 100);
			taskScrollPanel.add(te);
			System.out.println("Adding " + t);
			i++;
		}
		taskScrollPanel.setPreferredSize(new Dimension(300, 100 * i));
		
		scrollPane.setViewportView(taskScrollPanel);
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
	
	private class TaskElement extends JPanel {
		private Task task;
		
		private JLabel titleLabel;
		private JLabel descriptionLabel;
		private JLabel deadlineLabel;
		private JLabel assignedToLabel;
		private JComboBox<String> assignedToComboBox;
		private JButton editButton;
		
		private Border border = BorderFactory.createLineBorder(Color.BLACK);
		
		private GridBagLayout gbLayout;
		private GridBagConstraints gbC;
		
		public TaskElement(Task t) {
			this.task = t;
			
			this.setSize(new Dimension(300, 100));
			this.setLayout(null);
			
			initComponents();
			this.setBorder(border);
			this.setBackground(Color.RED);
		}
		
		private void initComponents() {
			
			titleLabel = new JLabel(task.getTitle());
			titleLabel.setBounds(5, 5, 150, 20);
			
			deadlineLabel = new JLabel(task.getDeadline().getDate().asString('/'));
			deadlineLabel.setBounds(160, 5, 150, 20);
			
			descriptionLabel = new JLabel(String.format("<html><p style='width: %d;'>%s</p></html>", 250, task.getDescription()));
			descriptionLabel.setBounds(5, 25, 295, 50);	
			
			assignedToLabel = new JLabel("Assigned to:");
			assignedToLabel.setBounds(5, 75, 95, 25);
			
			assignedToComboBox = new JComboBox<String>();
			assignedToComboBox.setBounds(105, 75, 100, 25);
			
			// I hate null pointers...
//			for(User u : task.getAssignedTo()) {
//				if(u == null) {
//					System.out.println("null2");
//				}
//				assignedToComboBox.addItem(u.getUsername());
//			}
			
			this.add(titleLabel);
			this.add(deadlineLabel);
			this.add(descriptionLabel);
			this.add(assignedToLabel);
			this.add(assignedToComboBox);
		}
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
		
		private JComboBox<String> dayCombo, hourCombo, minCombo;
		private JComboBox<String> monthCombo, yearCombo, amPmCombo;
		
		// Action Buttons
		private JButton submitButton;
		
		public TaskWindow(TaskWindowMode mode) {
			this.currMode = mode;
			this.setLayout(new BorderLayout());
			
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
			
			this.setSize(400, 300);
			
			// Center the frame in the screen
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

			this.setVisible(true);
		}

		private void initCommon() {
			int i, year;
			
			taskWindowPanel = new JPanel();
			taskWindowPanel.setBackground(Color.GRAY);;
			
			taskWindowPanel.setLayout(null);
			
			// Initialize field label
			titleLabel = new JLabel("Title", SwingConstants.RIGHT);
			descriptionLabel = new JLabel("Description", SwingConstants.RIGHT);
			deadlineLabel =	new JLabel("Deadline", SwingConstants.RIGHT);
			priorityLabel = new JLabel("Priority", SwingConstants.RIGHT);
			
			// Init input fields
			titleTextField = new JTextField(30);
			descriptionTextArea = new JTextArea(4, 40);
			deadlineTextField = new JFormattedTextField(new SimpleDateFormat("MM/dd/yyyy HH:mm"));
			priorityComboBox = new JComboBox(TaskPriority.values());
			
			// Date Combos
			dayCombo = new JComboBox<String>();
			monthCombo = new JComboBox<String>();
			yearCombo = new JComboBox<String>();
			hourCombo = new JComboBox<String>();
			minCombo = new JComboBox<String>();
			
			dayCombo.setMaximumRowCount(12);
			monthCombo.setMaximumRowCount(8);
			yearCombo.setMaximumRowCount(6);
			hourCombo.setMaximumRowCount(8);
			minCombo.setMaximumRowCount(8);
			
			dayCombo.addItem("Day");
			for(i = 1; i <= 31; i++) {
				dayCombo.addItem(Integer.toString(i));
			}
			
			monthCombo.addItem("Month");
			for(i = 0; i < shortMonths.length; i++) {
				monthCombo.addItem(shortMonths[i]);
			}
			
			year = Calendar.getInstance().get(Calendar.YEAR);
			yearCombo.addItem("Year");
			for(i = year; i < year + 10; i++) {
				yearCombo.addItem(Integer.toString(i));
			}
			
			hourCombo.addItem("Hour");
			for(i = 0; i < 24; i++) {
				hourCombo.addItem(Integer.toString(i));
			}
			
			minCombo.addItem("Min");
			for(i = 0; i < 60; i++) {
				minCombo.addItem(Integer.toString(i));
			}
			
			submitButton = new JButton("Submit");
			submitButton.addActionListener(new SubmitButtonPress());
			descriptionTextArea.setLineWrap(true);
			descriptionTextArea.setWrapStyleWord(true);
     
			// Position everything
			titleLabel.setBounds(10,10,100,20);
			titleTextField.setBounds(112, 10, 100, 20);
			descriptionLabel.setBounds(10, 35, 100, 20);
			descriptionTextArea.setBounds(115, 35, 200, 95);
			deadlineLabel.setBounds(10, 135, 100, 20);
			dayCombo.setBounds(110, 135, 80, 20);
			monthCombo.setBounds(190, 135, 90, 20);
			yearCombo.setBounds(280, 135, 80, 20);
			hourCombo.setBounds(110, 160, 100, 20);
			minCombo.setBounds(210, 160, 100, 20);
			priorityLabel.setBounds(10, 185, 100, 20);
			priorityComboBox.setBounds(110, 185, 100, 20);
			submitButton.setBounds(160, 220, 80, 30);
			
			this.add(titleLabel);
			this.add(titleTextField);
			this.add(descriptionLabel);
			this.add(descriptionTextArea);
			this.add(deadlineLabel);
			this.add(dayCombo);
			this.add(monthCombo);
			this.add(yearCombo);
			this.add(hourCombo);
			this.add(minCombo);
			this.add(priorityLabel);
			this.add(priorityComboBox);
			this.add(submitButton);
			
//			this.addComponent(0, 0, 3, 1, gbC, taskWindowPanel, titleLabel);
//			this.addComponent(3, 0, 5, 1, gbC, taskWindowPanel, titleTextField);
//			this.addComponent(0, 1, 3, 1, gbC, taskWindowPanel, descriptionLabel);
//			this.addComponent(3, 1, 6, 2, gbC, taskWindowPanel, descriptionTextArea);
//			this.addComponent(0, 7, 3, 1, gbC, taskWindowPanel, deadlineLabel);
//			this.addComponent(3, 7, 5, 1, gbC, taskWindowPanel, deadlineTextField);
//			this.addComponent(0, 8, 3, 1, gbC, taskWindowPanel, priorityLabel);
//			this.addComponent(3, 8, 5, 1, gbC, taskWindowPanel, priorityComboBox);
//			this.addComponent(2, 9, 3, 1, gbC, taskWindowPanel, submitButton);
			
			this.add(taskWindowPanel, BorderLayout.CENTER);
		}
		
		// Initializes the components for the "New Task" window
		private void initNewTaskWindow() {

			this.setTitle("Create a New Task");
		 
		}
		private void initEditTaskWindow() {
			// TODO Auto-generated method stub

		}
		
		private class SubmitButtonPress implements ActionListener {
			private Registrar registrar = new Registrar();
			private SessionManager sm;
			private Date deadline;
			@Override
			public void actionPerformed(ActionEvent e) {
				sm = TaskPanel.this.sessionManager;
				Object response = null;

				if(dateInputValid()) {
					deadline = new Date((String)yearCombo.getSelectedItem(), (String)monthCombo.getSelectedItem(), (String)dayCombo.getSelectedItem(),
							(String)hourCombo.getSelectedItem(), (String)minCombo.getSelectedItem());
					if(e.getSource().equals(submitButton)) {
						// Send the new task to the DB.
						try {
							response = registrar.addNewTask(sm.getActiveGroup(), sm.getActiveUser(), titleTextField.getText(),
								descriptionTextArea.getText(), deadline, priorityComboBox.getSelectedIndex());
						} catch(Exception ex) {
							System.out.println(ex);
						}
						
						if(response.getClass().equals(ServerResponse.class)) {
							ServerResponse servResponse = (ServerResponse)response;
							// TODO Error handling
						} else if(response.getClass().equals(Task.class)) {
							Task newTask = (Task)response;
							// Add the task locally
							sm.getActiveGroup().getTaskManager().addTask(newTask);
							// Refresh the interface
							sm.getMainGUI().getTaskPanel().refresh();
						}
						
						// calendarPanel.addEvent(titleTextField.getText(), deadlineTextField.getText());
						TaskWindow.this.dispose();
					}
				}

			}

			private boolean dateInputValid() {
				// TODO Auto-generated method stub
				return yearCombo.getSelectedItem().equals("Year") == false && monthCombo.getSelectedItem().equals("Month") == false && 
						dayCombo.getSelectedItem().equals("Day") == false && hourCombo.getSelectedItem().equals("Hour") == false &&
								minCombo.getSelectedItem().equals("Min") == false;
			}
		}
	}	
}
