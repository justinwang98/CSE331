package hw3;


import org.eclipse.jgit.annotations.Nullable;

import java.util.*;

/**
 * Represents a multigraph without duplicate edges
 * @spec.specfield nodes : HashMap of GraphNode // The HashMap of nodes that represent the graph
 *
 * Rep Invariant:
 * nodes != null
 *
 */
public class Graph {
    /**
     * boolean to disable checkrep during testing and implementation
     */
    private static final boolean TEST = true;

    /** The HashSet of nodes that represent the graph*/
    private Map<String, GraphNode> nodes;

    /**
     * Graph Constructor
     */
    public Graph() {
        nodes = new HashMap<String, GraphNode>();
    }

    /**
     * Returns a copy of the HashSet of the internal nodes
     * @return a copy of nodes
     */
    public Map<String, GraphNode> getNodes() {
        checkRep();
        return new HashMap<String, GraphNode>(nodes);
    }

    /**
     * Returns the node associated with the key
     * @param key string of node
     * @return graphnode associated with string key
     */
    public @Nullable GraphNode get(String key) {
        return nodes.get(key);
    }

    /**
     * Adds a node to the graph
     * @param node: node to be added
     * @spec.requires node != null
     * @spec.modifies nodes
     * @spec.effects adds a node to the nodes HashSet
     */
    public void add(GraphNode node) {
        checkRep();
        nodes.put(node.getContent(), node);
        checkRep();
    }

    /**
     * Removes a node from the graph
     * @param node: node to be removed
     * @spec.requires node != null
     * @spec.modifies nodes
     * @spec.effects removes a node from the nodes HashSet
     */
    public void remove(GraphNode node) {
        checkRep();
        nodes.remove(node.getContent());
        checkRep();
    }

    /**
     * Returns whether the graph contains a node
     * @param node to be checked if contained
     * @return boolean if the graph contains a node
     */
    public boolean contains(GraphNode node) {
        return nodes.keySet().contains(node.getContent());
    }

    /**
     * Checks if the node HashSet is empty
     * @return boolean of the state of emptiness of the node HashSet
     */
    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    /**
     * Clears the node HashSet of nodes
     * @spec.modifies nodes
     * @spec.effects removes all nodes in nodes(HashSet)
     */
    public void clear() {
        checkRep();
        nodes.clear();
        checkRep();
    }

    /**
     * Returns the size of the nodes HashSet
     * @return an int representing the size of the node HashSet
     */
    public int size() {
        return nodes.size();
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
            return nodes.equals(otherGraph.getNodes());
        }
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
        if (!TEST) {
          for (String n : nodes.keySet()) {
              assert(n != null);
              assert (nodes.get(n) != null);
          }
        }
    }
}
