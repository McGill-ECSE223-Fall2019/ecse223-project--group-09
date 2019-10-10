
package ca.mcgill.ecse223.quoridor.controller;

/**
 * Transfer object for wall information:
 * To ensure only controller interacts with data from model
 *
 * @author Paul Teng (260862906)
 */

public final class TOWall {
	
	public enum Orientation{HORIZONTAL, VERTICAL};

    private Orientation orientation;
    private int row;
    private int column;
    public boolean grabbed;
    public TOWallCandidate wallCandidate = null;

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

    
    public void SetGrabbed(boolean grabbed) {
    	this.grabbed = grabbed;
    }
    
    public boolean isGrabbed() {
    	if (this.grabbed) return true;
    	else return false;
    }
    
    public void createWallCandidate() {
    	this.wallCandidate = new TOWallCandidate(this.orientation,this.row, this.column);
    }
    
    public TOWallCandidate getWallCandidate() {
    	if (wallCandidate!=null) return this.wallCandidate;
    	else return null;
    }
}