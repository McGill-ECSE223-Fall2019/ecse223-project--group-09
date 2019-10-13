
package ca.mcgill.ecse223.quoridor.controller;




/**
 * Transfer object for wall information:
 * To ensure only controller interacts with data from model
 *
 * @author Paul Teng (260862906)
 */

public final class TOWall {

	
	
    private Orientation orientation;
    private int row;
    private int column;
    public boolean grabbed;


    
    
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return this.row;
    }

    public void setColumn(int col) {
        this.column = col;
    }

    public int getColumn() {
        return this.column;
    }

    
    /**
     * @author alixe delabrousse
     * @param grabbed
     */
    
    public void SetGrabbed(boolean grabbed) {
    	this.grabbed = grabbed;
    }
    
    /**
     * @author alixe delabrousse
     */
    
    public boolean isGrabbed() {
    	return this.isGrabbed();
    }
    
}