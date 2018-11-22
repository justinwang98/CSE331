package hw8;

import hw3.Graph;
import hw6.MarvelParser;
import hw7.MarvelPaths2;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;

/**
 * this is not an adt
 */
public class CampusPathModel {

    /**
     * finals represent value of 1/8 pi to compare to when testing for direction
     */
    private static final double ONE_EIGHTH_PI = Math.PI / 8;
    /**
     * finals represent value of 3/8 pi to compare to when testing for direction
     */
    private static final double THREE_EIGHTHS_PI = 3 * ONE_EIGHTH_PI;
    /**
     * finals represent value of 5/8 pi to compare to when testing for direction
     */
    private static final double FIVE_EIGHTHS_PI = 5 * ONE_EIGHTH_PI;
    /**
     * finals represent value of 7/8 pi to compare to when testing for direction
     */
    private static final double SEVEN_EIGHTHS_PI = 7 * ONE_EIGHTH_PI;
    /**
     * finals represent value of -1/8 pi to compare to when testing for direction
     */
    private static final double NEG_ONE_EIGHTH_PI = -1 * ONE_EIGHTH_PI;
    /**
     * finals represent value of -3/8 pi to compare to when testing for direction
     */
    private static final double NEG_THREE_EIGHTHS_PI = -1 * THREE_EIGHTHS_PI;
    /**
     * finals represent value of -5/8 pi to compare to when testing for direction
     */
    private static final double NEG_FIVE_EIGHTHS_PI = -1 * FIVE_EIGHTHS_PI;
    /**
     * finals represent value of -7/8 pi to compare to when testing for direction
     */
    private static final double NEG_SEVEN_EIGHTHS_PI = -1 * SEVEN_EIGHTHS_PI;

    /**
     * mapping shortened names to long names
     */
    private static Map<String, String> nameToLong = new HashMap<String, String>();

    /**
     * mapping short names to coordinate strings
     */
    private static Map<String, String> coordData = new HashMap<String, String>();

    /**
     * Graph holding the data
     */
    private static Graph g = new Graph();

    /**
     * initiliazes the data
     * @throws MarvelParser.MalformedDataException if data is malformed
     */
    public static void init() throws MarvelParser.MalformedDataException {
        parseData();
    }

    /**
     * Parses the data
     * @throws MarvelParser.MalformedDataException if data is malformed
     */
    public static void parseData() throws MarvelParser.MalformedDataException {
        DataParser parser = new DataParser();
        parser.parseBuildingData("campus_buildings.tsv", nameToLong, coordData);
        parser.parsePathData("campus_paths.tsv", g);
    }

    /**
     * Creates a list of strings to be given to the controller of the buildings
     * @return A list of strings containing properly formated buildings
     * @throws MarvelParser.MalformedDataException if data is malformed
     */
    public static List<String> buildings() throws MarvelParser.MalformedDataException {
        init();
        Set<String> buildingList = new TreeSet<String>(nameToLong.keySet());
        List<String> fullList = new ArrayList<>();

        for (String s : buildingList) {
            String full = s;
            full += ": " + nameToLong.get(s);
            fullList.add(full);
        }

        return fullList;
    }

    /**
     * Finds the shortest path given the start and end nodes
     * @param src start string
     * @param dest end string
     * @return a list of strings containing properly formated path data
     * @throws MarvelParser.MalformedDataException if data is malformed
     */
    public static @Nullable List<String> findShortestPath(String src, String dest)
            throws MarvelParser.MalformedDataException {
        init();
        boolean containsSRC = nameToLong.containsKey(src);
        boolean containsDEST = nameToLong.containsKey(dest);
        if (!containsSRC || !containsDEST) {
            if (!containsSRC) {
                System.out.println("Unknown building: " + src);
            }
            if (!containsDEST) {
                System.out.println("Unknown building: " + dest);
            }
            return null;
        }
        List<String> strList = new ArrayList<String>();
        strList.add("Path from " + nameToLong.get(src) + " to " + nameToLong.get(dest) + ":");

        String srcCoords = coordData.get(src);
        String endCoords = coordData.get(dest);
        String[] srcSplit = new String[0];
        List<Graph.GraphEdge> edgeList = new ArrayList<Graph.GraphEdge>();
        if (srcCoords != null && endCoords != null) {
          edgeList = MarvelPaths2.MarvelPaths2(srcCoords, endCoords, g);
          srcSplit = srcCoords.split(",");
        }
        double totalDistance = 0.0;

        boolean first = true; // for the fencepost to get coord data for source
        double x = 0.0;
        double y = 0.0;
        double x2 = 0.0;
        double y2 = 0.0;
        x = Double.valueOf(srcSplit[0]);
        y = Double.valueOf(srcSplit[1]);
        double prevDist = 0;

        if (edgeList != null) {
          for (Graph.GraphEdge edge : edgeList) {
          String temp = (String) edge.getDestination().getContent();
          String[] preFormated = new String[0];
            if (temp != null) {
               preFormated= temp.split(",");
            }
            x2 = Double.valueOf(preFormated[0]);
            y2 = Double.valueOf(preFormated[1]);
            double currDist = (double) edge.getLabel();
            totalDistance += currDist - prevDist;

            String direction = getDirection(Math.atan2(y2 - y, x2 - x));
            String miniPath =
                String.format(
                    "\tWalk %.0f feet %s to (%.0f, %.0f)",
                    currDist - prevDist,
                    direction,
                    Double.valueOf(preFormated[0]),
                    Double.valueOf(preFormated[1]));
            strList.add(miniPath);
            prevDist = currDist;
            x = x2;
            y = y2;
          }
        }
        strList.add(String.format("Total distance: %.0f feet", totalDistance)); // includes total distance
        return strList;
    }

    /**
     * Returns the proper direction given a theta value
     * @param theta angle to be compared to
     * @return a string representing the appropriate direction
     */
    public static String getDirection(double theta) {
        if ((theta > 0 && theta <= ONE_EIGHTH_PI) || (theta < 0 && theta > NEG_ONE_EIGHTH_PI)) {
            return "E";
        } else if (theta > ONE_EIGHTH_PI && theta <= THREE_EIGHTHS_PI) {
            return "SE";
        } else if (theta > THREE_EIGHTHS_PI && theta <= FIVE_EIGHTHS_PI) {
            return "S";
        } else if (theta > FIVE_EIGHTHS_PI && theta <= SEVEN_EIGHTHS_PI) {
            return "SW";
        } else if (theta >= NEG_SEVEN_EIGHTHS_PI && theta < NEG_FIVE_EIGHTHS_PI) {
            return "NW";
        } else if (theta >= NEG_FIVE_EIGHTHS_PI && theta < NEG_THREE_EIGHTHS_PI) {
            return "N";
        } else if (theta >= NEG_THREE_EIGHTHS_PI && theta < NEG_ONE_EIGHTH_PI) {
            return "NE";
        } else {
            return "W";
        }
    }
}
