package userInterface;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarPanel extends JPanel{
	JLabel testLabel;
	
	public CalendarPanel() {
		testLabel = new JLabel("CalendarPanel");
		
		this.add(testLabel);
		
		this.setBackground(Color.MAGENTA);
	}
}
