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

    private static final Color BOARD_COLOR = Color.lightGray;

    // ***** Rendering State Variables *****
    private JPanel whitePawnTile;
    private JPanel blackPawnTile;

    // ***** Additional UI Components *****
    private final JPanel[][] pawnCells = new JPanel[ROWS][COLS];


    public BoardView() {
        // Use the GridBagLayout
        this.setLayout(new GridBagLayout());

        this.initializeRowLabels();
        this.initializeColumnLabels();

        this.initializePawnCells();
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
                cell.setBackground(BOARD_COLOR);

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
            this.whitePawnTile.setBackground(BOARD_COLOR);
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
            this.blackPawnTile.setBackground(BOARD_COLOR);
        }
        
        // Get the new position
        this.blackPawnTile = this.pawnCells[row - 1][col - 1];
        
        // Paint it to black
        if (this.blackPawnTile != null) {
            this.blackPawnTile.setBackground(Color.black);
        }
    }
    /**
     * Resets the position of all pawns as if none ever existed on the board
     * 
     * @author Group 9
     */
    public void resetPawnPositions() {
        if (this.whitePawnTile != null) {
            this.whitePawnTile.setBackground(BOARD_COLOR);
            this.whitePawnTile = null;
        }

        if (this.blackPawnTile != null) {
            this.blackPawnTile.setBackground(BOARD_COLOR);
            this.blackPawnTile = null;
        }
    }

    }
