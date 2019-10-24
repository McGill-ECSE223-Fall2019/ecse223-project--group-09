package ca.mcgill.ecse223.quoridor.view;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;

public class QuoridorView extends JFrame{
	
	//FIELDS
	
	JButton newGameButton;
	JButton loadGameButton;
	JButton rulesButton;
	JButton aboutButton;
	JButton quitGameButton;
	
	JLabel quoridorLabel;
	
	// new quoridor page
	public QuoridorView() {
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
		
		
		
		
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setVerticalGroup(layout.createParallelGroup()
									.addComponent(quoridorLabel)
									.addComponent(horizontalLineBelowTitle)
									.addComponent(newGameButton)
									.addComponent(loadGameButton)
									.addComponent(horizontalLineMiddle)
									.addComponent(rulesButton)
									.addComponent(aboutButton)
									.addComponent(quitGameButton)
		);
		
		pack();
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
