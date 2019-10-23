package ca.mcgill.ecse223.quoridor.view;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * A specialized JPanel that displays the components of title_screen.png
 *
 * @author Group 9
 */
public class TitleScreenView extends JPanel {

    private static final String PANE_OPERATIONS = "PANE_OPERATIONS";
    private static final String PANE_RULES = "PANE_RULES";
    private static final String PANE_ABOUT = "PANE_ABOUT";

    // ***** Additional UI Components *****
    private final JPanel bodyPanel = new JPanel(new CardLayout());

    private final JButton btnNewGame = new JButton("New Game");
    private final JButton btnLoadGame = new JButton("Load Game");

    private final JButton btnRules = new JButton("Rules");
    private final JButton btnAbout = new JButton("About");
    private final JButton btnQuitGame = new JButton("Quit Game");

    public TitleScreenView() {
        this.setLayout(new GridLayout(0, 1));

        final JLabel label = new JLabel("Quoridor");
        label.setFont(label.getFont().deriveFont(28.0f));

        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label);

        this.initializeOperationPane();
        this.initializeRulesPane();
        this.initializeAboutPane();

        this.add(bodyPanel);

        // Map rules button
        this.btnRules.addActionListener(e -> {
            final CardLayout cl = (CardLayout) this.bodyPanel.getLayout();
            cl.show(this.bodyPanel, PANE_RULES);
        });

        // Map about button
        this.btnAbout.addActionListener(e -> {
            final CardLayout cl = (CardLayout) this.bodyPanel.getLayout();
            cl.show(this.bodyPanel, PANE_ABOUT);
        });
    }

    private void initializeOperationPane() {
        final JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(this.btnNewGame);
        panel.add(this.btnLoadGame);

        panel.add(Box.createVerticalGlue());

        panel.add(this.btnRules);
        panel.add(this.btnAbout);
        panel.add(this.btnQuitGame);

        this.bodyPanel.add(panel, PANE_OPERATIONS);
    }

    private void initializeRulesPane() {
        this.initializeNewCardPane(PANE_RULES,
                "TODO: Geez... Now where are we copying our description from?");
    }

    private void initializeAboutPane() {
        this.initializeNewCardPane(PANE_ABOUT,
                "Group 9 made this for ECSE 223:\n" +
                "Barry Chen,\n" +
                "Mohamed Mohamed,\n" +
                "Ada Andrei,\n" +
                "Paul Teng,\n" +
                "Alixe Delabrousse");
    }

    /**
     * Initializes a new card pane with that has a text area (non-editable and
     * wrapping) and a back button
     *
     * @param key Key for card pane switching
     * @param desc Descriptive text in the text area
     */
    private void initializeNewCardPane(final String key, final String desc) {
        final JPanel panel = new JPanel(new GridBagLayout());

        final JTextArea text = new JTextArea();
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setText(desc);

        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = c.gridy = 0;
            c.weightx = 1.0;
            c.weighty = 1.0;
            c.anchor = GridBagConstraints.PAGE_START;
            c.fill = GridBagConstraints.BOTH;

            panel.add(new JScrollPane(text,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), c);
        }

        final JButton btn = new JButton("Back");
        btn.addActionListener(e -> {
            final CardLayout cl = (CardLayout) this.bodyPanel.getLayout();
            cl.show(this.bodyPanel, PANE_OPERATIONS);
        });

        {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 1;
            c.anchor = GridBagConstraints.PAGE_END;
            c.fill = GridBagConstraints.HORIZONTAL;

            panel.add(btn, c);
        }

        this.bodyPanel.add(panel, key);
    }

    /**
     *
     * @return the new-game button instance
     *
     * @author Group 9
     */
    public JButton getNewGameButton() {
        return this.btnNewGame;
    }

    /**
     *
     * @return the load-game button instance
     *
     * @author Group 9
     */
    public JButton getLoadGameButton() {
        return this.btnLoadGame;
    }

    /**
     *
     * @return the quit-game button instance
     *
     * @author Group 9
     */
    public JButton getQuitGameButton() {
        return this.btnQuitGame;
    }

}