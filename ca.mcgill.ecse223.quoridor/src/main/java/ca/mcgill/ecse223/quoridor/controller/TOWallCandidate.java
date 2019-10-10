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
    
    TOWallCandidate(Orientation orientation, int row, int column) {
    	
    	this.orientation = orientation;
    	this.row = row;
    	this.column = column;
    }
    
}
