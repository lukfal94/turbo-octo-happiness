package managers;

import social.User;

/*
 * The session manager stores the active User object,
 * as well as a reference to the GroupManager. 
 * Contains methods to refresh the session and update
 * the other manager's information.
 */
public class SessionManager {
	private User activeUser;
	private GroupManager gm;
}
