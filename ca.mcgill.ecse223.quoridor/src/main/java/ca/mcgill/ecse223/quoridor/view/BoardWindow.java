package ca.mcgill.ecse223.quoridor.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.EnumSet;

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
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicArrowButton;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.Color;
import ca.mcgill.ecse223.quoridor.controller.Orientation;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.controller.TOPlayer;
import ca.mcgill.ecse223.quoridor.controller.TOWall;
import ca.mcgill.ecse223.quoridor.controller.TOWallCandidate;
import ca.mcgill.ecse223.quoridor.controller.WallStockEmptyException;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import ca.mcgill.ecse223.quoridor.view.event.GameBoardListener;

/**
 * Creates a window that looks somewhat like GUI3.png
 *
 * @author Paul Teng (260862906) [SavePosition.feature;LoadPosition.feature]
 */
public class BoardWindow extends JFrame implements GameBoardListener {

    private static final int UPDATE_DELAY = 350;

    // ***** Result Screen Constants *****

    private static final String[] OPTIONS = { "Restart Game", "Enter Replay Mode", "Quit Game" };

    // ***** Rendering State Variables *****

    private final DefaultListModel<String> replayListData = new DefaultListModel<>();
    private final JList<String> replayList = new JList<>(this.replayListData);
    private final Timer PLAYER_INFO_TIMER;

    // ***** Additional UI Components *****
    private final SaveLoadPanel saveLoadPanel = new SaveLoadPanel();
    private final PlayerInfoPanel playerInfoPanel = new PlayerInfoPanel();
    private final GridPanel gridPanel = new GridPanel();

    private final JButton btnEnterReplayMode = new JButton("Enter Replay Mode");
    private final JButton btnExitReplayMode = new JButton("Exit Replay Mode");
    
    private final JButton btnQuitGame = new JButton("Quit Game");
    private final JButton btnResign = new JButton("Resign");

    private final JButton grabWallButton = new JButton("Grab a wall");

    /**
     * @author Mohamed Mohamed adding the rotate wall JButton
     */

    private final JButton rotateWall = new JButton("Rotate Wall");
    private final JButton stepBackward = new JButton("Step Backward");
    private final JButton stepForward = new JButton("Step Forward");
    
    private final JButton jumpToFinal = new JButton("Jump to Final Position");
    private final JButton jumpToStart = new JButton("Jump to Start Position");
    
    private final JButton cont = new JButton("continue");

    public BoardWindow() {
    	
    	this.setLayout(new BorderLayout());

        this.add(generateRightPanel(), BorderLayout.EAST);
        this.add(gridPanel, BorderLayout.CENTER);

        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(this.createFileMenu());

        this.setJMenuBar(menuBar);

        // Install the event listener for the game board
        this.gridPanel.addGameBoardListener(this);

        // Setup timer that periodically fetches
        // the time remaining of the current player:
        this.PLAYER_INFO_TIMER = new Timer(UPDATE_DELAY, e -> this.fetchCurrentPlayerInfoFromController());
        this.PLAYER_INFO_TIMER.setInitialDelay(0);
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        saveLoadPanel.addMenuEntries(fileMenu);
        return fileMenu;
    }

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

        replayList.setDragEnabled(false);
        replayList.setEnabled(false);
        replayList.setSelectionBackground(java.awt.Color.cyan);

