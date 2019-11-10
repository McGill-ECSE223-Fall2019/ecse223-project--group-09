package ca.mcgill.ecse223.quoridor.controller.path;

import java.util.function.Consumer;

/**
 * Nodes for the path finder
 *
 * @author Paul Teng (260862906)
 */
public final class Node {

    private Node north;
    private Node south;
    private Node east;
    private Node west;

    /**
     * Establishes a north link with the south link of another node
     *
     * @param node The other link, must not be the node itself
     *
     * @author Paul Teng (260862906)
     */
    public void linkNorth(Node node) {
        if (this == node) {
            throw new IllegalArgumentException("No self links allowed");
        }

        node.unlinkSouth();
        this.unlinkNorth();

        this.north = node;
        node.south = this;
    }

    /**
     * Establishes a south link with the north link of another node
     *
     * @param node The other link, must not be the node itself
     *
     * @author Paul Teng (260862906)
     */
    public void linkSouth(Node node) {
        if (this == node) {
            throw new IllegalArgumentException("No self links allowed");
        }

        node.unlinkNorth();
        this.unlinkSouth();

        this.south = node;
        node.north = this;
    }

    /**
     * Establishes a east link with the west link of another node
     *
     * @param node The other link, must not be the node itself
     *
     * @author Paul Teng (260862906)
     */
    public void linkEast(Node node) {
        if (this == node) {
            throw new IllegalArgumentException("No self links allowed");
        }

        node.unlinkWest();
        this.unlinkEast();

        this.east = node;
        node.west = this;
    }

    /**
     * Establishes a west link with the east link of another node
     *
     * @param node The other link, must not be the node itself
     *
     * @author Paul Teng (260862906)
     */
    public void linkWest(Node node) {
        if (this == node) {
            throw new IllegalArgumentException("No self links allowed");
        }

        node.unlinkEast();
        this.unlinkWest();

        this.west = node;
        node.east = this;
    }

    /**
     * Removes the north link, and as a result, the south link of the linked node.
     * If the no link exists, this method does nothing.
     *
     * @author Paul Teng (260862906)
     */
    public void unlinkNorth() {
        if (this.north == null)
            return;

        this.north.south = null;
        this.north = null;
    }

    /**
     * Removes the south link, and as a result, the north link of the linked node.
     * If the no link exists, this method does nothing.
     *
     * @author Paul Teng (260862906)
     */
    public void unlinkSouth() {
        if (this.south == null)
            return;

        this.south.north = null;
        this.south = null;
    }

    /**
     * Removes the east link, and as a result, the west link of the linked node. If
     * the no link exists, this method does nothing.
     *
     * @author Paul Teng (260862906)
     */
    public void unlinkEast() {
        if (this.east == null)
            return;

        this.east.west = null;
        this.east = null;
    }

    /**
     * Removes the west link, and as a result, the east link of the linked node. If
     * the no link exists, this method does nothing.
     *
     * @author Paul Teng (260862906)
     */
    public void unlinkWest() {
        if (this.west == null)
            return;

        this.west.east = null;
        this.west = null;
    }

    /**
     * Consumes the north node if link exists
     *
     * @param cons The consumer
     *
     * @author Paul Teng (260862906)
     */
    public void consumeNorthNode(Consumer<? super Node> cons) {
        if (this.north != null) {
            cons.accept(this.north);
        }
    }

    /**
     * Consumes the south node if link exists
     *
     * @param cons The consumer
     *
     * @author Paul Teng (260862906)
     */
    public void consumeSouthNode(Consumer<? super Node> cons) {
        if (this.south != null) {
            cons.accept(this.south);
        }
    }

    /**
     * Consumes the east node if link exists
     *
     * @param cons The consumer
     *
     * @author Paul Teng (260862906)
     */
    public void consumeEastNode(Consumer<? super Node> cons) {
        if (this.east != null) {
            cons.accept(this.east);
        }
    }

    /**
     * Consumes the west node if link exists
     *
     * @param cons The consumer
     *
     * @author Paul Teng (260862906)
     */
    public void consumeWestNode(Consumer<? super Node> cons) {
        if (this.west != null) {
            cons.accept(this.west);
        }
    }

    @Override
    public String toString() {
        return String.format("{N:%s, S:%s, E:%s, W:%s}", this.north, this.south, this.east, this.west);
    }
}