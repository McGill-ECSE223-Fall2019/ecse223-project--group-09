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
import javax.swing.filechooser.FileFilter;

import ca.mcgill.ecse223.quoridor.controller.InvalidLoadException;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;

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
        File file;
        FileFilter filter;
        while (true) {
            if (this.chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
                // User did not click on save/approve button in file chooser,
                // we are done.
                return;
            }

            filter = this.chooser.getFileFilter();
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
            ((IOPerformer) filter).performSave(file);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Save operation failed:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) {
            displayThrowableTrace(this, ex);
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
        if (this.chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        final FileFilter filter = this.chooser.getFileFilter();
        final File file = this.chooser.getSelectedFile();
        try {
            ((IOPerformer) filter).performLoad(file);
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

    /**
     * Hack around needing to do instanceof when checking which method to load
     * the file with
     *
     * @author Paul Teng (260862906)
     */
    private static interface IOPerformer {

        /**
         * Tries to save whatever it is to a file. Should overwrite if file
         * already exists.
         *
         * @param file The file being written to
         * @throws IOException any IOException that happens...
         *
         * @author Paul Teng (260862906)
         */
        public abstract void performSave(File file) throws IOException;

        /**
         * Tries to load whatever it is from a file. File guaranteed to exist
         *
         * @param file The file being read from
         * @throws IOException any IOException that happens ...
         * @throws InvalidLoadException any invalid loading that happens ...
         */
        public abstract void performLoad(File file) throws IOException, InvalidLoadException;
    }

    /**
     * A file filter that only accepts GamePosition files
     *
     * Note: This is so we can differentiate between game positions and games
     *
     * @author Paul Teng (260862906)
     */
    private static class GamePositionFileFilter extends FileFilter implements IOPerformer {

        @Override
        public boolean accept(File f) {
            // Right now there is no filtering process
            return true;
        }

        @Override
        public String getDescription() {
            return "Board Snapshot (GamePosition)";
        }

        /**
         * Saves the game position to the file
         *
         * @param file The file being written to
         * @throws IOException any IOException that happens...
         *
         * @author Paul Teng (260862906)
         */
        @Override
        public void performSave(File file) throws IOException {
            // If file does not exist, overwrite flag is ignored,
            // If file does exist, reaching here means we want overwriting
            // (hence true for overwrite-flag parameter)
            QuoridorController.savePosition(file.getAbsolutePath(), true);
        }

        /**
         * Loads the game position from the file
         *
         * @param file The file being read from
         * @throws IOException any IOException that happens ...
         * @throws InvalidLoadException any invalid loading that happens ...
         *
         * @author Paul Teng (260862906)
         */
        @Override
        public void performLoad(File file) throws IOException, InvalidLoadException {
            QuoridorController.loadPosition(file.getAbsolutePath());
        }
    }

    /**
     * A file filter that only accepts Game files
     *
     * Right now it is just a placeholder for the future save-game and
     * load-game feature
     *
     * Note: This is so we can differentiate between game positions and games
     *
     * @author Paul Teng (260862906)
     */
    private static class GameFileFilter extends FileFilter implements IOPerformer {

        @Override
        public boolean accept(File f) {
            // Right now there is no filtering process
            return true;
        }

        @Override
        public String getDescription() {
            return "Game";
        }

        /**
         * This will be filled in by whoever is asssigned to the later save
         * game feature (hence no author)
         *
         * @param file The file being written to
         * @throws IOException any IOException that happens...
         */
        @Override
        public void performSave(File file) throws IOException {
            throw new UnsupportedOperationException("Wait for Phase 2 Save game");
        }

        /**
         * This will be filled in by whoever is asssigned to the later load
         * game feature (hence no author)
         *
         * @param file The file being read from
         * @throws IOException any IOException that happens ...
         * @throws InvalidLoadException any invalid loading that happens ...
         */
        @Override
        public void performLoad(File file) throws IOException, InvalidLoadException {
            throw new UnsupportedOperationException("Wait for Phase 2 Load game");
        }
    }

    public static void main(String[] args) {
        // This is just a demo of how it could look

        javax.swing.JFrame frame = new javax.swing.JFrame("DEMO");

        final SaveLoadPanel panel = new SaveLoadPanel();
        frame.add(panel);

        final javax.swing.JMenuBar bar = new javax.swing.JMenuBar();
        final JMenu fileMenu = new JMenu("File");
        bar.add(fileMenu);
        panel.addMenuEntries(fileMenu);
        frame.setJMenuBar(bar);

        frame.setSize(200, 120);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }
}