package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mcgill.ecse223.quoridor.controller.Orientation;

/**
 * A specialized JPanel that displays a grid (9-by-9) for a game of Quoridor.
 *
 * Includes
 * - 1-to-9 and a-to-i labeling for each row and column.
 * - Attachable click handler
 *
 * @author Group 9
 */
public class BoardView extends JPanel {

    private static final int ROWS = 9;
    private static final int COLS = 9;

    private static final Color PAWN_CELL_COLOR = Color.lightGray;
    private static final Color WALL_CELL_COLOR = Color.cyan;

    // ***** Rendering State Variables *****
    private JPanel whitePawnTile;
    private JPanel blackPawnTile;

    // ***** Additional UI Components *****
    private final JPanel[][] pawnCells = new JPanel[ROWS][COLS];

    private final JPanel[][] horizontalCells = new JPanel[ROWS - 1][COLS];
    private final JPanel[][] verticalCells = new JPanel[ROWS][COLS - 1];
    private final JPanel[][] junctionCells = new JPanel[ROWS - 1][COLS - 1];

    public BoardView() {
        // Use the GridBagLayout
        this.setLayout(new GridBagLayout());

        this.initializeRowLabels();
        this.initializeColumnLabels();

        this.initializePawnCells();
        this.initializeWallCells();
    }

    /**
     * Initializes row labels
     * 
     * @author Group 9
     */
    private void initializeRowLabels() {
        for (int i = 0; i < ROWS; ++i) {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 2 * (ROWS - 1 - i);
            this.add(new JLabel(Integer.toString(i + 1)), c);
        }
    }

    /**
     * Initializes column labels
     * 
     * @author Group 9
     */
    private void initializeColumnLabels() {
        for (int i = 0; i < COLS; ++i) {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 2 * i + 1;
            c.gridy = 2 * ROWS - 1;
            this.add(new JLabel(Character.toString('a' + i)), c);
        }
    }

