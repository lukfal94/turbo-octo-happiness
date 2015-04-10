package managers;

import java.util.ArrayList;

import databaseComm.ServerResponse.ServerErrorMessage;
import social.Group;
import social.User;

/*
 * The session manager stores the active User object,
 * as well as a reference to the GroupManager. 
 * Contains methods to refresh the session and update
 * the other manager's information.
 */
public class SessionManager {
	private User activeUser;
	private Group activeGroup;
	private GroupManager gm;
	
	public SessionManager(User user) {
		this.activeUser = user;
		this.gm = new GroupManager();
		
		gm.setSessionManager(this);
		
		ServerErrorMessage syncStatus = null;
		
		try {
			syncStatus = gm.syncGroups();
		} catch( Exception ex) {
			System.out.println(ex);
		}
		
		if(syncStatus == ServerErrorMessage.NO_GROUPS) {

		}
		else if(syncStatus == ServerErrorMessage.NO_ERROR) {
			// Set the active group to the first group.
			setActiveGroup(gm.getGroups().get(0));
			
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
}
