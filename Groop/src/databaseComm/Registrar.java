package databaseComm;

import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import social.User;

// Communicates w/ the Database to verify user when logging in, or
// to register a new user if they are not in the database.
//
public class Registrar {
	ObjectMapper mapper;
	
	public Registrar() {
		mapper = new ObjectMapper();
	}
	public User login(String username, String pwd) throws JsonParseException, JsonMappingException, IOException{
		User user = null;
		URL jsonUrl = new URL("http://www.lukefallon.com/groop/api/login.php?id=" 
				+ username + "&password=" + pwd);
		user = mapper.readValue(jsonUrl, User.class);
		
		return user;
	}

	public User getUserByID(int id) {
		// TODO Auto-generated method stub
		User usr = null;
		URL jsonUrl = null;
		try {
			jsonUrl = new URL("http://www.lukefallon.com/groop/api/users.php?id=" + id);
		} catch(Exception ex) {
			System.out.println(ex);
		}
		try {
			usr = mapper.readValue(jsonUrl, User.class);
		} catch(Exception ex) {
			System.out.println(ex);
		}
		return usr;
	}
}
