package ca.mcgill.ecse223.quoridor.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.controller.TOWallCandidate;

/**
 * Creates a window that looks somewhat like GUI3.png
 *
 * @author Paul Teng (260862906) [SavePosition.feature;LoadPosition.feature]
 */
public class BoardWindow extends JFrame {

	// ***** Rendering State Variables *****
	private final DefaultListModel<String> replayList = new DefaultListModel<>();

	// ***** Additional UI Components *****
	private final SaveLoadPanel saveLoadPanel = new SaveLoadPanel();
	private final PlayerInfoPanel playerInfoPanel = new PlayerInfoPanel();
	private final GridPanel gridPanel = new GridPanel();

	public BoardWindow() {
		this.setLayout(new BorderLayout());

		this.add(generateRightPanel(), BorderLayout.EAST);
		this.add(gridPanel, BorderLayout.CENTER);
		//this.add(generateWallPanel(), BorderLayout.SOUTH);

		final JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		menuBar.add(this.createFileMenu());
	}

	private JMenu createFileMenu() {
		final JMenu fileMenu = new JMenu("File");
		saveLoadPanel.addMenuEntries(fileMenu);
		return fileMenu;
	}

	/*
	 * // *** walls UI implementation ***
	 * 
	 *//**
		 * Generates the UI related to the remaining walls and wall methods for the
		 * current player
		 * 
		 * @author alixe delabrousse
		 * 
		 * @return
		 *//*
			 * 
			 * private JPanel generateWallPanel() { final JPanel wallPanel = new JPanel();
			 * wallPanel.setLayout(new BoxLayout(wallPanel, BoxLayout.X_AXIS));
			 * 
			 * final JButton grabWallButton = new JButton("Grab Wall");
			 * grabWallButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			 * grabWallButton.setMaximumSize(new Dimension(125, 125));
			 * 
			 * 
			 * //int wallsRemaining =
			 * QuoridorController.getPlayerOfCurrentTurn().getWallsRemaining();
			 * 
			 * int wallsRemaining = 10; JLabel remainingWalls = new JLabel("You have " +
			 * wallsRemaining + " walls remaining");
			 * 
			 * 
			 * 
			 * 
			 * final JPanel remainingWalls = new JPanel();
			 * 
			 * 
			 * Shape wall1 = new Rectangle(5,10); Shape wall2 = new Rectangle(5,10); Shape
			 * wall3 = new Rectangle(5,10); Shape wall4 = new Rectangle(5,10); Shape wall5 =
			 * new Rectangle(5,10); Shape wall6 = new Rectangle(5,10); Shape wall7 = new
			 * Rectangle(5,10); Shape wall8 = new Rectangle(5,10); Shape wall9 = new
			 * Rectangle(5,10); Shape wall10 = new Rectangle(5,10);
			 * 
			 * 
			 * remainingWalls.setLayout(new GridLayout(1,10));
			 * remainingWalls.add((Component) wall1); remainingWalls.add((Component) wall2);
			 * remainingWalls.add((Component) wall3); remainingWalls.add((Component) wall4);
			 * remainingWalls.add((Component) wall5); remainingWalls.add((Component) wall6);
			 * remainingWalls.add((Component) wall7); remainingWalls.add((Component) wall8);
			 * remainingWalls.add((Component) wall9); remainingWalls.add((Component)
			 * wall10);
			 * 
			 * 
			 * 
			 * wallPanel.add(grabWallButton); //wallPanel.add(remainingWalls);
			 * wallPanel.add(Box.createHorizontalStrut(20));
			 * 
			 * wallPanel.add(remainingWalls);
			 * 
			 * return wallPanel;
			 * 
			 * }
			 */

	/**
	 * Generates the panel on the right side
	 *
	 * @return A panel
	 *
	 * @author Paul Teng (260862906)
	 */
	private JPanel generateRightPanel() {
		final JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		final JScrollPane listMoves = new JScrollPane(new JList<>(replayList), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		final JPanel container = new JPanel();
		container.add(listMoves);
		container.setBorder(new EmptyBorder(10, 2, 5, 2));

		final JButton btnEnterReplayMode = new JButton("Enter Replay Mode");
		btnEnterReplayMode.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnEnterReplayMode.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		btnEnterReplayMode.addActionListener(e -> this.onEnterReplayModeButtonClicked());

		final JButton btnQuitGame = new JButton("Quit Game");
		btnQuitGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnQuitGame.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		btnQuitGame.addActionListener(e -> this.onQuitGameButtonClicked());

		final JButton btnResign = new JButton("Resign");
		btnResign.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnResign.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		btnResign.addActionListener(e -> this.onResignButtonClicked());

		final JButton grabWallButton = new JButton("Grab a wall");
		grabWallButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		grabWallButton.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		grabWallButton.addActionListener(e -> this.onGrabAWallButtonClicked());

		panel.add(container);
		panel.add(btnEnterReplayMode);

		panel.add(saveLoadPanel);
		panel.add(btnQuitGame);

		panel.add(Box.createVerticalGlue());

		// Hack to give us more horizontal padding
		// (value should at least be 200)
		panel.add(Box.createHorizontalStrut(225));
		panel.add(playerInfoPanel);
		panel.add(grabWallButton);
		// TODO: Remember to add the grab-wall button here

		panel.add(btnResign);

		// Add breathing room between the resign
		// button and the bottom of the app
		panel.add(Box.createVerticalStrut(30));

		// XXX: Disable features not for this deliverable!
		btnEnterReplayMode.setEnabled(false);

		return panel;
	}

	/**
	 * This method is called when the enter-replay-mode button is clicked
	 */
	private void onEnterReplayModeButtonClicked() {
		JOptionPane.showMessageDialog(this, "Enter replay mode is not implemented yet!");
	}

	/**
	 * This method is called when the quit-game button is clicked
	 */
	private void onQuitGameButtonClicked() {
		JOptionPane.showMessageDialog(this, "Quit game is not implemented yet!");
	}

	/**
	 * This method is called when the resign button is clicked
	 */
	private void onResignButtonClicked() {
		JOptionPane.showMessageDialog(this, "Resign is not implemented yet!");
	}
	
	/**
	 * 
	 * @param args
	 */
	
	private void onGrabAWallButtonClicked() {
		//QuoridorController.grabWall();
		//TOWallCandidate wallCandidate = QuoridorController.getPlayerOfCurrentTurn().getWallCandidate();
		
		JOptionPane.showMessageDialog(this, "Grab a wall is not implemented yet!");
		
	}

	public static void main(String[] args) {
		// This is just a demo of how it could look

		try {
			// Try to make the frames/windows look *not java like*
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
				| IllegalAccessException ex) {
			// If we cannot do that, then continue, as apps
			// will use the default java-look...
		}

		final BoardWindow frame = new BoardWindow();
		frame.setTitle("DEMO");
		frame.setDefaultCloseOperation(3);
		frame.setSize(800, 550);
		frame.setVisible(true);
	}
}