    /**
     * Initializes cells for the pawn
     * 
     * @author Group 9
     */
    private void initializePawnCells() {
        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLS; ++j) {
                final JPanel cell = new JPanel();
                cell.setBackground(PAWN_CELL_COLOR);

                final GridBagConstraints c = new GridBagConstraints();
                c.gridx = 2 * j + 1;
                c.gridy = 2 * (ROWS - 1 - i);
                c.fill = GridBagConstraints.BOTH;
                this.add(cell, c);

                // List is linearly indexed
                this.pawnCells[i][j] = cell;
            }
        }
    }

    /**
     * Initialize cells for the wall
     * 
     * @author Group 9
     */
    private void initializeWallCells() {
        this.initializeHorizontalCells();
        this.initializeVerticalCells();
        this.initializeJunctionCells();

        this.resetWallPositions();
    }

    /**
     * Initialize the horizontal wall cells
     * 
     * @author Group 9
     */
    private void initializeHorizontalCells() {
        for (int i = 0; i < ROWS - 1; ++i) {
            for (int j = 0; j < COLS; ++j) {
                final JPanel cell = new JPanel();
                
                final GridBagConstraints c = new GridBagConstraints();
                c.gridx = 2 * j + 1;
                c.gridy = 2 * (ROWS - 1 - i) - 1;
                c.fill = GridBagConstraints.BOTH;
                this.add(cell, c);
                
                this.horizontalCells[i][j] = cell;
            }
        }
    }

    /**
     * Initialize the vertical wall cells
     * 
     * @author Group 9
     */
    private void initializeVerticalCells() {
        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLS - 1; ++j) {
                final JPanel cell = new JPanel();

                final GridBagConstraints c = new GridBagConstraints();
                c.gridx = 2 * (j + 1);
                c.gridy = 2 * (ROWS - 1 - i);
                c.fill = GridBagConstraints.BOTH;
                this.add(cell, c);

                this.verticalCells[i][j] = cell;
            }
        }
    }

    /**
     * Initialize the wall cells that are at the junctions
     * 
     * @author Group 9
     */
    private void initializeJunctionCells() {
        for (int i = 0; i < ROWS - 1; ++i) {
            for (int j = 0; j < COLS - 1; ++j) {
                final JPanel cell = new JPanel();

                final GridBagConstraints c = new GridBagConstraints();
                c.gridx = 2 * (j + 1);
                c.gridy = 2 * (ROWS - 1 - i) - 1;
                c.fill = GridBagConstraints.BOTH;
                this.add(cell, c);

                this.junctionCells[i][j] = cell;
            }
        }
    }

    /**
     * Updates the position of the white pawn
     *
     * @param row Row in pawn coordinates
     * @param col Column in pawn coordinates
     *
     * @author Group 9
     */
    public void setWhitePawnPosition(int row, int col) {
        // Unset previous location if exists
        if (this.whitePawnTile != null) {
            this.whitePawnTile.setBackground(PAWN_CELL_COLOR);
        }

        // Get the new position
        this.whitePawnTile = this.pawnCells[row - 1][col - 1];

        // Paint it to white
        if (this.whitePawnTile != null) {
            this.whitePawnTile.setBackground(Color.white);
        }
    }

    /**
     * Updates the position of the black pawn
     *
     * @param row Row in pawn coordinates
     * @param col Column in pawn coordinates
     *
     * @author Group 9
     */
    public void setBlackPawnPosition(int row, int col) {
        // Unset previous location if exists
        if (this.blackPawnTile != null) {
            this.blackPawnTile.setBackground(PAWN_CELL_COLOR);
        }
        
        // Get the new position
        this.blackPawnTile = this.pawnCells[row - 1][col - 1];
        
        // Paint it to black
        if (this.blackPawnTile != null) {
            this.blackPawnTile.setBackground(Color.black);
        }
    }

    /**
     * Adds a wall of the white player on to the board
     * 
     * @param row Row in wall coordinates
     * @param col Column in wall coordinates
     * @param orientation Orientation of the wall
     * 
     * @author Group 9
     */
    public void addWhiteWall(int row, int col, Orientation orientation) {
        switch (orientation) {
            case VERTICAL: {
                this.verticalCells[row - 1][col - 1].setBackground(Color.white);
                this.junctionCells[row - 1][col - 1].setBackground(Color.white);
                this.verticalCells[row][col - 1].setBackground(Color.white);
                break;
            }
            case HORIZONTAL: {
                this.horizontalCells[row - 1][col - 1].setBackground(Color.white);
                this.junctionCells[row - 1][col - 1].setBackground(Color.white);
                this.horizontalCells[row - 1][col].setBackground(Color.white);
                break;
            }
            default:
                throw new AssertionError("Unhandled orientation: " + orientation);
        }
    }

    /**
     * Adds a wall of the black player on to the board
     * 
     * @param row Row in wall coordinates
     * @param col Column in wall coordinates
     * @param orientation Orientation of the wall
     * 
     * @author Group 9
     */
    public void addBlackWall(int row, int col, Orientation orientation) {
        switch (orientation) {
            case VERTICAL: {
                this.verticalCells[row - 1][col - 1].setBackground(Color.black);
                this.junctionCells[row - 1][col - 1].setBackground(Color.black);
                this.verticalCells[row][col - 1].setBackground(Color.black);
                break;
            }
            case HORIZONTAL: {
                this.horizontalCells[row - 1][col - 1].setBackground(Color.black);
                this.junctionCells[row - 1][col - 1].setBackground(Color.black);
                this.horizontalCells[row - 1][col].setBackground(Color.black);
                break;
            }
            default:
                throw new AssertionError("Unhandled orientation: " + orientation);
        }
    }

    /**
     * Resets the position of all pawns as if none ever existed on the board
     * 
     * @author Group 9
     */
    public void resetPawnPositions() {
        if (this.whitePawnTile != null) {
            this.whitePawnTile.setBackground(PAWN_CELL_COLOR);
            this.whitePawnTile = null;
        }

        if (this.blackPawnTile != null) {
            this.blackPawnTile.setBackground(PAWN_CELL_COLOR);
            this.blackPawnTile = null;
        }
    }

    /**
     * Resets the position of all walls as if none ever existed on the board
     * 
     * @author Group 9
     */
    public void resetWallPositions() {
        resetWallHelper(this.horizontalCells);
        resetWallHelper(this.verticalCells);
        resetWallHelper(this.junctionCells);
    }

    /**
     * Sets the JPanel's color to the board's color
     * 
     * @param array a 2D JPanel array whose colors being set
     */
    private static void resetWallHelper(final JPanel[][] array) {
        for (int i = 0; i < array.length; ++i) {
            final JPanel[] linear = array[i];
            for (int j = 0; j < linear.length; ++j) {
                linear[j].setBackground(WALL_CELL_COLOR);
            }
        }
    }

    }
