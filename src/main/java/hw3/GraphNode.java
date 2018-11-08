package hw3;


import org.eclipse.jgit.annotations.Nullable;

import java.util.*;

/**
 * Represents a Node in a Graph
 * @spec.specfield content: String // the content held by a node
 * @spec.specfield edges : TreeSet of GraphEdge // the out-edges a node has
 *
 * Rep Invariant:
 * content != null, edges != null
 */
public class GraphNode implements Comparable<GraphNode> {
    /**
     * the value stored by the node
     */
    private String content;
    /**
     * the out-edges the node has
     */
    private Set<GraphEdge> edges;

    /**
     * Basic Constructor for the node
     * @param content is the value to be put into the node
     * @spec.requires content != null
     */
    public GraphNode(String content) {
        this.content = content;
        edges = new TreeSet<GraphEdge>();
    }

    /**
     * Constructor for the node with edges to other nodes
     * @param content is the value to be put into the node
     * @param dest is the destination node of the edge from this node
     * @param edgeLabel is the label to be placed on the edge from this node to the dest node
     * @spec.requires content, dest, edgeLabel != null
     */
    public GraphNode(String content, GraphNode dest, String edgeLabel) {
        this.content = content;
        edges = new TreeSet<GraphEdge>();
        this.edges.add(new GraphEdge(dest, edgeLabel));
    }

    /**
     * Returns the value stored in the node
     * @return the string/value stored in the node
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns a boolean representing if the node contains an edge
     * @param edge out-edge of which to check if the node contains
     * @spec.requires edge != null
     * @return boolean of whether edge e is held by this node
     */
    public boolean contains(GraphEdge edge) {
        checkRep();
        return edges.contains(edge);
    }

  /**
   * Returns a copy of the HashSet of edges
   *
   * @return a TreeSet which is a copy of the TreeSet of edges
   */
  public Set<GraphEdge> getEdges() {
        checkRep();
        return new TreeSet<>(edges);
  }

    /**
     * Clears the TreeSet of edges
     * @spec.modifies edges
     * @spec.effects removes all the edges in the edges TreeSet
     */
    public void clear() {
        checkRep();
        edges.clear();
        checkRep();
    }

    /**
     * Adds an edge if it is not already there to the node
     * @param edge: the edge to be added
     * @spec.requires edge != null
     * @spec.modifies edges
     * @spec.effects adds an additional edge to edges
     */
    public void add(GraphEdge edge) {
        checkRep();
        edges.add(edge);
        checkRep();
    }

    /**
     * Removes an edge from the node
     * @param edge: edge to be removed
     * @spec.requires edge != null
     * @spec.modifies edges
     * @spec.effects removes an edge from the TreeSet of edges
     */
    public void remove(GraphEdge edge) {
        checkRep();
        edges.remove(edge);
        checkRep();
    }

    /**
     * Gets an edge given the label, or null if not in the node
     * @param content the label of the edge
     * @return the GraphEdge associated with the label string or null if not there
     */
    public GraphEdge get(String content) {
        for (GraphEdge e: edges) {
            if (e.getLabel().equals(content)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Returns an iterator over the TreeSet of edges
     * @return an iterator over the edges in the TreeSet edges
     */
    public Iterator<GraphEdge> iterator() {
        checkRep();
        return edges.iterator();
    }

    /**
     * Returns the size of the edges TreeSet
     * @return the size as an int of the TreeSet of edges, edges
     */
    public int size() {
        checkRep();
        return edges.size();
    }

    /**
     * Returns whether the other equals this
     * @param other: object to be compared
     * @return whether other equals this
     */
    @Override public boolean equals(@Nullable Object other) {
        checkRep();
        if (!(other instanceof GraphNode)) {
            checkRep();
            return false;
        } else {
            checkRep();
            GraphNode otherNode = (GraphNode) other;
            if (!otherNode.getContent().equals(this.content)) {
                return false;
            }
            for (GraphEdge e : this.edges) {
                if (!otherNode.getEdges().contains(e)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns a hashcode for the function
     * @return int: hashcode of the function
     */
    @Override public int hashCode() {
        return 53 * Objects.hash(edges, content);
    }

    @Override
    public int compareTo(GraphNode o) {
        return this.content.compareTo(o.getContent());
    }

    /**
     * Checks to see if the representation invariant holds
     */
    private void checkRep() {
        assert (content != null);
        assert (edges != null);
    }
}