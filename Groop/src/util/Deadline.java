package util;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class Deadline extends Event {
	private String priority;
	
	public void setDueDate(int year, int month, int day, int hr, int min, int sec){
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, day, hr, min, sec);
		
		// this.dueDate = c;
	}
	
	public void setPriority(String newPriority){
		this.priority = newPriority;
	}
	
	public String getPriority(){
		return priority;
	}
}