package managers;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import social.Group;
import social.User;
import util.Task;
import databaseComm.Registrar;
import databaseComm.ServerResponse;
import databaseComm.ServerResponse.ServerErrorMessage;

public class GroupManager {
	private ArrayList<Group> groups;
	private User user;
	private SessionManager sessionManager;
	private ObjectMapper mapper;
	
	public GroupManager() {
		mapper = new ObjectMapper();
		groups = new ArrayList<Group>();
	}
	
	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
		this.user = sessionManager.getActiveUser();
	}
	
	public Object createGroup(User user, String title, String desc) throws JsonParseException, JsonMappingException, IOException {
		Group group = null;
		ServerResponse response = null;
		URL jsonUrl = null;

		String urlStr = "http://www.lukefallon.com/groop/api/groups.php?mode=0&cid=" + user.getId() + "&title=" + title + "&desc=" + desc;

		URL url = new URL(urlStr);
		try {
			URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			jsonUrl = uri.toURL();
		} catch ( Exception ex) {
			System.out.println("Error parsing url");
		}
		System.out.println(jsonUrl.toString());
		
		try {
			group = mapper.readValue(jsonUrl, Group.class);
			return group;
		} catch(Exception ex) {
			response = mapper.readValue(jsonUrl, ServerResponse.class);
			return response;
		}
	}
	
	// Returns ServerErrorMessage.NO_ERROR if sync is successful, .NO_GROUPS otherwise.
	public ServerErrorMessage syncGroups() throws JsonParseException, JsonMappingException, IOException {
		ServerResponse response = null;
		List<Group> groups = null;
		
		URL jsonUrl = new URL("http://www.lukefallon.com/groop/api/groups.php?mode=2&uid=" + user.getId());
		
		try {
			groups = mapper.readValue(jsonUrl, new TypeReference<ArrayList<Group>>() { });
			this.groups = (ArrayList<Group>) groups;
			return ServerErrorMessage.NO_ERROR;
		} catch (Exception ex) {
			System.out.println(">>" + ex);
			response = mapper.readValue(jsonUrl, ServerResponse.class);
			return response.getServerErrorMessage();
		}
	}
	
	public void addGroup(Group g) {
		this.groups.add(g);
	}
	
	public ArrayList<Group> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}

	public ServerErrorMessage deleteGroup(Group g) throws JsonParseException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		ServerResponse response = null;
		
		URL jsonUrl = new URL("http://www.lukefallon.com/groop/api/groups.php?mode=4&gid=" + g.getId());
		
		System.out.println(jsonUrl);
		
		try {
			response = mapper.readValue(jsonUrl, ServerResponse.class);
		} catch (Exception ex) {
			System.out.println(ex);
			response = mapper.readValue(jsonUrl, ServerResponse.class);
			return response.getServerErrorMessage();
		}
		
		if(response.getServerErrorMessage() == ServerErrorMessage.NO_ERROR) {
			this.getGroups().remove(g);
		}
			
		return response.getServerErrorMessage();
	}

}





