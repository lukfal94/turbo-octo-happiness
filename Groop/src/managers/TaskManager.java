package managers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import databaseComm.ServerResponse;
import databaseComm.ServerResponse.ServerErrorMessage;
import social.Group;
import social.User;
import util.Deadline;
import util.Task;

public class TaskManager{
	private ArrayList<Task> tasks;
	private ActivityManager activityManager;
	
	// constructor
	public TaskManager(){
		this.tasks = new ArrayList<Task>();
	}
	
	public void addTask(Task task) {
		this.tasks.add(task);
	}
	
	public ArrayList<Task> getTasks(){
		return this.tasks;
	}
	
	public void deleteTask(Task task){
		
		task = null;
	}
	
	public ActivityManager getActivityManager() {
		return activityManager;
	}

	public ServerErrorMessage syncTasks(Group g) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		ServerResponse response = null;
		List<Task> tasks = null;

		URL jsonUrl = new URL("http://www.lukefallon.com/groop/api/tasks.php?mode=2&gid=" + g.getId());

		try {
			tasks = mapper.readValue(jsonUrl, new TypeReference<ArrayList<Task>>() { });	
			this.tasks = (ArrayList<Task>) tasks;
			return ServerErrorMessage.NO_ERROR;
		} catch (Exception ex) {
			System.out.println(ex);
			response = mapper.readValue(jsonUrl, ServerResponse.class);
			return response.getServerErrorMessage();
		}
	}

	public ServerErrorMessage assignTask(Task task, User activeUser) throws JsonParseException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();
		ServerResponse response = null;

		URL jsonUrl = new URL("http://www.lukefallon.com/groop/api/tasks.php?mode=4&tid=" + task.getId() + "&uid=" + activeUser.getId());

		try {
			response = mapper.readValue(jsonUrl, ServerResponse.class);
			return response.getServerErrorMessage();
		} catch (Exception ex) {
			System.out.println(ex);
			response = mapper.readValue(jsonUrl, ServerResponse.class);
			return response.getServerErrorMessage();
		}
	}
}