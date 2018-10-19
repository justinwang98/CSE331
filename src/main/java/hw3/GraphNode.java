package hw3;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;
import java.util.Set;

/**
 * Represents a Node in a Graph
 * @spec.specfield content: String // the content held by a node
 * @spec.specfield edges : Set<GraphEdge> // the out-edges a node has
 *
 * Abstraction Invariant:
 * content != null, edges != null
 */
public class GraphNode {

    private String content; // the value stored by the node
    private Set<GraphEdge> edges; // the out-edges the node has

    /**
     * Basic Constructor for the node
     * @param content is the value to be put into the node
     */
    public GraphNode(String content) {
        throw new NotImplementedException();
    }

    /**
     * Constructor for the node with edges to other nodes
     * @param content is the value to be put into the node
     * @param dest is the destination node of the edge from this node
     */
    public GraphNode(String content, GraphNode dest) {
        throw new NotImplementedException();
    }

    /**
     * Returns the value stored in the node
     * @return the string/value stored in the node
     */
    public String getContent() {
        throw new NotImplementedException();
    }

    /**
     * Returns a boolean representing if the node contains an edge
     * @param edge out-edge of which to check if the node contains
     * @requires edge != null
     * @return boolean of whether edge e is held by this node
     */
    public boolean contains(GraphEdge edge) {
        throw new NotImplementedException();
    }

    /**
     * Returns a copy of the set of edges
     * @return a set which is a copy of the set of edges
     */
    public Set<GraphNode> getChildren() {
        throw new NotImplementedException();
    }

    /**
     * Clears the set of edges
     * @modifies edges
     * @effects removes all the edges in the edges set
     */
    public void clear() {
        throw new NotImplementedException();
    }

    /**
     * Adds an edge to the node
     * @param edge: the edge to be added
     * @requires edge != null
     * @modifies edges
     * @effects adds an additional edge to edges
     */
    public void add(GraphEdge edge) {
        throw new NotImplementedException();
    }

    /**
     * Removes an edge from the node
     * @param edge: edge to be removed
     * @requires edge != null
     * @modifies edges
     * @effects removes an edge from the set of edges
     */
    public void remove(GraphEdge edge) {
        throw new NotImplementedException();
    }

    /**
     * Returns an iterator over the set of edges
     * @return an iterator over the edges in the set edges
     */
    public Iterator<GraphEdge> iterator() {
        throw new NotImplementedException();
    }

    /**
     * Returns the size of the edges set
     * @return the size as an int of the set of edges, edges
     */
    public int size() {
        throw new NotImplementedException();
    }

    /**
     * Checks to see if the representation invariant holds
     */
    public void checkRep() {
        throw new NotImplementedException();
    }
}
