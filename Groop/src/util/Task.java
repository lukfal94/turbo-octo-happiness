package util;

import java.util.ArrayList;
import java.util.List;

import social.User;

public class Task{
	private int id;
	private String title;
	private String description;
	private Deadline deadline;
	private ArrayList<User> assignedTo;
	private boolean complete;
	
	public enum TaskPriority {
		NONE, LOW, MEDIUM, HIGH
	}
	// constructor
	public Task(){
		
		id = 0;
		title = "";
		description = "";
		deadline = new Deadline();
		assignedTo = new ArrayList<User>();
		complete = false;
		
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return  id;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return  title;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return  description;
	}
	
	public void setDeadline(Deadline deadline){
		this.deadline = deadline;
	}
	
	public Deadline getDeadline(){
		return  deadline;
	}
	
	public void addToAssignedTo(User user){
		this.assignedTo.add(user);
	}
	
	public void removeFromAssignedTo(User user){
		//This needs to be setup
	}
	
	public ArrayList<User> getAssignedTo(){
		return  assignedTo;
	}
	
	public void setComplete(boolean complete){
		this.complete = complete;
	}
	
	public boolean getComplete(){
		return  complete;
	}
	
	public void makeCompleted(){
		this.complete = true;
	}
	
}