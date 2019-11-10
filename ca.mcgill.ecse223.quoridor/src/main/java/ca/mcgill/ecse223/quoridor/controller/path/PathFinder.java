package ca.mcgill.ecse223.quoridor.controller.path;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * A class that finds a path from a node to a set of potential nodes
 *
 * @author Paul Teng (260862906)
 */
public class PathFinder {

    private Node start;
    private Set<Node> ends;

    /**
     * Sets the starting node
     *
     * @param start The starting node
     *
     * @author Paul Teng (260862906)
     */
    public void setStartingNode(Node start) {
        this.start = start;
    }

    /**
     * Sets the set of ending nodes
     *
     * @param end The ending nodes
     *
     * @author Paul Teng (260862906)
     */
    public void setEndingNodes(Collection<Node> nodes) {
        this.ends = new HashSet<>(nodes);
    }

    /**
     * Tries to trace any path between start and end nodes
     *
     * @return true if any path is found, false if no such path is found
     *
     * @author Paul Teng (260862906)
     */
    public boolean trace() {
        // Check cases where there is nothing to trace
        if (this.start == null)
            return false;
        if (this.ends == null || this.ends.isEmpty())
            return false;

        // Set the current explore point, we start traversing from start node
        final LinkedList<Node> expNodes = new LinkedList<>();
        expNodes.add(this.start);

        // Keep a set of traversed nodes
        final HashSet<Node> traversed = new HashSet<>();

        // Start the tracing process
        Node node;
        while ((node = expNodes.pollFirst()) != null) {
            if (traversed.contains(node)) {
                // We already traversed this node
                continue;
            }

            if (this.ends.contains(node)) {
                // We already reached the end, path could be traced
                return true;
            }

            // Keep a lists of its neighbours to traverse
            node.consumeNorthNode(expNodes::addLast);
            node.consumeSouthNode(expNodes::addLast);
            node.consumeEastNode(expNodes::addLast);
            node.consumeWestNode(expNodes::addLast);

            // We finished traversing this node
            traversed.add(node);
        }

        // If we traversed all possible paths yet we have not reached the
        // end points, then there is no path that can be traced.
        return false;
    }

    @Override
    public String toString() {
        return "From: " + this.start + " to: " + this.ends;
    }
}