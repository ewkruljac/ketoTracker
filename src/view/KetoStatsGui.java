package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class KetoStatsGui implements ActionListener {
	
	private JButton submitButton = null;
	private JTextField weightTextField = null;
	private JTextField ketoneTextField = null;
	private JTextField dateTextField = null;
	private JTextField glucoseTextField = null;
	
	private db.DbDriver dbDriver = null;
	private Font guiFont = new Font("Comic Sans", Font.ITALIC, 15);
	
	public KetoStatsGui() {
		dbDriver = new db.DbDriver();
		
		//main frame
		JFrame mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(1000, 1000);
		mainFrame.setLayout(null);
		mainFrame.setTitle("Keto Stats Tracker v1.0");
		
		// Labels
		JLabel dateLabel = new JLabel();
		dateLabel.setText("Date:");
		dateLabel.setBounds(50, 50, 100, 50);
		dateLabel.setFont(guiFont);
		
		JLabel weightLabel = new JLabel();
		weightLabel.setText("Weight:");
		weightLabel.setBounds(225, 50, 100, 50);
		weightLabel.setFont(guiFont);
		
		JLabel ketoneLabel = new JLabel();
		ketoneLabel.setText("Ketones:");
		ketoneLabel.setBounds(400, 50, 100, 50);
		ketoneLabel.setFont(guiFont);
		
		JLabel glucoseLabel = new JLabel();
		glucoseLabel.setText("Glucose:");
		glucoseLabel.setBounds(600, 50, 100, 50);
		glucoseLabel.setFont(guiFont);
		
		// Input Text Fields
		dateTextField = new JTextField();
		dateTextField.setBounds(95, 60, 100, 30);
		dateTextField.setFont(guiFont);
		
		weightTextField = new JTextField();
		weightTextField.setBounds(285, 60, 100, 30);
		weightTextField.setFont(guiFont);
		
		ketoneTextField = new JTextField();
		ketoneTextField.setBounds(475, 60, 100, 30);
		ketoneTextField.setFont(guiFont);
		
		glucoseTextField = new JTextField();
		glucoseTextField.setBounds(675, 60, 100, 30);
		ketoneTextField.setFont(guiFont);
		
		// Buttons
		submitButton = new JButton();
		submitButton.setText("Submit");
		submitButton.setFocusable(false);
		submitButton.setFont(new Font("Comic Sans", Font.ITALIC, 25));
		submitButton.setBounds(800, 50, 150, 50);
		submitButton.addActionListener(this);
		
		// Add Labels to mainFrame
		mainFrame.add(dateLabel);
		mainFrame.add(weightLabel);
		mainFrame.add(ketoneLabel);
		mainFrame.add(glucoseLabel);
		
		// Add Text Fields to mainFrame
		mainFrame.add(dateTextField);
		mainFrame.add(weightTextField);
		mainFrame.add(ketoneTextField);
		mainFrame.add(glucoseTextField);
		
		// Add Buttons to mainFrame
		mainFrame.add(submitButton);
		
		
		mainFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==submitButton) {
			String date = dateTextField.getText();
			float weight = Float.parseFloat(weightTextField.getText());
			float ketones = Float.parseFloat(ketoneTextField.getText());
			float glucose = Float.parseFloat(glucoseTextField.getText());
			dbDriver.insert(date, weight, glucose, ketones, "stats");
		}
	}
	
}
