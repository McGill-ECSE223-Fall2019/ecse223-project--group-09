package ca.mcgill.ecse223.quoridor.controller;

public class TOWallCandidate {
	
	/**
	 * This is the Wall Candidate class. The constructor creates a 
	 * 
	 * 
	 * @author Alixe Delabrousse
	 * 
	 * 
	 */

    private Orientation orientation;
    private int row;
    private int column;
    
    private TOWall associatedWall;
    
    TOWallCandidate(Orientation orientation, int row, int column) {
    	
    	this.orientation = orientation;
    	this.row = row;
    	this.column = column;
    }
    
    public int getRow() {
    	return this.row;
    }
    
    public void setRow(int row) {
    	this.row = row;
    }
    
    public int getColumn() {
    	return this.column;
    }
    
    public void setColumn(int column) {
    	this.column = column;
    }
    public Orientation getOrientation() {
    	return this.orientation;
    }
    
    public void setOrientation(Orientation orientation) {
    	this.orientation = orientation;
    }
    
    public void rotate() {
    	if(this.orientation==orientation.HORIZONTAL) {
    		
    		this.setOrientation(orientation.VERTICAL);
    	}else { // if the orientation is vertcial
    		
    		this.setOrientation(orientation.HORIZONTAL);
    	}
    }
    
    public TOWall getAssociatedWall(){
    	return this.associatedWall;
    	
    }

	
    
}
