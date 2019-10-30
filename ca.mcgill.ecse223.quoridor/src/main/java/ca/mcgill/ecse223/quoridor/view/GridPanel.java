package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Collections;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mcgill.ecse223.quoridor.controller.Orientation;
import ca.mcgill.ecse223.quoridor.controller.TOPlayer;
import ca.mcgill.ecse223.quoridor.controller.TOWall;

/**
 * Creates a panel for the board's grid
 *
 * @author Paul Teng (260862906)
 */
public class GridPanel extends JPanel {

    private static final int SIDE = 9;
    private static final int DIV = 10;

    private static final Color PAWN_CELL_COLOR = Color.lightGray;
    private static final Color WALL_CELL_COLOR = Color.cyan;

    // ***** Additional UI Components *****
    private final TileMap tileMap = new TileMap();

    /**
     * Creates a grid panel
     *
     * @author Paul Teng (260862906)
     */
    public GridPanel() {
        this.setLayout(new GridBagLayout());

        for (int i = 0; i < SIDE; ++i) {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = SIDE - (i + 1);
            c.weighty = 1.0;
            this.add(new JLabel(Integer.toString(i + 1)), c);
        }

        for (int i = 0; i < SIDE; ++i) {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = i + 1;
            c.gridy = SIDE;
            c.weightx = 1.0;
            this.add(new JLabel(Character.toString((char) (i + 'a'))), c);
        }

        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = SIDE;
        c.gridwidth = SIDE;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        this.add(this.tileMap, c);
    }

    /**
     * Set the player with the white pawn, this changes the position being
     * displayed.
     *
     * @param whitePlayer white player
     *
     * @author Paul Teng (260862906)
     */
    public void setWhitePlayer(TOPlayer whitePlayer) {
        this.tileMap.whitePlayer = whitePlayer;
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
        this.tileMap.blackPlayer = blackPlayer;
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
        this.tileMap.whiteWalls = walls != null ? walls : Collections.emptyList();
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
        this.tileMap.blackWalls = walls != null ? walls : Collections.emptyList();
        this.repaint();
    }

    /**
     * This is only the tile part
     *
     * @author Paul Teng (260862906)
     */
    private static class TileMap extends JPanel {

        private static interface TileDispatcher {
            public void dispatchTile(int row, int col);
        }
        
        private static interface SlotDispatcher {
            public void dispatchSlot(int row, int col, Orientation orientation);
        }

        private TOPlayer whitePlayer;
        private TOPlayer blackPlayer;

        private List<TOWall> whiteWalls = Collections.emptyList();
        private List<TOWall> blackWalls = Collections.emptyList();

        private Orientation junctionOrientation = Orientation.HORIZONTAL;

        public TileMap() {
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
         */
        private void onSlotClicked(int row, int col, Orientation orientation) {
            // TODO
            System.out.println(Character.toString((char) (col - 1 + 'a')) + row
                    + (orientation == Orientation.VERTICAL ? "v" : "h"));
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
         */
        private void onSlotEntered(int row, int col, Orientation orientation) {
            // TODO
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

            /**
             * {@inheritDoc}
             *
             * Note: handling of this event should be done in
             * {@link TileMap#onMouseWheelRotate(double) onMouseWheelRotate} instead
             *
             * @author Paul Teng (260862906)
             */
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                TileMap.this.onMouseWheelRotate(e.getPreciseWheelRotation());
            }

            /**
             * {@inheritDoc}
             *
             * Note: handling of this event should be done in either
             * {@link TileMap#onTileClicked(int, int) onTileClicked} or
             * {@link TileMap#onSlotClicked(int, int, Orientation) onSlotClicked} instead
             *
             * @author Paul Teng (260862906)
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                defaultMouseEventHandler(e, TileMap.this::onTileClicked, TileMap.this::onSlotClicked);
            }

            private void defaultMouseEventHandler(MouseEvent e, TileDispatcher tileMethod, SlotDispatcher slotMethod) {
                final Dimension d = TileMap.this.getSize();
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

            // ***** DO NOT MODIFY THESE OUTSIDE OF mouseMoved and the dispatch methods *****
            private int lastX = SIDE + 1;
            private int lastY = SIDE + 1;
            private Orientation lastOrientation = null;

            private void dispatchEnterSlot(int row, int col, Orientation orientation) {
                // If same as previous event, then we never exited, do not generate event
                if (this.lastX == row && this.lastY == col && this.lastOrientation == orientation) {
                    return;
                }

                if (this.lastOrientation != null) {
                    TileMap.this.onSlotExited(this.lastX, this.lastY, this.lastOrientation);
                } else if (this.lastX < SIDE + 1) {
                    TileMap.this.onTileExited(this.lastX, this.lastY);
                }

                TileMap.this.onSlotEntered((this.lastX = row), (this.lastY = col), (this.lastOrientation = orientation));
            }

            private void dispatchEnterTile(int row, int col) {
                // If same as previous event, then we never exited, do not generate event
                if (this.lastX == row && this.lastY == col && this.lastOrientation == null) {
                    return;
                }

                if (this.lastOrientation != null) {
                    TileMap.this.onSlotExited(this.lastX, this.lastY, this.lastOrientation);
                } else if (this.lastX < SIDE + 1) {
                    TileMap.this.onTileExited(this.lastX, this.lastY);
                }

                // tiles do not have an orientation
                this.lastOrientation = null;
                TileMap.this.onTileEntered((this.lastX = row), (this.lastY = col));
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                this.defaultMouseEventHandler(e, this::dispatchEnterTile, this::dispatchEnterSlot);
            }
        }
    }
}