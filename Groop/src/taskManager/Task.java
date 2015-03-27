package taskManager;
package social;

import java.util.List;

public class Task{
	private int id;
	private String title;
	private String description;
	private Deadline deadline;
	private User[] assignedTo;
	private Task[] subTasks;
	private boolean complete;
	
	public void makeCompleted(){
		complete = true;
	}
	
}