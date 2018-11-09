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
     * Represents an edge in a graph.
     * @spec.specfield destination : GraphNode //
     * @spec.specfield label : String //
     *
     * Rep invariant:
     *  edge cannot be null, string cannot be null
     */
    public static class GraphEdge implements Comparable<GraphEdge>{

        /** The node where the edge points to.*/
        private GraphNode destination;
        /**
         * The string label assigned to the edge.
         */
        private String label;

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
        @Override public boolean equals(@Nullable Object other) {
            checkRep();
            if (!(other instanceof GraphEdge)) {
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
        @Override public int hashCode() {
            return 31 * Objects.hash(label, destination.getContent());
        }


        @Override
        public int compareTo(GraphEdge o) {
            int temp = this.destination.compareTo(o.getDestination());

            if (temp != 0) {
                return temp;
            } else {
                return this.label.compareTo(o.getLabel());
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
    public static class GraphNode implements Comparable<GraphNode> {
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
            edges = new HashSet<GraphEdge>();
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
            edges = new HashSet<GraphEdge>();
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
         * @return a HashSet which is a copy of the HashSet of edges
         */
        public Set<GraphEdge> getEdges() {
            checkRep();
            return new HashSet<>(edges);
        }

        /**
         * Clears the HashSet of edges
         * @spec.modifies edges
         * @spec.effects removes all the edges in the edges HashSet
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
         * @spec.effects removes an edge from the HashSet of edges
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
        public @Nullable GraphEdge get(String content) {
            for (GraphEdge e: edges) {
                if (e.getLabel().equals(content)) {
                    return e;
                }
            }
            return null;
        }

        /**
         * Returns an iterator over the HashSet of edges
         * @return an iterator over the edges in the HashSet edges
         */
        public Iterator<GraphEdge> iterator() {
            checkRep();
            return edges.iterator();
        }

        /**
         * Returns the size of the edges HashSet
         * @return the size as an int of the HashSet of edges, edges
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
                return edges.equals(otherNode.getEdges());
            }
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
