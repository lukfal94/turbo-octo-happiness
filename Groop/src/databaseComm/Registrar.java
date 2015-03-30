package databaseComm;

import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import social.User;
import userInterface.LoginWindow;

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
}
