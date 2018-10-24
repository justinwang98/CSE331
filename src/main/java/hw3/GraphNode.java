package hw3;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashSet;
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
        this.content = content;
        edges = new HashSet<GraphEdge>();
        checkRep();
    }

    /**
     * Constructor for the node with edges to other nodes
     * @param content is the value to be put into the node
     * @param dest is the destination node of the edge from this node
     * @param edgeLabel is the label to be placed on the edge from this node to the dest node
     */
    public GraphNode(String content, GraphNode dest, String edgeLabel) {
        this.content = content;
        edges = new HashSet<GraphEdge>();
        this.edges.add(new GraphEdge(dest, edgeLabel));
        checkRep();
    }

    /**
     * Returns the value stored in the node
     * @return the string/value stored in the node
     */
    public String getContent() {
        checkRep();
        return content;
    }

    /**
     * Returns a boolean representing if the node contains an edge
     * @param edge out-edge of which to check if the node contains
     * @requires edge != null
     * @return boolean of whether edge e is held by this node
     */
    public boolean contains(GraphEdge edge) {
        checkRep();
        return edges.contains(edge);
    }

    /**
     * Returns a copy of the set of edges
     * @return a set which is a copy of the set of edges
     */
    public Set<GraphEdge> getEdges() {
        checkRep();
        Iterator<GraphEdge> iter = edges.iterator();
        Set<GraphEdge> copy = new HashSet<GraphEdge>();
        while (iter.hasNext()) { //copy every node in nodes to copy
            copy.add(iter.next());
        }
        checkRep();
        return copy;
    }

    /**
     * Clears the set of edges
     * @modifies edges
     * @effects removes all the edges in the edges set
     */
    public void clear() {
        checkRep();
        edges.clear();
        checkRep();
    }

    /**
     * Adds an edge to the node
     * @param edge: the edge to be added
     * @requires edge != null
     * @modifies edges
     * @effects adds an additional edge to edges
     */
    public void add(GraphEdge edge) {
        checkRep();
        edges.add(edge);
        checkRep();
    }

    /**
     * Removes an edge from the node
     * @param edge: edge to be removed
     * @requires edge != null
     * @modifies edges
     * @effects removes an edge from the set of edges
     */
    public void remove(GraphEdge edge) {
        checkRep();
        edges.remove(edge);
        checkRep();
    }

    /**
     * Returns an iterator over the set of edges
     * @return an iterator over the edges in the set edges
     */
    public Iterator<GraphEdge> iterator() {
        checkRep();
        return edges.iterator();
    }

    /**
     * Returns the size of the edges set
     * @return the size as an int of the set of edges, edges
     */
    public int size() {
        checkRep();
        return edges.size();
    }

    public equals() {
        //TODO
    }

    public hashcode() {
        //TODO
    }

    /**
     * Checks to see if the representation invariant holds
     */
    public void checkRep() {
        assert (content != null);
        assert (edges != null);
    }
}
