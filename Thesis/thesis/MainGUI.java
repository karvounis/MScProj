package thesis;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Main window.
 * @author Evangelos Karvounis
 *
 */
public class MainGUI extends JFrame implements ActionListener{

	private JLabel nameJar;
	private JButton startBtn, clearBtn, chooseBtn, exitBtn;
	private JFileChooser fileChooser;
	public static JTextArea log;
	private File component;
	private Decomposer decomposer;

	public MainGUI(){
		super("Memory Leaks Reporting Tool"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);

		//North layout
		JPanel topPanel = new JPanel();		
		topPanel.add(new JLabel("Choose a .jar file for analysis:"));

		//Opens the FileChooser
		chooseBtn = new JButton("Open jar...");
		chooseBtn.addActionListener(this);

		//Clears the chosen component
		clearBtn = new JButton("Clear");
		clearBtn.addActionListener(this);

		nameJar = new JLabel();

		topPanel.add(chooseBtn);
		topPanel.add(clearBtn);
		topPanel.add(nameJar);

		//Center layout
		JPanel centerPanel = new JPanel();

		//Logging text area
		log = new JTextArea(15, 30);
		JScrollPane scroll = new JScrollPane(log);

		centerPanel.add(new JLabel("Log:"));
		centerPanel.add(scroll);

		//South layout
		JPanel botPanel = new JPanel();

		startBtn = new JButton("Start");
		startBtn.addActionListener(this);
		exitBtn = new JButton("Exit");
		exitBtn.addActionListener(this);

		botPanel.add(startBtn);
		botPanel.add(exitBtn);

		//Adds the panels to the frame
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(botPanel, BorderLayout.SOUTH);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == chooseBtn){
			fileChooser = new JFileChooser("C:/Users/Vag/Documents/");
			//choice = new JFileChooser(System.getProperty("user.home"));

			//Accepts only .jar files
			FileNameExtensionFilter type2 = new FileNameExtensionFilter("Jar/Class files", "jar");

			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.addChoosableFileFilter(type2);
			fileChooser.setFileFilter(type2);

			int returnVal = fileChooser.showDialog(MainGUI.this, "Choose");

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				component = fileChooser.getSelectedFile();
				log.append("File chosen: " + component.getName() + ".\n");
				nameJar.setText(component.getName());

			} else {
				log.append("Jar choice cancelled by user.\n");
			}
		}else if(e.getSource() == startBtn){
			//Start button pressed
			if(component != null){
				log.append("Initiation! FIRE!\n");

				//Creates a decomposer object that will start the analysis
				decomposer = new Decomposer(component.getPath());
				log.append("Starting jar analysis..\n");
				decomposer.startJarAnalysis();		
				log.append("Starting evaluation..\n");
				decomposer.startEvaluation();

				clearNameJar();
				log.append("Process completed. Please choose another jar file or exit.\n");
			}else{
				log.append("Choose jar please!\n");
			}
		}else if(e.getSource() == clearBtn){
			//Clear button pressed
			if(component != null){
				clearNameJar();
				log.append("Cleared selected jar.\n");				
			}
		}else if(e.getSource() == exitBtn){
			//Exit button pressed
			System.exit(0);
		}
	}

	private void clearNameJar(){
		component = null;
		nameJar.setText("");
	}
}