package social;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import databaseComm.ServerResponse;
import managers.ActivityManager;
import managers.EventManager;
import managers.SessionManager;
import managers.TaskManager;

public class Group {
	private int id;
	private User creator;
	private String name;
	private String description;
	private ArrayList<User> members;
	private ArrayList<User> admins;
	
	private TaskManager taskManager;
	private ActivityManager activityManager;
	private EventManager eventManager;
	
	public Group() {
		this.taskManager = new TaskManager();
		this.activityManager = new ActivityManager();
		this.eventManager = new EventManager();
		
		this.members = new ArrayList<User>();
		this.admins = new ArrayList<User>();
	}
	
	// Queries the server to update its listing of members
	public void syncMembers() {
		// TODO Auto-generated method stub
		ServerResponse response = null;
		ArrayList<User> users = null;
		ObjectMapper mapper = new ObjectMapper();
		
		URL jsonUrl;
		
		try {
			jsonUrl = new URL("http://www.lukefallon.com/groop/api/groups.php?mode=2&gid=" + this.id);
		} catch(Exception ex) {
			System.out.println(ex);
			return;
		}
		
		try {
			users = mapper.readValue(jsonUrl, new TypeReference<ArrayList<User>>() { });
			this.members = (ArrayList<User>) users;
			System.out.println("syncMembers() successful ");
		} catch(Exception ex) {
			try {
				response = mapper.readValue(jsonUrl, ServerResponse.class);
			} catch (Exception ex2) {
				System.out.println(ex);
			}
		}
	}
	
	public Object addUser(String username) throws JsonParseException, JsonMappingException, IOException {
		ServerResponse response;
		User newUser;
		ObjectMapper mapper = new ObjectMapper();
		
		URL jsonUrl = new URL("http://www.lukefallon.com/groop/api/groups.php?mode=3&username=" + username +"&gid=" + this.id);
		
		try {
			newUser = mapper.readValue(jsonUrl, User.class);
			return newUser;
		} catch (Exception ex) {
			response = mapper.readValue(jsonUrl, ServerResponse.class);
			return response;
		}
	}
	public int getId() {
		return this.id;
	}

	public TaskManager getTaskManager() {
		return taskManager;
	}

	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}

	public ActivityManager getActivityManager() {
		return activityManager;
	}

	public void setActivityManager(ActivityManager activityManager) {
		this.activityManager = activityManager;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<User> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<User> members) {
		this.members = members;
	}

	public ArrayList<User> getAdmins() {
		return admins;
	}

	public void setAdmins(ArrayList<User> admins) {
		this.admins = admins;
	}

	public EventManager getEventManager() {
		return eventManager;
	}

	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
	}
}
