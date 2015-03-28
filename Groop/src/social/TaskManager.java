package social;

import java.util.List;

public class TaskManager{
	private Task task;
	private ActivityManager activity;
	
	public Task createTask(String title, String description, Deadline deadline){
		task = new Task();
		task.setTitle(title);
		task.setDescription(description);
		task.setDeadline( deadline);
		
		return task;
	}
	
	public void deleteTask(Task task){
		
		task = null;
	}
	
}