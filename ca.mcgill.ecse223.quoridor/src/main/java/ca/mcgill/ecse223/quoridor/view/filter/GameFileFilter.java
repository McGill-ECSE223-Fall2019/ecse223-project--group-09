package ca.mcgill.ecse223.quoridor.view.filter;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileFilter;

import ca.mcgill.ecse223.quoridor.controller.InvalidLoadException;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;

/**
 * A file filter that only accepts Game files
 *
 * Right now it is just a placeholder for the future save-game and load-game
 * feature
 *
 * Note: This is so we can differentiate between game positions and games
 *
 * @author Paul Teng (260862906)
 */
public class GameFileFilter extends FileFilter implements IOPerformer {

    private static final String EXTENSION = ".mov";

    @Override
    public boolean accept(File f) {
        // So... Based on the information in the SavePosition.feature
        // These files seem to end with '.mov'
        return f.getName().toLowerCase().endsWith(EXTENSION);
    }

    @Override
    public String getDescription() {
        return "Game (" + EXTENSION + ")";
    }

    /**
     * This will be filled in by whoever is asssigned to the later save game feature
     * (hence no author)
     *
     * @param file The file being written to
     * @throws IOException any IOException that happens...
     */
    @Override
    public void performSave(File file) throws IOException {
        throw new UnsupportedOperationException("Wait for Phase 2 Save game");
    }

    /**
     * This will be filled in by whoever is asssigned to the later load game feature
     * (hence no author)
     *
     * @param file The file being read from
     * @throws IOException          any IOException that happens ...
     * @throws InvalidLoadException any invalid loading that happens ...
     */
    @Override
    public void performLoad(File file) throws IOException, InvalidLoadException {
        throw new UnsupportedOperationException("Wait for Phase 2 Load game");
    }
}