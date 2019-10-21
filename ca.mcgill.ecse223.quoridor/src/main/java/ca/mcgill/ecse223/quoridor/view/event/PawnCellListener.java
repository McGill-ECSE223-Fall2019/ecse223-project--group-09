package ca.mcgill.ecse223.quoridor.view.event;

/**
 * A Listener for pawn cell related events
 *
 * @author Group 9
 */
public interface PawnCellListener {

    /**
     * Called when an empty pawn cell is clicked
     *
     * @param source Object where the event occurred
     * @param row Row in pawn coordinates
     * @param col Column in pawn coordinates
     *
     * @author Group 9
     */
    public void emptyPawnCellClicked(Object source, int row, int col);

    /**
     * Called when the white pawn cell is clicked
     *
     * @param source Object where the event occurred
     * @param row Row in pawn coordinates
     * @param col Column in pawn coordinates
     *
     * @author Group 9
     */
    public void whitePawnCellClicked(Object source, int row, int col);

    /**
     * Called when the black pawn cell is clicked
     *
     * @param source Object where the event occurred
     * @param row Row in pawn coordinates
     * @param col Column in pawn coordinates
     *
     * @author Group 9
     */
    public void blackPawnCellClicked(Object source, int row, int col);
}
