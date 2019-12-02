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
    public File normalizeExtension(final File file) {
        if (this.accept(file)) {
            return file;
        }
        return new File(file.getAbsolutePath() + EXTENSION);
    }

    @Override
    public String getDescription() {
        return "Game (" + EXTENSION + ")";
    }

    /**
     * This saves the game. 
     *
     * @param file The file being written to
     * @throws IOException any IOException that happens...
     * 
     * @Ada Andrei (260866279)
     */
    @Override
    public void performSave(File file) throws IOException {
        // If file does not exist, overwrite flag is ignored,
        // If file does exist, reaching here means we want overwriting
        // (hence true for overwrite-flag parameter)
        QuoridorController.saveGame(file.getAbsolutePath(), true);
    }

    /**
     * Loads the game from the files
     *
     * @param file The file being read from
     * @throws IOException          any IOException that happens ...
     * @throws InvalidLoadException any invalid loading that happens ...
     * 
     * @author Group-9
     */
    @Override
    public void performLoad(File file) throws IOException, InvalidLoadException {
        QuoridorController.loadGame(file.getAbsolutePath());
    }
}