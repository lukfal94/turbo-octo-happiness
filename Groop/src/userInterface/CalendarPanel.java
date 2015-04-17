package userInterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.util.Calendar;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import social.User;
import userInterface.CalendarPanel.EventWindow.UserSelector;

public class CalendarPanel extends JPanel{
	private JLabel testLabel;
	
	// Month Names (short)
	private String[] shortMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	
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
		private JTextArea descriptionTextArea, locationTextArea;
		private JComboBox<String> dayCombo, hourCombo, minCombo;
		private JComboBox<String> monthCombo, yearCombo, amPmCombo;
		private JComboBox<JPanel> inviteCombo;
		private ComboBoxRenderer renderer;
		
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
			int i, year;
			
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
			inviteLabel = new JLabel("Select who to invite");
			locationLabel = new JLabel("Location");
			
			// Init input members
			titleTextField = new JTextField(20);
			descriptionTextArea = new JTextArea(3, 40);
			locationTextArea = new JTextArea(3,40);
			
			// Date Combos
			dayCombo = new JComboBox<String>();
			monthCombo = new JComboBox<String>();
			yearCombo = new JComboBox<String>();
			
			dayCombo.setMaximumRowCount(12);
			monthCombo.setMaximumRowCount(8);
			yearCombo.setMaximumRowCount(6);
			
			dayCombo.addItem("Day");
			for(i = 1; i <= 31; i++) {
				dayCombo.addItem(Integer.toString(i));
			}
			
			monthCombo.addItem("Month");
			for(i = 0; i < shortMonths.length; i++) {
				monthCombo.addItem(shortMonths[i]);
			}
			
			year = Calendar.getInstance().get(Calendar.YEAR);
			yearCombo.addItem("Year");
			for(i = year; i < year + 10; i++) {
				yearCombo.addItem(Integer.toString(i));
			}
			
			// Handle the user invite JCombo
			inviteCombo = new JComboBox<JPanel>();
			
			ComboBoxRenderer renderer = new ComboBoxRenderer();
			renderer.setPreferredSize(new Dimension(100, 40));
			inviteCombo.setRenderer(renderer);
			inviteCombo.setMaximumRowCount(6);
			
			mainPanel.add(titleLabel);
			mainPanel.add(titleTextField);
			mainPanel.add(dateLabel);
			mainPanel.add(dayCombo);
			mainPanel.add(monthCombo);
			mainPanel.add(yearCombo);
			mainPanel.add(descriptionLabel);
			mainPanel.add(descriptionTextArea);
			mainPanel.add(inviteLabel);
			mainPanel.add(inviteCombo);
			
			this.add(mainPanel);
		}
		
		public class UserSelector extends JPanel {
			private User user;
			private JLabel nameLabel;
			private JCheckBox checkBox;
			
			public UserSelector(User u) {
				this.user = u;
				
				nameLabel = new JLabel(u.getFullname());
				checkBox = new JCheckBox();
				
				this.add(checkBox);
				this.add(nameLabel);
			}
		}
		
		private class ComboBoxRenderer extends JPanel implements ListCellRenderer {

			@Override
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				// TODO Auto-generated method stub
				
				
				return null;
			}
		}

	}
}
