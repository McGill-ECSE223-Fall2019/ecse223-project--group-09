package ca.mcgill.ecse223.quoridor.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A custom component that displays a save and load button
 *
 * @author Paul Teng (260862906) [SavePosition.feature;LoadPosition.feature]
 */
public class SaveLoadPanel extends JPanel {

    // ***** Additional UI Components *****
    private final JButton btnSave = new JButton("Save");
    private final JButton btnLoad = new JButton("Load");

    /**
     * Initializes a SaveLoadPanel
     *
     * @author Paul Teng (260862906)
     */
    public SaveLoadPanel() {
        this.setLayout(new GridBagLayout());

        final GridBagConstraints saveCst = new GridBagConstraints();
        saveCst.gridx = 0;
        saveCst.gridy = 0;
        saveCst.weightx = 0.5;
        saveCst.fill = GridBagConstraints.HORIZONTAL;
        this.add(btnSave, saveCst);

        final GridBagConstraints loadCst = new GridBagConstraints();
        loadCst.gridx = 1;
        loadCst.gridy = 0;
        loadCst.weightx = 0.5;
        loadCst.fill = GridBagConstraints.HORIZONTAL;
        this.add(btnLoad, loadCst);

        this.btnSave.addActionListener(e -> this.doSaveAction());
        this.btnLoad.addActionListener(e -> this.doLoadAction());
    }

    /**
     * Callback for when the save button is clicked
     *
     * @author Paul Teng (260862906)
     */
    public void doSaveAction() {
        System.out.println("Start save...");
    }

    /**
     * Callback for when the save button is clicked
     *
     * @author Paul Teng (260862906)
     */
    public void doLoadAction() {
        System.out.println("Start load...");
    }

    public static void main(String[] args) {
        // This is just a demo of how it could look

        javax.swing.JFrame frame = new javax.swing.JFrame("DEMO");
        final SaveLoadPanel panel = new SaveLoadPanel();
        frame.add(panel);
        frame.setSize(200, 120);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }
}