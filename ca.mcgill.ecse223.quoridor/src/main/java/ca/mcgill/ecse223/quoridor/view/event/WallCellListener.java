package ca.mcgill.ecse223.quoridor.view.event;

/**
 * A Listener for wall cell related events
 * 
 * @author Group 9
 */
public interface WallCellListener {
    
    /**
     * Called when wall cell is clicked
     *
     * @param row Row in wall coordinates
     * @param col Column in wall coordinates
     * 
     * @author Group 9
     */
    public void wallCellClicked(int row, int col);
}
