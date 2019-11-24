package ca.mcgill.ecse223.quoridor.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mcgill.ecse223.quoridor.controller.Orientation;
import ca.mcgill.ecse223.quoridor.controller.TOPlayer;
import ca.mcgill.ecse223.quoridor.controller.TOWall;
import ca.mcgill.ecse223.quoridor.controller.TOWallCandidate;
import ca.mcgill.ecse223.quoridor.view.event.GameBoardListener;

/**
 * Creates a panel for the board's grid
 *
 * @author Paul Teng (260862906)
 */
public final class GridPanel extends JPanel {

    // ***** Additional UI Components *****
    private final TileMapPanel tileMap = new TileMapPanel();

    /**
     * Creates a grid panel
     *
     * @author Paul Teng (260862906)
     */
    public GridPanel() {
        this.setLayout(new GridBagLayout());

        final Insets rowInsets = new Insets(0, 5, 0, 0);
        final Insets colInsets = new Insets(0, 0, 5, 0);

        for (int i = 0; i < TileMapPanel.SIDE; ++i) {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = TileMapPanel.SIDE - (i + 1);
            c.weighty = 1.0;
            c.insets = rowInsets;
            this.add(new JLabel(Integer.toString(i + 1)), c);
        }

        for (int i = 0; i < TileMapPanel.SIDE; ++i) {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = i + 1;
            c.gridy = TileMapPanel.SIDE;
            c.weightx = 1.0;
            c.insets = colInsets;
            this.add(new JLabel(Character.toString((char) (i + 'a'))), c);
        }

        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = TileMapPanel.SIDE;
        c.gridwidth = TileMapPanel.SIDE;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = rowInsets;
        this.add(this.tileMap, c);
    }

    /**
     * Returns the tile-map panel instance associated with this grid panel
     *
     * @return the tile-map panel instance, never null
     *
     * @author Paul Teng (260862906)
     */
    public TileMapPanel getTileMapPanel() {
        return this.tileMap;
    }

    // ***** Forwarding common methods to tile-map panel instance *****

    /**
     * Set the player with the white pawn, this changes the position being
     * displayed.
     *
     * @param whitePlayer white player
     *
     * @author Paul Teng (260862906)
     *
     * @see TileMapPanel#setWhitePlayer(TOPlayer)
     */
    public void setWhitePlayer(TOPlayer whitePlayer) {
        this.tileMap.setWhitePlayer(whitePlayer);
    }

    /**
     * Set the player with the black pawn, this changes the position being
     * displayed.
     *
     * @param blackPlayer black player
     *
     * @author Paul Teng (260862906)
     *
     * @see TileMapPanel#setBlackPlayer(TOPlayer)
     */
    public void setBlackPlayer(TOPlayer blackPlayer) {
        this.tileMap.setBlackPlayer(blackPlayer);
    }

    /**
     * Sets the list of walls associated to the white player
     *
     * @param walls walls of the white player
     *
     * @author Paul Teng (260862906)
     *
     * @see TileMapPanel#setWhiteWalls(List)
     */
    public void setWhiteWalls(final List<TOWall> walls) {
        this.tileMap.setWhiteWalls(walls);
    }

    /**
     * Sets the list of walls associated to the black player
     *
     * @param walls walls of the black player
     *
     * @author Paul Teng (260862906)
     *
     * @see TileMapPanel#setBlackWalls(List)
     */
    public void setBlackWalls(final List<TOWall> walls) {
        this.tileMap.setBlackWalls(walls);
    }

    /**
     * Sets a wall candidate
     *
     * @param wallCandidate A wall candidate
     *
     * @author Paul Teng (260862906)
     *
     * @see TileMapPanel#setWallCandidate(TOWallCandidate)
     */
    public void setWallCandidate(final TOWallCandidate wallCandidate) {
        this.tileMap.setWallCandidate(wallCandidate);
    }

    /**
     * Returns the current wall candidate
     *
     * @return the current wall candidate, null if none were set
     *
     * @author Paul Teng (260862906)
     */
    public TOWallCandidate getWallCandidate() {
        return this.tileMap.getWallCandidate();
    }

    /**
     * Sets the wall orientation on slot junctions
     *
     * @param junctionOrientation The new orientation for junctions
     *
     * @author Paul Teng (260862906)
     *
     * @see TileMapPanel#setJunctionOrientation(Orientation)
     */
    public void setJunctionOrientation(final Orientation junctionOrientation) {
        this.tileMap.setJunctionOrientation(junctionOrientation);
    }

    /**
     * Installs a new game board listener to the current tile map
     *
     * @param lis The listener being installed, null does nothing
     *
     * @author Paul Teng (260862906)
     *
     * @see TileMapPanel#addGameBoardListener(GameBoardListener)
     */
    public void addGameBoardListener(final GameBoardListener lis) {
        this.tileMap.addGameBoardListener(lis);
    }

    /**
     * Removes a previously installed game board listener from the current tile map
     *
     * @param lis The listener being removed, null or non-installed does nothing
     *
     * @author Paul Teng (260862906)
     *
     * @see TileMapPanel#removeGameBoardListener(GameBoardListener)
     */
    public void removeGameBoardListener(final GameBoardListener lis) {
        this.tileMap.removeGameBoardListener(lis);
    }


}
