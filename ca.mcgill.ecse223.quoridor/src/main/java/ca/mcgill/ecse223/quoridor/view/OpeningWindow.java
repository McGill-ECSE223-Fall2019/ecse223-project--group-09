package ca.mcgill.ecse223.quoridor.view;

import java.awt.GridLayout;

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

		/*
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setVerticalGroup(layout.createSequentialGroup()
									.addComponent(quoridorLabel)
									.addComponent(horizontalLineBelowTitle)
									.addComponent(newGameButton)
									.addComponent(loadGameButton)
									.addComponent(horizontalLineMiddle)
									.addComponent(rulesButton)
									.addComponent(aboutButton)
									.addComponent(quitGameButton)
		);
		layout.setHorizontalGroup(layout.createParallelGroup()
											.addComponent(quoridorLabel)
											.addComponent(horizontalLineBelowTitle)
											.addComponent(newGameButton)
											.addComponent(loadGameButton)
											.addComponent(horizontalLineMiddle)
											.addComponent(rulesButton)
											.addComponent(aboutButton)
											.addComponent(quitGameButton));

		//pack();*/
	}


	/**
	 * This will be called when the newGameButton is clicked
	 * 
	 * TODO: Whoever implements these methods needs to add their name
	 * to the author tag
	 */
	public void newGameButtonActionPerformed() {
		// Dispose the current window
		this.dispose();
		
		// Create the next window
		BoardWindow newBoardWindow = new BoardWindow();
		newBoardWindow.setSize(1000, 700);
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
	 * TODO: Whoever implements these methods needs to add their name
	 * to the author tag
	 */
	public void rulesButtonActionPerformed() {
		// Proof that it works
		JOptionPane.showMessageDialog(this, "called method rulesButtonActionPerformed\n\nRemember to change this behaviour!");
	}

	/**
	 * This will be called when the aboutButton is clicked:
	 *
	 * ~~ Group 9 did this project yah ~~
	 * 
	 * @author Paul Teng (260862906)
	 */
	public void aboutButtonActionPerformed() {
		final JLabel lbl = new JLabel("Quoridor");
		lbl.setFont(lbl.getFont().deriveFont(28.0f));
		final JComponent[] list = {
			lbl,
			new JSeparator(),
			new JLabel("Made by ECSE 223 - Group 9:"),
			new JLabel("Barry Chen, Mohamed Mohamed, Ada Andrei, Paul Teng, and Alixe Delabrousse")
		};
		JOptionPane.showMessageDialog(this, list, "", JOptionPane.PLAIN_MESSAGE);
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
