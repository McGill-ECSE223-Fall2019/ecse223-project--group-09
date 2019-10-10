package ca.mcgill.ecse223.quoridor.controller;

import ca.mcgill.ecse223.quoridor.controller.Orientation;

public class TOWallCandidate {

    private Orientation orientation;
    private int row;
    private int column;
    
    TOWallCandidate(Orientation orientation, int row, int column) {
    	
    	this.orientation = orientation;
    	this.row = row;
    	this.column = column;
    }
    
}
