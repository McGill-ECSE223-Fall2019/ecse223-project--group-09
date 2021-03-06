package ca.mcgill.ecse223.quoridor.view;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.sql.Time;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import ca.mcgill.ecse223.quoridor.controller.Color;
import ca.mcgill.ecse223.quoridor.controller.InvalidLoadException;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.view.filter.*;

public class OpeningWindow extends JFrame {

	// game-setup-dialog component
	// (static so state is saved across instances)
	private final static GameSetupDialog GAME_SETUP_DIALOG = new GameSetupDialog();

	// UI Elements

	JButton newGameButton;
	JButton loadGameButton;
	JButton rulesButton;
	JButton aboutButton;
	JButton quitGameButton;

	JLabel quoridorLabel;

	JPanel mainPanel;

	private final JFileChooser chooser = new JFileChooser();

	// new quoridor page
	public OpeningWindow() {
		initWelcomePage();

        // Only use the filters we provide (no Choose-any-file-type option)
        this.chooser.setAcceptAllFileFilterUsed(false);

        this.chooser.addChoosableFileFilter(new GameFileFilter());
	}

	public void initWelcomePage() {

		this.quoridorLabel = new JLabel();
		this.quoridorLabel.setText("Quoridor");
		this.quoridorLabel.setFont(this.quoridorLabel.getFont().deriveFont(28.0f));
		this.quoridorLabel.setHorizontalAlignment(SwingConstants.CENTER);

		this.newGameButton = new JButton("New Game");
		this.loadGameButton = new JButton("Load Game");
		this.rulesButton = new JButton("Rules");
		this.aboutButton = new JButton("About");
		this.quitGameButton = new JButton("Quit Game");

		JSeparator horizontalLineMiddle = new JSeparator();
		JSeparator horizontalLineBelowTitle = new JSeparator();

		// listeners

		newGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// calls the methods defined below:
				newGameButtonActionPerformed();
			}
		});

		loadGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// calls the methods defined below:
				loadGameButtonActionPerformed();
			}
		});

		rulesButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// calls the methods defined below:
				rulesButtonActionPerformed();
			}
		});

		aboutButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// calls the methods defined below:
				aboutButtonActionPerformed();
			}
		});

		quitGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// calls the methods defined below:
				quitGameButtonActionPerformed();
			}
		});

		mainPanel = new JPanel(new GridLayout(8, 1, 2, 2));

		mainPanel.add(quoridorLabel);
		mainPanel.add(horizontalLineBelowTitle);
		mainPanel.add(newGameButton);
		mainPanel.add(loadGameButton);
		mainPanel.add(horizontalLineMiddle);
		mainPanel.add(rulesButton);
		mainPanel.add(aboutButton);
		mainPanel.add(quitGameButton);

		this.add(mainPanel);
	}

	/**
	 * This will be called when the newGameButton is clicked.
	 *
	 *
	 * @author Ada Andrei
	 * @author Paul Teng (260862906) [ReportFinalResult.feature]
	 */
	public void newGameButtonActionPerformed() {
		try {
			GAME_SETUP_DIALOG.replaceNameHints(QuoridorController.getUsernames());
			final String namePlayer1;
			final String namePlayer2;

			while (true) {
				if (GAME_SETUP_DIALOG.showSetupDialog(this) != GameSetupDialog.START_GAME_OPTION) {
					// We are done, player hit cancel or sth like that
					return;
				}

				// Only continue if all players have name selected
				if (GAME_SETUP_DIALOG.allPlayersHaveName()) {
					namePlayer1 = GAME_SETUP_DIALOG.getSelectedPlayerName(0);
					namePlayer2 = GAME_SETUP_DIALOG.getSelectedPlayerName(1);
					break;
				}
			}

			QuoridorController.createGame();// make a game

			QuoridorController.createOrSelectUsername(namePlayer1, Color.WHITE);
			QuoridorController.createOrSelectUsername(namePlayer2, Color.BLACK);
			Time time = GAME_SETUP_DIALOG.getThinkingTime();
			QuoridorController.setTime(time.getMinutes(), time.getSeconds());

			QuoridorController.startNewGame();
			QuoridorController.initiateBoard();

			// Dispose the current window
			this.dispose();

			// Create the next window
			BoardWindow.launchWindow();
		}
		catch (RuntimeException ex) {
			SaveLoadPanel.displayThrowableTrace(this, ex);
		}
	}

	/**
	 * This will be called when the loadGameButton is clicked
	 *
	 * @author Paul Teng (260862906) [Getting the UI to showup]
	 */
	public void loadGameButtonActionPerformed() {
		if (this.chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}

        final FileFilter filter = this.chooser.getFileFilter();
        final File file = this.chooser.getSelectedFile();
        try {
			// If we succeed, then we display the game board
			((IOPerformer) filter).performLoad(file);
			BoardWindow.launchWindow("Game was successfully loaded from file: " + file + "\nGet ready!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Load operation failed:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidLoadException ex) {
            JOptionPane.showMessageDialog(this, "Loaded file is invalid:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) {
            SaveLoadPanel.displayThrowableTrace(this, ex);
        }
	}

	/**
	 * This will be called when the rulesButton is clicked
	 *
	 * @author Paul Teng (260862906)
	 */
	public void rulesButtonActionPerformed() {
		final JLabel lbl = new JLabel("Rules");
		lbl.setFont(lbl.getFont().deriveFont(28.0f));

		final JLabel ruleText = new JLabel();
		ruleText.setText("<html>Please consult <a href=\"\">http://lode.ameije.com/quoridor/Rules/quoridor_rules.html</a> for rules</html>");
		ruleText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					java.awt.Desktop.getDesktop().browse(URI.create("http://lode.ameije.com/quoridor/Rules/quoridor_rules.html"));
				} catch (Exception ex) {
					// Well... nothing we can do
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// Making it obvious that this is clickable
				ruleText.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// Making it obvious that this is clickable
				ruleText.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		final JComponent[] list = {
			lbl,
			new JSeparator(),
			ruleText
		};
		JOptionPane.showMessageDialog(this, list, "", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * This will be called when the aboutButton is clicked
	 *
	 * @author Paul Teng (260862906)
	 */
	public void aboutButtonActionPerformed() {
		this.showAboutPopup();
	}

	/**
	 * Creates a popup dialog for the about page
	 *
	 * ~~ Group 9 did this project yah ~~
	 *
	 * @author Paul Teng (26086290)
	 */
	private void showAboutPopup() {
		final JLabel lbl = new JLabel("Quoridor");
		lbl.setFont(lbl.getFont().deriveFont(28.0f));
		final JComponent[] list = {
			lbl,
			new JSeparator(),
			new JLabel("Made by ECSE 223 - Group 9:"),
			new JLabel("Barry Chen, Mohamed Mohamed, Ada Andrei, Paul Teng, and Alixe Delabrousse")
		};
		JOptionPane.showMessageDialog(null, list, "", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * This will be called when the quitGameButton is clicked:
	 *
	 * It asks the user again, then quits
	 *
	 * @author Paul Teng (260862906)
	 */
	public void quitGameButtonActionPerformed() {
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Are you sure?", "",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
			// Generate a close-window event
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
	}

	public static OpeningWindow launchWindow() {
		final OpeningWindow openWindow = new OpeningWindow();

		openWindow.setSize(400, 550);
		openWindow.setDefaultCloseOperation(3);
		openWindow.setLocationRelativeTo(null);
		openWindow.setVisible(true);

		return openWindow;
	}
}
