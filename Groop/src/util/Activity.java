package util;
import java.util.Date;

import social.User;

public class Activity {
	
	private int id;
	private User user;
	private Task task;
	private Date timeStamp;
	private int timeSpend;
	private String description;
	private boolean verified;
	
	public Activity() {
		id = 0;
		user = new User();
		task = new Task();
		timeStamp = new Date();
		timeSpend = 0;
		description = "";
		verified = false;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int newId) {
		id = newId;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User newUser) {
		user = newUser;
	}
	
	public Task getTask() {
		return this.task;
	}
	
	public void setTask(Task newTask) {
		task = newTask;
	}
	
	public Date getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(Date newTimeStamp) {
		timeStamp = newTimeStamp;
	}
	
	public int getTimeSpend() {
		return timeSpend;
	}
	
	public void setTimeSpend(int newTimeSpend) {
		timeSpend = newTimeSpend;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String newDescription) {
		description = newDescription;
	}
	
	public boolean isVerified() {
		return verified;
	}
	
	public void verify(boolean verify) {
		verified = verify;
	}
	
	public void verify() {
		verified = true;
	}
}
