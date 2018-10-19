package hw3;


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Represents an edge in a graph.
 * @spec.specfield destination : GraphNode //
 * @spec.specfield label : String //
 *
 * Abstract invariant:
 *  edge cannot be null, string cannot be null
 */
public class GraphEdge {

    private GraphNode destination; // The node where the edge points to.
    private String label; // The string label assigned to the edge.

    /**
     * Constructor for GraphEdge
     * @param dest: destination node
     * @param label: label for the edge
     * @requires dest != null & label != null
     */
    public GraphEdge(GraphNode dest, String label) {
        throw new NotImplementedException();
    }

    /**
     * Getter for destination
     * @return the destination node
     */
    public GraphNode getDestination() {
        throw new NotImplementedException();
    }

    /**
     * Getter for label
     * @return label for the edge
     */
    public String getLabel() {
        throw new NotImplementedException();
    }
}
