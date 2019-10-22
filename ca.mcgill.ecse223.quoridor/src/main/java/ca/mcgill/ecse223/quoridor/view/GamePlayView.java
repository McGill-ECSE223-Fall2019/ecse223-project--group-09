package ca.mcgill.ecse223.quoridor.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.Time;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * A specialized JPanel that displays the components of GUI3.png
 *
 * @author Group 9
 */
public class GamePlayView extends JPanel {

    // ***** Rendering State Variables *****
    private final DefaultListModel<String> gameLog = new DefaultListModel<>();

    // ***** Additional UI Components *****
    private final BoardView board;

    private final JScrollPane panelGameLog = new JScrollPane(new JList<>(this.gameLog),
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private final JButton btnEnterReplayMode = new JButton("Enter Replay Mode");

    private final JButton btnSave = new JButton("Save");
    private final JButton btnLoad = new JButton("Load");
    private final JButton btnQuit = new JButton("Quit Game");

    private final JLabel lblCurrentPlayerName = new JLabel();
    private final JLabel lblWallsRemaining = new JLabel();
    private final JLabel lblTimeRemaining = new JLabel();

    private final JButton btnGrabWall = new JButton("Grab Wall");
    private final JButton btnResign = new JButton("Resign");

    public GamePlayView(BoardView board) {
        this.board = board;

        this.setLayout(new GridBagLayout());
        this.initializeBoardView();
        this.initializeReplayFeatures();
        this.initializeSaveLoadQuitButtons();
        this.initializePlayerInfo();
        this.initializePlayerOperations();
    }

    private void initializeBoardView() {
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        c.gridheight = 6;
        c.weightx = 1;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;

        this.add(this.board, c);
    }

    private void initializeReplayFeatures() {
        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 0;
            c.gridwidth = 2;
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(10, 10, 10, 10);

            this.add(this.panelGameLog, c);
        }

        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 1;
            c.gridwidth = 2;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.insets = new Insets(0, 0, 35, 0);

            this.add(this.btnEnterReplayMode, c);
        }
    }

    private void initializeSaveLoadQuitButtons() {
        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 2;
            c.weightx = 0.05;
            c.fill = GridBagConstraints.HORIZONTAL;

            this.add(this.btnSave, c);
        }
        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 2;
            c.gridy = 2;
            c.weightx = 0.05;
            c.fill = GridBagConstraints.HORIZONTAL;

            this.add(this.btnLoad, c);
        }
        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 3;
            c.gridwidth = 2;
            c.fill = GridBagConstraints.HORIZONTAL;

            this.add(this.btnQuit, c);
        }
    }

    private void initializePlayerInfo() {
        final JPanel p = new JPanel();
        p.setLayout(new GridLayout(0, 2));

        p.add(new JLabel("Current player:"));
        p.add(this.lblCurrentPlayerName);

        p.add(new JLabel("Walls Remaining:"));
        p.add(this.lblWallsRemaining);

        p.add(new JLabel("Time Remaining:"));
        p.add(this.lblTimeRemaining);

        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 4;
            c.gridwidth = 2;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.insets = new Insets(35, 10, 0, 10);

            this.add(p, c);
        }
    }

    private void initializePlayerOperations() {
        final JPanel p = new JPanel();
        p.setLayout(new GridLayout(0, 1));

        p.add(this.btnGrabWall);
        p.add(this.btnResign);

        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 5;
            c.gridwidth = 2;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.PAGE_END;

            this.add(p, c);
        }
    }

    /**
     * Appends a string to the game log
     *
     * @param log String being appended
     *
     * @author Group 9
     */
    public void appendGameLog(final String log) {
        // Must use invokeLater otherwise blink-and-gone might happen!
        SwingUtilities.invokeLater(() -> this.gameLog.addElement(log));
    }

    /**
     * Prependss a string to the game log
     *
     * @param log String being prepended
     *
     * @author Group 9
     */
    public void prependGameLog(final String log) {
        SwingUtilities.invokeLater(() -> this.gameLog.insertElementAt(log, 0));
    }

    /**
     * Clears the game log
     *
     * @author Group 9
     */
    public void clearGameLog() {
        SwingUtilities.invokeLater(() -> this.gameLog.clear());
    }

    /**
     *
     * @return the enter-replay-mode button instance
     *
     * @author Group 9
     */
    public JButton getEnterReplayModeButton() {
        return this.btnEnterReplayMode;
    }

    /**
     *
     * @return the save button instance
     *
     * @author Group 9
     */
    public JButton getSaveButton() {
        return this.btnSave;
    }

    /**
     *
     * @return the load button instance
     *
     * @author Group 9
     */
    public JButton getLoadButton() {
        return this.btnLoad;
    }

    /**
     *
     * @return the quit-game button instance
     *
     * @author Group 9
     */
    public JButton getQuitGameButton() {
        return this.btnQuit;
    }

    /**
     *
     * @return the grab-wall button instance
     *
     * @author Group 9
     */
    public JButton getGrabWallButton() {
        return this.btnGrabWall;
    }

    /**
     *
     * @return the resign button instance
     *
     * @author Group 9
     */
    public JButton getResignButton() {
        return this.btnResign;
    }

    /**
     * Sets the player's name being displayed
     *
     * @param name Player's name
     *
     * @author Group 9
     */
    public void setCurrentPlayerName(String name) {
        this.lblCurrentPlayerName.setText(name);
    }

    /**
     * Sets the amount of walls remaining being displayed
     *
     * @param rem Amount of walls remaining
     *
     * @author Group 9
     */
    public void setWallsRemaining(int rem) {
        this.lblWallsRemaining.setText(Integer.toString(rem));
    }

    /**
     * Sets the amount of time remaining being displayed
     *
     * @param t Amount of time remaining
     *
     * @author Group 9
     */
    public void setTimeRemaining(Time t) {
        this.lblTimeRemaining.setText(t.getMinutes() + ":" + t.getMinutes());
    }


}