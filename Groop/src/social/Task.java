package social;

import java.util.List;

public class Task{
	private int id;
	private String title;
	private String description;
	private Deadline deadline;
	private User[] assignedTo;
	private boolean complete;
	
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
	
	public void setDeadline(String priority, int year, int month, int day, int hr, int min, int sec){
		Deadline deadline;
		deadline.setDueDate(year, month, day, hr, min, sec);
		deadline.setPriority(priority);
		
		this.deadline = deadline;
	}
	
	public deadline getDeadline(){
		return  deadline;
	}
	
	public void setAssignedTo(int User[] assignedTo){
		this.assignedTo = assignedTo;
	}
	
	public User[] AssignedTo(){
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