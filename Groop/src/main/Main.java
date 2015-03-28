package main;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import social.User;
import userInterface.LoginWindow;

public class Main {
	public static void main(String args[]) throws JsonMappingException, JsonGenerationException, IOException{
		LoginWindow loginFrame = new LoginWindow();
//		User test = new User();
//		test.setFirstname("Luke");
//		ObjectMapper om = new ObjectMapper();
//		
//		om.writeValue(new File("user.json"), test);
	}
}
