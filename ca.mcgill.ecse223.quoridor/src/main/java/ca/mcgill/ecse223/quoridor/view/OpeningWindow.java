package ca.mcgill.ecse223.quoridor.view;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class OpeningWindow extends JFrame {

	//UI Elements

	JButton newGameButton;
	JButton loadGameButton;
	JButton rulesButton;
	JButton aboutButton;
	JButton quitGameButton;

	JLabel quoridorLabel;

	JPanel mainPanel;

	// new quoridor page
	public OpeningWindow() {
		initWelcomePage();

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

		//listeners

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



		mainPanel = new JPanel(new GridLayout(8,1,2,2));

		mainPanel.add(quoridorLabel);
		mainPanel.add(horizontalLineBelowTitle);
		mainPanel.add(newGameButton);
		mainPanel.add(loadGameButton);
		mainPanel.add(horizontalLineMiddle);
		mainPanel.add(rulesButton);
		mainPanel.add(aboutButton);
		mainPanel.add(quitGameButton);

		this.add(mainPanel);

		// XXX: Disable features not for this deliverable!
		this.loadGameButton.setEnabled(false);

	}


	/**
	 * This will be called when the newGameButton is clicked
	 * 
	 * TODO: Whoever implements these methods needs to add their name
	 * to the author tag
	 */
	public void newGameButtonActionPerformed() {

		// Proof that it works
		//JOptionPane.showMessageDialog(this, "called method newGameButtonActionPerformed\n\nRemember to change this behaviour!");
		

		// Dispose the current window
		this.dispose();
		
		// Create the next window
		BoardWindow newBoardWindow = new BoardWindow();
		newBoardWindow.setSize(800, 550);
		newBoardWindow.setDefaultCloseOperation(3);
		newBoardWindow.setLocationRelativeTo(null);

		newBoardWindow.setVisible(true);
	}

	/**
	 * This will be called when the loadGameButton is clicked
	 * 
	 * TODO: Whoever implements these methods needs to add their name
	 * to the author tag
	 */
	public void loadGameButtonActionPerformed() {
		// Proof that it works
		JOptionPane.showMessageDialog(this, "called method loadGameButtonActionPerformed\n\nRemember to change this behaviour!");
	}

	/**
	 * This will be called when the rulesButton is clicked
	 * 
	 * @author Paul Teng (260862906)
	 */
	public void rulesButtonActionPerformed() {
		// // Proof that it works
		// JOptionPane.showMessageDialog(this,
		// 		"called method rulesButtonActionPerformed\n\nRemember to change this behaviour!");

		final JLabel lbl = new JLabel("Rules");
		lbl.setFont(lbl.getFont().deriveFont(28.0f));

		final JLabel ruleText = new JLabel();
		ruleText.setText("<html>Please consult <a href=\"https://en.wikipedia.org/wiki/Quoridor\">https://en.wikipedia.org/wiki/Quoridor</a> for rules</html>");
		ruleText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					java.awt.Desktop.getDesktop().browse(URI.create("https://en.wikipedia.org/wiki/Quoridor"));
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
			// Dispose will *free* the frame and close it
			this.dispose();
		}
	}
	

}
