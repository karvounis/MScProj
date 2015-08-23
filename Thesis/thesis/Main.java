package thesis;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			MainGUI mainGUI;
			public void run() {
				mainGUI = new MainGUI();
			}
		});
	}
}
