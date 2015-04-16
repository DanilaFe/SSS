package com.sss.mastercontroller.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sss.mastercontroller.editors.PreferencesEditor;
import com.sss.mastercontroller.editors.ValuesEditor;
import com.sss.mastercontroller.lists.Enemies;
import com.sss.mastercontroller.lists.Events;
import com.sss.mastercontroller.lists.InternalProblems;
import com.sss.mastercontroller.lists.Preferences;

public class MasterController implements ActionListener {

	private Enemies enemies;
	private Events events;
	private InternalProblems internalproblems;
	private Preferences preferences;
	private static MasterController ms;

	private Container selectionC = new Container();
	private Container itemsC = new Container();
	private Container valuesC = new Container();

	private JButton[] selections;
	private JButton[] items;

	private JScrollPane pane = new JScrollPane();

	private JPanel buttonPanel = new JPanel();

	private int selectorsNum = 5;
	private int itemsNum = 10;
	private int optionsNum = 4;
	private int num;
	private int selectedNum = 0;
	int selectedItemNum = 0;

	private boolean[] isSelected;
	private boolean[] isItemSelected;
	
	public JFrame frame = new JFrame("SSS Master Controller 1.0 - Bryce Hahn");

	public MasterController() {
		enemies = new Enemies();
		events = new Events();
		internalproblems = new InternalProblems();
		preferences = new Preferences();
	}

	public void start() {
		frame.setUndecorated(true);
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// initialize stuff
		initContainers();
		initButtons();
		addButtons();
		initItems(0);
		
		// set frame visible
		frame.setVisible(true);
	}

