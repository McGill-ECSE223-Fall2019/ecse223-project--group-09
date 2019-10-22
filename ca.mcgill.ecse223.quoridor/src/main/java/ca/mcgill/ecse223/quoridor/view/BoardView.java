package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import ca.mcgill.ecse223.quoridor.controller.Orientation;
import ca.mcgill.ecse223.quoridor.view.event.PawnCellListener;
import ca.mcgill.ecse223.quoridor.view.event.WallCellListener;

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

    /**
     * A predicate for whether or not a wall slot should be displayed with
     * placement cue
     * 
     * @author Group 9
     */
    public static interface WallPlacementCuePredicate {

        /**
         * 
         * @param row Row in wall coordinates
         * @param col Column in wall coordinates
         * @param orientation Orientation of the wall
         * @return true if this slot should be displayed with a placement cue,
         *         false otherwise
         * 
         * @author Group 9
         */
        public boolean shouldRenderCue(int row, int col, Orientation orientation);
    }

    /**
     * A predicate for whether or not a pawn tile should be displayed with
     * placement cue
     * 
     * @author Group 9
     */
    public static interface PawnPlacementCuePredicate {

        /**
         * 
         * @param row Row in pawn coordinates
         * @param col Column in pawn coordinates
         * @return true if this tile should be displayed with a placement cue,
         *         false otherwise
         * 
         * @author Group 9
         */
        public boolean shouldRenderCue(int row, int col);
    }

    private static final int ROWS = 9;
    private static final int COLS = 9;

    private static final Color PAWN_CELL_COLOR = Color.lightGray;
    private static final Color WALL_CELL_COLOR = Color.cyan;
    private static final Color PLACEMENT_CUE_COLOR = Color.red;

    // By default, nothing are displayed as placement cues
    private static final PawnPlacementCuePredicate DEFAULT_PAWN_CUE_PREDICATE = (row, col) -> false;
    private static final WallPlacementCuePredicate DEFAULT_WALL_CUE_PREDICATE = (row, col, or) -> false;

    // ***** Event-handling Variables *****
    private List<PawnCellListener> pawnCellListeners = new ArrayList<>();
    private List<WallCellListener> wallCellListeners = new ArrayList<>();

    // ***** Rendering State Variables *****
    private JPanel whitePawnTile;
    private JPanel blackPawnTile;

    private boolean pawnTileCue = false;
    private PawnPlacementCuePredicate pawnCuePredicate = DEFAULT_PAWN_CUE_PREDICATE;

    private Orientation wallTileCue = null;
    private WallPlacementCuePredicate wallCuePredicate = DEFAULT_WALL_CUE_PREDICATE;

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
            c.weighty = 1.0;
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
            c.weightx = 1.0;
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

                cell.addMouseListener(new PawnCellEventBridge(this, cell, i, j));
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
     * Creates a mouse listener for walls that will delegate the clicked event
     * to installed WallCellListener
     *
     * @param i Array's i
     * @param j Array's j
     * @return Mouse listener for a particular wall
     *
     * @author Group 9
     */
    private MouseListener createWallMouseListener(int i, int j) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (final WallCellListener lis : wallCellListeners) {
                    lis.wallCellClicked(i + 1, j + 1);
                }
            }
        };
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

                cell.addMouseListener(this.createWallMouseListener(i, j));
                if (j + 1 == COLS) {
                    // If at edge, it should act like the wall of the previous horizontal cell
                    cell.addMouseListener(new WallHintingEventListener(this, i + 1, j));
                } else {
                cell.addMouseListener(new WallHintingEventListener(this, i + 1, j + 1));
                }
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

                cell.addMouseListener(this.createWallMouseListener(i, j));
                if (i + 1 == ROWS) {
                    // If at edge, it should act like the wall of the previous vertical cell
                    cell.addMouseListener(new WallHintingEventListener(this, i, j + 1));
                } else {
                cell.addMouseListener(new WallHintingEventListener(this, i + 1, j + 1));
                }
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

                // Junction cells are awkward since these don't
                // actually exists in the wall coordinate system
                // so this.createWallMouseListener for these

                cell.addMouseListener(new WallHintingEventListener(this, i + 1, j + 1));
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
        this.setWallBackgroundColor(row, col, orientation, Color.white);
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
        this.setWallBackgroundColor(row, col, orientation, Color.black);
    }

    /**
     * Sets the background color of the wall
     *
     * Note: No overlapping check is performed
     *
     * @param row Row in wall coordinates
     * @param col Column in wall coordinates
     * @param orientation Orientation of the wall
     * @param color New background color
     *
     * @author Group 9
     */
    private void setWallBackgroundColor(int row, int col, Orientation orientation, Color color) {
        switch (orientation) {
            case VERTICAL: {
                this.verticalCells[row - 1][col - 1].setBackground(color);
                this.junctionCells[row - 1][col - 1].setBackground(color);
                this.verticalCells[row][col - 1].setBackground(color);
                break;
            }
            case HORIZONTAL: {
                this.horizontalCells[row - 1][col - 1].setBackground(color);
                this.junctionCells[row - 1][col - 1].setBackground(color);
                this.horizontalCells[row - 1][col].setBackground(color);
                break;
            }
            default:
                throw new AssertionError("Unhandled orientation: " + orientation);
        }
    }

    /**
     * Retrieves the background color of the wall
     *
     * Note: Assumes junction cells to not be tampered with
     *
     * @param row Row in wall coordinates
     * @param col Column in wall coordinates
     * @return The background color of that wall, null if out of bounds!
     *
     * @author Group 9
     */
    private Color getWallBackgroundColor(int row, int col) {
        try {
            return this.junctionCells[row - 1][col - 1].getBackground();
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }
    }

    /**
     * Resets the position of the white pawn by removing it from the wall
     *
     * @author Group 9
     */
    public void resetWhitePawnPosition() {
        if (this.whitePawnTile != null) {
            this.whitePawnTile.setBackground(PAWN_CELL_COLOR);
            this.whitePawnTile = null;
        }
    }

    /**
     * Resets the position of the black pawn by removing it from the wall
     *
     * @author Group 9
     */
    public void resetBlackPawnPosition() {
        if (this.blackPawnTile != null) {
            this.blackPawnTile.setBackground(PAWN_CELL_COLOR);
            this.blackPawnTile = null;
        }
    }

    /**
     * Resets the position of all pawns as if none ever existed on the board
     *
     * @author Group 9
     */
    public void resetPawnPositions() {
        this.resetWhitePawnPosition();
        this.resetBlackPawnPosition();
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
     * 
     * @author Group 9
     */
    private static void resetWallHelper(final JPanel[][] array) {
        for (int i = 0; i < array.length; ++i) {
            final JPanel[] linear = array[i];
            for (int j = 0; j < linear.length; ++j) {
                linear[j].setBackground(WALL_CELL_COLOR);
            }
        }
    }

    /**
     * Set whether or not placement cues for pawn tiles are enabled
     * 
     * @param flag true if placement cues should be enabled, false otherwise
     * 
     * @author Group 9
     */
    public void setPawnTilePlacementCueEnabled(boolean flag) {
        this.pawnTileCue = flag;
    }

    /**
     * Sets the predicate for deciding if a certain tile should be rendered
     * with the placement cue
     * 
     * @param predicate The predicate, null will cause all tiles to not be
     *                  rendered with the placement cue
     * 
     * @author Group 9
     */
    public void setPawnPlacementCuePredicate(PawnPlacementCuePredicate predicate) {
        this.pawnCuePredicate = predicate != null ? predicate : DEFAULT_PAWN_CUE_PREDICATE;
    }

    /**
     * Set the orientation of the wall placement cue
     *
     * @param orientation Orientation of the wall hint, null to disable
     *
     * @author Group 9
     */
    public void setWallTilePlacementCueOrientation(Orientation orientation) {
        this.wallTileCue = orientation;
    }

    /**
     * Sets the predicate for deciding if a certain slot should be rendered
     * with the placement cue
     * 
     * @param predicate The predicate, null will cause all slots to not be
     *                  rendered with the placement cue
     * 
     * @author Group 9
     */
    public void setWallPlacementCuePredicate(WallPlacementCuePredicate predicate) {
        this.wallCuePredicate = predicate != null ? predicate : DEFAULT_WALL_CUE_PREDICATE;
    }

    /**
     * Installs another pawn cell listener
     *
     * @param lis Listener, ignored if null
     * 
     * @author Group 9
     */
    public void addPawnCellListener(final PawnCellListener lis) {
        if (lis != null) {
            this.pawnCellListeners.add(lis);
        }
    }

    /**
     * Removes a previously installed pawn cell listener
     *
     * @param lis Listener, ignored if null
     * 
     * @author Group 9
     */
    public void removePawnCellListener(final PawnCellListener lis) {
        if (lis != null) {
            this.pawnCellListeners.remove(lis);
        }
    }

    /**
     * Installs another wall cell listener
     *
     * @param lis Listener, ignored if null
     * 
     * @author Group 9
     */
    public void addWallCellListener(final WallCellListener lis) {
        if (lis != null) {
            this.wallCellListeners.add(lis);
        }
    }

    /**
     * Removes a previously installed wall cell listener
     *
     * @param lis Listener, ignored if null
     * 
     * @author Group 9
     */
    public void removeWallCellListener(final WallCellListener lis) {
        if (lis != null) {
            this.wallCellListeners.remove(lis);
        }
    }


    private static class WallHintingEventListener extends MouseAdapter implements ActionListener {

        private final BoardView board;
        private final int x;
        private final int y;
        private final Timer timer;

        private Orientation lastOrientation = null;

        public WallHintingEventListener(BoardView board, int x, int y) {
            this.board = board;
            this.x = x;
            this.y = y;

            this.timer = new Timer(500, this);
            this.timer.setInitialDelay(0);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // Have timer handle the hover event
            // see actinPerformed(ActionEvent)
            this.timer.start();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            this.timer.stop();

            this.revertCorrectedColor();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Handles the hovering stuff here

            final Orientation orientation = this.board.wallTileCue;
            if (orientation == null) {
                // Not drawing visual cues, done!
                // Might need to reset previously drawn cues
                this.revertCorrectedColor();
                return;
            }

            // Change color to display placement-cue
            // if the tile is vacant
            if (this.lastOrientation == null) {
                if (this.board.wallCuePredicate.shouldRenderCue(x, y, orientation)) {
                    this.board.setWallBackgroundColor(x, y, orientation, BoardView.PLACEMENT_CUE_COLOR);
                    this.lastOrientation = orientation;
                }
            }
        }

        /**
         * Reverts a color change due to placement cues if needed
         *
         * @author Group 9
         */
        private void revertCorrectedColor() {
            // Correct color back if necessary
            final Orientation orientation = this.lastOrientation;
            if (orientation != null) {
                if (this.board.wallCuePredicate.shouldRenderCue(x, y, orientation)) {
                    this.board.setWallBackgroundColor(x, y, orientation, BoardView.WALL_CELL_COLOR);
                    this.lastOrientation = null;
                }
            }
        }
    }

    /**
     * An event listener for pawn cells
     * 
     * @author Group 9
     */
    private static class PawnCellEventBridge extends MouseAdapter implements ActionListener {

        private final BoardView board;
        private final JPanel panel;
        private final int x;
        private final int y;
        private final Timer timer;

        public PawnCellEventBridge(BoardView board, JPanel panel, int i, int j) {
            this.board = board;
            this.panel = panel;
            this.x = i + 1;
            this.y = j + 1;

            this.timer = new Timer(500, this); // called every half a second
            this.timer.setInitialDelay(0);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            final JPanel whiteTile = this.board.whitePawnTile;
            final JPanel blackTile = this.board.blackPawnTile;

            // Do not convert the following loop to a foreach loop:
            // Could get ConcurrentModificationException for that!
            final List<PawnCellListener> list = this.board.pawnCellListeners;
            for (int i = 0; i < list.size(); ++i) {
                final PawnCellListener lis = list.get(i);
                if (this.panel == whiteTile) {
                    lis.whitePawnCellClicked(this.board, x, y);
                } else if (this.panel == blackTile) {
                    lis.blackPawnCellClicked(this.board, x, y);
                } else {
                    lis.emptyPawnCellClicked(this.board, x, y);
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // Have timer handle the hover event
            // see actionPerformed(ActionEvent)
            this.timer.start();
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            this.timer.stop();

            this.revertCorrectedColor();
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            // Handles the hovering stuff here
            
            if (!this.board.pawnTileCue) {
                // Not drawing visual cues, done!
                // Might need to reset previously drawn cues
                this.revertCorrectedColor();
                return;
            }

            if (this.board.pawnCuePredicate.shouldRenderCue(x, y)) {
                    this.panel.setBackground(BoardView.PLACEMENT_CUE_COLOR);
            }
        }

        /**
         * Reverts a color change due to placement cues if needed
         * 
         * @author Group 9
         */
        private void revertCorrectedColor() {
            // Correct color back if necessary
            if (this.board.pawnCuePredicate.shouldRenderCue(x, y)) {
                    this.panel.setBackground(BoardView.PAWN_CELL_COLOR);
            }
        }
    }
}
