package userInterface;

import java.awt.Color;
import java.awt.Font;

import util.Task.TaskPriority;

public class UIVisual {
	public static Color guiColor = new Color(131, 204, 237);
	public static Font titleFont = new Font("Sans-Serif", Font.BOLD, 24);
	
	public static Color noPriorColor = new Color(71, 218, 255);
	public static Color lowPriorColor = new Color(255, 255, 0);
	public static Color medPriorColor = new Color(255, 153, 0);
	public static Color highPriorColor = new Color(255, 0, 0);
	public static Color completeTaskColor = new Color(106, 255, 106);
	
	public static Color priorityAsColor(TaskPriority tp) {
		switch(tp) {
			case NONE:
				return noPriorColor;
			case LOW:
				return lowPriorColor;
			case MEDIUM:
				return medPriorColor;
			case HIGH:
				return highPriorColor;
			default:
				return noPriorColor;
		}
	}
}
