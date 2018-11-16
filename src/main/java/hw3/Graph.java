package hw3;


import org.eclipse.jgit.annotations.NonNull;
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
public class Graph<E> {
    /**
     * Represents an edge in a graph.
     * @spec.specfield destination : GraphNode //
     * @spec.specfield label : E //
     *
     * Rep invariant:
     *  edge cannot be null, E cannot be null
     */
    public static class GraphEdge<E> implements Comparable<GraphEdge<E>>{

        /** The node where the edge points to.*/
        private GraphNode destination;
        /**
         * The E label assigned to the edge.
         */
        private E label;

        /**
         * Constructor for GraphEdge
         * @param dest: destination node
         * @param label: label for the edge
         * @spec.requires dest != null and label != null
         */
        public GraphEdge(GraphNode dest, E label) {
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
        public E getLabel(){
            return label;
        }

        /**
         * Returns whether the other equals this
         * @param other: object to be compared
         * @return whether this equals others
         */
        @Override public boolean equals(@Nullable Object other) {
            checkRep();
            if (!(other instanceof GraphEdge)) {
                checkRep();
                return false;
            } else {
                checkRep();
                GraphEdge otherEdge = (GraphEdge) other;
                if (!otherEdge.getDestination().equals(this.destination) || !otherEdge.getLabel().equals(this.label)) {
                    return false;
                }
            }
            return true;
        }

        /**
         * to string method for edges
         * @return string, representing the edge label
         */
        @Override public String toString() {
            return "Edge weight: " + this.label;
        }

        /**
         * Returns a hashcode for the function
         * @return int: hashcode of the function
         */
        @Override public int hashCode() {
            return 31 * Objects.hash(label, destination.getContent());
        }


        @Override
        public int compareTo(GraphEdge o) {
            int temp = this.destination.compareTo(o.getDestination());

            if (temp != 0) {
                return temp;
            } else {
                int temp2 = ((String) this.label).compareTo((String) o.getLabel());
                return temp2;
            }
        }

        /**
         * Checks to see if the representation invariant holds
         */
        private void checkRep() {
            assert (label != null);
            assert (destination != null);
        }
    }



    /**
     * Represents a Node in a Graph
     * @spec.specfield content: String // the content held by a node
     * @spec.specfield edges : HashSet of GraphEdge // the out-edges a node has
     *
     * Rep Invariant:
     * content != null, edges != null
     */
    public static class GraphNode<E> implements Comparable<GraphNode<E>> {
        /**
         * the value stored by the node
         */
        private E content;
        /**
         * Maps the content of the destination of the edge to the edge
         */
        private Map<E, GraphEdge> edges;

        /**
         * Basic Constructor for the node
         * @param content is the value to be put into the node
         * @spec.requires content != null
         */
        public GraphNode(E content) {
            this.content = content;
            edges = new HashMap<E, GraphEdge>();
        }

        /**
         * Constructor for the node with edges to other nodes
         * @param content is the value to be put into the node
         * @param dest is the destination node of the edge from this node
         * @param edgeLabel is the label to be placed on the edge from this node to the dest node
         * @spec.requires content, dest, edgeLabel != null
         */
        public GraphNode(E content, GraphNode dest, E edgeLabel) {
            this.content = content;
            edges = new HashMap<E, GraphEdge>();
            this.edges.put((E) dest.getContent(), new GraphEdge(dest, edgeLabel));
        }

        /**
         * Returns the value stored in the node
         * @return the string/value stored in the node
         */
        public E getContent() {
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
            return edges.keySet().contains(edge.getDestination().getContent());
        }

        /**
         * Returns a copy of the HashMap of edges
         *
         * @return a HashMap which is a copy of the HashMap of edges
         */
        public Map<E, GraphEdge> getEdges() {
            checkRep();
            return new HashMap<>(edges);
        }

        /**
         * Clears the HashMap of edges
         * @spec.modifies edges
         * @spec.effects removes all the edges in the edges HashMap
         */
        public void clear() {
            checkRep();
            edges.clear();
            checkRep();
        }

        /**
         * Returns the edge associated with the key
         * @param key string of edge
         * @return graphedge associated with string key
         */
        public @Nullable GraphEdge get(E key) {
            return edges.get(key);
        }

        /**
         * Adds a edge to the edge map
         * @param edge: edge to be added
         * @spec.requires edge != null
         * @spec.modifies edges
         * @spec.effects adds a edge to the edges HashMap
         */
        public void add(GraphEdge edge) {
            checkRep();
            edges.put((E) edge.getDestination().getContent(), edge);
            checkRep();
        }

        /**
         * Removes an edge from the node
         * @param edge: edge to be removed
         * @spec.requires edge != null
         * @spec.modifies edges
         * @spec.effects removes an edge from the HashMap of edges
         */
        public void remove(GraphEdge edge) {
            checkRep();
            edges.remove(edge.getDestination().getContent());
            checkRep();
        }

        /**
         * Returns the size of the edges HashMap
         * @return the size as an int of the HashMap of edges, edges
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
            int temp = ((String) this.content).compareTo((String) o.getContent());
            return temp;
        }

        /**
         * Checks to see if the representation invariant holds
         */
        private void checkRep() {
            assert (content != null);
            assert (edges != null);
        }
    }

    /**
     * boolean to disable checkrep during testing and implementation
     */
    private static final boolean TEST = true;

    /** The HashMap of nodes that represent the graph*/
    private Map<E, GraphNode> nodes;

    /**
     * Graph Constructor
     */
    public Graph() {
        nodes = new HashMap<E, GraphNode>();
    }

    /**
     * Returns a copy of the HashMap of the internal nodes
     * @return a copy of nodes
     */
    public Map<E, GraphNode> getNodes() {
        checkRep();
        return new HashMap<E, GraphNode>(nodes);
    }

    /**
     * Returns the node associated with the key
     * @param key string of node
     * @return graphnode associated with string key
     */
    public @Nullable GraphNode get(E key) {
        return nodes.get(key);
    }

    /**
     * Adds a node to the graph
     * @param node: node to be added
     * @spec.requires node != null
     * @spec.modifies nodes
     * @spec.effects adds a node to the nodes HashMap
     */
    public void add(GraphNode node) {
        checkRep();
        nodes.put((E) node.getContent(), node);
        checkRep();
    }

    /**
     * Removes a node from the graph
     * @param node: node to be removed
     * @spec.requires node != null
     * @spec.modifies nodes
     * @spec.effects removes a node from the nodes HashMap
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
    public boolean contains(GraphNode<Object> node) {
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
          for (E n : nodes.keySet()) {
              assert(n != null);
              assert (nodes.get(n) != null);
          }
        }
    }
}
