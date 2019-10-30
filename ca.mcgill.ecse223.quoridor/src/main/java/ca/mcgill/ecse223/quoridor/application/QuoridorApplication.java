package ca.mcgill.ecse223.quoridor.application;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.view.OpeningWindow;

public class QuoridorApplication {

	private static Quoridor quoridor;

	public static Quoridor getQuoridor() {
		if (quoridor == null) {
			quoridor = new Quoridor();
		}
 		return quoridor;
	}

	public static void main(String[] args) {
		try {
			// Try to make the frames/windows look *not java like*
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			// If we cannot do that, then continue, as apps
			// will use the default java-look...
		}

		OpeningWindow openWindow = new OpeningWindow();
		
		openWindow.setSize(400, 550);
		openWindow.setDefaultCloseOperation(3);
		openWindow.setVisible(true);
	}
}
