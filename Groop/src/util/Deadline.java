package util;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class Deadline{
	
	private String dueDate;
	private String priority;
	
	public void setDueDate(int year, int month, int day, int hr, int min, int sec){
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, day, hr, min, sec);
		
		// this.dueDate = c;
	}
	
	public String getDueDate(){
		return dueDate;
	}
	
	public void setPriority(String newPriority){
		this.priority = newPriority;
	}
	
	public String getPriority(){
		return priority;
	}
}