	private void initItems(int btAmount) {
		// the panel
	    buttonPanel.setLayout(new GridBagLayout());
	    buttonPanel.setSize(400, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
	    // the scroll pane
	    pane.setSize(400, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
	    // GridBagConstraint for button
	    GridBagConstraints constraint = new GridBagConstraints();
	    constraint.anchor = GridBagConstraints.CENTER;
	    constraint.fill = GridBagConstraints.NONE;
	    constraint.gridx = 0;
	    constraint.gridy = GridBagConstraints.RELATIVE;
	    constraint.weightx = 1.0f;
	    constraint.weighty = 1.5f;
	    constraint.insets = new Insets(5, 5, 5, 5);
	    //the buttons
    	items = new JButton[btAmount];
	    for(int i = 0; i < btAmount; i++) {
	    	if (selectedNum == 0) { //enemies
	    		items[i] = new JButton(enemies.getEnemy(i));
		        // other attributes you will set
		        buttonPanel.add(items[i], constraint);
	    	} else if (selectedNum == 1) { //internal problems
	    		items[i] = new JButton(internalproblems.getInternalProblem(i));
		        // other attributes you will set
		        buttonPanel.add(items[i], constraint);
	    	} else if (selectedNum == 2) { //events
	    		items[i] = new JButton(events.getEventName(i));
		        // other attributes you will set
		        buttonPanel.add(items[i], constraint);
	    	} else if (selectedNum == 3) {
	    		items[i] = new JButton(preferences.getPreferenceName(i));
	    		// other attributes you will set
	    		buttonPanel.add(items[i], constraint);
	    	}
	    	items[i].setBackground(Color.LIGHT_GRAY);
	    	items[i].setForeground(Color.BLACK);
    		items[i].addActionListener(this);
	    }
	    //add it
	    pane.setViewportView(buttonPanel);
	    frame.add(pane, BorderLayout.CENTER);
	    //update
	    pane.updateUI();
	}

	// initialize all the used Containers before giving them buttons and labels,
	// ex...
	private void initContainers() {
		selectionC.setLayout(new GridLayout(selectorsNum, 1));
		selectionC.setVisible(true);
		itemsC.setLayout(new GridLayout(itemsNum, 1));
		itemsC.setVisible(false);
		valuesC.setLayout(new GridLayout(optionsNum, 2));
		valuesC.setVisible(true);
	}

	// initialize all the used buttons to give them names, values, ex...
	private void initButtons() {
		selections = new JButton[selectorsNum]; // there will be `selectorsNum`
												// amount of buttons
		isSelected = new boolean[selectorsNum]; // there will be `selectorsNum`
												// amount of booleans
		isItemSelected = new boolean[num]; // there will be `num` amount of
											// booleans

		selections[0] = new JButton("Enemies");
		selections[1] = new JButton("Internal Problems");
		selections[2] = new JButton("Events");
		selections[3] = new JButton("Preferences");
		selections[4] = new JButton("Exit");

		for (int i = 0; i < selections.length; i++) {
			selectionC.add(selections[i]);
			selections[i].addActionListener(this);
			selections[i].setBackground(Color.LIGHT_GRAY);
			isSelected[i] = false;
		}

		for (int i = 0; i < isItemSelected.length; i++) {
			isItemSelected[i] = false;
		}
	}

	private void addButtons() {
		frame.add(selectionC, BorderLayout.WEST);
		frame.add(itemsC, BorderLayout.CENTER);
		frame.add(valuesC, BorderLayout.EAST);
	}

	public void showItems(int i) {
		if (i == 0) { // show enemies buttons
			num = enemies.getEnemies();
			initItems(num);
		} else if (i == 1) { // show internal problems buttons
			num = internalproblems.getInternalProblems();
			initItems(num);
		} else if (i == 2) { // show events buttons
			num = events.getEvents();
			initItems(num);
		} else if (i == 3) { //show preferences
			num = preferences.getPreferences();
			initItems(num);
		} else if (i == 4) { //exit button
			System.exit(0);
		} else { // some how there was an error
			System.err.println("For some weird reason there was an error on the selection you have tried to make, try choosing the option again.");
		}
	}

	public void clearItems() {
		for (int i = 0; i < items.length; i++) {
			items[i].setVisible(false);
			items[i].setEnabled(false);
		}
	}
	
	private void clearSelections() {
		for (int i = 0; i < selections.length; i++) {
			selections[i].setBackground(Color.LIGHT_GRAY);
			selections[i].setForeground(Color.BLACK);
			isSelected[i] = false;
		}
	}

	private void showValues(int i) {
		frame.setEnabled(false);
		if (selectedNum == 0) { // enemies tab
			new ValuesEditor(enemies.getEnemy(i), enemies.getDefinition(i), selectedNum);
		} else if (selectedNum == 1) { // internal problems tab
			new ValuesEditor(internalproblems.getInternalProblem(i), internalproblems.getDefinition(i), selectedNum);
		} else if (selectedNum == 2) { // events tab
			new ValuesEditor(events.getEventName(i), events.getEventDefinition(i), selectedNum);
		} else {
			System.err.println("For some reason you are still running when you pressed exit button...");
		}
	}
	
	private void showPreferences(int i) {
		frame.setEnabled(false);
		new PreferencesEditor(preferences.getPreferenceName(i), preferences.getPreferenceDefinition(i), selectedNum);
	}

	public static void main(String[] args) {
		MasterController master = new MasterController();
		ms = master;
		master.start();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// selectors buttons test
		for (int i = 0; i < selections.length; i++) {
			if (event.getSource().equals(selections[i])) {
				clearSelections();
				if (selections[i].getBackground().equals(Color.BLUE)) {
					// it is already selected, de-select it.
					selections[i].setBackground(Color.LIGHT_GRAY);
					selections[i].setForeground(Color.BLACK);
					isSelected[i] = false;
					// give the items section to the user
					clearItems();
					selectedNum = -1;
				} else {
					selections[i].setBackground(Color.BLUE);
					selections[i].setForeground(Color.WHITE);
					isSelected[i] = true;
					selectedNum = i;
					// clean the items button slate
					clearItems();
					// give the items section to the user
					showItems(i);
				}
			}
		}
		// items buttons test
		for (int i = 0; i < num; i++) {
			if (event.getSource().equals(items[i]) && selectedNum != 3) {
				if (items[i].getBackground().equals(new Color(60, 60, 200))) {
					// it is already selected, de-select it.
					items[i].setBackground(Color.LIGHT_GRAY);
					items[i].setForeground(Color.BLACK);
					selectedItemNum = -1;
				} else {
					items[i].setBackground(new Color(60, 60, 200));
					items[i].setForeground(Color.WHITE);
					selectedItemNum = i;
					// give the new values section to the user
					showValues(i);
				}
			} else if (event.getSource().equals(items[i]) && selectedNum == 3) {
				if (items[i].getBackground().equals(new Color(60, 60, 200))) {
					// it is already selected, de-select it.
					items[i].setBackground(Color.LIGHT_GRAY);
					items[i].setForeground(Color.BLACK);
					selectedItemNum = -1;
				} else {
					items[i].setBackground(new Color(60, 60, 200));
					items[i].setForeground(Color.WHITE);
					selectedItemNum = i;
					// give the new values section to the user
					showPreferences(i);
				}
			}
		}
	}
	
	public static MasterController getMasterController() {
		return ms;
	}
}