import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI extends JFrame implements ActionListener{

	private JLabel text;
	private JButton submitJar, clearJar, chooseJar;
	private JFileChooser choice;
	private JTextField log;

	public GUI(){
		super("Welcome!"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		setSize(600, 400);

		text = new JLabel("Choose a .jar file for analysis:");
		getContentPane().add(text);

		chooseJar = new JButton("Open jar...");
		chooseJar.addActionListener(this);
		getContentPane().add(chooseJar);

		log = new JTextField();
		log.setSize(300, 200);
		getContentPane().add(log);
		//choice = new JFileChooser();
		//getContentPane().add(choice);

		submitJar = new JButton("Submit");

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == chooseJar){
			choice = new JFileChooser();
			//choice = new JFileChooser(System.getProperty("user.home"));
			
			FileNameExtensionFilter type1 = new FileNameExtensionFilter("Image Files", new String[] {"jpg", "bmp", "png"});
			FileNameExtensionFilter type2 = new FileNameExtensionFilter("Jar files", "jar");
			
			choice.setAcceptAllFileFilterUsed(false);
			choice.addChoosableFileFilter(type2);
			choice.addChoosableFileFilter(type1);

			choice.setFileFilter(type2);

			int returnVal = choice.showDialog(GUI.this,
					"Choose");

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = choice.getSelectedFile();
				//log.setText("Attaching file: " + file.getName()
				//          + "." + "\n");
				System.err.println(file.getName());
				System.err.println("Till here");
				System.err.println(file.getAbsolutePath());

			} else {
				//log.append("Attachment cancelled by user." + newline);
			}
		}
	}

	public static void main(String[] args) {

		GUI john = new GUI();

	}
}
