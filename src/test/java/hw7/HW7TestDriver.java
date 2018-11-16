package hw7;

import hw3.Graph;
import hw6.MarvelParser;

import java.io.*;
import java.util.*;

/**
 * This class implements a testing driver which reads test scripts from files for your graph ADT and
 * improved MarvelPaths application using Dijkstra's algorithm.
 */

public class HW7TestDriver {

  public static void main(String args[]) {
    try {
      if (args.length > 1) {
        printUsage();
        return;
      }

      hw7.HW7TestDriver td;

      if (args.length == 0) {
        td =
                new hw7.HW7TestDriver(new InputStreamReader(System.in), new OutputStreamWriter(System.out));
      } else {

        String fileName = args[0];
        File tests = new File(fileName);

        if (tests.exists() || tests.canRead()) {
          td = new hw7.HW7TestDriver(new FileReader(tests), new OutputStreamWriter(System.out));
        } else {
          System.err.println("Cannot read from " + tests.toString());
          printUsage();
          return;
        }
      }
      td.runTests();
    } catch (IOException e) {
      System.err.println(e.toString());
      e.printStackTrace(System.err);
    }
  }

  private static void printUsage() {
    System.err.println("Usage:");
    System.err.println("to read from a file: java hw7.test.HW7TestDriver <name of input script>");
    System.err.println("to read from standard in: java hw7.test.HW7TestDriver");
  }

  /** String -> Graph: maps the names of graphs to the actual graph * */
  private final Map<String, Graph> graphs = new HashMap<>();
  private final PrintWriter output;

  private final BufferedReader input;

  /**
   * @spec.requires r != null && w != null
   * @spec.effects Creates a new HW6TestDriver which reads command from <tt>r</tt> and writes
   *     results to <tt>w</tt>.
   */
  public HW7TestDriver(Reader r, Writer w) {
    input = new BufferedReader(r);
    output = new PrintWriter(w);
  }

  /**
   * @spec.effects Executes the commands read from the input and writes results to the output
   * @throws IOException if the input or output sources encounter an IOException
   */
  public void runTests() throws IOException {
    String inputLine;
    while ((inputLine = input.readLine()) != null) {
      if ((inputLine.trim().length() == 0) || (inputLine.charAt(0) == '#')) {
        // echo blank and comment lines
        output.println(inputLine);
      } else {
        // separate the input line on white space
        StringTokenizer st = new StringTokenizer(inputLine);
        if (st.hasMoreTokens()) {
          String command = st.nextToken();

          List<String> arguments = new ArrayList<>();
          while (st.hasMoreTokens()) {
            arguments.add(st.nextToken());
          }

          executeCommand(command, arguments);
        }
      }
      output.flush();
    }
  }

  private void executeCommand(String command, List<String> arguments) {
    try {
      if (command.equals("CreateGraph")) {
        createGraph(arguments);
      } else if (command.equals("AddNode")) {
        addNode(arguments);
      } else if (command.equals("AddEdge")) {
        addEdge(arguments);
      } else if (command.equals("ListNodes")) {
        listNodes(arguments);
      } else if (command.equals("ListChildren")) {
        listChildren(arguments);
      } else if (command.equals("LoadGraph")) {
        loadGraph(arguments);
      } else if (command.equals("FindPath")) {
        findPath(arguments);
      } else {
        output.println("Unrecognized command: " + command);
      }
    } catch (Exception e) {
      output.println("Exception: " + e.toString());
    }
  }

  private void createGraph(List<String> arguments) {
    if (arguments.size() != 1) {
      throw new hw7.HW7TestDriver.CommandException("Bad arguments to CreateGraph: " + arguments);
    }

    String graphName = arguments.get(0);
    createGraph(graphName);
  }

  private void createGraph(String graphName) {
    Graph g = new Graph();
    graphs.put(graphName, g);
    output.println("created graph " + graphName);
  }

  private void loadGraph(List<String> arguments) throws MarvelParser.MalformedDataException {
    if (arguments.size() != 2) {
      throw new hw7.HW7TestDriver.CommandException("Bad arguments to LoadGraph: " + arguments);
    }

    String graphName = arguments.get(0);
    Graph g = MarvelPaths2.loadGraph(arguments.get(1));
    graphs.put(graphName, g);
    output.println("loaded graph " + graphName);
  }

  private void addNode(List<String> arguments) {
    if (arguments.size() != 2) {
      throw new hw7.HW7TestDriver.CommandException("Bad arguments to addNode: " + arguments);
    }

    String graphName = arguments.get(0);
    String nodeName = arguments.get(1);

    addNode(graphName, nodeName);
  }

