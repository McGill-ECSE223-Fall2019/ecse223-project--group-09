package ca.mcgill.ecse223.quoridor.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;


public class OpeningWindow extends JFrame{
	
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
				//method
			}
		});
		
		loadGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//method
			}
		});
		
		rulesButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//method
			}
		});
		
		aboutButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//method
			}
		});
		
		quitGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//method
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
	
	
	public void newGameButtonActionPerformed() {
		
	}
	
	public void loadGameButtonActionPerformed() {
		
	}
	
	public void rulesButtonActionPerformed() {
		
	}
	
	public void aboutButtonActionPerformed() {
		
	}
	
	public void quitGameButtonActionPerformed() {
		
	}
}
