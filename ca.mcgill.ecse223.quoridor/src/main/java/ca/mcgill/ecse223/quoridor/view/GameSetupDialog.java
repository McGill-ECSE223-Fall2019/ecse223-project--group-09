package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Time;
import java.util.Collection;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.InputVerifier;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * A specialized JPanel that displays the components of GUI1.png
 *
 * @author Group 9
 */
public class GameSetupDialog extends JPanel {

    private static final Object[] OPTIONS = { "Start Game", "Cancel" };

    public static final int START_GAME_OPTION = 0;
    public static final int CANCEL_OPTION = 1;

    private static final String DEFAULT_TIME_LIMIT = "3:00";

    // ***** Rendering State Variables *****
    private final Vector<String> nameHints = new Vector<>();

    // ***** Additional UI Components *****
    private final JTextField timeField = new JTextField();

    @SuppressWarnings("unchecked")
    private final JComboBox<String>[] cboxPlayerNames = new JComboBox[2];
    private final JPanel panePlayers = new JPanel();

    public GameSetupDialog() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.initializeThinkingTimeSection();

        this.panePlayers.setLayout(new BoxLayout(this.panePlayers, BoxLayout.Y_AXIS));
        this.add(this.panePlayers);

        {
            final JComboBox<String> player1Names = new JComboBox<>(this.nameHints);
            this.cboxPlayerNames[0] = player1Names;
            this.initializePlayerOptions("Player 1", player1Names, "white");
        }

        {
            final JComboBox<String> player2Names = new JComboBox<>(this.nameHints);
            this.cboxPlayerNames[1] = player2Names;
            this.initializePlayerOptions("Player 2", player2Names, "black");
        }
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
     * @param colorStr The color of the player
     *
     * @author Group 9
     */
    private synchronized void initializePlayerOptions(String title, JComboBox<String> nameList, String colorStr) {
        final Border grayLine = BorderFactory.createLineBorder(Color.gray);
        final TitledBorder border = BorderFactory.createTitledBorder(grayLine, title + " (" + colorStr + ')');

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(border);
        panel.add(new JLabel("Name:"));
        panel.add(nameList);

        // We want to be able to type in a brand-new name
        nameList.setEditable(true);

        // Add this to this.panePlayers!
        this.panePlayers.add(panel);
    }

    /**
     * Note: Output is only defined if the index is in the correct range
     *
     * @param idx index of the player [0, max-number-of-numbers)
     * @return the selected name of the particular player
     *
     * @author Group 9
     */
    public String getSelectedPlayerName(int idx) {
        // Cast is safe since JComboBox all work with strings
        return (String) this.cboxPlayerNames[idx].getSelectedItem();
    }

    /**
     * Checks to see if all players have a name selected
     * 
     * @return true if all players have a name selected, false otherwise
     * 
     * @author Group 9
     */
    public boolean allPlayersHaveName() {
        for (final JComboBox<?> cb : this.cboxPlayerNames) {
            if (cb.getSelectedItem() == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if list of player name hints is empty
     *
     * @return true if empty, false otherwise
     *
     * @author Group 9
     */
    public boolean nameHintsIsEmpty() {
        return this.nameHints.isEmpty();
    }

    /**
     * Removes all player name hints
     *
     * @author Group 9
     */
    public void removeAllNameHints() {
        this.nameHints.clear();
        this.repaint();
    }

    /**
     * Adds a name to the list of player name hints
     *
     * Note: name is added even if the name already exists
     *
     * @param name name being added
     *
     * @see GameSetupDialog#addNewNameHint(String)
     * @author Group 9
     */
    public void addNameHint(String name) {
        this.nameHints.add(name);
    }

    /**
     * Adds a new name to the list of player name hints
     *
     * Note: name is not added if name already exists
     *
     * @param name new name being added
     * @return true if name is added, false if name already exists
     *
     * @see GameSetupDialog#addNameHint(String)
     * @author Group 9
     */
    public boolean addNewNameHint(String name) {
        if (this.nameHints.contains(name)) {
            return false;
        }
        this.nameHints.add(name);
        return true;
    }

    /**
     * Replaces the list of name hints with a new list
     *
     * @param names The new list of name hints
     *
     * @author Group 9
     */
    public void replaceNameHints(Collection<String> names) {
        this.nameHints.clear();
        this.nameHints.addAll(names);
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

        return new Time(hstr == null ? 0 : Integer.parseInt(hstr), mstr == null ? 0 : Integer.parseInt(mstr),
                sstr == null ? 0 : Integer.parseInt(sstr));
    }

    /**
     * Displays the setup dialog
     *
     * @param parent The parent component of the dialog
     *
     * @return {@link GameSetupDialog#START_GAME_OPTION},
     *         {@link GameSetupDialog#CANCEL_OPTION}, or
     *         {@link JOptionPane#CLOSED_OPTION}
     *
     * @author Paul Teng (260862906)
     */
    public int showSetupDialog(final Component parent) {
        int ret;
        do {
            ret = JOptionPane.showOptionDialog(parent, this, "Game Setup", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, OPTIONS, OPTIONS[0]);
        } while (ret == START_GAME_OPTION && this.timeField.getForeground() == TimeInputVerifier.INVALID_COLOR);
        return ret;
    }

    /**
     * Verifies if the input is a valid time
     *
     * Assumption: - Use this only on JTextField
     *
     * @author Group 9
     */
    private static class TimeInputVerifier extends InputVerifier {

        public static final Color VALID_COLOR = Color.black;
        public static final Color INVALID_COLOR = Color.red;

        // CHANGE THIS PATTERN WITH CARE AS THERE ARE
        // CODE THAT DEPEND ON THE GROUPINGS OF THIS!
        public static final Pattern TIME_FMT = Pattern.compile("^(?:(?:([0-5]?[0-9]):)?([0-5]?[0-9]):)?([0-5]?[0-9])$");

        @Override
        public boolean verify(JComponent component) {
            // We will only use this on a JTextField
            JTextField field = (JTextField) component;
            final boolean result = TIME_FMT.matcher(field.getText()).matches();

            // Change to red if input is bad!
            field.setForeground(result ? VALID_COLOR : INVALID_COLOR);

            return result;
        }
    }
}