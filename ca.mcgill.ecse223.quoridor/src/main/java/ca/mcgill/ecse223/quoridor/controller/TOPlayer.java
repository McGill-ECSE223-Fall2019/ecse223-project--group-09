package ca.mcgill.ecse223.quoridor.controller;

import java.sql.Time;

/**
 * Transfer object for player information:
 * To ensure only controller interacts with data from model
 *
 * @author Paul Teng (260862906)
 */
public final class TOPlayer {

    private String name;
    private int row;
    private int column;
    private Time timeRemaining;
    private Color color;
    private boolean wallInHand = false;
    private int wallsRemaining;
    private TOWallCandidate currentWallCandidate;
    
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
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

    public void setTimeRemaining(Time time) {
        this.timeRemaining = time;
    }

    public void setWallsRemaining(int remaining) {
        this.wallsRemaining = remaining;
    }

    public int getColumn() {
        return this.column;
    }
    public Time getTimeRemaining() {
    	return this.timeRemaining;
    }
    public void setColor(Color color) {
    	this.color = color;
    }
    public Color getColor() {
    	return this.color;
    }
    public void setWallInHand(boolean hasWall) {
    	this.wallInHand = hasWall;//set if a player has a wall in hand or no
    }
    
    public boolean hasWallInHand() {
    	return this.wallInHand;
    }
    
    public int getWallsRemaining() {
    	return this.wallsRemaining;
    }
    public TOWallCandidate getWallCandidate() {
    	return this.currentWallCandidate;
    }
    
}