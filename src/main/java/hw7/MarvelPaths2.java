package hw7;

import hw3.Graph;
import hw6.MarvelParser;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.reflect.Array;
import java.util.*;

/** this is not an ADT*/
public class MarvelPaths2 {

    /**
     * Creates a graph based upon the tsv file passed in, mapping characters to each over in every
     * book they have been in
     *
     * @param fileName file to be processed
     * @return a graph based upon the the file
     * @throws MarvelParser.MalformedDataException if the file is not well-formed: each line contains
     *     exactly two * tokens separated by a tab, or else starting with a # symbol to indicate a
     *     comment line
     */
    public static Graph<String> loadGraph(String fileName) throws MarvelParser.MalformedDataException {
        Graph<String> graph = new Graph();
        String file = "./src/main/java/hw7/data/" + fileName;
        Set<String> chars = new HashSet<String>();
        Map<String, List<String>> book = new HashMap<String, List<String>>();
        MarvelParser.parseData(file, chars, book);
        for (String character : chars) {
            graph.add(new Graph.GraphNode(character)); //maybe have to add in edges to self
        }
        Iterator<String> iter2 = book.keySet().iterator();

        // first for loop sets the edge weights and increments them
        for (String bookName : book.keySet()) { // loop over every book
            List<String> charsInBook = book.get(bookName);
            for (String character : charsInBook) { // loop over every character in book
                for  (String otherChar : charsInBook) { // loop over every other char in book
                    if (!character.equals(otherChar)) {
                        Graph.GraphNode temp = graph.get(otherChar);
                        if (temp != null) {
                            Graph.GraphNode temp2 = graph.get(character);
                            if (temp2 != null) {
                                Graph.GraphEdge edge = temp2.get(temp.getContent());
                                if (edge == null) { // no edge yet
                                    temp2.add(new Graph.GraphEdge(temp, 1.0)); // add edge between src and dest node
                                } else {
                                  temp2.add(new Graph.GraphEdge(temp, (double) edge.getLabel() + 1.0)); // increment weight
                                }
                            }
                        }
                    }
                }
            }
        }
        for (String character : chars) { // second for loop inverts all edge weights
            Graph.GraphNode tempChars = graph.get(character);
            if (tempChars != null) {
                Set<String> temp = (Set<String>) tempChars.getEdges().keySet();
                for (String edgeDest : temp) {
                  Graph.GraphEdge tempEdge = tempChars.get(edgeDest);
                  if (tempEdge != null) {
                    tempChars.add(
                        new Graph.GraphEdge(
                            tempEdge.getDestination(), // inverts value
                            1.0 / (double) tempEdge.getLabel()));
                  }
                }
            }
        }
        return graph;
    }

  /**
   * Returns the shortest path between the node at char1 and the node char2
   *
   * @param char1 string for src node
   * @param char2 string for dest node
   * @param graph graph to do operation
   * @return A list of Graphedges that represents the shortest path between two nodes, if no path is
   *     found returns null
   */
  public static @Nullable List<Graph.GraphEdge> MarvelPaths2(
      String char1, String char2, Graph<String> graph) {
    //        Dijkstra's algorithm assumes a graph with nonnegative edge weights.
    //
    //        start = starting node
    Graph.GraphNode start = graph.get(char1);
    //                dest = destination node
    Graph.GraphNode dest = graph.get(char2);
    //                active = priority queue.  Each element is a path from start to a
    //        given node. A path's “priority” in the queue is the total
    //        cost of that path. Nodes for which no path is known yet are
    //        not in the queue.
    Queue<ArrayList<Graph.GraphEdge>> active =
        new PriorityQueue<ArrayList<Graph.GraphEdge>>(new Comparator<ArrayList<Graph.GraphEdge>>() {
            @Override
            public int compare(ArrayList<Graph.GraphEdge> o1, ArrayList<Graph.GraphEdge> o2) {
                double path1 = (double) o1.get(o1.size() - 1).getLabel();
                double path2 = (double) o2.get(o2.size() - 1).getLabel();
                if (path1 > path2) {
                    return 1;
                } else if (path2 > path1) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

    //        finished = set of nodes for which we know the minimum-cost path from
    //        start.
    Set<Graph.GraphNode> finished = new HashSet<Graph.GraphNode>();
    //
    //                // Initially we only know of the path from start to itself, which has
    //                // a cost of zero because it contains no edges.
    //         Add a path from start to itself to active
    ArrayList<Graph.GraphEdge> init = new ArrayList<Graph.GraphEdge>();
    if (start != null) {
      init.add(new Graph.GraphEdge<Double>(start, 0.0));
    }
    active.add(init);
    //
    //        while active is non-empty:
    while (!active.isEmpty()) {

      //        // minPath is the lowest-cost path in active and is the
      //        // minimum-cost path to some node
      //        minPath = active.removeMin()
      ArrayList<Graph.GraphEdge> minPath = active.poll();
      if (minPath != null) {
        Graph.GraphEdge lastEdge = minPath.get(minPath.size() - 1);

        //        minDest = destination node in minPath
        Graph.GraphNode minDest = lastEdge.getDestination();
        //
        //        if minDest is dest:
        if (minDest.equals(dest)) {
          //        return minPath
          minPath.remove(0);
          return minPath;
        }
        //
        //        if minDest is in finished:
        //        continue
        if (finished.contains(minDest)) {
          continue;
        }

        //
        //        for each edge e = ⟨minDest, child⟩:
        for (String e : (Set<String>) minDest.getEdges().keySet()) {
          //        // If we don't know the minimum-cost path from start to child,
          //        // examine the path we've just found
          //        if child is not in finished:
            Graph.GraphEdge edge = minDest.get(e);
          if (edge != null) {
            if (!finished.contains(edge.getDestination())) {
              double updatedWeight =
                  (double) lastEdge.getLabel() + (double) edge.getLabel();
              //        newPath = minPath + e
              ArrayList<Graph.GraphEdge> newPath = new ArrayList<Graph.GraphEdge>(minPath);

              newPath.add(
                  new Graph.GraphEdge(edge.getDestination(), (double) updatedWeight));
              active.add(newPath);
            }
          }
        }
        //        add minDest to finished
        finished.add(minDest);
        //
      }
    }
    return null;
    //        If the loop terminates, then no path exists from start to dest.
    //                The implementation should indicate this to the client.
  }
}
