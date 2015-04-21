package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JCheckBox;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import managers.SessionManager;
import social.User;
import userInterface.CalendarPanel.EventWindow.UserSelector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class CalendarPanel extends JPanel{
	
    static JButton prevBtn, nextBtn;
    static JTable calendarTable;
    static JComboBox yearBox;
    static JFrame mainFrame;
    static JScrollPane tableSCale; //The scrollpane
    static JPanel calendarPanel;
    static int yearReal, monthReal, dayReal, yearCur, mthCur;
    static JLabel monthLbl, yearLbl;

    static Container pane;
    static DefaultTableModel tableMCalendar; //Table model
    
    static Hashtable <String, List>  eventRowHash = new Hashtable<String, List>();
    static Hashtable <String, String>  eventNameHash = new Hashtable<String, String>();
    
	private SessionManager sessionManager;
	private JLabel testLabel;
	
	// Month Names (short)
	private String[] shortMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	
	public CalendarPanel(SessionManager sm) {
		this.sessionManager = sm;
		
		testLabel = new JLabel("CalendarPanel");
		
		this.add(testLabel);
		
		this.setBackground(Color.MAGENTA);
		this.initCalendar();
	}
	
	private void initCalendar() {
		this.setSize(new Dimension(400,200));
		
		//Look and feel
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException e) {} 
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}
        
        //Prepare frame
        mainFrame = new JFrame (""); //Create frame
        pane = mainFrame.getContentPane(); //Get content pane
        this.setLayout(new BorderLayout()); //Apply null layout
        this.setPreferredSize((new Dimension(200, 200) ));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close when X is clicked
        
        //Create controls
        monthLbl = new JLabel ("January");
        yearLbl = new JLabel ("Change year:");
        yearBox = new JComboBox();
        prevBtn = new JButton ("<<");
        nextBtn = new JButton (">>");
        tableMCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
        calendarTable = new JTable(tableMCalendar);
        tableSCale = new JScrollPane(calendarTable);
        calendarPanel = new JPanel(null);
        
        //Set border
        
        //Register action listeners
        prevBtn.addActionListener(new prevBtn_Action());
        nextBtn.addActionListener(new nextBtn_Action());
        yearBox.addActionListener(new yearBox_Action());
        
        //Add controls to pane
        pane.add(this);
        this.add(monthLbl);
        this.add(yearLbl);
        this.add(yearBox);
        this.add(prevBtn);
        this.add(nextBtn);
        this.add(tableSCale);
        
        //Set bounds
        this.setBounds(0, 0, 300, 335);
        monthLbl.setBounds(360-monthLbl.getPreferredSize().width/2, 300, 100, 25);
        yearLbl.setBounds(210, 355, 80, 20);
        yearBox.setBounds(430, 355, 80, 20);
        prevBtn.setBounds(210, 400, 50, 25);
        nextBtn.setBounds(450, 400, 50, 25);
        tableSCale.setBounds(210, 50, 300, 250);
        
        //Make frame visible
        mainFrame.setResizable(true);
        mainFrame.setVisible(false);
        
        //Get real month/year
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        dayReal = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
        monthReal = cal.get(GregorianCalendar.MONTH); //Get month
        yearReal = cal.get(GregorianCalendar.YEAR); //Get year
        mthCur = monthReal; //Match month and year
        yearCur = yearReal;
        
        //Add headers
        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
        for (int i=0; i<7; i++){
            tableMCalendar.addColumn(headers[i]);
        }
        
        calendarTable.getParent().setBackground(calendarTable.getBackground()); //Set background
        
        //No resize/reorder
        calendarTable.getTableHeader().setResizingAllowed(false);
        calendarTable.getTableHeader().setReorderingAllowed(false);
        
        //Single cell selection
        calendarTable.setColumnSelectionAllowed(true);
        calendarTable.setRowSelectionAllowed(true);
        calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //Set row/column count
        calendarTable.setRowHeight(48);
        tableMCalendar.setColumnCount(7);
        tableMCalendar.setRowCount(6);
        
        //Populate table
        for (int i=yearReal-100; i<=yearReal+100; i++){
            yearBox.addItem(String.valueOf(i));
        }

        //Refresh calendar
        calRefresh (monthReal, yearReal); //Refresh calendar
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
		
		private JComboBox inviteCombo;
		private ArrayList<UserSelector> invitees;
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
			invitees = new ArrayList<UserSelector>();
			for(User u : sessionManager.getActiveGroup().getMembers()) {
				invitees.add(new UserSelector(u));
			}
			
			inviteCombo = new JComboBox();
			
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

	
	public static void calRefresh(int month, int year){
        //Variables
        String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int nod, som; //Number Of Days, Start Of Month
        
        //Allow/disallow buttons
        prevBtn.setEnabled(true);
        nextBtn.setEnabled(true);
        if (month == 0 && year <= yearReal-10){prevBtn.setEnabled(false);} //Too early
        if (month == 11 && year >= yearReal+100){nextBtn.setEnabled(false);} //Too late
        monthLbl.setText(months[month]); //Refresh the month label (at the top)
        monthLbl.setBounds(160-monthLbl.getPreferredSize().width/2, 355, 180, 25); //Re-align label with calendar
        yearBox.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
        
        String dateNoDelims = Integer.toString(month + 1) + Integer.toString(year);
        
        List <Integer>  rowList = new ArrayList<Integer>();
        
        
        
        if(eventRowHash.containsKey(dateNoDelims))
        {
        	 rowList = (List<Integer>) eventRowHash.get(dateNoDelims);
        }
  
        	
        //Clear table
        for (int i=0; i<6; i++){
            for (int j=0; j<7; j++){
            	if(!rowList.contains(i))
            		tableMCalendar.setValueAt(null, i, j);
            }
        }
        
        //Get first day of month and number of days
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        som = cal.get(GregorianCalendar.DAY_OF_WEEK);
        
        String dateStoreNoDelimsDay;
        
        //Draw calendar
        for (int i=1; i<=nod; i++){
            int row = new Integer((i+som-2)/7);
            int column  =  (i+som-2)%7;
            
            dateStoreNoDelimsDay = Integer.toString(i) + Integer.toString(month +1) + Integer.toString(year);
            
            if(!rowList.contains(i))
            	tableMCalendar.setValueAt(i, row, column);
            else 
            {
            	tableMCalendar.setValueAt(Integer.toString(i) + " " + eventNameHash.get(dateStoreNoDelimsDay), row, column);
            }
        }
       
        //Apply renderers
        calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new renderCalendarTbl());
    }
    
    static class prevBtn_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (mthCur == 0){ //Back one year
                mthCur = 11;
                yearCur -= 1;
            }
            else{ //Back one month
                mthCur -= 1;
            }
            calRefresh(mthCur, yearCur);
        }
    }
    
    static class renderCalendarTbl extends DefaultTableCellRenderer{
        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 0 || column == 6){ //Week-end
                setBackground(new Color(255, 220, 220));
            }
            else{ //Week
                setBackground(new Color(255, 255, 255));
            }
            
            
            if (value != null){
            	
            	try{
	                if (Integer.parseInt(value.toString()) == dayReal && mthCur == monthReal && yearCur == yearReal){ //Today
	                    setBackground(new Color(220, 220, 255));
	                }
	            }
                catch(NumberFormatException nfe)  
                {  
            	    
            	}  
            }
            setBorder(null);
            setForeground(Color.black);
            return this;
        }
    }
    

    static class yearBox_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (yearBox.getSelectedItem() != null){
                String b = yearBox.getSelectedItem().toString();
                yearCur = Integer.parseInt(b);
                calRefresh(mthCur, yearCur);
            }
        }
    }
    static class nextBtn_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (mthCur == 11){ //Foward one year
                mthCur = 0;
                yearCur += 1;
            }
            else{ //Foward one month
                mthCur += 1;
            }
            calRefresh(mthCur, yearCur);
        }
    }

    public static void addEvent(String newEvent, String dateDue)
    {
    	String delims = "[/, :]";
    	String[] tokens = new String [4];
    	tokens = dateDue.split(delims);
    	
    	
    	int day = Integer.parseInt(tokens[1]);
    	int month = Integer.parseInt(tokens[0]);
    	int year = Integer.parseInt(tokens[2]);
    	
    	System.out.println(dateDue + " " + day + " " + month + " " + year);
    	
        GregorianCalendar cal = new GregorianCalendar(year, month-1, day);
        int nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        int som = cal.get(GregorianCalendar.DAY_OF_WEEK);
        
        int row = new Integer((day+som-2)/7);
        int column  =  (day+som-2)%7;
        
    	//tableMCalendar.setValueAt(tokens[1] + "   " + newEvent, row, column);
    	
    	int [] rowColPair = new int[2];
    	
    	rowColPair[0] = row;
    	rowColPair[1] = column;
    	
    	String dateStoreNoDelims = Integer.toString(month) + Integer.toString(year);
    	String dateStoreNoDelimsDay = Integer.toString(day) + Integer.toString(month) + Integer.toString(year);
    	
    	List <Integer>  rowList = new ArrayList<Integer>();
    	
    	rowList.add((Integer)day);
    	
    	if(eventRowHash.containsKey(dateStoreNoDelims))
    	{
    		rowList.addAll( eventRowHash.get(dateStoreNoDelims));
    	}
    	

    	
    	
    	
    	eventRowHash.put(dateStoreNoDelims, rowList);
    	eventNameHash.put(dateStoreNoDelimsDay, newEvent);
    	
    	calRefresh(month - 1, year);
    	
    }
}
