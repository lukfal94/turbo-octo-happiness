package managers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import social.Group;
import databaseComm.ServerResponse;
import databaseComm.ServerResponse.ServerErrorMessage;

public class GroupManager {
	private ArrayList<Group> groups;
	private int userID;
	private SessionManager sessionManager;
	private ObjectMapper mapper;
	
	public GroupManager() {
		mapper = new ObjectMapper();
	}
	
	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
		this.userID = sessionManager.getUser().getId();
	}
	
	// Returns ServerErrorMessage.NO_ERROR if sync is successful, .NO_GROUPS otherwise.
	public ServerErrorMessage syncGroups() throws JsonParseException, JsonMappingException, IOException {
		ServerResponse response = null;
		List<Group> groups = null;
		
		URL jsonUrl = new URL("http://www.lukefallon.com/groop/api/groups.php?uid=" + userID);
		
		try {
			groups = mapper.readValue(jsonUrl, new TypeReference<ArrayList<Group>>() { });
			
			this.groups = (ArrayList<Group>) groups;
			return ServerErrorMessage.NO_ERROR;
		} catch (Exception ex) {
			System.out.println(ex);
			response = mapper.readValue(jsonUrl, ServerResponse.class);
			return response.getServerErrorMessage();
		}
	}

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}

}





