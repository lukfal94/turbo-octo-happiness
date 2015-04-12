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
	public Task createTask(String title, String description, Deadline deadline){
		Task task = new Task();
		task.setTitle(title);
		task.setDescription(description);
		task.setDeadline(deadline);
		
		return task;
	}
	
	public void createTasks(int numTasks){
		for(int i = 0; i < numTasks; i++){
			this.tasks.add(createTask("","",null));
		}
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