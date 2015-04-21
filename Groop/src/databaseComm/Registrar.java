package databaseComm;

import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import social.User;
import social.Group;
import userInterface.LoginWindow;
import util.Task;
import util.Task.TaskPriority;
import util.Date;

// Communicates w/ the Database to verify user when logging in, or
// to register a new user if they are not in the database.
//
public class Registrar {
	ObjectMapper mapper;
	LoginWindow loginWindow;
	
	public Registrar() {
		mapper = new ObjectMapper();
	}
	
	public Object login(String username, String pwd) 
			throws JsonParseException, JsonMappingException, IOException{
		User user = null;
		ServerResponse response = null;
		URL jsonUrl = new URL("http://www.lukefallon.com/groop/api/login.php?username=" 
				+ username + "&password=" + pwd);

		try {
			user = mapper.readValue(jsonUrl, User.class);
			return user;
		} catch(Exception ex) {
			response = mapper.readValue(jsonUrl, ServerResponse.class);
			return response;
		}
	}
	
	public Object registerUser(String username, String firstname, String lastname,
			String email, String organization, String phonenumber, String password) 
			throws JsonParseException, JsonMappingException, IOException {
		User user = null;
		ServerResponse response = null;
		URL jsonUrl = new URL("http://www.lukefallon.com/groop/api/register.php?" +
				"username=" + username + "&firstname=" + firstname +
				"&lastname=" + lastname + "&email=" + email + "&organization=" + organization +
				"&phonenumber=" + phonenumber + "&password=" + password);

		try {
			user = mapper.readValue(jsonUrl, User.class);
			return user;
		} catch(Exception ex) {
			response = mapper.readValue(jsonUrl, ServerResponse.class);
			return response;
		}
	}
	
	public User getUserByID(int id) throws JsonParseException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		User usr = null;
		URL jsonUrl = null;
		
		jsonUrl = new URL("http://www.lukefallon.com/groop/api/users.php?id=" + id);
		usr = mapper.readValue(jsonUrl, User.class);
		
		return usr;
	}
	
	public Object addNewTask(Group group, User user, String title, String description, Date deadline, int priority)
			throws JsonParseException, JsonMappingException, IOException {
		Task task = null;
		ServerResponse response = null;
		URL jsonUrl = null;
		
		String urlStr = "http://www.lukefallon.com/groop/api/tasks.php?mode=0&gid=" + group.getId()
				+ "&uid=" + user.getId() + "&title=" + title + "&desc=" + description 
				+ "&year=" + deadline.getYear() + "&month=" + deadline.monthToInt() + 
				"&day=" + deadline.getDay() + "&hour=" + deadline.getHour() + 
				"&min=" + deadline.getMinute() + "&prior=" + priority;

		URL url = new URL(urlStr);
		try {
			URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			jsonUrl = uri.toURL();
		} catch ( Exception ex) {
			System.out.println("Error parsing url");
		}
		
		System.out.println(jsonUrl);
		
		try {
			task = mapper.readValue(jsonUrl, Task.class);
			return task;
		} catch(Exception ex) {
			response = mapper.readValue(jsonUrl, ServerResponse.class);
			return response;
		}

	}
	
	public Object getTaskByID(int id) 
			throws JsonParseException, JsonMappingException, IOException{
		Task task = null;
		ServerResponse response = null;
		URL jsonUrl = new URL("http://www.lukefallon.com/groop/api/tasks.php?id="+id);

		try {
			task = mapper.readValue(jsonUrl, Task.class);
			return task;
		} catch(Exception ex) {
			response = mapper.readValue(jsonUrl, ServerResponse.class);
			return response;
		}
	}

	public Object addNewActivity() {
		// TODO Auto-generated method stub
		return null;
	}
}
