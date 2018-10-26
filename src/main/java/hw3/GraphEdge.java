package hw3;

/**
 * Represents an edge in a graph.
 * @spec.specfield destination : GraphNode //
 * @spec.specfield label : String //
 *
 * Rep invariant:
 *  edge cannot be null, string cannot be null
 */
public class GraphEdge {

    private GraphNode destination; // The node where the edge points to.
    private String label; // The string label assigned to the edge.

    /**
     * Constructor for GraphEdge
     * @param dest: destination node
     * @param label: label for the edge
     * @spec.requires dest != null and label != null
     */
    public GraphEdge(GraphNode dest, String label) {
        this.destination = dest;
        this.label = label;
    }

    /**
     * Getter for destination
     * @return the destination node
     */
    public GraphNode getDestination(){
        checkRep();
        return destination;
    }

    /**
     * Getter for label
     * @return label for the edge
     */
    public String getLabel(){
        return label;
    }

    /**
     * Returns whether the other equals this
     * @param other: object to be compared
     * @return whether this equals others
     */
    public boolean equals(Object other) {
        checkRep();
        if (!(other instanceof GraphNode)) {
            checkRep();
            return false;
        } else {
            checkRep();
            GraphEdge otherEdge = (GraphEdge) other;
            if (!otherEdge.getLabel().equals(this.label)) {
                return false;
            }
            if (!otherEdge.getDestination().equals(this.destination) || !otherEdge.getLabel().equals(this.label)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a hashcode for the function
     * @return int: hashcode of the function
     */
    public int hashCode() {
        return 31 * label.hashCode();
    }

    /**
     * Checks to see if the representation invariant holds
     */
    private void checkRep() {
        assert (label != null);
        assert (destination != null);
    }
}
