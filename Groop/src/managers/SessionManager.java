package managers;

import java.util.ArrayList;

import databaseComm.ServerResponse.ServerErrorMessage;
import social.Group;
import social.User;
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
	
	public SessionManager(User user) {
		this.activeUser = user;
		this.gm = new GroupManager();
		
		gm.setSessionManager(this);
		
		ServerErrorMessage syncGroups = null;
		
		try {
			syncGroups = gm.syncGroups();
		} catch( Exception ex) {
			
		}
		
		if(syncGroups == ServerErrorMessage.NO_GROUPS) {
			setGuiMode(GuiMode.BLANK);
		}
		else if(syncGroups == ServerErrorMessage.NO_ERROR) {
			// Set the active group to the first group.
			setActiveGroup(gm.getGroups().get(0));
			setGuiMode(GuiMode.STANDARD);
			
			System.out.println(gm.getGroups().get(0).getName());
		}
	}
	
	public User getUser() {
		return this.activeUser;
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
}
