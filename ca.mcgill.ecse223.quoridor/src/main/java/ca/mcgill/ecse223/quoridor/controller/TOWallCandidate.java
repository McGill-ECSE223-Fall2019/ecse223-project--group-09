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
    
    public TOWallCandidate(Orientation orientation, int row, int column) {
    	
    	this.orientation = orientation;
    	this.row = row;
    	this.column = column;
    	
    	this.associatedWall= new TOWall();
    	
    	this.associatedWall.setOrientation(orientation);
    	this.associatedWall.setRow(row);
    	this.associatedWall.setColumn(column);
    	
    	
    }
    
    public int getRow() {
    	return this.row;
    }
    
    public void setRow(int row) {
    	this.row = row;
    	this.associatedWall.setRow(row);
    }
    
    public int getColumn() {
    	return this.column;
    }
    
    public void setColumn(int column) {
    	this.column = column;
    	this.associatedWall.setColumn(column);
    }
    public Orientation getOrientation() {
    	return this.orientation;
    }
    
    public void setOrientation(Orientation orientation) {
    	this.orientation = orientation;
    	this.associatedWall.setOrientation(orientation);
    }
    
    /**
     * @author Mohamed Mohamed
     */
    public void rotate() {
    	if(this.orientation==Orientation.HORIZONTAL) {
    		
    		this.setOrientation(Orientation.VERTICAL);
    		associatedWall.setOrientation(Orientation.VERTICAL);
    	}else { // if the orientation is vertcial
    		
    		this.setOrientation(Orientation.HORIZONTAL);
    		this.associatedWall.setOrientation(Orientation.HORIZONTAL);
    	}
    }
    
    public TOWall getAssociatedWall(){ //this should be heavily used
    	return this.associatedWall;
    	
    }

	
    
}
