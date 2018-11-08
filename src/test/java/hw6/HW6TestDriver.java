package hw6;

import hw3.Graph;
import hw3.GraphEdge;
import hw3.GraphNode;

import java.io.*;
import java.util.*;

/**
 * This class implements a testing driver which reads test scripts from files for testing Graph, the
 * Marvel parser, and your BFS algorithm.
 */

public class HW6TestDriver {

  public static void main(String args[]) {
    try {
      if (args.length > 1) {
        printUsage();
        return;
      }

      hw6.HW6TestDriver td;

      if (args.length == 0) {
        td =
                new hw6.HW6TestDriver(new InputStreamReader(System.in), new OutputStreamWriter(System.out));
      } else {

        String fileName = args[0];
        File tests = new File(fileName);

        if (tests.exists() || tests.canRead()) {
          td = new hw6.HW6TestDriver(new FileReader(tests), new OutputStreamWriter(System.out));
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
    System.err.println("to read from a file: java hw6.test.HW6TestDriver <name of input script>");
    System.err.println("to read from standard in: java hw6.test.HW6TestDriver");
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
  public HW6TestDriver(Reader r, Writer w) {
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
      } else {
        output.println("Unrecognized command: " + command);
      }
    } catch (Exception e) {
      output.println("Exception: " + e.toString());
    }
  }

  private void createGraph(List<String> arguments) {
    if (arguments.size() != 1) {
      throw new hw6.HW6TestDriver.CommandException("Bad arguments to CreateGraph: " + arguments);
    }

    String graphName = arguments.get(0);
    createGraph(graphName);
  }

  private void createGraph(String graphName) {
    Graph g = new Graph();
    graphs.put(graphName, g);
    output.println("created graph " + graphName);
  }

  private void loadGraph(List<String> arguments) {
    if (arguments.size() != 2) {
      throw new hw6.HW6TestDriver.CommandException("Bad arguments to LoadGraph: " + arguments);
    }

    String graphName = arguments.get(0);
    String file = ".\\src\\main\\java\\hw6\\data\\" + arguments.get(1);
    MarvelParser parser = new MarvelParser();
    Set<String> chars = new HashSet<String>();
    Map<String, List<String>> book = new HashMap<String, List<String>>();
    try {
      parser.parseData(file, chars, book);
    } catch (MarvelParser.MalformedDataException m) {
      throw new hw6.HW6TestDriver.CommandException("MalformedData argument to LoadGraph: " + arguments.get(1));
    }
    loadGraph(graphName, chars, book);
  }

  private void loadGraph(String graphName, Set<String> chars, Map<String, List<String>> book ) {
    Graph g = new Graph();
    graphs.put(graphName, g);
    Iterator<String> iter = chars.iterator();
    while (iter.hasNext()) { // adds all the chars as nodes in the graph
      g.add(new GraphNode(iter.next()));
    }
    Iterator<String> iter2 = book.keySet().iterator();
    while (iter2.hasNext()) { // loop over every book
      String bookName = iter2.next();
      List<String> charsInBook = book.get(bookName);
      for (String character : charsInBook) { // loop over every character in book
        for (String otherChar : charsInBook) { // loop over every other char in book
          if (!character.equals(otherChar)) {
            g.get(character).add(new GraphEdge(g.get(otherChar), bookName)); // add edge between src and dest node
          }
        }
      }
    }
    output.println("loaded graph " + graphName);
  }

  private void addNode(List<String> arguments) {
    if (arguments.size() != 2) {
      throw new hw6.HW6TestDriver.CommandException("Bad arguments to addNode: " + arguments);
    }

    String graphName = arguments.get(0);
    String nodeName = arguments.get(1);

    addNode(graphName, nodeName);
  }

  private void addNode(String graphName, String nodeName) {
    Graph g = graphs.get(graphName);
    g.add(new GraphNode(nodeName));
    output.println("added node " + nodeName + " to " + graphName);
  }

  private void addEdge(List<String> arguments) {
    if (arguments.size() != 4) {
      throw new hw6.HW6TestDriver.CommandException("Bad arguments to addEdge: " + arguments);
    }

    String graphName = arguments.get(0);
    String parentName = arguments.get(1);
    String childName = arguments.get(2);
    String edgeLabel = arguments.get(3);

    addEdge(graphName, parentName, childName, edgeLabel);
  }

  private void addEdge(String graphName, String parentName, String childName, String edgeLabel) {
    Graph g = graphs.get(graphName);
    GraphNode dest = g.get(childName);
    if (dest != null) {
      GraphNode src = g.get(parentName);
      if (src != null) {
        src.add(new GraphEdge(dest, edgeLabel));
      }
    }
    output.println("added edge " + edgeLabel + " from " + parentName + " to " + childName + " in " + graphName);
  }

  private void listNodes(List<String> arguments) {
    if (arguments.size() != 1) {
      throw new hw6.HW6TestDriver.CommandException("Bad arguments to listNodes: " + arguments);
    }

    String graphName = arguments.get(0);
    listNodes(graphName);
  }

  private void listNodes(String graphName) {
    output.print(graphName + " contains: ");
    Graph g = graphs.get(graphName);
    for (GraphNode node : g.getNodes()) {
      output.print(node.getContent() + " ");
    }
    output.println();
  }

  private void listChildren(List<String> arguments) {
    if (arguments.size() != 2) {
      throw new hw6.HW6TestDriver.CommandException("Bad arguments to listChildren: " + arguments);
    }
    String graphName = arguments.get(0);
    String parentName = arguments.get(1);
    listChildren(graphName, parentName);
  }

  private void listChildren(String graphName, String parentName) {
    Graph g = graphs.get(graphName);
    output.print("the children of " + parentName + " in " + graphName + " are: ");
    GraphNode node = g.get(parentName);
    for (GraphEdge edge : node.getEdges()) {
      output.print(edge.getDestination().getContent() + "(" + edge.getLabel() + ") ");
    }
    output.println();
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

