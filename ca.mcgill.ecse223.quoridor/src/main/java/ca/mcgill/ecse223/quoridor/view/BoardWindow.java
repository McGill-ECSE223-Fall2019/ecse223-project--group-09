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

        final JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        menuBar.add(this.createFileMenu());
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

        /**
         * @author Mohamed Mohamed adding the drop wall and rotate wall JButton
         */

        final JButton dropWall = new JButton("Drop Wall");
        final JButton rotateWall = new JButton("Rotate Wall");

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

    /**
     * This method is called when Drop Wall button is clicked
     */
    private void onDropWallButtonClicked() {
        JOptionPane.showMessageDialog(this, "Drop Wall is not implemented yet!");

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