package ca.mcgill.ecse223.quoridor.view;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
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

	public BoardWindow() {
        this.setLayout(new BorderLayout());

        this.add(generateRightPanel(), BorderLayout.EAST);

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

        panel.add(saveLoadPanel);
        panel.add(Box.createVerticalGlue());
        panel.add(playerInfoPanel);
        return panel;
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
