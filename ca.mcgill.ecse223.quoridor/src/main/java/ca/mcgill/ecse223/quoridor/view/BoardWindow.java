package ca.mcgill.ecse223.quoridor.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Creates a window that looks somewhat like GUI3.png
 *
 * @author Paul Teng (260862906) [SavePosition.feature;LoadPosition.feature]
 */
public class BoardWindow extends JFrame {

    //***** Additional UI Components *****
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

        final JButton btnQuitGame = new JButton("Quit Game");
        btnQuitGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnQuitGame.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        btnQuitGame.addActionListener(e -> this.onQuitGameButtonClicked());

        final JButton btnResign = new JButton("Resign");
        btnResign.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnResign.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        btnResign.addActionListener(e -> this.onResignButtonClicked());

        panel.add(saveLoadPanel);
        panel.add(btnQuitGame);

        panel.add(Box.createVerticalGlue());

        panel.add(playerInfoPanel);

        // TODO: Remember to add the grab-wall button here

        panel.add(btnResign);

        // Add breathing space between the resign
        // button and the bottom of the app
        panel.add(Box.createVerticalStrut(70));
        return panel;
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

    public static void main(String[] args) {
        // This is just a demo of how it could look

        final BoardWindow frame = new BoardWindow();
        frame.setTitle("DEMO");
        frame.setDefaultCloseOperation(3);
        frame.setSize(800, 700);
        frame.setVisible(true);
    }
}
