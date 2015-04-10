package userInterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import managers.SessionManager;
import social.User;

public class GroopMainInterface extends JFrame{
	private SessionManager sessionManager;
	
	private GridBagLayout gbLayout;
	private GridBagConstraints gbC;
	
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
		
		if(sessionManager.getGuiMode() == GuiMode.STANDARD)
			initStandardComponents();
		else if(sessionManager.getGuiMode() == GuiMode.BLANK) 
			initBlankComponents();
	}

	private void initStandardComponents() {
		this.setTitle("Groop - " + sessionManager.getUser().getUsername());

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
		activityPanel = new ActivityPanel();
		taskPanel = new TaskPanel();
		calendarPanel = new CalendarPanel();
		
		this.addComponent(0, 0, 1, 1, gbC, this, userInfoPanel);
		this.addComponent(1, 0, 1, 1, gbC, this, groupInfoPanel);
		this.addComponent(0, 1, 3, 3, gbC, this, calendarPanel);
		this.addComponent(3, 0, 1, 2, gbC, this, taskPanel);
		this.addComponent(3, 2, 1, 2, gbC, this, activityPanel);
		
		
		this.setVisible(true);
	}
	
	private void initBlankComponents() {
		this.setTitle("Groop - " + sessionManager.getUser().getUsername());

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
		activityPanel = new ActivityPanel();
		taskPanel = new TaskPanel();
		calendarPanel = new CalendarPanel();
		
		this.addComponent(0, 0, 1, 1, gbC, this, userInfoPanel);
		this.addComponent(1, 0, 1, 1, gbC, this, groupInfoPanel);
		this.addComponent(0, 1, 3, 3, gbC, this, calendarPanel);
		this.addComponent(3, 0, 1, 2, gbC, this, taskPanel);
		this.addComponent(3, 2, 1, 2, gbC, this, activityPanel);
		
		
		this.setVisible(true);
	}
	
	public TaskPanel getTaskPanel() {
		return taskPanel;
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
