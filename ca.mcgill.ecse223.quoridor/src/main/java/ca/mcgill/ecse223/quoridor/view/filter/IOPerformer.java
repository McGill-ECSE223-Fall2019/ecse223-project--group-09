package ca.mcgill.ecse223.quoridor.view.filter;

import java.io.File;
import java.io.IOException;

import ca.mcgill.ecse223.quoridor.controller.InvalidLoadException;

/**
 * Hack around needing to do instanceof when checking which method to load the
 * file with
 *
 * @author Paul Teng (260862906)
 */
public interface IOPerformer {

    /**
     * Tries to save whatever it is to a file. Should overwrite if file already
     * exists.
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
     * @throws IOException          any IOException that happens ...
     * @throws InvalidLoadException any invalid loading that happens ...
     */
    public abstract void performLoad(File file) throws IOException, InvalidLoadException;
}