package ca.mcgill.ecse223.quoridor.view.event;

/**
 * A Listener for pawn cell related events
 *
 * @author Group 9
 */
public interface PawnCellListener {
    
    /**
     * Called when pawn cell is clicked
     *
     * @param row Row in pawn coordinates
     * @param col Column in pawn coordinates
     *
     * @author Group 9
     */
    public void pawnCellClicked(int row, int col);
}
