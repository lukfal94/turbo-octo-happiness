package util;

import java.util.Date;


public class Event {
	private int id;
	private String title;
	private String description;
	private Date date;
	private EventType type;
	
	public enum EventType {
		MEETING, DEADLINE
	}
}
