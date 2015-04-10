package userInterface;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ActivityPanel extends JPanel{
	JLabel testLabel;
	
	public ActivityPanel() {
		testLabel = new JLabel("Activity Panel");
		
		this.add(testLabel);
		
		this.setBackground(Color.GREEN);
	}
}
