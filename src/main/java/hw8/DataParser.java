package hw8;

import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import hw3.Graph;
import hw6.MarvelParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * This is not an ADT
 */
public class DataParser {

  /**
   * Parses the building data into two maps
   * @param filenameBuildings the file to be parsed from
   * @param nametoLong map that maps short names to longer names
   * @param buildings map that maps short names to coordinates
   * @throws MarvelParser.MalformedDataException
   */
  public static void parseBuildingData(String filenameBuildings, Map<String, String> nametoLong,
                                       Map<String, Coordinates> buildings)
          throws MarvelParser.MalformedDataException {
    BufferedReader reader = null;
    try { // parse buildings
      reader = new BufferedReader(new FileReader(filenameBuildings));

      // Construct the collections of characters and books, one
      // <character, book> pair at a time.
      reader.readLine(); // gets rid of first example line
      String inputLine;
      while ((inputLine = reader.readLine()) != null) {

        // Ignore comment lines.
        if (inputLine.startsWith("#")) {
          continue;
        }

        // Parse the data and throwing
        // an exception for malformed lines.
        String[] tokens = inputLine.split("\t");
        if (tokens.length != 4) {
          throw new MarvelParser.MalformedDataException("Line should contain exactly three tabs: " + inputLine);
        }

        String shortName = tokens[0];
        String longName = tokens[1];
        double xCoord = Double.valueOf(tokens[2]);
        double yCoord = Double.valueOf(tokens[3]);
        Coordinates coord = new Coordinates(xCoord, yCoord);

        nametoLong.put(shortName, longName);
        buildings.put(shortName, coord);
      }
    } catch (IOException e) {
      System.err.println(e.toString());
      e.printStackTrace(System.err);
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          System.err.println(e.toString());
          e.printStackTrace(System.err);
        }
      }
    }
  }

  /**
   * Builds the Graph based upon the paths file
   * @param filenamePaths file with the path info
   * @param g graph to be modified
   * @param paths map that maps short names to coordinates
   * @throws MarvelParser.MalformedDataException if data is malformed
   * @throws CsvDataTypeMismatchException if csv data is mismatched
   * @throws CsvConstraintViolationException if violates csv constraints
   */
  public static void parsePathData(String filenamePaths, Graph g, Map<String, Coordinates> paths)
          throws MarvelParser.MalformedDataException, CsvDataTypeMismatchException, CsvConstraintViolationException {
    BufferedReader reader = null;
    try { // parse paths
      reader = new BufferedReader(new FileReader(filenamePaths));

      // Construct the collections of characters and books, one
      // <character, book> pair at a time.
      reader.readLine(); // gets rid of example line
      String inputLine;
      while ((inputLine = reader.readLine()) != null) {

        // Ignore comment lines.
        if (inputLine.startsWith("#")) {
          continue;
        }

        // Parse the data and throwing
        // an exception for malformed lines.
        String[] tokens = inputLine.split("\t");
        if (tokens.length != 3) {
          throw new MarvelParser.MalformedDataException("Line should contain exactly two tabs: " + inputLine);
        }

        String src = tokens[0];
        String dest = tokens[1];
        double distance = Double.valueOf(tokens[2]);

        CoordinateConverter converter = new CoordinateConverter();
        Coordinates source = (Coordinates) converter.convert(src);
        Coordinates destination = (Coordinates) converter.convert(dest);

        Graph.GraphNode srcNode = g.get(source);
        Graph.GraphNode destNode = g.get(destination);

        if (srcNode == null) { // source isnt in graph
          srcNode = new Graph.GraphNode(source);
          g.add(srcNode);
        }
        if (destNode == null) {
          destNode = new Graph.GraphNode(destination);
          g.add(destNode);
        }
        srcNode.add(new Graph.GraphEdge(destNode, distance));
      }
    } catch (IOException e) {
      System.err.println(e.toString());
      e.printStackTrace(System.err);
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          System.err.println(e.toString());
          e.printStackTrace(System.err);
        }
      }
    }
  }
}
