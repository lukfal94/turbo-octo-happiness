package social;

import managers.ActivityManager;
import managers.TaskManager;

public class Group {
	private int id;
	private User creator;
	private String name;
	private String description;
	private User[] members;
	
	private TaskManager taskManager;
	private ActivityManager activityManager;
	
	
}
