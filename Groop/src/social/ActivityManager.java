import java.util.ArrayList;

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
}
