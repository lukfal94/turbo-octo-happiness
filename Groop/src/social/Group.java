package social;

import managers.ActivityManager;
import managers.SessionManager;
import managers.TaskManager;

public class Group {
	private int id;
	private int creatorID;
	private User creator;
	private String name;
	private String description;
	private User[] members;
	private User[] admins;
	
	private TaskManager taskManager;
	private ActivityManager activityManager;
	
	public Group() {
		this.taskManager = new TaskManager();
		this.activityManager = new ActivityManager();
	}
	public int getId() {
		return this.id;
	}

	public TaskManager getTaskManager() {
		return taskManager;
	}

	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}

	public ActivityManager getActivityManager() {
		return activityManager;
	}

	public void setActivityManager(ActivityManager activityManager) {
		this.activityManager = activityManager;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User[] getMembers() {
		return members;
	}

	public void setMembers(User[] members) {
		this.members = members;
	}

	public User[] getAdmins() {
		return admins;
	}

	public void setAdmins(User[] admins) {
		this.admins = admins;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCreatorID() {
		return creatorID;
	}

	public void setCreatorID(int creatorID) {
		this.creatorID = creatorID;
	}
	
}
