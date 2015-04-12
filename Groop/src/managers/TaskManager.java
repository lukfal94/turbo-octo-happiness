package managers;

import java.util.ArrayList;
import java.util.List;

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
	
}