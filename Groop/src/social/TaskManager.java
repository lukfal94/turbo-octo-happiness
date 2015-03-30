package social;

import java.util.List;

public class TaskManager{
	private ArrayList<Task> tasks;
	private ActivityManager activityManager;
	
	// constructor
	public TaskManager(){
		this.tasks = new ArrayList<Task>();
		this.activity = new ActivityManager;
	}
	
	public Task createTask(String title, String description, Deadline deadline){
		Task task = new Task();
		task.setTitle(title);
		task.setDescription(description);
		task.setDeadline(deadline);
		
		return task;
	}
	
	public void createTasks(int numTasks){
		for(int i = 0; i < numTasks){
			this.tasks.add(createTask("","",null));
		}
	}
	
	public ArrayList<Task> getTasks(){
		return this.tasks
	}
	
	public void deleteTask(Task task){
		
		task = null;
	}
	
	public ActivityManager getAvtivityManager() {
		return activityManager;
	}
	
}