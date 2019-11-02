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

import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.controller.TOPlayer;
import ca.mcgill.ecse223.quoridor.controller.TOWallCandidate;

/**
 * Creates a window that looks somewhat like GUI3.png
 *
 * @author Paul Teng (260862906) [SavePosition.feature;LoadPosition.feature]
 */
public class BoardWindow extends JFrame {

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

    // should we implement the drop wall as clicking on the wall candidate?
    private final JButton dropWall = new JButton("Drop Wall");
    private final JButton rotateWall = new JButton("Rotate Wall");

    public BoardWindow() {
        this.setLayout(new BorderLayout());

        this.add(generateRightPanel(), BorderLayout.EAST);
        this.add(gridPanel, BorderLayout.CENTER);

        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(this.createFileMenu());

        this.setJMenuBar(menuBar);

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
        dropWall.addActionListener(e -> this.onDropWallButtonClicked());

        rotateWall.setAlignmentX(Component.CENTER_ALIGNMENT);
        rotateWall.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        rotateWall.addActionListener(e -> this.onDropWallButtonClicked());

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
     * Issues a call to the controller requesting for new player information
     *
     * @author Group 9
     */
    public final void fetchCurrentPlayerInfoFromController() {
        final TOPlayer player = QuoridorController.getPlayerOfCurrentTurn();

        this.playerInfoPanel.updateInfo(player);

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
        try {
            QuoridorController.grabWall();
            TOWallCandidate wallCandidate = QuoridorController.getCurrentWallCandidate();
            gridPanel.setWallCandidate(wallCandidate);
        } catch (Exception e) {
            System.out.println("No game loaded: create new or select game");
        }

    }

    /**
     * This method is called when Drop Wall button is clicked
     */
    private void onDropWallButtonClicked() {
        JOptionPane.showMessageDialog(this, "Drop Wall is not implemented yet!");

    }

    public static void launchWindow() {
        BoardWindow newBoardWindow = new BoardWindow();
        newBoardWindow.setSize(800, 550);
        newBoardWindow.setDefaultCloseOperation(3);
        newBoardWindow.setLocationRelativeTo(null);

        newBoardWindow.setVisible(true);
        newBoardWindow.startFetchInfoThread();
    }
}
