package managers;

import java.util.ArrayList;

import databaseComm.ServerResponse.ServerErrorMessage;
import social.Group;
import social.User;
import userInterface.GroopMainInterface;
import userInterface.GroopMainInterface.GuiMode;

/*
 * The session manager stores the active User object,
 * as well as a reference to the GroupManager. 
 * Contains methods to refresh the session and update
 * the other manager's information.
 */
public class SessionManager {
	private User activeUser;
	private Group activeGroup;
	private GuiMode guiMode;
	private GroupManager gm;
	
	private GroopMainInterface mainGUI;
	
	public SessionManager(User user) {
		this.activeUser = user;
		
		this.gm = new GroupManager();
		
		gm.setSessionManager(this);
		
		this.startSession();
	}
	
	private void startSession() {
		
		// Fetch the groups the User is in
		ServerErrorMessage syncStatus = null;
		
		try {
			syncStatus = gm.syncGroups();
		} catch( Exception ex) {
			System.out.println("Error: Could not sync groups");
		}
		if(syncStatus == ServerErrorMessage.NO_GROUPS) {
			setGuiMode(GuiMode.BLANK);
		}
		else if(syncStatus == ServerErrorMessage.NO_ERROR) {
			// Set the active group to the first group.
			setActiveGroup(gm.getGroups().get(0));
			setGuiMode(GuiMode.STANDARD);
			
			System.out.println(gm.getGroups().get(0).getName());
		}
		
		// Tasks
		for(Group g : gm.getGroups()) {
			try {
				syncStatus = g.getTaskManager().syncTasks(g.getId());
			} catch( Exception ex) {
				System.out.println(">>" + ex);
				System.out.println("Error: Could not sync tasks for group");
			}
		}
	}
	
	public GroupManager getGroupManager() {
		return this.gm;
	}
	public ArrayList<Group> getGroups() {
		return this.gm.getGroups();
	}

	public Group getActiveGroup() {
		return activeGroup;
	}

	public void setActiveGroup(Group activeGroup) {
		this.activeGroup = activeGroup;
	}

	public GuiMode getGuiMode() {
		return guiMode;
	}

	public void setGuiMode(GuiMode guiMode) {
		this.guiMode = guiMode;
	}

	public User getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(User activeUser) {
		this.activeUser = activeUser;
	}

	public void switchGroup(Group group) {
		// TODO Auto-generated method stub
		this.activeGroup = group;
	}

	public void refreshSession() {
		// TODO Auto-generated method stub
		mainGUI.refreshInterface();
	}

	public void setMainGUI(GroopMainInterface groopMainInterface) {
		// TODO Auto-generated method stub
		this.mainGUI = groopMainInterface;
	}
}
