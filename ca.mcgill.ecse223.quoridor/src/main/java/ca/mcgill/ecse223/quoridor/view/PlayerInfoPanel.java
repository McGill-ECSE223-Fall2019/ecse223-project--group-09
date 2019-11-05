package ca.mcgill.ecse223.quoridor.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Time;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mcgill.ecse223.quoridor.controller.TOPlayer;

/**
 * A custom component that displays information that is relevant to the current
 * player
 *
 * @author Group 9 [SwitchPlayer.feature]
 */
public class PlayerInfoPanel extends JPanel {

    // ***** Additional UI Components *****
    private final JLabel lblName = new JLabel();
    private final JLabel lblColor = new JLabel();
    private final JLabel lblWalls = new JLabel();
    private final JLabel lblTime = new JLabel();

    /**
     * Creates a PlayerInfoPanel that tries to fill up space in the x direction but
     * stays compact in the y direction
     *
     * @author Group 9
     */
    public PlayerInfoPanel() {
        // This means we want it to:
        // - fill up space in x direction
        // - keep it compact in y direction
        this(1.0, 0.0);
    }

    /**
     * Creates a PlayerInfoPanel with the specified filling weights
     *
     * @param weightX Filling weight in x direction
     * @param weightY Filling weight in y direction
     *
     * @author Group 9
     */
    public PlayerInfoPanel(final double weightX, final double weightY) {
        this.setLayout(new GridBagLayout());

        final Insets insetsNBot = new Insets(10, 10, 0, 10);
        final Insets insetsNTop = new Insets(0, 10, 10, 10);

        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.weightx = weightX;
            c.weighty = weightY;
            c.insets = insetsNBot;
            this.add(new JLabel("Current player:"), c);
        }
        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 0;
            c.anchor = GridBagConstraints.FIRST_LINE_END;
            c.weightx = weightX;
            c.weighty = weightY;
            c.insets = insetsNBot;
            this.add(lblName, c);
        }

        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 1;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.weightx = weightX;
            c.weighty = weightY;
            c.insets = insetsNTop;
            this.add(new JLabel("Color:"), c);
        }
        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 1;
            c.anchor = GridBagConstraints.FIRST_LINE_END;
            c.weightx = weightX;
            c.weighty = weightY;
            c.insets = insetsNTop;
            this.add(lblColor, c);
        }

        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 2;
            c.anchor = GridBagConstraints.LAST_LINE_START;
            c.weightx = weightX;
            c.insets = insetsNBot;
            this.add(new JLabel("Walls remaining:"), c);
        }
        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 2;
            c.anchor = GridBagConstraints.LAST_LINE_END;
            c.weightx = weightX;
            c.insets = insetsNBot;
            this.add(lblWalls, c);
        }

        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 3;
            c.anchor = GridBagConstraints.LAST_LINE_START;
            c.weightx = weightX;
            c.insets = insetsNTop;
            this.add(new JLabel("Time remaining:"), c);
        }
        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 3;
            c.anchor = GridBagConstraints.LAST_LINE_END;
            c.weightx = weightX;
            c.insets = insetsNTop;
            this.add(lblTime, c);
        }
    }

    /**
     * Updates the info displayed
     *
     * @param player Player transfer object containing the new info. null will cause
     *               current displayed info to be cleared
     *
     * @author Group 9
     */
    public void updateInfo(TOPlayer player) {
        if (player == null) {
            // Clears all info
            this.lblName.setText("");
            this.lblColor.setText("");
            this.lblWalls.setText("");
            this.lblTime.setText("");
            return;
        }

        this.lblName.setText(player.getUsername());
        this.lblColor.setText(player.getColor().name().toLowerCase());
        this.lblWalls.setText(Integer.toString(player.getWallsRemaining()));

        final Time time = player.getTimeRemaining();
        this.lblTime.setText(String.format("%02d:%02d:%02d", time.getHours(), time.getMinutes(), time.getSeconds()));
    }

    /**
     * Returns the text displayed by the color label. Exists so we can test the UI.
     *
     * @return text, could be empty but never null
     * 
     * @author Group 9
     */
    public final String getPlayerColorText() {
        return this.lblColor.getText();
    }
}