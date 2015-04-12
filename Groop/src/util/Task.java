package util;

import java.util.ArrayList;
import java.util.List;

import social.User;

public class Task{
	private int id;
	private int createdBy;
	private String title;
	private String description;
	private Deadline deadline;
	private ArrayList<User> assignedTo;
	private boolean completed;
	
	public enum TaskPriority {
		NONE, LOW, MEDIUM, HIGH
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Deadline getDeadline() {
		return deadline;
	}

	public void setDeadline(Deadline deadline) {
		this.deadline = deadline;
	}

	public ArrayList<User> getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(ArrayList<User> assignedTo) {
		this.assignedTo = assignedTo;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}