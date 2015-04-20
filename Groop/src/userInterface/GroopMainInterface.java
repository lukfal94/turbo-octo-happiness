package userInterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import managers.SessionManager;
import social.Group;
import social.User;
import userInterface.ActivityPanel.ActivityWindowMode;
import userInterface.CalendarPanel.EventWindowMode;
import userInterface.GroupInfoPanel.GroupWindowMode;
import userInterface.TaskPanel.TaskWindowMode;

public class GroopMainInterface extends JFrame{
	private SessionManager sessionManager;
	
	private GridBagLayout gbLayout;
	private GridBagConstraints gbC;
	
	private GroopMenuBar menuBar;
	private JMenu fileMenu, newMenu, editMenu, groupMenu, switchGroupMenu;
	private JMenuItem menuItem;
	
	private UserInfoPanel userInfoPanel;
	private GroupInfoPanel groupInfoPanel;
	private ActivityPanel activityPanel;
	private TaskPanel taskPanel;
	private CalendarPanel calendarPanel;

    private static final int FRAME_X = 1200;
    private static final int FRAME_Y = 800;
    private boolean lockFrame = true;
    
	public enum GuiMode {
		BLANK, TUTORIAL, STANDARD
	}
	
	public GroopMainInterface(User user) {
		sessionManager = new SessionManager(user);
		sessionManager.setMainGUI(this);
		
		this.setTitle("Groop - " + sessionManager.getActiveUser().getUsername());

		this.setSize(FRAME_X, FRAME_Y);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		if(lockFrame) {	        
	        this.setMinimumSize(new Dimension(FRAME_X, FRAME_Y));
	        this.setMaximumSize(new Dimension(FRAME_X, FRAME_Y));
		}
		
		// Center the frame in the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		menuBar = new GroopMenuBar();
		menuBar.initMenuBar();
		this.setJMenuBar(menuBar);
		
		initComponents();
	}

	private class GroupMenuItem extends JMenuItem {
		private Group group;
		
		public GroupMenuItem(Group g) {
			this.setText(g.getName());
			this.group = g;
		}
		
		public Group getGroup() {
			return this.group;
		}
	}
	
	private class GroopMenuBar extends JMenuBar {
		private JMenu fileMenu, newMenu, editMenu, groupMenu, switchGroupMenu, membersMenu;
		private JMenuItem menuItem;
	
		public void updateGroupMenu() {
			// Delete old menu
			menuBar.remove(groupMenu);
			System.out.println("Deleted old menu");
			
			// Build Group menu
			groupMenu = new JMenu("Group");
			groupMenu.setMnemonic(KeyEvent.VK_G);
			
			switchGroupMenu = new JMenu("Switch to");

			for(Group g : sessionManager.getGroups()) {
				menuItem = new GroupMenuItem(g);
				menuItem.addActionListener(new MenuActionListener());
				switchGroupMenu.add(menuItem);
			}
			
			groupMenu.add(switchGroupMenu);
			
			menuItem = new JMenuItem("Invite User");
			menuItem.addActionListener(new MenuActionListener());
			groupMenu.add(menuItem);
			
			menuItem = new JMenuItem("Delete Group");
			menuItem.addActionListener(new MenuActionListener());
			
			System.out.println("Constructed new menu");
			
			groupMenu.add(menuItem);
			
			menuBar.add(groupMenu);
		}
		
