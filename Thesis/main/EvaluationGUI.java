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
		setSize(1300, 1000);
		setLocationRelativeTo(null);
		
		JPanel centerPanel = new JPanel();
		
		resultsLog = new JTextArea(40, 105);
		resultsLog.setFont(new Font("Courier", Font.BOLD, 14));
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
	
	public void fillTextArea(List<ClassInfo> classes){
		StringBuilder str = new StringBuilder();
		
		for(ClassInfo cl : classes){
			str.append("Class: " + cl.getClassOrigin().getName() + "\n");
			str.append("---------------------------------------------------------------------------------------------------------\n");
			str.append(String.format("	%-30s | %-30s %n", "Number of methods", "Number of instance variables"));
			str.append(String.format("	%-30d | %-30d %n", cl.getNumberOfMethods(), cl.getInstanceVars()));
			str.append(String.format("	%-30s | %-30s | %-30s %n", "Private/Protected Methods", "Static Methods", "Static Variables"));
			str.append(String.format("	%-30d | %-30d | %-30d %n", cl.getPrivProtMethods(), cl.getStaticMethods(), cl.getStaticFields()));
			str.append(String.format("	%-30s | %-30s | %-30s %n", "Referenced classes", "Long parameter list methods", "is Data Class"));
			str.append(String.format("	%-30s | %-30s | %-30b %n", cl.getCouplingClasses(), cl.getLongParameterMethods(), cl.isDataClass()));
			str.append("---------------------------------------------------------------------------------------------------------\n");
		}
		
		resultsLog.setText(str.toString());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == closeBtn){
			dispose();
		}		
	}
}
