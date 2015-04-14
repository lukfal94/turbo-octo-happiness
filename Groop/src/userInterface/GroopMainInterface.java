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
import javax.swing.JPanel;

import managers.SessionManager;
import social.Group;
import social.User;
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

	
	public enum GuiMode {
		BLANK, TUTORIAL, STANDARD
	}
	
	public GroopMainInterface(User user) {
		sessionManager = new SessionManager(user);
		sessionManager.setMainGUI(this);
		
		this.setTitle("Groop - " + sessionManager.getActiveUser().getUsername());

		this.setSize(1200, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		// Center the frame in the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		menuBar = new GroopMenuBar();
		menuBar.initMenuBar();
		this.setJMenuBar(menuBar);
		
		if(sessionManager.getGuiMode() == GuiMode.STANDARD)
			initStandardComponents();
		else if(sessionManager.getGuiMode() == GuiMode.BLANK) 
			initBlankComponents();
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
		private JMenu fileMenu, newMenu, editMenu, groupMenu, switchGroupMenu;
		private JMenuItem menuItem;
	
		public void updateGroupListing() {
			switchGroupMenu = new JMenu("Switch to");

			for(Group g : sessionManager.getGroups()) {
				menuItem = new GroupMenuItem(g);
				menuItem.addActionListener(new MenuActionListener());
				switchGroupMenu.add(menuItem);
			}
			groupMenu.add(switchGroupMenu);
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
	
	
	private void initStandardComponents() {
		
		gbLayout = new GridBagLayout();
		gbC = new GridBagConstraints();
		
		gbC.fill = GridBagConstraints.BOTH;
		gbC.weightx = 0.5;
		gbC.weighty = 0.5;
		
		this.setLayout(gbLayout);
		
		// Initialize the content panes
		userInfoPanel = new UserInfoPanel(sessionManager);
		groupInfoPanel = new GroupInfoPanel(sessionManager);
		activityPanel = new ActivityPanel(sessionManager);
		taskPanel = new TaskPanel(sessionManager);
		calendarPanel = new CalendarPanel();
		
		this.addComponent(0, 0, 1, 1, gbC, this, userInfoPanel);
		this.addComponent(1, 0, 1, 1, gbC, this, groupInfoPanel);
		this.addComponent(0, 1, 3, 3, gbC, this, calendarPanel);
		this.addComponent(3, 0, 1, 2, gbC, this, taskPanel);
		this.addComponent(3, 2, 1, 2, gbC, this, activityPanel);
		
		
		this.setVisible(true);
	}
	
	private void initBlankComponents() {
		this.setTitle("Groop - " + sessionManager.getActiveUser().getUsername());

		this.setSize(1200, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Center the frame in the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		gbLayout = new GridBagLayout();
		gbC = new GridBagConstraints();
		
		gbC.fill = GridBagConstraints.BOTH;
		gbC.weightx = 0.5;
		gbC.weighty = 0.5;
		
		this.setLayout(gbLayout);
		
		// Initialize the content panes
		userInfoPanel = new UserInfoPanel(sessionManager);
		groupInfoPanel = new GroupInfoPanel(sessionManager);
		activityPanel = new ActivityPanel(sessionManager);
		taskPanel = new TaskPanel(sessionManager);
		calendarPanel = new CalendarPanel();
		
		this.addComponent(0, 0, 1, 1, gbC, this, userInfoPanel);
		this.addComponent(1, 0, 1, 1, gbC, this, groupInfoPanel);
		this.addComponent(0, 1, 3, 3, gbC, this, calendarPanel);
		this.addComponent(3, 0, 1, 2, gbC, this, taskPanel);
		this.addComponent(3, 2, 1, 2, gbC, this, activityPanel);
		
		
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
			else if(e.getSource().getClass().equals(GroupMenuItem.class)) {
				GroupMenuItem gMenuItem = (GroupMenuItem) e.getSource();
				
				// Switch the active group
				if(!gMenuItem.getGroup().equals(sessionManager.getActiveGroup())) {
					sessionManager.switchGroup(gMenuItem.getGroup());
					mainGui.refreshInterface();
				} else {
					mainGui.refreshInterface();
				}
				
			}
			// NEED TO CHECK FOR ACTIVE GROUP OR DISABLE MENU BUTTONS
			if(e.getActionCommand().equals("Task")) {
				// Launch the new task window
				mainGui.getTaskPanel().openTaskWindow(TaskWindowMode.NEW_TASK);
				
				//GroopMainInterface.this.sessionManager.getActiveGroup().getTaskManager()
				
				// taskPanel.refresh();
			}

		}
	}
	
	public TaskPanel getTaskPanel() {
		return taskPanel;
	}

	public GroupInfoPanel getGroupInfoPanel() {
		// TODO Auto-generated method stub
		return groupInfoPanel;
	}

	public void refreshInterface() {
		// TODO Auto-generated method stub
//		taskPanel.refresh();
		groupInfoPanel.refresh();
		menuBar.updateGroupListing();
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
