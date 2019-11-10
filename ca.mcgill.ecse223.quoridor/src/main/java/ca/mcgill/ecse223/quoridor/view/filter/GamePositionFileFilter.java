package ca.mcgill.ecse223.quoridor.view.filter;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileFilter;

import ca.mcgill.ecse223.quoridor.controller.InvalidLoadException;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;

/**
 * A file filter that only accepts GamePosition files
 *
 * Note: This is so we can differentiate between game positions and games
 *
 * @author Paul Teng (260862906)
 */
public class GamePositionFileFilter extends FileFilter implements IOPerformer {

    private static final String EXTENSION = ".dat";

    @Override
    public boolean accept(File f) {
        // So... Based on the information in the SavePosition.feature
        // These files seem to end with '.dat'
        return f.getName().toLowerCase().endsWith(EXTENSION);
    }

    @Override
    public String getDescription() {
        return "Board Snapshot (" + EXTENSION + ")";
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
     * @throws IOException          any IOException that happens ...
     * @throws InvalidLoadException any invalid loading that happens ...
     *
     * @author Paul Teng (260862906)
     */
    @Override
    public void performLoad(File file) throws IOException, InvalidLoadException {
        QuoridorController.loadPosition(file.getAbsolutePath());
    }
}