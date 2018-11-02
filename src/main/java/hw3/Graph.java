package hw3;


import org.checkerframework.checker.initialization.qual.Initialized;
import org.eclipse.jgit.annotations.Nullable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Represents a multigraph without duplicate edges
 * @spec.specfield nodes : HashSet of GraphNode // The set of nodes that represent the graph
 *
 * Rep Invariant:
 * nodes != null
 *
 */
public @Initialized class Graph {
    /** The set of nodes that represent the graph*/
    private Set<GraphNode> nodes;

    /**
     * Graph Constructor
     */
    public Graph() {
        nodes = new HashSet<GraphNode>();
        checkRep();
    }

    /**
     * Returns a copy of the set of the internal nodes
     * @return a copy of nodes
     */
    public Set<GraphNode> getNodes() {
        checkRep();
        Iterator<GraphNode> iter = nodes.iterator();
        Set<GraphNode> copy = new HashSet<GraphNode>();
        while (iter.hasNext()) { //copy every node in nodes to copy
            copy.add(iter.next());
        }
        checkRep();
        return copy;
    }
    /**
     * Adds a node to the graph
     * @param node: node to be added
     * @spec.requires node != null
     * @spec.modifies nodes
     * @spec.effects adds a node to the nodes set
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
     * @spec.effects removes a node from the nodes set
     */
    public void remove(GraphNode node) {
        checkRep();
        nodes.remove(node);
        checkRep();
    }

    /**
     * Checks if the node set is empty
     * @return boolean of the state of emptiness of the node set
     */
    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    /**
     * Clears the node set of nodes
     * @spec.modifies nodes
     * @spec.effects removes all nodes in nodes(set)
     */
    public void clear() {
        checkRep();
        nodes.clear();
        checkRep();
    }

    /**
     * Returns the size of the nodes set
     * @return an int representing the size of the node set
     */
    public int size() {
        return nodes.size();
    }

    /**
     * Returns an iterator over the node set
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
        return 37 * nodes.hashCode();
    }

    /**
     * Checks if the representation invariant holds
     */
    private void checkRep() {
        assert(nodes != null);
    }
}
