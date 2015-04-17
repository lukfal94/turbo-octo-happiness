package userInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CalendarPanel extends JPanel{
	JLabel testLabel;
	
	public CalendarPanel() {
		testLabel = new JLabel("CalendarPanel");
		
		this.add(testLabel);
		
		this.setBackground(Color.MAGENTA);
	}
	
	public enum EventWindowMode {
		CREATE_EVENT
	}
	
	public void openEventWindow(EventWindowMode mode) {
		EventWindow window = new EventWindow(mode);
	}
	
	public class EventWindow extends JFrame {
		private JPanel mainPanel;
		private EventWindowMode mode;
		
		private JLabel titleLabel, descriptionLabel, dateLabel, inviteLabel, locationLabel;
		private JTextField titleTextField;
		private JTextArea descriptionTextField, locationTextArea;
		private JComboBox dayCombo, monthCombo, yearCombo, hourCombo, minCombo, amPmCombo, inviteCombo;
		
		private GridBagLayout gbLayout;
		private GridBagConstraints gbC;
		
		public EventWindow(EventWindowMode mode) {
			this.mode = mode;
			
			initCommonComponents();
			
			if(this.mode == EventWindowMode.CREATE_EVENT) {
				
			}
			
			this.setSize(600, 500);
			
			// Center the frame in the screen
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

			this.setBackground(Color.CYAN);
			
			this.setVisible(true);
		}

		private void initCommonComponents() {
			// TODO Auto-generated method stub
			
			// Init Main Panel
			mainPanel = new JPanel();
			mainPanel.setBackground(Color.YELLOW);
			
			gbLayout = new GridBagLayout();
			gbC = new GridBagConstraints();
			
			mainPanel.setLayout(gbLayout);
			
			// Init Labels
			titleLabel = new JLabel("Title");
			descriptionLabel = new JLabel("Description");
			dateLabel = new JLabel("Date");
			inviteLabel = new JLabel("Select Who to Invite");
			locationLabel = new JLabel("Location");
			
			// Init input members
			titleTextField = new JTextField(20);
			descriptionTextField = new JTextArea(3, 40);
			locationTextArea = new JTextArea(3,40);
			dayCombo = new JComboBox<String>();
			
		}

	}
}
