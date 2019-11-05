package ca.mcgill.ecse223.quoridor.controller;

public class TOWallCandidate {
	
	/**
	 * This is the Wall Candidate class.  
	 * 
	 * 
	 * @author Alixe Delabrousse & Mohamed Mohamed 
	 * 
	 * 
	 */

    private Orientation orientation;
    private int row;
    private int column;
    private boolean validity;
    private TOWall associatedWall;
    
    public TOWallCandidate(Orientation orientation, int row, int column) {
    	
    	this.orientation = orientation;
    	this.row = row;
    	this.column = column;
    	this.validity=true;
    	 
    	this.associatedWall= new TOWall();
    	this.associatedWall.setOrientation(orientation);
    	this.associatedWall.setRow(row);
    	this.associatedWall.setColumn(column);
    	this.associatedWall.setValidity(true);
    	
    	
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
    
    
    public void rotate() {
    	if(this.orientation.equals(Orientation.HORIZONTAL)) {
    		
    		this.setOrientation(Orientation.VERTICAL);
    		associatedWall.setOrientation(Orientation.VERTICAL);
    	}else { // if the orientation is vertcial
    		
    		this.setOrientation(Orientation.HORIZONTAL);
    		this.associatedWall.setOrientation(Orientation.HORIZONTAL);
    	}
    }
    
    /*
     * @param isValid Indicates if the wall is valid or not.
     */
    public void setValidity(boolean isValid) {
    	this.validity=isValid;
    	this.associatedWall.setValidity(isValid);
    	
    }
    
    public boolean getValidity() {
    	return validity;
    }
    
    public TOWall getAssociatedWall(){ //this should be heavily used
    	return this.associatedWall;
    	
    }
    
	public void resetWall() {
	    	this.orientation=null;
	    	this.row=0;
	    	this.column=0;
	}

}