		public void initMenuBar() {
			// Build File menu
			fileMenu = new JMenu("File");
			fileMenu.setMnemonic(KeyEvent.VK_F);
			
			newMenu = new JMenu("New");
			
			menuItem = new JMenuItem("Group");
			menuItem.addActionListener(new MenuActionListener());
			newMenu.add(menuItem);
			
			menuItem = new JMenuItem("Task");
			menuItem.addActionListener(new MenuActionListener());
			newMenu.add(menuItem);
			
			menuItem = new JMenuItem("Activity");
			menuItem.addActionListener(new MenuActionListener());
			newMenu.add(menuItem);
			
			menuItem = new JMenuItem("Event");
			menuItem.addActionListener(new MenuActionListener());
			newMenu.add(menuItem);
			
			menuItem = new JMenuItem("Quit");
			menuItem.addActionListener(new MenuActionListener());
			
			fileMenu.add(newMenu);
			fileMenu.add(menuItem);
			
			// Build Edit menu
			editMenu = new JMenu("Edit");
			editMenu.setMnemonic(KeyEvent.VK_E);
			
			menuItem = new JMenuItem("Profile");
			menuItem.addActionListener(new MenuActionListener());
			editMenu.add(menuItem);
			
			menuItem = new JMenuItem("Group Info");
			menuItem.addActionListener(new MenuActionListener());
			editMenu.add(menuItem);
			
//			menuItem = new JMenuItem("Task");
//			menuItem.addActionListener(new MenuActionListener());
//			editMenu.add(menuItem);
//			
//			menuItem = new JMenuItem("Activity");
//			menuItem.addActionListener(new MenuActionListener());
//			editMenu.add(menuItem);
			
			
			// Build Group menu
			groupMenu = new JMenu("Group");
			groupMenu.setMnemonic(KeyEvent.VK_G);
			
			switchGroupMenu = new JMenu("Switch to");

			for(Group g : sessionManager.getGroups()) {
				menuItem = new GroupMenuItem(g);
				menuItem.addActionListener(new MenuActionListener());
				switchGroupMenu.add(menuItem);
			}
			groupMenu.add(switchGroupMenu);
			
			
			if(sessionManager.getGuiMode().equals(GuiMode.STANDARD)) {
				membersMenu = new JMenu("Members");
			
				for(User u : sessionManager.getActiveGroup().getMembers()) {
					menuItem = new JMenuItem(u.getUsername());
					membersMenu.add(menuItem);
				}
				groupMenu.add(membersMenu);
			}
			
			menuItem = new JMenuItem("Invite User");
			menuItem.addActionListener(new MenuActionListener());
			groupMenu.add(menuItem);
			
			menuItem = new JMenuItem("Delete Group");
			menuItem.addActionListener(new MenuActionListener());
			groupMenu.add(menuItem);
			
			this.add(fileMenu);
			this.add(editMenu);
			this.add(groupMenu);
//			
//			fileMenu.add()
		}
		
	}
	
	
	private void initComponents() {
		
		gbLayout = new GridBagLayout();
		gbC = new GridBagConstraints();
		
		gbC.fill = GridBagConstraints.BOTH;
		gbC.weightx = 0.5;
		gbC.weighty = 0.5;
		
		this.setLayout(null);
		
		// Initialize the content panes
		userInfoPanel = new UserInfoPanel(sessionManager);
		groupInfoPanel = new GroupInfoPanel(sessionManager);
		taskPanel = new TaskPanel(sessionManager);
		activityPanel = new ActivityPanel(sessionManager);
		calendarPanel = new CalendarPanel(sessionManager);
		
		userInfoPanel.setBounds(0, 0, 300, 200);
		groupInfoPanel.setBounds(300, 0, 500, 200);
		taskPanel.setBounds(800, 0, 400, 400);
		activityPanel.setBounds(800, 400, 400, 400);
		calendarPanel.setBounds(0, 200, 800, 600);
		
//		this.addComponent(0, 0, 1, 1, gbC, this, userInfoPanel);
//		this.addComponent(1, 0, 1, 1, gbC, this, groupInfoPanel);
//		this.addComponent(0, 1, 3, 3, gbC, this, calendarPanel);
//		this.addComponent(3, 0, 1, 2, gbC, this, taskPanel);
//		this.addComponent(3, 2, 1, 2, gbC, this, activityPanel);
		
		this.add(userInfoPanel);
		this.add(groupInfoPanel);
		this.add(taskPanel);
		this.add(activityPanel);
		this.add(calendarPanel);
		
		this.setVisible(true);
	}
	
