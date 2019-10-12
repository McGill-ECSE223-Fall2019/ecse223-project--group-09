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
    private String color;
    private boolean wallInHand = false;
    private int wallsRemaining;
    
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

    public int getColumn() {
        return this.column;
    }
    public Time getTimeRemaining() {
    	return this.timeRemaining;
    }
    public void setColor(String color) {
    	this.color = color;
    }
    public String getColor() {
    	return this.color;
    }
    public void addWallInHand() {
    	this.wallInHand = true;
    }
    
    public boolean hasWallInHand() {
    	return this.wallInHand;
    }
    
    public int getWallsRemaining() {
    	return this.wallsRemaining;
    }
    
}