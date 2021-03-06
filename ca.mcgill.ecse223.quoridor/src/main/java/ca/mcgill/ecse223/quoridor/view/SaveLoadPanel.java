package ca.mcgill.ecse223.quoridor.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import ca.mcgill.ecse223.quoridor.controller.InvalidLoadException;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.view.filter.GameFileFilter;
import ca.mcgill.ecse223.quoridor.view.filter.GamePositionFileFilter;
import ca.mcgill.ecse223.quoridor.view.filter.IOPerformer;

/**
 * A custom component that displays a save and load button
 *
 * Note: Future features save-game and load-game will also be done here
 * When that happens, be sure to update the author tag accordingly
 *
 * @author Paul Teng (260862906) [SavePosition.feature;LoadPosition.feature]
 */
public class SaveLoadPanel extends JPanel {

    private static final boolean IS_MAC;

    static {
        final String osName = System.getProperty("os.name").toLowerCase();
        IS_MAC = osName.contains("mac os x") || osName.contains("darwin") || osName.contains("osx");
    }

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

        // Only use the filters we provide (no Choose-any-file-type option)
        this.chooser.setAcceptAllFileFilterUsed(false);

        this.chooser.addChoosableFileFilter(new GamePositionFileFilter());
        this.chooser.addChoosableFileFilter(new GameFileFilter());
    }

    /**
     * Adds the equivalent save load functionality to a menu bar (like how most
     * save / open file buttons are located)
     *
     * @param menu Menu we are adding to
     *
     * @author Paul Teng (260862906)
     */
    public void addMenuEntries(JMenu menu) {
        final JMenuItem loadFileItem = new JMenuItem("Load...");
        loadFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, IS_MAC ? KeyEvent.META_DOWN_MASK : KeyEvent.CTRL_DOWN_MASK));
        loadFileItem.addActionListener(e -> this.doLoadAction());

        final JMenuItem saveFileItem = new JMenuItem("Save...");
        saveFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, IS_MAC ? KeyEvent.META_DOWN_MASK : KeyEvent.CTRL_DOWN_MASK));
        saveFileItem.addActionListener(e -> this.doSaveAction());

        menu.add(loadFileItem);
        menu.addSeparator();
        menu.add(saveFileItem);
    }

    /**
     * Prompts the user, potentially multiple times, for a file then proceeds
     * to save the current game position to it
     *
     * This is also used as the action callback for the save button
     *
     * @author Paul Teng (260862906)
     */
    public void doSaveAction() {
        try {
            QuoridorController.stopClockForCurrentPlayer();

            File file;
            IOPerformer filter;
            while (true) {
                if (this.chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
                    // User did not click on save/approve button in file chooser,
                    // we are done.
                    return;
                }

                filter = (IOPerformer) this.chooser.getFileFilter();
                file = filter.normalizeExtension(this.chooser.getSelectedFile());

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
                filter.performSave(file);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Save operation failed:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (RuntimeException ex) {
                displayThrowableTrace(this, ex);
            }
        } finally {
            QuoridorController.runClockForCurrentPlayer();
        }
    }

    /**
     * Prompts the user for a file then proceeds to load it in as a position
     *
     * This is also used as the action callback for the load button
     *
     * @author Paul Teng (260862906)
     */
    public void doLoadAction() {
        try {
            QuoridorController.stopClockForCurrentPlayer();

            if (this.chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            final IOPerformer filter = (IOPerformer) this.chooser.getFileFilter();
            final File file = filter.normalizeExtension(this.chooser.getSelectedFile());
            try {
                filter.performLoad(file);
                BoardWindow.launchWindow("File: " + file + " was successfully loaded\nGet ready!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Load operation failed:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidLoadException ex) {
                JOptionPane.showMessageDialog(this, "Loaded file is invalid:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (RuntimeException ex) {
                displayThrowableTrace(this, ex);
            }
        } finally {
            QuoridorController.runClockForCurrentPlayer();
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
            new JLabel(throwable instanceof UnsupportedOperationException ? "Operation is not supported yet!" : "Something bad just happened"),
            new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED)
        };
        JOptionPane.showMessageDialog(parentComponent, list, "Internal Error", JOptionPane.WARNING_MESSAGE);
    }
}