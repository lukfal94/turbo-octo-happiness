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

import social.User;

public class GroopMainInterface extends JFrame{
	private User activeUser;
	private GridBagLayout gbLayout;
	private GridBagConstraints gbC;
	
	private UserInfoPanel userInfoPanel;
	private ActivityPanel activityPanel;
	private TaskPanel taskPanel;
	private CalendarPanel calendarPanel;
	
	public GroopMainInterface(User user) {
		activeUser = user;
		initComponents();
	}
	
	private void initComponents() {
		this.setTitle("Groop - " + activeUser.getUsername());

		this.setSize(1200, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Center the frame in the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		gbLayout = new GridBagLayout();
		gbC = new GridBagConstraints();
		
		this.setLayout(gbLayout);
		
		userInfoPanel = new UserInfoPanel();
		activityPanel = new ActivityPanel();
		taskPanel = new TaskPanel();
		calendarPanel = new CalendarPanel();
		
		this.addComponent(0, 0, 4, 3, 0, 0, new Insets(0, 0, 0, 0), 0, 0, gbC, this, userInfoPanel);
		this.addComponent(0, 3, 8, 6, 0, 0, new Insets(5, 5, 5, 5), 0, 0, gbC, this, activityPanel);
		this.addComponent(0, 9, 8, 6, 0, 0, new Insets(0, 0, 0, 0), 0, 0, gbC, this, taskPanel);
		this.setVisible(true);
	}
	
	public TaskPanel getTaskPanel() {
		return taskPanel;
	}

	public void setTaskPanel(TaskPanel taskPanel) {
		this.taskPanel = taskPanel;
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
