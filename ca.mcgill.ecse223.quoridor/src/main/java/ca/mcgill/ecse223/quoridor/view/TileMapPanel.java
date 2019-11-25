package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import ca.mcgill.ecse223.quoridor.controller.Orientation;
import ca.mcgill.ecse223.quoridor.controller.TOPlayer;
import ca.mcgill.ecse223.quoridor.controller.TOWall;
import ca.mcgill.ecse223.quoridor.controller.TOWallCandidate;
import ca.mcgill.ecse223.quoridor.view.event.GameBoardListener;

/**
 * This is only the tile part
 *
 * @author Paul Teng (260862906)
 */
public class TileMapPanel extends JPanel {

    public static final int SIDE = 9;

    private static final int DIV = 10;

    private static final Color PAWN_CELL_COLOR = Color.lightGray;
    private static final Color WALL_CELL_COLOR = Color.cyan;
    private static final Color COORD_CUE_COLOR = Color.red;
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

    // ***** Rendering state *****

    private TOPlayer whitePlayer;
    private TOPlayer blackPlayer;

    private List<TOWall> whiteWalls = Collections.emptyList();
    private List<TOWall> blackWalls = Collections.emptyList();

    private TOWallCandidate wallCandidate;

    private Orientation junctionOrientation = Orientation.HORIZONTAL;

    // ***** Event handling state *****

    private final List<GameBoardListener> listeners = new ArrayList<>();

    private boolean blockEvents;

    private int lastRow;
    private int lastCol;
    private Orientation lastOrientation;

    public TileMapPanel() {
        // Invalidate the event state
        this.invalidateEventState();

        // Setup mouse handlers
        final MouseHandler handler = new MouseHandler();
        this.addMouseListener(handler);
        this.addMouseWheelListener(handler);
        this.addMouseMotionListener(handler);
    }

    // ***** Rendering state related methods *****

    /**
     * Set the player with the white pawn, this changes the position being
     * displayed.
     *
     * @param whitePlayer white player
     *
     * @author Paul Teng (260862906)
     */
    public void setWhitePlayer(TOPlayer whitePlayer) {
        this.whitePlayer = whitePlayer;
        this.repaint();
    }

    /**
     * Set the player with the black pawn, this changes the position being
     * displayed.
     *
     * @param blackPlayer black player
     *
     * @author Paul Teng (260862906)
     */
    public void setBlackPlayer(TOPlayer blackPlayer) {
        this.blackPlayer = blackPlayer;
        this.repaint();
    }

    /**
     * Sets the list of walls associated to the white player
     *
     * @param walls walls of the white player
     *
     * @author Paul Teng (260862906)
     */
    public void setWhiteWalls(final List<TOWall> walls) {
        this.whiteWalls = walls != null ? walls : Collections.emptyList();
        this.repaint();
    }

    /**
     * Sets the list of walls associated to the black player
     *
     * @param walls walls of the black player
     *
     * @author Paul Teng (260862906)
     */
    public void setBlackWalls(final List<TOWall> walls) {
        this.blackWalls = walls != null ? walls : Collections.emptyList();
        this.repaint();
    }

    /**
     * Sets a wall candidate
     *
     * @param wallCandidate A wall candidate
     *
     * @author Paul Teng (260862906)
     */
    public void setWallCandidate(final TOWallCandidate wallCandidate) {
        this.wallCandidate = wallCandidate;
        this.repaint();
    }

    /**
     * Returns the current wall candidate
     *
     * @return the current wall candidate, null if none were set
     *
     * @author Paul Teng (260862906)
     */
    public TOWallCandidate getWallCandidate() {
        return this.wallCandidate;
    }

    /**
     * Sets the wall orientation on slot junctions
     *
     * @param junctionOrientation The new orientation for junctions
     *
     * @author Paul Teng (260862906)
     */
    public void setJunctionOrientation(final Orientation junctionOrientation) {
        this.junctionOrientation = junctionOrientation != null ? junctionOrientation : Orientation.HORIZONTAL;
        this.repaint();
    }

    // ***** Event related methods *****

