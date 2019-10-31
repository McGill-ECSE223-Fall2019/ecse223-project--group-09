package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ca.mcgill.ecse223.quoridor.controller.Orientation;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.controller.TOPlayer;
import ca.mcgill.ecse223.quoridor.controller.TOWall;
import ca.mcgill.ecse223.quoridor.controller.TOWallCandidate;

/**
 * This is only the tile part
 *
 * @author Paul Teng (260862906)
 */
/* package */ class TileMapPanel extends JPanel {

    public static final int SIDE = 9;

    private static final int DIV = 10;

    private static final Color PAWN_CELL_COLOR = Color.lightGray;
    private static final Color WALL_CELL_COLOR = Color.cyan;
    private static final Color CANDIDATE_COLOR = Color.green;

    /**
     * A function interface for tile related methods
     *
     * @author Paul Teng (260862906)
     */
    private static interface TileDispatcher {

        /**
         * Dispatches a tile position
         *
         * @param row Row in pawn coordinates
         * @param col Column in pawn coordinates
         *
         * @author Paul Teng (260862906)
         */
        public void dispatchTile(int row, int col);
    }

    /**
     * A function interface for wall related methods
     *
     * @author Paul Teng (260862906)
     */
    private static interface SlotDispatcher {

        /**
         * Dispatches a slot position
         *
         * @param row         Row in wall coordinates
         * @param col         Column in wall coordinates
         * @param orientation Orientation
         *
         * @author Paul Teng (260862906)
         */
        public void dispatchSlot(int row, int col, Orientation orientation);
    }

    public TOPlayer whitePlayer;
    public TOPlayer blackPlayer;

    public List<TOWall> whiteWalls = Collections.emptyList();
    public List<TOWall> blackWalls = Collections.emptyList();

    public TOWallCandidate wallCandidate;

    public Orientation junctionOrientation = Orientation.HORIZONTAL;
    
    
    public TileMapPanel() {
        final MouseHandler handler = new MouseHandler();
        this.addMouseListener(handler);
        this.addMouseWheelListener(handler);
        this.addMouseMotionListener(handler);
    }

    /**
     * {@inheritDoc}
     *
     * We draw here
     *
     * @author Paul Teng (260862906)
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        final Dimension d = this.getSize();

        g.setColor(PAWN_CELL_COLOR);
        g.fillRect(0, 0, d.width, d.height);

        this.drawDividers(g);

        if (this.shouldDrawWhitePlayer()) {
            this.drawPawn(g, this.whitePlayer.getRow(), this.whitePlayer.getColumn(), Color.white);
        }

        if (this.shouldDrawBlackPlayer()) {
            this.drawPawn(g, this.blackPlayer.getRow(), this.blackPlayer.getColumn(), Color.white);
        }

        for (final TOWall wall : this.whiteWalls) {
            this.drawWall(g, wall, Color.white);
        }

        for (final TOWall wall : this.blackWalls) {
            this.drawWall(g, wall, Color.black);
        }

        if (this.shouldDrawWallCandidate()) {
            this.drawWall(g, this.wallCandidate.getRow(), this.wallCandidate.getColumn(),
                    this.wallCandidate.getOrientation(), CANDIDATE_COLOR);
        }
    }

    /**
     * Check if we should draw the white player
     *
     * @return true if we should, false otherwise
     *
     * @author Paul Teng (260862906)
     */
    private boolean shouldDrawWhitePlayer() {
        return this.whitePlayer != null && this.whitePlayer.getRow() >= 1 && this.whitePlayer.getRow() <= 9
                && this.whitePlayer.getColumn() >= 1 && this.whitePlayer.getColumn() <= 9;
    }

    /**
     * Check if we should draw the black player
     *
     * @return true if we should, false otherwise
     *
     * @author Paul Teng (260862906)
     */
    private boolean shouldDrawBlackPlayer() {
        return this.whitePlayer != null && this.blackPlayer.getRow() >= 1 && this.blackPlayer.getRow() <= 9
                && this.blackPlayer.getColumn() >= 1 && this.blackPlayer.getColumn() <= 9;
    }

    /**
     * Check if we should draw the wall candidate
     * 
     * @return true if we should, false otherwise
     * 
     * @author Paul Teng (260862906)
     */
    private boolean shouldDrawWallCandidate() {
        return this.wallCandidate != null;
    }

    /**
     * Draws the pawn at a specific spot
     *
     * @param g     Graphics object
     * @param row   Row in pawn coordinates
     * @param col   Column in pawn coordinates
     * @param color Color
     *
     * @author Paul Teng (260862906)
     */
    private void drawPawn(Graphics g, int row, int col, Color color) {
        g.setColor(color);

        final Dimension d = this.getSize();
        final int tileX = d.width / SIDE;
        final int tileY = d.height / SIDE;

        final int padX = 2 * tileX / DIV;
        final int padY = 2 * tileY / DIV;

        final int baseX = tileX * (col - 1);
        final int baseY = tileY * (SIDE - row);
        g.fillOval(baseX + padX, baseY + padY, tileX - 2 * padX, tileY - 2 * padY);
    }

    /**
     * Draws dividers, which are like slots for walls
     *
     * @param g Graphics object
     *
     * @author Paul Teng (260862906)
     */
    private void drawDividers(Graphics g) {
        g.setColor(WALL_CELL_COLOR);

        final Dimension d = this.getSize();
        final int tileX = d.width / SIDE;
        final int tileY = d.height / SIDE;

        final int padX = tileX / DIV;
        final int padY = tileY / DIV;

        // Only 8 wall slots
        for (int i = 1; i < SIDE; ++i) {
            final int baseX = tileX * i;
            final int baseY = tileY * i;

            g.fillRect(baseX - padX, 0, 2 * padX, d.height);
            g.fillRect(0, baseY - padY, d.width, 2 * padY);
        }
    }

    /**
     * Draws the wall at a specific spot
     *
     * @param g     Graphics object
     * @param wall  A wall
     * @param color Color
     *
     * @author Paul Teng (260862906)
     */
    private void drawWall(Graphics g, TOWall wall, Color color) {
        final int row = wall.getRow();
        final int col = wall.getColumn();
        final Orientation orientation = wall.getOrientation();

        this.drawWall(g, row, col, orientation, color);
    }

    /**
     * Draws the wall at a specific spot
     * 
     * @param g           Graphics object
     * @param row         Row in wall coordinates
     * @param col         Column in wall coordaintes
     * @param orientation Orientation of wall
     * @param color       Color
     * 
     * @author Paul Teng (260862906)
     */
    private void drawWall(Graphics g, int row, int col, Orientation orientation, Color color) {
        if (orientation == null) {
            // We are done
            return;
        }

        g.setColor(color);

        final Dimension d = this.getSize();
        final int tileX = d.width / SIDE;
        final int tileY = d.height / SIDE;

        final int padX = tileX / DIV;
        final int padY = tileY / DIV;

        switch (orientation) {
        case VERTICAL: {
            final int baseX = tileX * col;
            final int baseY = tileY * (SIDE - row - 1);
            g.fillRect(baseX - padX, baseY + padY, 2 * padX, 2 * (tileY - padY));
            break;
        }
        case HORIZONTAL: {
            final int baseX = tileX * (col - 1);
            final int baseY = tileY * (SIDE - row);
            g.fillRect(baseX + padX, baseY - padY, 2 * (tileX - padX), 2 * padY);
            break;
        }
        }
    }

    /**
     * This is called when the mouse wheel rotates
     *
     * @param clicks The amount the wheel rotated
     */
    private void onMouseWheelRotate(double clicks) {
        // TODO

    }

    /**
     * This is called when any pawn tile is clicked on
     *
     * @param row Row in pawn coordinates
     * @param col Column in pawn coordinates
     */
    private void onTileClicked(int row, int col) {
        // TODO
        System.out.println("Clicked on tile: " + Character.toString((char) (col - 1 + 'a')) + row);
    }

    /**
     * This is called when any wall slot is clicked on
     *
     * @param row         Row in wall coordinates
     * @param col         Column in wall coordinates
     * @param orientation Orientation
     * 
     * @author alixe
     */
    private void onSlotClicked(int row, int col, Orientation orientation) {
        // TODO
    	try {
    		this.wallCandidate.setColumn(col);
    		this.wallCandidate.setRow(row);
    		this.wallCandidate.setOrientation(orientation);
    		
    		String position = Character.toString((char) (col - 1 + 'a')) + row + (orientation == Orientation.VERTICAL ? "v" : "h");
    		
    		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Do you want to place your wall here?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
    			QuoridorController.moveWall(position);
    			JOptionPane.showMessageDialog(this, "Select 'Drop Wall' to confirm move");
    		}
    		
    	} catch (NullPointerException e) {
    		System.out.println("No wall grabbed");
    	}
    	
        System.out.println(
                Character.toString((char) (col - 1 + 'a')) + row + (orientation == Orientation.VERTICAL ? "v" : "h"));
    }

    /**
     * This is called when mouse enters any pawn tile
     *
     * @param row Row in pawn coordinates
     * @param col Column in pawn coordinates
     */
    private void onTileEntered(int row, int col) {
        // TODO
        System.out.println("Entered tile: " + Character.toString((char) (col - 1 + 'a')) + row);
    }

    /**
     * This is called when mouse leaves any pawn tile
     *
     * @param row Row in pawn coordinates
     * @param col Column in pawn coordinates
     */
    private void onTileExited(int row, int col) {
        // TODO
        System.out.println("Exited tile: " + Character.toString((char) (col - 1 + 'a')) + row);
    }

    /**
     * This is called when mouse enters any wall slot
     *
     * @param row         Row in wall coordinates
     * @param col         Column in wall coordinates
     * @param orientation Orientation
     * @author alixe
     * 
     */
    private void onSlotEntered(int row, int col, Orientation orientation) {
        //TODO
    	
    	try {
    		this.wallCandidate.setRow(row);
    		this.wallCandidate.setColumn(col);
    		this.wallCandidate.setOrientation(orientation);
    		this.repaint();
    	} catch (NullPointerException e) {
    		 System.out.println("No wall grabbed");
    	}
    		
    	System.out.println("Entered: " + Character.toString((char) (col - 1 + 'a')) + row
                + (orientation == Orientation.VERTICAL ? "v" : "h"));
    }

    /**
     * This is called when mouse leaves any wall slot
     *
     * @param row         Row in wall coordinates
     * @param col         Column in wall coordinates
     * @param orientation Orientation
     */
    private void onSlotExited(int row, int col, Orientation orientation) {
        // TODO
        System.out.println("Exited: " + Character.toString((char) (col - 1 + 'a')) + row
                + (orientation == Orientation.VERTICAL ? "v" : "h"));
    }

    /**
     * A class that handles mouse related stuff for the Quoridor grid
     *
     * @author Paul Teng (260862906)
     */
    private final class MouseHandler extends MouseAdapter {

        // ***** State to make magic in tile/slot enter/exit methods *****
        private int lastX = SIDE + 1;
        private int lastY = SIDE + 1;
        private Orientation lastOrientation = null;

        /**
         * {@inheritDoc}
         *
         * Note: handling of this event should be done in
         * {@link TileMapPanel#onMouseWheelRotate(double) onMouseWheelRotate} instead
         *
         * @author Paul Teng (260862906)
         */
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            TileMapPanel.this.onMouseWheelRotate(e.getPreciseWheelRotation());
        }

        /**
         * {@inheritDoc}
         *
         * Note: handling of this event should be done in either
         * {@link TileMapPanel#onTileClicked(int, int) onTileClicked} or
         * {@link TileMapPanel#onSlotClicked(int, int, Orientation) onSlotClicked}
         * instead
         *
         * @author Paul Teng (260862906)
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            defaultMouseEventHandler(e, TileMapPanel.this::onTileClicked, TileMapPanel.this::onSlotClicked);
        }

        /**
         * {@inheritDoc}
         *
         * Note: handling of this event should be done in either
         * {@link TileMapPanel#onTileEntered(int, int) onTileEntered},
         * {@link TileMapPanel#onTileExited(int, int) onTileExited},
         * {@link TileMapPanel#onSlotEntered(int, int, Orientation) onSlotEntered}, or
         * {@link TileMapPanel#onSlotExited(int, int, Orientation) onSlotExited} instead
         *
         * @author Paul Teng (260862906)
         */
        @Override
        public void mouseMoved(MouseEvent e) {
            this.defaultMouseEventHandler(e, this::dispatchEnterTile, this::dispatchEnterSlot);
        }

        /**
         * {@inheritDoc}
         *
         * It forces tiles or slots to be exited when the mouse leaves the panel
         *
         * Note: handling of this event should be done in either
         * {@link TileMapPanel#onTileExited(int, int) onTileExited} or
         * {@link TileMapPanel#onSlotExited(int, int, Orientation) onSlotExited} instead
         *
         * @author Paul Teng (260862906)
         */
        @Override
        public void mouseExited(MouseEvent e) {
            this.tryDispatchExitCall();
        }

        /**
         * Based on the mouse event, dispatches either a tile-related method or a
         * slot-related method
         *
         * @param e          The mouse event
         * @param tileMethod The tile-related method
         * @param slotMethod The slot-related method
         *
         * @author Paul Teng (260862906)
         */
        private void defaultMouseEventHandler(MouseEvent e, TileDispatcher tileMethod, SlotDispatcher slotMethod) {
            final Dimension d = TileMapPanel.this.getSize();
            final int tileW = d.width / SIDE;
            final int tileH = d.height / SIDE;

            final int padW = tileW / DIV;
            final int padH = tileH / DIV;

            final int mouseX = e.getX();
            final int mouseY = e.getY();

            // Find the correct spot
            final int col = Math.min(SIDE, mouseX / tileW + 1);
            final int row = Math.min(SIDE, SIDE - mouseY / tileH);

            // Differentiate between tile and slots
            final int baseX = tileW * (col - 1);
            final int baseY = tileH * (SIDE - row);

            final boolean flagLeftX = col > 1 && mouseX < baseX + padW;
            final boolean flagRightX = col < 9 && mouseX > baseX + tileW - padW;
            final boolean flagTopY = row < 9 && mouseY < baseY + padH;
            final boolean flagBottomY = row > 1 && mouseY > baseY + tileH - padH;

            // Try to figure out if slot junctions
            // should be vertical or horizontal
            switch (junctionOrientation) {
            case VERTICAL:
                if (flagLeftX) {
                    slotMethod.dispatchSlot(Math.min(SIDE - 1, row), col - 1, Orientation.VERTICAL);
                    return;
                }
                if (flagRightX) {
                    slotMethod.dispatchSlot(Math.min(SIDE - 1, row), col, Orientation.VERTICAL);
                    return;
                }
                if (flagTopY) {
                    slotMethod.dispatchSlot(row, Math.min(SIDE - 1, col), Orientation.HORIZONTAL);
                    return;
                }
                if (flagBottomY) {
                    slotMethod.dispatchSlot(row - 1, Math.min(SIDE - 1, col), Orientation.HORIZONTAL);
                    return;
                }
                break;
            case HORIZONTAL:
                if (flagTopY) {
                    slotMethod.dispatchSlot(row, Math.min(SIDE - 1, col), Orientation.HORIZONTAL);
                    return;
                }
                if (flagBottomY) {
                    slotMethod.dispatchSlot(row - 1, Math.min(SIDE - 1, col), Orientation.HORIZONTAL);
                    return;
                }
                if (flagLeftX) {
                    slotMethod.dispatchSlot(Math.min(SIDE - 1, row), col - 1, Orientation.VERTICAL);
                    return;
                }
                if (flagRightX) {
                    slotMethod.dispatchSlot(Math.min(SIDE - 1, row), col, Orientation.VERTICAL);
                    return;
                }
                break;
            }

            tileMethod.dispatchTile(row, col);
        }

        /**
         * Triggers the exit events if necessary:
         * {@link TileMapPanel#onTileExited(int, int) onTileExit} or
         * {@link TileMapPanel#onSlotExited(int, int, Orientation) onSlotExited}.
         *
         * Note: This call resets the internal state; when called multiple times in
         * sequence, the exit calls can only be dispatched at most once.
         *
         * @author Paul Teng (260862906)
         */
        private void tryDispatchExitCall() {
            // Exit the last tile or slot
            if (this.lastOrientation != null) {
                TileMapPanel.this.onSlotExited(this.lastX, this.lastY, this.lastOrientation);
            } else if (this.lastX < SIDE + 1) {
                TileMapPanel.this.onTileExited(this.lastX, this.lastY);
            }

            // Then invalid the saved state
            this.lastX = SIDE + 1;
            this.lastY = SIDE + 1;
            this.lastOrientation = null;
        }

        /**
         * Triggers the {@link TileMapPanel#onSlotEntered(int, int, Orientation)
         * onSlotEntered} event and the exit events if necessary
         *
         * @param row         Row in wall coordinates
         * @param col         Column in wall coordinates
         * @param orientation Orientation
         *
         * @author Paul Teng (260862906)
         */
        private void dispatchEnterSlot(int row, int col, Orientation orientation) {
            // If same as previous event, then we never exited, do not generate event
            if (this.lastX == row && this.lastY == col && this.lastOrientation == orientation) {
                return;
            }

            // Dispatch and invalidate saved state
            this.tryDispatchExitCall();

            // Save new state and dispatch
            TileMapPanel.this.onSlotEntered((this.lastX = row), (this.lastY = col),
                    (this.lastOrientation = orientation));
        }

        /**
         * Triggers the {@link TileMapPanel#onTileEntered(int, int) onTileEntered} event
         * and the exit events if necessary
         *
         * @param row Row in pawn coordinates
         * @param col Column in pawn coordinates
         *
         * @author Paul Teng (260862906)
         */
        private void dispatchEnterTile(int row, int col) {
            // If same as previous event, then we never exited, do not generate event
            if (this.lastX == row && this.lastY == col && this.lastOrientation == null) {
                return;
            }

            // Dispatch and invalidate saved state
            this.tryDispatchExitCall();

            // Save new state and dispatch
            TileMapPanel.this.onTileEntered((this.lastX = row), (this.lastY = col));
        }
    }
}
