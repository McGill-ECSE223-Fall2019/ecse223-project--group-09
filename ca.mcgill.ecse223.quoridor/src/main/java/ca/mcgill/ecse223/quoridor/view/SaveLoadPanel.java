package ca.mcgill.ecse223.quoridor.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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

    /**
     * Causes a dialog box to pop up with the full stack trace of the throwable
     * 
     * Note: This is a debug-level construct, use it sparringly
     * 
     * @param parentComponent The associated parent UI component for this dialog box
     * @param throwable The throwable whose stack trace is being dumped
     * 
     * @author Paul TEng (260862906)
     */
    public static void displayThrowableTrace(Component parentComponent, Throwable throwable) {
        final StringWriter pw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(pw));

        final JTextArea textArea = new JTextArea(pw.toString(), 20, 50);
        textArea.setEditable(false);
        textArea.setTabSize(2);

        final JComponent[] list = {
            new JLabel("Something bad just happened"),
            new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED)
        };
        JOptionPane.showMessageDialog(parentComponent, list, "Internal Error", JOptionPane.WARNING_MESSAGE);
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