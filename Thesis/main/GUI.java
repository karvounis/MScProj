package main;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI extends JFrame implements ActionListener{

	private JLabel nameJar;
	private JButton start, clear, choose, exit;
	private JFileChooser fileChooser;
	public static JTextArea log;
	private JPanel top, center, bot;
	private File component;	
	private Decomposer frida;

	public GUI(){
		super("Welcome to my thesis GUI!"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);

		//North layout
		top = new JPanel();		
		top.add(new JLabel("Choose a .jar file for analysis:"));

		//Opens the FileChooser
		choose = new JButton("Open jar...");
		choose.addActionListener(this);

		//Clears the chosen component
		clear = new JButton("Clear");
		clear.addActionListener(this);

		nameJar = new JLabel();

		top.add(choose);
		top.add(clear);
		top.add(nameJar);

		add(top, BorderLayout.NORTH);

		//Center Layout
		center = new JPanel();

		//Logging text area
		log = new JTextArea(15, 30);
		JScrollPane scroll = new JScrollPane(log);
		
		center.add(new JLabel("Log:"));
		center.add(scroll);

		add(center, BorderLayout.CENTER);

		//South layout
		bot = new JPanel();

		start = new JButton("Start doing thingies");
		start.addActionListener(this);
		exit = new JButton("Exit");
		exit.addActionListener(this);

		bot.add(start);
		bot.add(exit);

		add(bot, BorderLayout.SOUTH);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == choose){
			fileChooser = new JFileChooser();
			//choice = new JFileChooser(System.getProperty("user.home"));
			//Accepts only .jar files and image
			FileNameExtensionFilter type1 = new FileNameExtensionFilter("Image Files", new String[] {"jpg", "bmp", "png"});
			FileNameExtensionFilter type2 = new FileNameExtensionFilter("Jar files", "jar");

			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.addChoosableFileFilter(type2);
			fileChooser.addChoosableFileFilter(type1);
			fileChooser.setFileFilter(type2);

			int returnVal = fileChooser.showDialog(GUI.this, "Choose");

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				component = fileChooser.getSelectedFile();
				log.append("File chosen: " + component.getName() + ".\n");
				//System.err.println(component.getName());
				//System.err.println("Till here");
				//System.err.println(component.getAbsolutePath());

				nameJar.setText(component.getName());
			} else {
				log.append("Jar choice cancelled by user.\n");
			}
		}else if(e.getSource() == start){
			//Start button pressed
			if(component != null){
				
				log.append("Initiation! FIRE!\n");

				frida = new Decomposer(component.getPath());
				frida.loadClassInfo();
				//frida.startAnalysis();
				
			}else{
				log.append("Choose jar please!\n");
			}
		}else if(e.getSource() == clear){
			//Clear button pressed
			if(component != null){
				component = null;
				log.append("Cleared selected jar.\n");
				nameJar.setText("");
			}
		}else if(e.getSource() == exit){
			//Exit button pressed
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GUI();
			}
		});
	}
}