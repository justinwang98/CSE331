package hw6;

import hw3.Graph;
import hw3.GraphEdge;
import hw3.GraphNode;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.reflect.Array;
import java.util.*;

/**
 * This is not an abstract data type
 */
public class MarvelPaths {

    /**
     * Creates a graph based upon the tsv file passed in, mapping characters to each over in every book they have been in
     * @param fileName file to be processed
     * @return a graph based upon the the file
     * @throws MarvelParser.MalformedDataException
     */
    public static Graph loadGraph(String fileName) throws MarvelParser.MalformedDataException {
        Graph graph = new Graph();
        String file = ".\\src\\main\\java\\hw6\\data\\" + fileName;
        Set<String> chars = new HashSet<String>();
        Map<String, List<String>> book = new HashMap<String, List<String>>();
        MarvelParser.parseData(file, chars, book);
        for (String character : chars) {
            graph.add(new GraphNode(character));
        }
        Iterator<String> iter2 = book.keySet().iterator();
        for (String bookName : book.keySet()) { // loop over every book
            List<String> charsInBook = book.get(bookName);
            for (String character : charsInBook) { // loop over every character in book
                for (String otherChar : charsInBook) { // loop over every other char in book
                    if (!character.equals(otherChar)) {
                        GraphNode temp = graph.get(otherChar);
                        if (temp != null) {
                            GraphNode temp2 = graph.get(character);
                            if (temp2 != null) {
                                temp2.add(new GraphEdge(temp, bookName)); // add edge between src and dest node
                            }
                        }
                    }
                }
            }
        }
        return graph;
    }

    /**
     * Returns the shortest path between the node at char1 and the node char2
     * @param char1 string for src node
     * @param char2 string for dest node
     * @param graph graph to do operation
     * @return A list of Graphedges that represents the shortest path between two nodes,
     * if no path is found returns null
     */
    public static @Nullable List<GraphEdge> MarvelPaths(String char1, String char2, Graph graph) {
        GraphNode start = graph.get(char1);
        GraphNode dest = graph.get(char2);
        List<GraphNode> queue = new ArrayList<GraphNode>(); // queue
        Map<GraphNode, ArrayList<GraphEdge>> map = new HashMap<GraphNode, ArrayList<GraphEdge>>();
        if (start != null) {
            queue.add(start);
            map.put(start, new ArrayList<>()); // start mapped to empty list
        }

        int count = 0;
        while (!queue.isEmpty()) {
            GraphNode curr = queue.remove(0);
            if (curr.equals(dest)) {
                return map.get(curr);
            }
            Set<GraphEdge> sorted = new TreeSet<GraphEdge>(curr.getEdges()); // sorts the hashset alphabetically
            for (GraphEdge e : sorted) {
                GraphNode destination = e.getDestination();
                if (!map.containsKey(destination)) {
                    ArrayList<GraphEdge> temp = map.get(curr);
                    if (temp != null) {
                        ArrayList<GraphEdge> path = new ArrayList<>(temp);
                        path.add(e);
                        map.put(destination, path);
                        queue.add(destination);
                    }
                }
            }
            count++;
            System.out.println(count);
        }
        return null;
    }
}
