package util;

public class Date {
	private String year;
	private String month;
	private String day;
	private String hour;
	private String minute;
	
	private String[] shortMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	
	public Date(String s) {
		this.year = s.substring(0, 4);
		this.month = s.substring(5, 7);
		this.day = s.substring(8,10);
		this.hour = s.substring(11, 13);
		this.minute = s.substring(14, 16);
		
		
	}
	
	public Date(String y, String m, String d, String h, String min) {
		this.year = y;
		this.month = m;
		this.day = d;
		this.hour = h;
		this.minute = min;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}
	
	public int monthToInt() {
		switch(this.month) {
			case "Jan":
				return 1;
			case "Feb":
				return 2;
			case "Mar":
				return 3;
			case "Apr":
				return 4;
			case "May":
				return 5;
			case "Jun":
				return 6;
			case "Jul":
				return 7;
			case "Aug":
				return 8;
			case "Sep":
				return 9;
			case "Oct":
				return 10;
			case "Nov":
				return 11;
			case "Dec":
				return 12;
			default:
				return -1;
		}
	}
	
	public String asString(char c) {
		return this.day + c + this.month + c + this.year + " " + this.hour + ":" + this.minute;
	}
}
