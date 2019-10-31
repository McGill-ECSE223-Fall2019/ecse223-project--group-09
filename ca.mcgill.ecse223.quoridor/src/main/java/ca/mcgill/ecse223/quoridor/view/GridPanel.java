package ca.mcgill.ecse223.quoridor.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collections;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mcgill.ecse223.quoridor.controller.TOPlayer;
import ca.mcgill.ecse223.quoridor.controller.TOWall;
import ca.mcgill.ecse223.quoridor.controller.TOWallCandidate;

/**
 * Creates a panel for the board's grid
 *
 * @author Paul Teng (260862906)
 */
public class GridPanel extends JPanel {

    // ***** Additional UI Components *****
    private final TileMapPanel tileMap = new TileMapPanel();

    /**
     * Creates a grid panel
     *
     * @author Paul Teng (260862906)
     */
    public GridPanel() {
        this.setLayout(new GridBagLayout());

        for (int i = 0; i < TileMapPanel.SIDE; ++i) {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = TileMapPanel.SIDE - (i + 1);
            c.weighty = 1.0;
            this.add(new JLabel(Integer.toString(i + 1)), c);
        }

        for (int i = 0; i < TileMapPanel.SIDE; ++i) {
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = i + 1;
            c.gridy = TileMapPanel.SIDE;
            c.weightx = 1.0;
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
     * Sets a wall candidate
     *
     * @param wallCandidate A wall candidate
     *
     * @author Paul Teng (260862906)
     */
    public void setWallCandidate(final TOWallCandidate wallCandidate) {
        this.tileMap.wallCandidate = wallCandidate;
        this.repaint();
    }
}