    /**
     * Installs a new game board listener to the current tile map
     *
     * @param lis The listener being installed, null does nothing
     *
     * @author Paul Teng (260862906)
     */
    public void addGameBoardListener(final GameBoardListener lis) {
        if (lis != null) {
            this.listeners.add(lis);
        }
    }

    /**
     * Removes a previously installed game board listener from the current tile map
     *
     * @param lis The listener being removed, null or non-installed does nothing
     *
     * @author Paul Teng (260862906)
     */
    public void removeGameBoardListener(final GameBoardListener lis) {
        if (lis != null) {
            this.listeners.remove(lis);
        }
    }

    /**
     * Invalidates the event state
     *
     * @author Paul Teng (260862906)
     */
    private void invalidateEventState() {
        this.lastRow = SIDE + 1;
        this.lastCol = SIDE + 1;
        this.lastOrientation = null;
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
    public void dispatchExitEvent() {
        // Exit the last tile or slot
        if (this.lastOrientation != null) {
            this.onSlotExited(this.lastRow, this.lastCol, this.lastOrientation);
        } else if (0 < this.lastRow && this.lastRow < SIDE + 1) {
            this.onTileExited(this.lastRow, this.lastCol);
        }

        // Then invalid the saved state
        this.invalidateEventState();
        this.repaint();
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
    public void dispatchEnterSlotEvent(int row, int col, Orientation orientation) {
        // If same as previous event, then we never exited, do not generate event
        if (this.lastRow == row && this.lastCol == col && this.lastOrientation == orientation) {
            return;
        }

        // Dispatch and invalidate saved state
        this.dispatchExitEvent();

        // This will validate the parameters before dispatching
        if (orientation == null || row < 1 || row > SIDE - 1 || col < 1 || col > SIDE - 1) {
            return;
        }

        // Save new state and dispatch
        this.onSlotEntered((this.lastRow = row), (this.lastCol = col), (this.lastOrientation = orientation));
        this.repaint();
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
    public void dispatchEnterTileEvent(final int row, final int col) {
        // If same as previous event, then we never exited, do not generate event
        if (this.lastRow == row && this.lastCol == col && this.lastOrientation == null) {
            return;
        }

        // Dispatch and invalidate saved state
        this.dispatchExitEvent();

        // This will validate the parameters before dispatching
        if (row < 1 || row > SIDE || col < 1 || col > SIDE) {
            return;
        }

        // Save new state and dispatch
        this.onTileEntered((this.lastRow = row), (this.lastCol = col));
        this.repaint();
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

        this.drawEmptyBoard(g);

        // This is a debug-level call
        // Feel free to comment it out
        // this.drawSelectedPlace(g);

        if (this.shouldDrawWhitePlayer()) {
            this.drawPawn(g, this.whitePlayer.getRow(), this.whitePlayer.getColumn(), Color.white, true);
        }

        if (this.shouldDrawBlackPlayer()) {
            this.drawPawn(g, this.blackPlayer.getRow(), this.blackPlayer.getColumn(), Color.black, true);
        }

        for (final TOWall wall : this.whiteWalls) {
            this.drawWall(g, wall, Color.white);
        }

        for (final TOWall wall : this.blackWalls) {
            this.drawWall(g, wall, Color.black);
        }

        if (this.shouldDrawWallCandidate()) {
            this.drawWall(g, this.wallCandidate.getRow(), this.wallCandidate.getColumn(),
                    this.wallCandidate.getOrientation(), CANDIDATE_COLOR, true);
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
     * @param fill  If pawn should be filled or not
     *
     * @author Paul Teng (260862906)
     */
    private void drawPawn(Graphics g, int row, int col, Color color, boolean fill) {
        g.setColor(color);

        final Dimension d = this.getSize();
        final int tileX = d.width / SIDE;
        final int tileY = d.height / SIDE;

        final int padX = 2 * tileX / DIV;
        final int padY = 2 * tileY / DIV;

        final int baseX = tileX * (col - 1);
        final int baseY = tileY * (SIDE - row);
        if (fill) {
            g.fillOval(baseX + padX, baseY + padY, tileX - 2 * padX, tileY - 2 * padY);
        } else {
            g.drawOval(baseX + padX, baseY + padY, tileX - 2 * padX, tileY - 2 * padY);
        }
    }

    /**
     * Draw a board with nothing on it
     *
     * @param g Graphics object
     *
     * @author Paul Teng (260862906)
     */
    private void drawEmptyBoard(Graphics g) {
        final Dimension d = this.getSize();
        final int tileX = d.width / SIDE;
        final int tileY = d.height / SIDE;

        final int padX = tileX / DIV;
        final int padY = tileY / DIV;

        final int limitX = d.width - 2 * padX;
        final int limitY = d.height - 2 * padY;

        // Fill the back board
        g.setColor(PAWN_CELL_COLOR);
        g.fillRect(padX, padY, limitX, limitY);

        // Draw the wall slots
        g.setColor(WALL_CELL_COLOR);

        // Only 8 wall slots
        for (int i = 1; i < SIDE; ++i) {
            final int baseX = tileX * i;
            final int baseY = tileY * i;

            g.fillRect(baseX - padX, padY, 2 * padX, limitY);
            g.fillRect(padX, baseY - padY, limitX, 2 * padY);
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

        this.drawWall(g, row, col, orientation, color, true);
    }

    /**
     * Draws the wall at a specific spot
     *
     * @param g           Graphics object
     * @param row         Row in wall coordinates
     * @param col         Column in wall coordaintes
     * @param orientation Orientation of wall
     * @param color       Color
     * @param fill        If wall should be filled or not
     *
     * @author Paul Teng (260862906)
     */
    private void drawWall(Graphics g, int row, int col, Orientation orientation, Color color, boolean fill) {
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

        final int x, y, width, height;
        switch (orientation) {
        case VERTICAL: {
            final int baseX = tileX * col;
            final int baseY = tileY * (SIDE - row - 1);
            x = baseX - padX;
            y = baseY + padY;
            width = 2 * padX;
            height = 2 * (tileY - padY);
            break;
        }
        case HORIZONTAL: {
            final int baseX = tileX * (col - 1);
            final int baseY = tileY * (SIDE - row);
            x = baseX + padX;
            y = baseY - padY;
            width = 2 * (tileX - padX);
            height = 2 * padY;
            break;
        }
        default:
            return;
        }

        if (fill) {
            g.fillRect(x, y, width, height);
        } else {
            g.drawRect(x, y, width, height);
        }
    }

    /**
     * This is a very debug-level method. It highlights the selected place.
     *
     * @param g Graphics object
     *
     * @author Paul Teng (260862906)
     */
    private void drawSelectedPlace(Graphics g) {
        final int cachedRow = this.lastRow;
        final int cachedCol = this.lastCol;
        final Orientation cachedOrientation = this.lastOrientation;

        if (cachedOrientation == null) {
            // Draw over a tile
            this.drawPawn(g, cachedRow, cachedCol, COORD_CUE_COLOR, false);
        } else {
            // Draw over a slot
            this.drawWall(g, cachedRow, cachedCol, cachedOrientation, COORD_CUE_COLOR, false);
        }
    }

    /**
     * This is called when the mouse wheel rotates.
     *
     * Please handle the event through
     * {@link GameBoardListener#onMouseWheelRotated(double)}.
     *
     * @param clicks The amount the wheel rotated
     *
     * @author Paul Teng (260862906)
     */
    private void onMouseWheelRotate(final double clicks) {
        // Do not change this to enhanced for loop
        for (int i = 0; i < listeners.size(); ++i) {
            listeners.get(i).onMouseWheelRotated(clicks);
        }
    }

    /**
     * This is called when any pawn tile is clicked on.
     *
     * Please handle the event through
     * {@link GameBoardListener#onTileClicked(int, int)}.
     *
     * @param row Row in pawn coordinates
     * @param col Column in pawn coordinates
     *
     * @author Paul Teng (260862906)
     */
    private void onTileClicked(final int row, final int col) {
        // Do not change this to enhanced for loop
        for (int i = 0; i < listeners.size(); ++i) {
            listeners.get(i).onTileClicked(row, col);
        }
    }

    /**
     * This is called when any wall slot is clicked on.
     *
     * Please handle the event through
     * {@link GameBoardListener#onSlotClicked(int, int, Orientation)}.
     *
     * @param row         Row in wall coordinates
     * @param col         Column in wall coordinates
     * @param orientation Orientation
     *
     * @author Paul Teng (260862906)
     */
    private void onSlotClicked(final int row, final int col, final Orientation orientation) {
        // Do not change this to enhanced for loop
    	//drop wall
    	
    	
        for (int i = 0; i < listeners.size(); ++i) {
            listeners.get(i).onSlotClicked(row, col, orientation);
        }
    }

    /**
     * This is called when mouse enters any pawn tile
     *
     * Please handle the event through
     * {@link GameBoardListener#onTileEntered(int, int)}.
     *
     * @param row Row in pawn coordinates
     * @param col Column in pawn coordinates
     *
     * @author Paul Teng (260862906)
     */
    private void onTileEntered(final int row, final int col) {
        // Do not change this to enhanced for loop
        for (int i = 0; i < listeners.size(); ++i) {
            listeners.get(i).onTileEntered(row, col);
        }
    }

    /**
     * This is called when mouse leaves any pawn tile
     *
     * Please handle the event through
     * {@link GameBoardListener#onTileExited(int, int)}.
     *
     * @param row Row in pawn coordinates
     * @param col Column in pawn coordinates
     *
     * @author Paul Teng (260862906)
     */
    private void onTileExited(final int row, final int col) {
        // Do not change this to enhanced for loop
        for (int i = 0; i < listeners.size(); ++i) {
            listeners.get(i).onTileExited(row, col);
        }
    }

    /**
     * This is called when mouse enters any wall slot.
     *
     * Please handle the event through
     * {@link GameBoardListener#onSlotEntered(int, int, Orientation)}.
     *
     * @param row         Row in wall coordinates
     * @param col         Column in wall coordinates
     * @param orientation Orientation
     *
     * @author Paul Teng (260862906)
     */
    private void onSlotEntered(final int row, final int col, final Orientation orientation) {
        // Do not change this to enhanced for loop
        for (int i = 0; i < listeners.size(); ++i) {
            listeners.get(i).onSlotEntered(row, col, orientation);
        }
    }

    /**
     * This is called when mouse leaves any wall slot.
     *
     * Please handle the event through
     * {@link GameBoardListener#onSlotExited(int, int, Orientation)}.
     *
     * @param row         Row in wall coordinates
     * @param col         Column in wall coordinates
     * @param orientation Orientation
     *
     * @author Paul Teng (260862906)
     */
    private void onSlotExited(int row, int col, Orientation orientation) {
        // Do not change this to enhanced for loop
        for (int i = 0; i < listeners.size(); ++i) {
            listeners.get(i).onSlotExited(row, col, orientation);
        }
    }

    /**
     * Decides if future input events to the installed game board listeners should
     * be swallowed (meaning no events get fired, listeners are not dispatched).
     * 
     * Note: This only includes input events, you can still force it by calling the
     * corresponding dispatch-event methods.
     *
     * @param flag true if events should be swallowed, false otherwise
     *
     * @author Paul Teng (260862906)
     */
    public void setBlockListenerEvents(boolean flag) {
        this.blockEvents = flag;
    }

    /**
     * A class that handles mouse related stuff for the Quoridor grid
     *
     * @author Paul Teng (260862906)
     */
    private final class MouseHandler extends MouseAdapter {

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
            if (TileMapPanel.this.blockEvents) {
                return;
            }

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
            if (TileMapPanel.this.blockEvents) {
                return;
            }

            this.defaultMouseEventHandler(e, TileMapPanel.this::onTileClicked, TileMapPanel.this::onSlotClicked);
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
            if (TileMapPanel.this.blockEvents) {
                return;
            }

            this.defaultMouseEventHandler(e, TileMapPanel.this::dispatchEnterTileEvent,
                    TileMapPanel.this::dispatchEnterSlotEvent);
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
            if (TileMapPanel.this.blockEvents) {
                return;
            }

            TileMapPanel.this.dispatchExitEvent();
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
    }
}
