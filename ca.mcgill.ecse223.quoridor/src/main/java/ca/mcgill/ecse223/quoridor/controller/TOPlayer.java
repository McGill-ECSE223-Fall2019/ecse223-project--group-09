package ca.mcgill.ecse223.quoridor.controller;

import java.sql.Time;

/**
 * Transfer object for player information: To ensure only controller interacts
 * with data from model
 *
 * @author Paul Teng (260862906)
 */
public final class TOPlayer {

    private String username;
    private int row;
    private int column;
    private Time timeRemaining;
    private Color color;
    private boolean wallInHand = false;
    private int wallsRemaining;
<<<<<<< HEAD
    
    public TOPlayer(String aUsername, int aSize) {
        //username = aUsername;
        //size = aSize;
	}
=======
    private TOWallCandidate currentWallCandidate;

    public void setName(String name) {
        this.name = name;
    }
>>>>>>> 51fca5e36896efef411ca25874be1c5626c23c50

	public void setUsername(String username) {
        this.username = username;
    }
  
    public String getUsername() {
        return this.username;
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
        this.wallInHand = hasWall;// set if a player has a wall in hand or no
    }

    public boolean hasWallInHand() {
        return this.wallInHand;
    }

    public int getWallsRemaining() {
        return this.wallsRemaining;
    }

    public void setWallCandidate(TOWallCandidate wallCandidate) {
        this.currentWallCandidate = wallCandidate;
    }

    public TOWallCandidate getWallCandidate() {
        return this.currentWallCandidate;
    }

    /**
     * Check if player can grab a wall. Player needs to *not* be grabbing a wall and
     * have enough walls remaining.
     *
     * @return if player can grab a wall
     *
     * @author Paul Teng (260862906)
     */
    public boolean canGrabWall() {
        return !this.hasWallInHand() && this.getWallsRemaining() > 0;
    }
}