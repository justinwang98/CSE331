package hw3;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Represents a multigraph without duplicate edges
 * @spec.specfield nodes : HashSet<GraphNode> // The set of nodes that represent the graph
 *
 * Abstraction Invariant:
 * nodes != null
 */
public class Graph {
    private HashSet<GraphNode> nodes; // The set of nodes that represent the graph

    /**
     * Graph Constructor
     */
    public Graph() {
        throw new NotImplementedException();
    }

    /**
     * Returns a copy of the set of the internal nodes
     * @return a copy of nodes
     */
    public Set<GraphNode> getNodes() {
        throw new NotImplementedException();
    }
    /**
     * Adds a node to the graph
     * @param node: node to be added
     * @requires node != null
     * @modifies nodes
     * @effects adds a node to the nodes set
     */
    public void add(GraphNode node) {
        throw new NotImplementedException();
    }

    /**
     * Removes a node from the graph
     * @param node: node to be removed
     * @requires node != null
     * @modifies nodes
     * @effects removes a node from the nodes set
     */
    public void remove(GraphNode node) {
        throw new NotImplementedException();
    }

    /**
     * Checks if the node set is empty
     * @return boolean of the state of emptiness of the node set
     */
    public boolean isEmpty() {
        throw new NotImplementedException();
    }

    /**
     * Clears the node set of nodes
     * @modifies nodes
     * @effects removes all nodes in nodes(set)
     */
    public void clear() {
        throw new NotImplementedException();
    }

    /**
     * Returns the size of the nodes set
     * @return an int representing the size of the node set
     */
    public int size() {
        throw new NotImplementedException();
    }

    /**
     * Returns an iterator over the node set
     * @return iterator that goes over nodes
     */
    public Iterator<GraphNode> iterator() {
        throw new NotImplementedException();
    }

    /**
     * Checks if the representation invariant holds
     */
    public void checkRep() {
        throw new NotImplementedException();
    }
}