	private class MenuActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			GroopMainInterface mainGui = GroopMainInterface.this;
			
			System.out.println("Selected: " + e.getActionCommand());
			
			if(e.getActionCommand().equals("Group")){
				mainGui.getGroupInfoPanel().openGroupInfoWindow(GroupWindowMode.NEW_GROUP);
			}
			// Switching active groups
			else if(e.getSource().getClass().equals(GroupMenuItem.class)) {
				GroupMenuItem gMenuItem = (GroupMenuItem) e.getSource();
				
				// Switch the active group
				if(!gMenuItem.getGroup().equals(sessionManager.getActiveGroup())) {
					sessionManager.switchGroup(gMenuItem.getGroup());
					mainGui.refreshInterface();
					try {
						sessionManager.getActiveGroup().getTaskManager().syncTasks(gMenuItem.getGroup());
					} catch(Exception ex) {
						System.out.println("Error: Could not sync tasks");
					}
				} else {
					try {
						sessionManager.getGroupManager().syncGroups();
					} catch(Exception ex) {
						System.out.println("Error: Could not sync groups");
					}
					GroopMainInterface.this.groupInfoPanel.refresh();
				}
				
			}
			// NEED TO CHECK FOR ACTIVE GROUP OR DISABLE MENU BUTTONS
			else if(e.getActionCommand().equals("Task")) {
				// Launch the new task window
				mainGui.getTaskPanel().openTaskWindow(TaskWindowMode.NEW_TASK);
				
				//GroopMainInterface.this.sessionManager.getActiveGroup().getTaskManager()
				
				// taskPanel.refresh();
			} 
			else if(e.getActionCommand().equals("Activity")) {
				mainGui.getActivityPanel().openActivityWindow(ActivityWindowMode.NEW_ACTIVITY);
			}
			else if(e.getActionCommand().equals("Event")) {
				mainGui.getCalendarPanel().openEventWindow(EventWindowMode.CREATE_EVENT);
			}
			else if(e.getActionCommand().equals("Invite User")) {
				InviteUserWindow userWindow = new InviteUserWindow(sessionManager);
			}
			else if(e.getActionCommand().equals("Delete Group")) {
				int n = JOptionPane.showConfirmDialog(GroopMainInterface.this, "<html><p style='width: 300px;'>Are you sure you wish to delete: \"" + sessionManager.getActiveGroup().getName() 
						+ "\"? This action can not be undone!</html></p>",
						"Delete Group", JOptionPane.YES_NO_OPTION);
				if(n == 0) {
					// Delete the group from the database and local record
					try {
						sessionManager.getGroupManager().deleteGroup(sessionManager.getActiveGroup());
					} catch(Exception ex) {
						System.out.println("Error: Could not delete group." + ex);
						return;
					}
					// Change the active group
					sessionManager.setActiveGroup(sessionManager.getGroups().get(0));
					
					// Refresh the interface
					GroopMainInterface.this.refreshInterface();
				}
			}

		}
	}
	
	public TaskPanel getTaskPanel() {
		return taskPanel;
	}

	public ActivityPanel getActivityPanel() {
		// TODO Auto-generated method stub
		return this.activityPanel;
	}

	public CalendarPanel getCalendarPanel() {
		// TODO Auto-generated method stub
		return this.calendarPanel;
	}

	public GroupInfoPanel getGroupInfoPanel() {
		return groupInfoPanel;
	}

	public void refreshInterface() {
		// TODO Auto-generated method stub
		groupInfoPanel.refresh();
		menuBar.updateGroupMenu();
	}

	public void setTaskPanel(TaskPanel taskPanel) {
		this.taskPanel = taskPanel;
	}

	private void addComponent(int x, int y, int w, int h,
			GridBagConstraints c, Container aContainer, Component aComponent )  
	{  
	    c.gridx = x;  
	    c.gridy = y;  
	    c.gridwidth = w;  
	    c.gridheight = h;
	    gbLayout.setConstraints( aComponent, c );  
	    aContainer.add( aComponent );  
	} 
}
