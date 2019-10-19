package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Time;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
    private static final String DEFAULT_TIME_LIMIT = "15:00";

    // ***** Rendering State Variables *****
    private int displayPlayerCount = 0;
    private final Vector<String> nameHints = new Vector<>();

    // ***** Additional UI Components *****
    private final JTextField timeField = new JTextField();

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

        {
        final JComboBox<String> player1Names = new JComboBox<>(this.nameHints);
        this.cboxPlayerNames[0] = player1Names;
        this.initializePlayerOptions("Player 1", player1Names);
        }

        {
        final JComboBox<String> player2Names = new JComboBox<>(this.nameHints);
            this.cboxPlayerNames[1] = player2Names;
        this.initializePlayerOptions("Player 2", player2Names);
        }

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
            panel.add(new JLabel("mm:ss format"), c);
        }

        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 0;
            c.weightx = 1.0;
            c.fill = GridBagConstraints.HORIZONTAL;
            panel.add(this.timeField, c);

            this.timeField.setText(DEFAULT_TIME_LIMIT);
            this.timeField.setInputVerifier(new TimeInputVerifier());
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

    /**
     *
     * @return the thinking time based on the values provided by the user; null if
     *         input has bad format and is not a *time*
     *
     * @author Group 9
     */
    public Time getThinkingTime() {
        final String text = this.timeField.getText();
        final Matcher matcher = TimeInputVerifier.TIME_FMT.matcher(text);
        if (!matcher.matches()) {
            // Bad format
            return null;
        }

        final String hstr = matcher.group(1);
        final String mstr = matcher.group(2);
        final String sstr = matcher.group(3);

        return new Time(
                hstr == null ? 0 : Integer.parseInt(hstr),
                mstr == null ? 0 : Integer.parseInt(mstr),
                sstr == null ? 0 : Integer.parseInt(sstr));
    }

    /**
     * Verifies if the input is a valid time
     *
     * Assumption: - Use this only on JTextField
     *
     * @author Group 9
     */
    private static class TimeInputVerifier extends InputVerifier {

        // CHANGE THIS PATTERN WITH CARE AS THERE ARE
        // CODE THAT DEPEND ON THE GROUPINGS OF THIS!
        public static final Pattern TIME_FMT = Pattern.compile("^(?:(?:([0-5]?[0-9]):)?([0-5]?[0-9]):)?([0-5]?[0-9])$");

        @Override
        public boolean verify(JComponent component) {
            // We will only use this on a JTextField
            JTextField field = (JTextField) component;
            final boolean result = TIME_FMT.matcher(field.getText()).matches();

            // Change to red if input is bad!
            field.setForeground(result ? Color.black : Color.red);

            return result;
        }
    }

}