  private void addNode(String graphName, String nodeName) {
    Graph g = graphs.get(graphName);
    g.add(new Graph.GraphNode(nodeName));
    output.println("added node " + nodeName + " to " + graphName);
  }

  private void addEdge(List<String> arguments) {
    if (arguments.size() != 4) {
      throw new hw7.HW7TestDriver.CommandException("Bad arguments to addEdge: " + arguments);
    }

    String graphName = arguments.get(0);
    String parentName = arguments.get(1);
    String childName = arguments.get(2);
    String edgeLabel = arguments.get(3);

    addEdge(graphName, parentName, childName, edgeLabel);
  }

  private void addEdge(String graphName, String parentName, String childName, String edgeLabel) {
    Graph g = graphs.get(graphName);
    double edgeLabel2 = Double.valueOf(edgeLabel);
    Graph.GraphNode dest = g.get(childName);
    if (dest != null) {
      Graph.GraphNode src = g.get(parentName);
      if (src != null) {
        // want to update the edgelabel
        src.add(new Graph.GraphEdge(dest, edgeLabel2));
      }
    }
    output.println("added edge " + String.format("%.3f", Double.valueOf(edgeLabel)) + " from " + parentName +
            " to " + childName + " in " + graphName);
  }

  private void listNodes(List<String> arguments) {
    if (arguments.size() != 1) {
      throw new hw7.HW7TestDriver.CommandException("Bad arguments to listNodes: " + arguments);
    }

    String graphName = arguments.get(0);
    listNodes(graphName);
  }

  private void listNodes(String graphName) {
    output.print(graphName + " contains: ");
    Graph g = graphs.get(graphName);
    Set<String> sorted = new TreeSet<String>(g.getNodes().keySet()); // sorts the hashset alphabetically
    for (String node : sorted) {
      output.print(node + " ");
    }
    output.println();
  }

  private void listChildren(List<String> arguments) {
    if (arguments.size() != 2) {
      throw new hw7.HW7TestDriver.CommandException("Bad arguments to listChildren: " + arguments);
    }
    String graphName = arguments.get(0);
    String parentName = arguments.get(1);
    listChildren(graphName, parentName);
  }

  private void listChildren(String graphName, String parentName) {
    Graph g = graphs.get(graphName);
    output.print("the children of " + parentName + " in " + graphName + " are: ");
    Graph.GraphNode node = g.get(parentName);
    Map<String, Graph.GraphEdge> sorted = new TreeMap<String, Graph.GraphEdge>(node.getEdges()); // sorts the hashset numberically
    for (String edge : sorted.keySet()) {
      output.print(sorted.get(edge).getDestination().getContent() + "(" + String.format("%.3f", sorted.get(edge).getLabel()) + ") ");
    }
    output.println();
  }

  private void findPath(List<String> arguments) {
    Graph<String> g = graphs.get(arguments.get(0));
    // sets the first and nth character and replaces underscores with spaces
    String char1 = arguments.get(1).replace("_", " ");
    String charn = arguments.get(2).replace("_", " ");
    if (g.get(char1) == null) {
      output.println("unknown character " + char1);
    }
    if (g.get(charn) == null) {
      output.println("unknown character " + charn);
    }
    if (g.get(char1) != null && g.get(charn) != null) {
      output.println("path from " + char1 + " to " + charn + ":");
      if (g.get(char1) != g.get(charn)) {
        List<Graph.GraphEdge> list = MarvelPaths2.MarvelPaths2(char1, charn, g);
        if (list == null) {
          output.println("no path found");
        } else {
          double totalWeight = 0.0;
          // fencepost because edges doesn't contain source
          double edgeWeight1 = (Double) list.get(0).getLabel();
          output.println(
                  char1
                          + " to "
                          + list.get(0).getDestination().getContent()
                          + " with weight "
                          + String.format("%.3f", edgeWeight1));
          totalWeight += edgeWeight1;
          for (int i = 1; i < list.size(); i++) {
            double edgeWeight2 = (Double) list.get(i).getLabel() - (Double) list.get(i - 1).getLabel();
            output.println(
                    list.get(i - 1).getDestination().getContent()
                            + " to "
                            + list.get(i).getDestination().getContent()
                            + " with weight "
                            + String.format("%.3f", edgeWeight2));
            totalWeight += edgeWeight2;
          }
          output.println("total cost: " + String.format("%.3f", totalWeight));

        }
      }
    }
  }

  /** This exception results when the input file cannot be parsed properly. */
  static class CommandException extends RuntimeException {

    public CommandException() {
      super();
    }

    public CommandException(String s) {
      super(s);
    }

    public static final long serialVersionUID = 3495;
  }
}


