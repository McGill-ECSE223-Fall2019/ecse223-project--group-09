package ca.mcgill.ecse223.quoridor.application;

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
		OpeningWindow openWindow = new OpeningWindow();
		
		openWindow.setSize(400, 550);
		openWindow.setDefaultCloseOperation(3);
		openWindow.setVisible(true);
		
	
	}
		

}
