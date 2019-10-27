package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.util.Collections;
import java.util.List;
import java.awt.GridBagConstraints;

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
    private static final Color PLACEMENT_CUE_COLOR = Color.red;

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
        this.add(new TileMap(), c);
    }

    /**
     * This is only the tile part
     *
     * @author Paul Teng (260862906)
     */
    private static class TileMap extends JPanel {

        private TOPlayer whitePlayer;
        private TOPlayer blackPlayer;

        private List<TOWall> whiteWalls = Collections.emptyList();
        private List<TOWall> blackWalls = Collections.emptyList();

        /**
         * Creates a tile map
         *
         * @author Paul Teng (260862906)
         */
        public TileMap() {
            TOWall wall1 = new TOWall();
            wall1.setRow(2);
            wall1.setColumn(4);
            wall1.setOrientation(Orientation.HORIZONTAL);
            whiteWalls = Collections.singletonList(wall1);

            TOWall wall2 = new TOWall();
            wall2.setRow(3);
            wall2.setColumn(3);
            wall2.setOrientation(Orientation.VERTICAL);
            blackWalls = Collections.singletonList(wall2);
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
            return this.whitePlayer != null
                && this.whitePlayer.getRow() >= 1 && this.whitePlayer.getRow() <= 9
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
            return this.whitePlayer != null
                && this.blackPlayer.getRow() >= 1 && this.blackPlayer.getRow() <= 9
                && this.blackPlayer.getColumn() >= 1 && this.blackPlayer.getColumn() <= 9;
        }

        /**
         * Draws the pawn at a specific spot
         *
         * @param g Graphics object
         * @param row Row in pawn coordinates
         * @param col Column in pawn coordinates
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
         * @param g Graphics object
         * @param wall A wall
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
    }
}