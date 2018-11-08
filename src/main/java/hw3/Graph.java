package hw3;


import org.eclipse.jgit.annotations.Nullable;

import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.Objects;

/**
 * Represents a multigraph without duplicate edges
 * @spec.specfield nodes : HashTreeSet of GraphNode // The TreeSet of nodes that represent the graph
 *
 * Rep Invariant:
 * nodes != null
 *
 */
public class Graph {
    /** The TreeSet of nodes that represent the graph*/
    private Set<GraphNode> nodes;

    /**
     * Graph Constructor
     */
    public Graph() {
        nodes = new TreeSet<GraphNode>();
    }

    /**
     * Returns a copy of the TreeSet of the internal nodes
     * @return a copy of nodes
     */
    public Set<GraphNode> getNodes() {
        checkRep();
        return new TreeSet<>(nodes);
    }
    /**
     * Adds a node to the graph
     * @param node: node to be added
     * @spec.requires node != null
     * @spec.modifies nodes
     * @spec.effects adds a node to the nodes TreeSet
     */
    public void add(GraphNode node) {
        checkRep();
        nodes.add(node);
        checkRep();
    }

    /**
     * Removes a node from the graph
     * @param node: node to be removed
     * @spec.requires node != null
     * @spec.modifies nodes
     * @spec.effects removes a node from the nodes TreeSet
     */
    public void remove(GraphNode node) {
        checkRep();
        nodes.remove(node);
        checkRep();
    }

    /**
     * Returns whether the graph contains a node
     * @param node to be checked if contained
     * @return boolean if the graph contains a node
     */
    public boolean contains(GraphNode node) {
        return nodes.contains(node);
    }

    /**
     * Gets a node given the content, or null if not in the graph
     * @param content the content of the node
     * @return the GraphNode associated with the content string or null if not there
     */
    public GraphNode get(String content) {
        for (GraphNode n: nodes) {
            if (n.getContent().equals(content)) {
                return n;
            }
        }
        return null;
    }

    /**
     * Checks if the node TreeSet is empty
     * @return boolean of the state of emptiness of the node TreeSet
     */
    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    /**
     * Clears the node TreeSet of nodes
     * @spec.modifies nodes
     * @spec.effects removes all nodes in nodes(TreeSet)
     */
    public void clear() {
        checkRep();
        nodes.clear();
        checkRep();
    }

    /**
     * Returns the size of the nodes TreeSet
     * @return an int representing the size of the node TreeSet
     */
    public int size() {
        return nodes.size();
    }

    /**
     * Returns an iterator over the node TreeSet
     * @return iterator that goes over nodes
     */
    public Iterator<GraphNode> iterator() {
        checkRep();
        return nodes.iterator();
    }

    /**
     * Returns if other and this are equal
     * @param other: object to be compared
     * @return whether this and object are equal
     */
    @Override public boolean equals(@Nullable Object other) {
        checkRep();
        if (!(other instanceof Graph)) {
            checkRep();
            return false;
        } else {
            checkRep();
            Graph otherGraph = (Graph) other;
            for (GraphNode g : this.nodes) {
                if (!otherGraph.getNodes().contains(g)) {
                    return false;
                } else { // contains node
                    for (GraphNode g2: otherGraph.getNodes()) { // check if equals
                        if (!g.equals(g2)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Returns a hashcode for this
     * @return a hashcode for this
     */
    @Override public int hashCode() {
        return 37 * Objects.hash(nodes);
    }

    /**
     * Checks if the representation invariant holds
     */
    private void checkRep() {
        assert(nodes != null);
        for (GraphNode n : nodes) {
            assert(n != null);
        }
    }
}