        final JScrollPane listMoves = new JScrollPane(replayList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(220, 110);
            }
        };
        listMoves.setWheelScrollingEnabled(false);

        final JPanel container = new JPanel();
        container.add(listMoves);
        container.setBorder(new EmptyBorder(10, 2, 5, 2));

        btnEnterReplayMode.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEnterReplayMode.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        btnEnterReplayMode.addActionListener(e -> this.onEnterReplayModeButtonClicked());
        
        btnExitReplayMode.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExitReplayMode.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        btnExitReplayMode.addActionListener(e -> this.onExitReplayModeButtonClicked());
        btnExitReplayMode.setVisible(false);

        btnQuitGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnQuitGame.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        btnQuitGame.addActionListener(e -> this.onQuitGameButtonClicked());

        btnResign.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnResign.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        btnResign.addActionListener(e -> this.onResignButtonClicked());

        grabWallButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        grabWallButton.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        grabWallButton.addActionListener(e -> this.onGrabAWallButtonClicked());

        rotateWall.setAlignmentX(Component.CENTER_ALIGNMENT);
        rotateWall.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        rotateWall.setEnabled(true);
        rotateWall.addActionListener(e -> this.onRotateWallButtonClicked());

        stepBackward.setAlignmentX(Component.CENTER_ALIGNMENT);
        stepBackward.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        stepBackward.addActionListener(e -> this.onStepBackwardButtonClicked());
        stepBackward.setVisible(false);

        stepForward.setAlignmentX(Component.CENTER_ALIGNMENT);
        stepForward.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        stepForward.addActionListener(e -> this.onStepForwardButtonClicked());
        stepForward.setVisible(false);
        
        jumpToStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        jumpToStart.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        jumpToStart.addActionListener(e -> this.onJumpToStartButtonClicked());
        jumpToStart.setVisible(false);
        
        jumpToFinal.setAlignmentX(Component.CENTER_ALIGNMENT);
        jumpToFinal.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        jumpToFinal.addActionListener(e -> this.onJumpToFinalButtonClicked());
        jumpToFinal.setVisible(false);
        
        cont.setAlignmentX(Component.CENTER_ALIGNMENT);
        cont.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        cont.addActionListener(e -> this.onContinueButtonClicked());
        cont.setVisible(false);
        
        panel.add(container);
        panel.add(btnEnterReplayMode);
        panel.add(btnExitReplayMode);
        
     // might just delete this depends on my patience
        /*
         * panel.setLayout(new GridBagLayout());
         *
         *
         * GridBagConstraints dropCst = new GridBagConstraints(); dropCst.gridx = 0;
         * dropCst.gridy = 0; dropCst.weightx = 0.5; dropCst.fill =
         * GridBagConstraints.HORIZONTAL; panel.add(dropWall, dropCst);
         *
         * GridBagConstraints rotateCst = new GridBagConstraints(); rotateCst.gridx = 1;
         * rotateCst.gridy = 0; rotateCst.weightx = 0.5; rotateCst.fill =
         * GridBagConstraints.HORIZONTAL; panel.add(rotateWall, rotateCst);
         *
         * panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
         */
        
        panel.add(saveLoadPanel);

        panel.add(btnQuitGame);

        panel.add(Box.createVerticalGlue());

        // Hack to give us more horizontal padding
        // (value should at least be 200)
        panel.add(Box.createHorizontalStrut(225));
        panel.add(playerInfoPanel);

        panel.add(grabWallButton);
        panel.add(rotateWall);
        panel.add(btnResign);
        
        panel.add(stepForward);
        panel.add(stepBackward);
        panel.add(jumpToStart);
        panel.add(jumpToFinal);
        
        

        // Add breathing room between the resign
        // button and the bottom of the app
        panel.add(Box.createVerticalStrut(30));

        return panel;
    }

    /**
     * Returns the player-info panel instance associated with this window
     *
     * @return the player-info panel instance, never null
     *
     * @author Group 9
     */
    public final PlayerInfoPanel getPlayerInfoPanel() {
        return this.playerInfoPanel;
    }

    /**
     * Returns the grid panel instance associated with this window
     *
     * @return the grid panel instance, never null
     *
     * @author
     */
    public final GridPanel getGridPanel() {
        return this.gridPanel;
    }

    /**
     * Issues a call to the controller requesting for new player information
     *
     * @author Group 9
     */
    public final void fetchCurrentPlayerInfoFromController() {
        final TOPlayer player = QuoridorController.getPlayerOfCurrentTurn();

        this.gridPanel.setWallCandidate(QuoridorController.getWallCandidate());

        // Get the grid to display correct info

        this.gridPanel.setWhitePlayer(QuoridorController.getWhitePlayer());
        this.gridPanel.setBlackPlayer(QuoridorController.getBlackPlayer());
        this.gridPanel.setWhiteWalls(QuoridorController.getWhiteWallsOnBoard());
        this.gridPanel.setBlackWalls(QuoridorController.getBlackWallsOnBoard());

        // Enable/Disable buttons based on what the player can do

        final boolean grabbed = player == null ? false : player.hasWallInHand();
        this.rotateWall.setEnabled(grabbed);

        final boolean canGrab = player == null ? false : player.canGrabWall();
        this.grabWallButton.setEnabled(canGrab);
        
        boolean inReplay=false;

        this.replayListData.clear();
        for (final String s : QuoridorController.getMovesAsStrings()) {
            // addAll only introduced post java-9, which is too new lol
            this.replayListData.addElement(s);
        }

        final int idx = QuoridorController.getIndexOfCurrentFrame();
        if (idx < 0) {
            this.replayList.clearSelection();
        } else {
            this.replayList.setSelectedIndex(idx);
        }
        this.replayList.ensureIndexIsVisible(Math.max(0, idx));

        final EnumSet<Color> winners = QuoridorController.getWinner();
        if (!winners.isEmpty()) {
            // Remember to stop the thread! Very important!
            this.stopFetchInfoThread();

            // Block all future events to the grid-panel
            this.gridPanel.setBlockListenerEvents(true);

            // Clear the wall candidate (just in case)
            this.gridPanel.setWallCandidate(null);

            // Disable the relevant buttons
            this.rotateWall.setEnabled(false);
            this.grabWallButton.setEnabled(false);
            this.btnResign.setEnabled(false);

            this.generateResultScreen(winners);
            return;
        }

        // Only update player info panel if game is still running!
        this.playerInfoPanel.updateInfo(player);
    }

    /**
     * Generates the result screen defined by result_screen.png
     *
     * @param winners Set of winners
     *
     * @author Paul Teng (260862906)
     */
    private void generateResultScreen(EnumSet<Color> winners) {
        String title = "";
        JLabel lbl = new JLabel("Fallback message");
        if (EnumSet.allOf(Color.class).equals(winners)) {
            lbl = new JLabel("DRAW!");
            title = "Quoridor: draw";
        } else {
            String str = "";
            for (Color c : winners) {
                str = QuoridorController.getPlayerByColor(c).getUsername();
                title = "Quoridor: " + c.name().toLowerCase() + " won";
            }
            lbl = new JLabel(str + " WON!");
        }
        lbl.setFont(lbl.getFont().deriveFont(28.0f));

        final int result = JOptionPane.showOptionDialog(this, lbl, title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, OPTIONS, OPTIONS[0]);
        switch (result) {
            case 0: // Restart game
                this.dispose();
                OpeningWindow.launchWindow().newGameButtonActionPerformed();
                break;
            case 1: // Enter replay mode
                // Act like as if we clicked on the same button!
                this.onEnterReplayModeButtonClicked();
                // Then restart the thread that fetches the info
                this.startFetchInfoThread();
                break;
            case 2: // Quit game
                this.onQuitGameButtonClicked();
                break;
        }
    }

    /**
     * Starts the background thread that continuously fetches the player's info
     *
     * @author Paul Teng (260862906)
     */
    public void startFetchInfoThread() {
        this.PLAYER_INFO_TIMER.start();
    }

    /**
     * Stops the background thread that continuously fetches the player's info
     *
     * @author Paul Teng (260862906)
     */
    public void stopFetchInfoThread() {
        this.PLAYER_INFO_TIMER.stop();
    }

    /**
     * This method is called when the enter-replay-mode button is clicked
     * 
     * @author Barry Chen
     */
    private void onEnterReplayModeButtonClicked() {
        //JOptionPane.showMessageDialog(this, "Enter replay mode is not implemented yet!");
    	QuoridorController.enterReplayMode();
    	this.btnEnterReplayMode.setVisible(false);
    	this.grabWallButton.setVisible(false);
    	this.rotateWall.setVisible(false);
    	this.btnResign.setVisible(false);
        this.btnExitReplayMode.setVisible(true);
        this.stepBackward.setVisible(true);
        this.stepForward.setVisible(true);
        this.jumpToStart.setVisible(true);
        this.jumpToFinal.setVisible(true);
        
    }
    
    /**
     * This method is called when the exit replay button is clicked
     *
     *
     * @author Barry Chen
     * 
     */
    private void onExitReplayModeButtonClicked() {
    	QuoridorController.exitReplayMode();
    	this.btnEnterReplayMode.setVisible(true);
    	this.grabWallButton.setVisible(true);
    	this.rotateWall.setVisible(true);
    	this.btnResign.setVisible(true);
        this.btnExitReplayMode.setVisible(false);
        this.stepBackward.setVisible(false);
        this.stepForward.setVisible(false);
        this.jumpToStart.setVisible(false);
        this.jumpToFinal.setVisible(false);

        // Force information re-fetch
        this.fetchCurrentPlayerInfoFromController();
    }

    /**
     * This method is called when the quit-game button is clicked
     *
     * We don't really System.exit, we just go back to the title screen
     *
     * @author Paul Teng (260862906)
     */
    private void onQuitGameButtonClicked() {
        this.stopFetchInfoThread();
        this.dispose();

        OpeningWindow.launchWindow();
    }

    /**
     * This method is called when the resign button is clicked
     * 
     * @author Barry Chen
     */
    private void onResignButtonClicked() {
        //JOptionPane.showMessageDialog(this, "Resign is not implemented yet!");
    	QuoridorController.playerResigns();
    }

    /**
     *
     *
     */

    private void onGrabAWallButtonClicked() {
       
        	Quoridor quoridor = QuoridorApplication.getQuoridor();
        	
        if (quoridor.getCurrentGame() != null) {
        	
        	try {
        		TOWall currentWall = QuoridorController.grabWall();
        	   WallMove wallMove = quoridor.getCurrentGame().getWallMoveCandidate();
               TOPlayer p = QuoridorController.getPlayerOfCurrentTurn();
               TOWallCandidate wallCandidate = QuoridorController.createTOWallCandidateFromWallMove(wallMove);
               gridPanel.setWallCandidate(wallCandidate);
               
               this.playerInfoPanel.updateInfo(QuoridorController.getPlayerOfCurrentTurn());
               
        	} catch (WallStockEmptyException e) {
        		 JOptionPane.showMessageDialog(this, "You have no more walls!");
        	}
 
        } else {
        	System.out.println("no game");
        }
            
       
    }

    /**
     * This method is called when rotate wall button is clicked
     */
    private void onRotateWallButtonClicked() {
        TOWallCandidate wallCandidate = gridPanel.getWallCandidate();
    	QuoridorController.rotateWall(wallCandidate);
    	
    	this.repaint();
     // *****  System.out.println("Rotated the wall candidate " );

    }
    
    private void onStepForwardButtonClicked() {

    	if(!QuoridorController.stepForward(false)) {
        	JOptionPane.showMessageDialog(this, "You are already at the end!");
        }
    	
    	//System.out.print("\n"+QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().toString()+"\n");
    	//System.out.print("\n was the fist move \n"+QuoridorApplication.getQuoridor().getCurrentGame().getPosition(QuoridorApplication.getQuoridor().getCurrentGame().getPositions().size()-1)+"\n");
    	System.out.println(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size()+" num of white walls on stock");
    	this.repaint();	
     			
     	
    }
    
    private void onStepBackwardButtonClicked() {
        
        if(!QuoridorController.stepBackward(false)) {
        	JOptionPane.showMessageDialog(this, "You are already at the start!");
        }
        //System.out.print("\n"+QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().toString()+"\n");
       // System.out.print("\n was the fist move \n"+QuoridorApplication.getQuoridor().getCurrentGame().getPosition(0)+"\n");
        System.out.println(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size()+" num of white walls on stock");
        System.out.println(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getId()+" id should be 0 at start");
        System.out.println(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite()+" is the player to move \n");
        this.repaint();
    }
    
    private void onJumpToStartButtonClicked() {
    	if(!QuoridorController.jumpToStartPosition()) {
    		JOptionPane.showMessageDialog(this, "You are already at the start!");
    	}
    	
    	this.repaint();
    	//JOptionPane.showMessageDialog(this, "Jump to start position is not implemented yet!");
    }
    
    private void onJumpToFinalButtonClicked() {

    	if (!QuoridorController.jumpToFinalPosition()) {
    		JOptionPane.showMessageDialog(this, "You are already at the end!");
    	}
    	this.repaint();
    	//JOptionPane.showMessageDialog(this, "Jump to final position is not implemented yet!");


    }
    
    private void onContinueButtonClicked() {
        JOptionPane.showMessageDialog(this, "continue is not implemented yet!");
    }

    @Override
    public void onMouseWheelRotated(double clicks) {
        // Proof that it works:
        TOWallCandidate wallCandidate = gridPanel.getWallCandidate();
    	//rotateWall
    	double val = Math.abs(clicks);
    	if(val > 0.8 && val < 1.2) {
  			QuoridorController.rotateWall(wallCandidate);
  	// *****		System.out.println("Rotated the wall candidate " );
  			this.repaint();
  			
    	}else{
    	// *****		System.out.println("To rotate must be bigger than 0.8 and smaller than 1,2: " + Math.abs(clicks));	
    	}
    }
    
    
    @Override
    public void onSlotClicked(int row, int col, Orientation orientation) {
        // Proof that it works:
    	
    	//dropWall
    	
    	
    	
       TOWallCandidate wallCandidate = gridPanel.getWallCandidate();
       if (wallCandidate == null) {
           // no wall
           return;
       }

     	if(QuoridorController.dropWall(wallCandidate.getAssociatedWall())) {//if true drop it
    		
//     		System.out.print(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()+"\n\n is the current game position"+QuoridorApplication.getQuoridor().getCurrentGame().getPositions().size()+" is size \n\n");
//        	System.out.println(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentMove()+"\n\n is the move"+QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()+" is the size\n\n");
     		System.out.println(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size()+" num of white walls on stock");
     		System.out.println(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getId()+" id should be 0 at start");
     		System.out.println(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite()+" is the player to move \n");
     		this.repaint();//the wall has been drawn and now you to 
    		gridPanel.setWallCandidate(null); //set the candidate to null once the wall is dropped 
    			
    	}else {
    		JOptionPane.showMessageDialog(this, "You cannot drop a wall here.");
    	}
    	
    // *****     System.out.println("Clicked: " + Character.toString((char) (col - 1 + 'a')) + row
    // *****             + (orientation == Orientation.VERTICAL ? "v" : "h"));
        
    }

    /**
     * This gets the pawn moving!
     *
     * @author Group-9
     */
    @Override
    public void onTileClicked(int row, int col) {
        if (this.gridPanel.getWallCandidate() != null) {
            // We are grabbing the wall, so no tile clicking is allowed
            return;
        }

        // Here we try to move the player
        
        final TOPlayer currentPlayer = QuoridorController.getPlayerOfCurrentTurn();
        final int r = currentPlayer.getRow();
        final int c = currentPlayer.getColumn();

        final int dcol = col - c;
        final int drow = row - r;

        // Steps
        if (dcol == 1 && drow == 0)     QuoridorController.moveCurrentPawnRight();
        if (dcol == -1 && drow == 0)    QuoridorController.moveCurrentPawnLeft();
        if (dcol == 0 && drow == 1)     QuoridorController.moveCurrentPawnUp();
        if (dcol == 0 && drow == -1)    QuoridorController.moveCurrentPawnDown();

        // Far jumps
        if (dcol == 2 && drow == 0)     QuoridorController.jumpCurrentPawnRight();
        if (dcol == -2 && drow == 0)    QuoridorController.jumpCurrentPawnLeft();
        if (dcol == 0 && drow == 2)     QuoridorController.jumpCurrentPawnUp();
        if (dcol == 0 && drow == -2)    QuoridorController.jumpCurrentPawnDown();

        // Lateral jumps
        if (dcol == 1 && drow == 1)     QuoridorController.jumpCurrentPawnUpRight();
        if (dcol == -1 && drow == 1)    QuoridorController.jumpCurrentPawnUpLeft();
        if (dcol == 1 && drow == -1)    QuoridorController.jumpCurrentPawnDownRight();
        if (dcol == -1 && drow == -1)   QuoridorController.jumpCurrentPawnDownLeft();
        
//        System.out.print(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()+"\n\n is the current game position"+QuoridorApplication.getQuoridor().getCurrentGame().getPositions().size()+" is size \n\n");
//    	System.out.println(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentMove()+"\n\n is the move"+QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()+" is the size\n\n");
    
        
        System.out.println(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size()+" num of white walls on stock");
        System.out.println(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getId()+" id should be 0 at start");
        //	System.out.println(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentMove()+"\n\n is the move"+QuoridorApplication.getQuoridor().getCurrentGame().getMoves().size()+" is the size\n\n");
    }

    @Override
    public void onTileEntered(int row, int col) {
        // Proof that it works:
    	// *****    System.out.println("Entered: " + Character.toString((char) (col - 1 + 'a')) + row);
    }

    @Override
    public void onTileExited(int row, int col) {
        // Proof that it works:
    	// *****    System.out.println("Exited: " + Character.toString((char) (col - 1 + 'a')) + row);
    }
    
    /**
     * @author alixe delabrousse (260868412)
     * 
     * this methods move the wall on the board along the mouse
     * you can only see the positions of the same orientation as the current one
     */

    @Override
    public void onSlotEntered(int row, int col, Orientation orientation) {
        // Proof that it works:
    	// *****    System.out.println("Entered: " + Character.toString((char) (col - 1 + 'a')) + row
    	// *****            + (orientation == Orientation.VERTICAL ? "v" : "h"));

        TOWallCandidate wallCandidate = gridPanel.getWallCandidate();
        try {
//            int aRow = wallCandidate.getRow();
//            int aCol = wallCandidate.getColumn();
        	
//            String side;
//
//            if (orientation == aOrientation) {
//                if (row == aRow && col == (aCol + 1)) {
//                    side = "right";
//                } else if (row == aRow && col == (aCol - 1)) {
//                    side = "left";
//                } else if (col == aCol && row == (aRow + 1)) {
//                    side = "up";
//                } else if (col == aCol && row == (aRow - 1)) {
//                    side = "down";
//                } else {
//                    side = "no new candidate";
//                }
//            } else {
//                side = "no new candidate";
//            }
//
//            wallCandidate = QuoridorController.moveWall(side);
        	
        	Orientation aOrientation = wallCandidate.getOrientation();
        	if (aOrientation == orientation) {
        	 	wallCandidate = QuoridorController.moveWall(row, col);
        	} else {
        		wallCandidate = QuoridorController.moveWall("no new candidate");
        	}
        	
            this.repaint();
        } catch (NullPointerException e) {
        // *****    System.out.println("No wall grabbed");
        }

    }

    @Override
    public void onSlotExited(int row, int col, Orientation orientation) {
        // Proof that it works:
    	// *****      System.out.println("Exited: " + Character.toString((char) (col - 1 + 'a')) + row
    	// *****              + (orientation == Orientation.VERTICAL ? "v" : "h"));
    }

    public static void launchWindow() {
        launchWindow("Game has started");
    }

    public static void launchWindow(String msg) {
        BoardWindow newBoardWindow = new BoardWindow();
        newBoardWindow.setSize(800, 550);
        newBoardWindow.setDefaultCloseOperation(3);
        newBoardWindow.setLocationRelativeTo(null);

        newBoardWindow.setVisible(true);
        newBoardWindow.startFetchInfoThread();

        JOptionPane.showMessageDialog(newBoardWindow, msg);
        QuoridorController.StartClock();
    }
}
