package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * A specialized JPanel that displays the components of GUI1.png
 *
 * @author Group 9
 */
public class GameSetupView extends JPanel {

    // Change to 4 if we ever get want to support 4 players!
    private static final int MAX_PLAYERS = 2;

    // ***** Rendering State Variables *****
    private int displayPlayerCount = 0;
    private final Vector<String> nameHints = new Vector<>();

    // ***** Additional UI Components *****
    private final JTextField intMinutes = new JTextField();
    private final JTextField intSeconds = new JTextField();

    @SuppressWarnings("unchecked")
    private final JComboBox<String>[] cboxPlayerNames = new JComboBox[MAX_PLAYERS];
    private final JPanel panePlayers = new JPanel();

    private final JButton btnAddPlayer = new JButton("Add Player");
    private final JButton btnStartGame = new JButton("Start Game");
    private final JButton btnCancel = new JButton("Cancel");

    public GameSetupView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.initializeThinkingTimeSection();

        this.panePlayers.setLayout(new BoxLayout(this.panePlayers, BoxLayout.Y_AXIS));
        this.add(this.panePlayers);

        final JComboBox<String> player1Names = new JComboBox<>(this.nameHints);
        this.cboxPlayerNames[0] = player1Names;
        this.initializePlayerOptions("Player 1", player1Names);

        final JComboBox<String> player2Names = new JComboBox<>(this.nameHints);
        this.cboxPlayerNames[1] = player1Names;
        this.initializePlayerOptions("Player 2", player2Names);

        this.initializeButtons();

        // Map behaviour of add-player button to actually add a player
        this.btnAddPlayer.addActionListener(e -> {
            final int k = this.displayPlayerCount;
            JComboBox<String> names = this.cboxPlayerNames[k];
            if (names == null) {
                this.cboxPlayerNames[k] = names = new JComboBox<>();
            }
            this.initializePlayerOptions("Player " + (k + 1), names);
            this.revalidate();
        });
    }

    /**
     * Initializes thinking time section
     *
     * @author Group 9
     */
    private void initializeThinkingTimeSection() {
        final Border grayLine = BorderFactory.createLineBorder(Color.gray);
        final TitledBorder border = BorderFactory.createTitledBorder(grayLine, "Thinking time");

        final JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(border);

        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            panel.add(new JLabel("Minutes"), c);
        }

        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 0;
            c.weightx = 1.0;
            c.fill = GridBagConstraints.HORIZONTAL;
            panel.add(this.intMinutes, c);
        }

        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 2;
            c.gridy = 0;
            panel.add(new JLabel("Seconds"), c);
        }

        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 3;
            c.gridy = 0;
            c.weightx = 1.0;
            c.fill = GridBagConstraints.HORIZONTAL;
            panel.add(this.intSeconds, c);
        }

        this.add(panel);
    }

    /**
     * Initializes a new player option section if the maximum player limit has not
     * been reached. This method will disable the add-player button if the limit is
     * reached by the end of this call.
     *
     * @param title    The name of the section
     * @param nameList The combo box for the user to pick their name from
     *
     * @author Group 9
     */
    private synchronized void initializePlayerOptions(String title, JComboBox<String> nameList) {
        if (!this.canHandleMorePlayers()) {
            // Maximum number of players reached...
            // Do not add another player
            return;
        }

        final Border grayLine = BorderFactory.createLineBorder(Color.gray);
        final TitledBorder border = BorderFactory.createTitledBorder(grayLine, title);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(border);
        panel.add(new JLabel("Name:"));
        panel.add(nameList);

        // We want to be able to type in a brand-new name
        nameList.setEditable(true);

        // Add this to this.panePlayers!
        this.panePlayers.add(panel);

        ++displayPlayerCount;
        if (!this.canHandleMorePlayers()) {
            // Disable the add-player button
            this.btnAddPlayer.setEnabled(false);
        }
    }

    /**
     * Initializes buttons
     *
     * @author Group 9
     */
    private void initializeButtons() {
        final JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            c.weightx = 1.0;
            c.fill = GridBagConstraints.HORIZONTAL;
            panel.add(this.btnAddPlayer, c);
        }

        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 1;
            c.weightx = 1.0;
            c.fill = GridBagConstraints.HORIZONTAL;
            panel.add(this.btnStartGame, c);
        }

        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 1;
            c.weightx = 1.0;
            c.fill = GridBagConstraints.HORIZONTAL;
            panel.add(this.btnCancel, c);
        }

        this.add(panel);
    }

    /**
     * Checks if the maximum amount of players has been reached
     *
     * @return true if maximum has been reached, false if not
     *
     * @author Group 9
     */
    public boolean canHandleMorePlayers() {
        return this.displayPlayerCount < MAX_PLAYERS;
    }

    /**
     *
     * @return the start-game button instance
     *
     * @author Group 9
     */
    public JButton getStartGameButton() {
        return this.btnStartGame;
    }

    /**
     *
     * @return the cancel button instance
     *
     * @author Group 9
     */
    public JButton getCancelButton() {
        return this.btnCancel;
    }

}