package main;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

public class EvaluationGUI extends JFrame implements ActionListener {
	
	private JTextArea resultsLog;
	private JButton closeBtn;

	public EvaluationGUI() {
		super("Evaluation");
		setSize(1500, 1000);
		setLocationRelativeTo(null);
		
		JPanel centerPanel = new JPanel();
		
		resultsLog = new JTextArea(40, 170);
		resultsLog.setFont(new Font("Courier", Font.BOLD, 13));
		JScrollPane scroll = new JScrollPane(resultsLog);
		
		centerPanel.add(scroll);
		
		JPanel botPanel = new JPanel();
		
		closeBtn = new JButton("Close");
		closeBtn.addActionListener(this);
		
		botPanel.add(closeBtn);
		
		add(centerPanel, BorderLayout.CENTER);
		add(botPanel, BorderLayout.SOUTH);
		
		//fillTextArea();
		
		setVisible(true);
	}
	
	public void fillTextArea(String sequence){
		resultsLog.setText(sequence);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == closeBtn){
			saveToExcel();
			dispose();
		}		
	}
	
	private void saveToExcel(){
		
	}
}
