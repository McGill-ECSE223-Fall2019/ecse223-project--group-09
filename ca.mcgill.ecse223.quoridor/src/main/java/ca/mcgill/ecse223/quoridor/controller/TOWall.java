
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
    public boolean validity;


    
    
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
    
    public void resetWall() {
    	this.orientation=null;
    	this.row=0;
    	this.column=0;
    	this.grabbed=false;
    }
    
    /**
     * @author Mohamed Mohamed
     * @param isValid indicates if the wall is valid or not.
     */
    public void setValidity(boolean isValid) {
    	this.validity=isValid;
    }
    
    public boolean getValidity() {
    	return validity;
    }
    
    /**
     * @author Mohamed Mohamed
     */
    public void rotate() {
    	if(this.orientation==Orientation.HORIZONTAL) {
    		
    		this.setOrientation(Orientation.VERTICAL);
    	}else { // if the orientation is vertcial
    		
    		this.setOrientation(Orientation.HORIZONTAL);
    	}
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