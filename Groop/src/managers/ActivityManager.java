package managers;
import java.util.ArrayList;

import util.Activity;

public class ActivityManager {
	private ArrayList<Activity> activityLog;
	
	public ActivityManager() {
		activityLog = new ArrayList<Activity>();
	}
	
	public void newActivity() {
		activityLog.add(new Activity());
	}
	
	public ArrayList<Activity> getActivityLog() {
		return activityLog;
	}

	public void addActivity(Activity newActivity) {
		// TODO Auto-generated method stub
		this.activityLog.add(newActivity);
	}
}
