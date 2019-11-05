package ca.mcgill.ecse223.quoridor.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.Orientation;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.controller.TOPlayer;
import ca.mcgill.ecse223.quoridor.controller.TOWall;
import ca.mcgill.ecse223.quoridor.controller.TOWallCandidate;
import ca.mcgill.ecse223.quoridor.controller.WallStockEmptyException;
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

    // ***** Rendering State Variables *****

    private final DefaultListModel<String> replayList = new DefaultListModel<>();
    private final Timer PLAYER_INFO_TIMER;

    // ***** Additional UI Components *****
    private final SaveLoadPanel saveLoadPanel = new SaveLoadPanel();
    private final PlayerInfoPanel playerInfoPanel = new PlayerInfoPanel();
    private final GridPanel gridPanel = new GridPanel();

    private final JButton btnEnterReplayMode = new JButton("Enter Replay Mode");
    private final JButton btnQuitGame = new JButton("Quit Game");
    private final JButton btnResign = new JButton("Resign");

    private final JButton grabWallButton = new JButton("Grab a wall");

    /**
     * @author Mohamed Mohamed adding the drop wall and rotate wall JButton
     */

    private final JButton dropWall = new JButton("Drop Wall");
    private final JButton rotateWall = new JButton("Rotate Wall");

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

        final JScrollPane listMoves = new JScrollPane(new JList<>(replayList), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        final JPanel container = new JPanel();
        container.add(listMoves);
        container.setBorder(new EmptyBorder(10, 2, 5, 2));

        btnEnterReplayMode.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEnterReplayMode.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        btnEnterReplayMode.addActionListener(e -> this.onEnterReplayModeButtonClicked());

        btnQuitGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnQuitGame.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        btnQuitGame.addActionListener(e -> this.onQuitGameButtonClicked());

        btnResign.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnResign.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        btnResign.addActionListener(e -> this.onResignButtonClicked());

        grabWallButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        grabWallButton.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        grabWallButton.addActionListener(e -> this.onGrabAWallButtonClicked());

        /**
         * @author Mohamed Mohamed adding the drop wall and rotate wall JButton
         */
      	dropWall.setAlignmentX(Component.CENTER_ALIGNMENT);
        dropWall.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        dropWall.setEnabled(true);
        dropWall.addActionListener(e -> this.onDropWallButtonClicked());

        rotateWall.setAlignmentX(Component.CENTER_ALIGNMENT);
        rotateWall.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        rotateWall.setEnabled(true);
        rotateWall.addActionListener(e -> this.onRotateWallButtonClicked());

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
        panel.add(dropWall);
        panel.add(rotateWall);

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

        panel.add(btnResign);

        // Add breathing room between the resign
        // button and the bottom of the app
        panel.add(Box.createVerticalStrut(30));

        // XXX: Disable features not for this deliverable!
        btnEnterReplayMode.setEnabled(false);

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

        this.playerInfoPanel.updateInfo(player);
        this.gridPanel.setWallCandidate(QuoridorController.getWallCandidate());

        // Get the grid to display correct info

        this.gridPanel.setWhitePlayer(QuoridorController.getWhitePlayer());
        this.gridPanel.setBlackPlayer(QuoridorController.getBlackPlayer());
        this.gridPanel.setWhiteWalls(QuoridorController.getWhiteWallsOnBoard());
        this.gridPanel.setBlackWalls(QuoridorController.getBlackWallsOnBoard());

        // Enable/Disable buttons based on what the player can do

        final boolean grabbed = player == null ? false : player.hasWallInHand();
        this.dropWall.setEnabled(grabbed);
        this.rotateWall.setEnabled(grabbed);

        final boolean canGrab = player == null ? false : player.canGrabWall();
        this.grabWallButton.setEnabled(canGrab);
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
     */
    private void onEnterReplayModeButtonClicked() {
        JOptionPane.showMessageDialog(this, "Enter replay mode is not implemented yet!");
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
     */
    private void onResignButtonClicked() {
        JOptionPane.showMessageDialog(this, "Resign is not implemented yet!");
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
     * This method is called when Drop Wall button is clicked
     */
   private void onDropWallButtonClicked() {
        JOptionPane.showMessageDialog(this, "Click on Wall to drop it!");

    }

    /**
     * This method is called when rotate wall button is clicked
     */
    private void onRotateWallButtonClicked() {
        TOWallCandidate wallCandidate = gridPanel.getWallCandidate();
    	QuoridorController.rotateWall(wallCandidate);
    	
    	this.repaint();
        System.out.println("Rotated the wall candidate " );
       // JOptionPane.showMessageDialog(this, "Drop Wall is not implemented yet!");
    //	TOWallCandidate wall = QuoridorController.getWallCandidate();
    //    QuoridorController.rotateWall(wall.getAssociatedWall());

    }

    @Override
    public void onMouseWheelRotated(double clicks) {
        // Proof that it works:
        TOWallCandidate wallCandidate = gridPanel.getWallCandidate();
    	//rotateWall
    	double val = Math.abs(clicks);
    	if(val > 0.150 && val < 0.25) {
  			QuoridorController.rotateWall(wallCandidate);
  			System.out.println("Rotated the wall candidate " );
  			this.repaint();
  			
    	}else{
    		System.out.println("To rotate must be bigger than 0.15 and smaller than 0.25: " + Math.abs(clicks));	
    	}
    }
    
    
    @Override
    public void onSlotClicked(int row, int col, Orientation orientation) {
        // Proof that it works:
    	
    	//dropWall
       TOWallCandidate wallCandidate = gridPanel.getWallCandidate();
     	if(QuoridorController.dropWall(wallCandidate.getAssociatedWall())) {//if true drop it
    		
    		this.repaint();//the wall has been drawn and now you to 
    		gridPanel.setWallCandidate(null); //set the candidate to null once the wall is dropped 
    			
    	}else {
    		JOptionPane.showMessageDialog(this, "You cannot drop a wall here.");
    	}
    	
        System.out.println("Clicked: " + Character.toString((char) (col - 1 + 'a')) + row
                + (orientation == Orientation.VERTICAL ? "v" : "h"));
        
        
        
    }

    @Override
    public void onTileClicked(int row, int col) {
        // Proof that it works:
        System.out.println("Clicked: " + Character.toString((char) (col - 1 + 'a')) + row);
    }

    @Override
    public void onTileEntered(int row, int col) {
        // Proof that it works:
        System.out.println("Entered: " + Character.toString((char) (col - 1 + 'a')) + row);
    }

    @Override
    public void onTileExited(int row, int col) {
        // Proof that it works:
        System.out.println("Exited: " + Character.toString((char) (col - 1 + 'a')) + row);
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
        System.out.println("Entered: " + Character.toString((char) (col - 1 + 'a')) + row
                + (orientation == Orientation.VERTICAL ? "v" : "h"));

        TOWallCandidate wallCandidate = gridPanel.getWallCandidate();
        try {
            int aRow = wallCandidate.getRow();
            int aCol = wallCandidate.getColumn();
            Orientation aOrientation = wallCandidate.getOrientation();
            String side;

            if (orientation == aOrientation) {
                if (row == aRow && col == (aCol + 1)) {
                    side = "right";
                } else if (row == aRow && col == (aCol - 1)) {
                    side = "left";
                } else if (col == aCol && row == (aRow + 1)) {
                    side = "up";
                } else if (col == aCol && row == (aRow - 1)) {
                    side = "down";
                } else {
                    side = "no new candidate";
                }
            } else {
                side = "no new candidate";
            }

            wallCandidate = QuoridorController.moveWall(side);
            this.repaint();
        } catch (NullPointerException e) {
            System.out.println("No wall grabbed");
        }

    }

    @Override
    public void onSlotExited(int row, int col, Orientation orientation) {
        // Proof that it works:
        System.out.println("Exited: " + Character.toString((char) (col - 1 + 'a')) + row
                + (orientation == Orientation.VERTICAL ? "v" : "h"));
    }

    public static void launchWindow() {
        BoardWindow newBoardWindow = new BoardWindow();
        newBoardWindow.setSize(800, 550);
        newBoardWindow.setDefaultCloseOperation(3);
        newBoardWindow.setLocationRelativeTo(null);

        newBoardWindow.setVisible(true);
        newBoardWindow.startFetchInfoThread();

        JOptionPane.showMessageDialog(newBoardWindow, "Game has started");
        QuoridorController.StartClock();
    }
}
