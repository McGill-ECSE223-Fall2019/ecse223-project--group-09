package ca.mcgill.ecse223.quoridor.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ca.mcgill.ecse223.quoridor.controller.InvalidLoadException;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;

/**
 * A custom component that displays a save and load button
 *
 * @author Paul Teng (260862906) [SavePosition.feature;LoadPosition.feature]
 */
public class SaveLoadPanel extends JPanel {

    // ***** Additional UI Components *****
    private final JButton btnSave = new JButton("Save");
    private final JButton btnLoad = new JButton("Load");

    private final JFileChooser chooser = new JFileChooser();

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
        File file;
        while (true) {
            if (this.chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
                // User did not click on save/approve button in file chooser,
                // we are done.
                return;
            }

            file = this.chooser.getSelectedFile();
            if (!file.exists()) {
                // File is doesn't exist, no need to prompt for overwriting
                break;
            }

            final int retVal = JOptionPane.showConfirmDialog(this,
                    "Selected file " + file.getName() + " already exists...\n" +
                    "\n" +
                    "Are you sure you want to save here?\n" +
                    "This will overwrite the contents of the selected file",
                    "Confirm File Overwrite",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            // Yes    -> Proceed with overwriting
            // No     -> Stop this saving process
            // Cancel -> Prompts user for file again
            if (retVal == JOptionPane.YES_OPTION) break;
            if (retVal == JOptionPane.NO_OPTION) return;
            if (retVal == JOptionPane.CANCEL_OPTION) continue;
        }

        try {
            // If file does not exist, overwrite flag is ignored,
            // If file does exist, reaching here means we want overwriting
            // (hence true for overwrite-flag parameter)
            QuoridorController.savePosition(file.getAbsolutePath(), true);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Save operation failed:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Callback for when the save button is clicked
     *
     * @author Paul Teng (260862906)
     */
    public void doLoadAction() {
        if (this.chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        final File f = this.chooser.getSelectedFile();
        try {
            QuoridorController.loadPosition(f.getAbsolutePath());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Load operation failed:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidLoadException ex) {
            JOptionPane.showMessageDialog(this, "Loaded file is invalid:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) {
            displayThrowableTrace(this, ex);
        }
    }

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