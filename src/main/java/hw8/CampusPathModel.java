package hw8;

import hw3.Graph;
import hw6.MarvelParser;
import hw7.MarvelPaths2;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;

public class CampusPathModel {

    private static final double ONE_EIGHTH_PI = Math.PI / 8;
    private static final double THREE_EIGHTHS_PI = 3 * ONE_EIGHTH_PI;
    private static final double FIVE_EIGHTHS_PI = 5 * ONE_EIGHTH_PI;
    private static final double SEVEN_EIGHTHS_PI = 7 * ONE_EIGHTH_PI;
    private static final double NEG_ONE_EIGHTH_PI = -1 * ONE_EIGHTH_PI;
    private static final double NEG_THREE_EIGHTHS_PI = -1 * THREE_EIGHTHS_PI;
    private static final double NEG_FIVE_EIGHTHS_PI = -1 * FIVE_EIGHTHS_PI;
    private static final double NEG_SEVEN_EIGHTHS_PI = -1 * SEVEN_EIGHTHS_PI;

    private static Map<String, String> nameToLong;
    private static Map<String, String> coordData;
    private static Graph g;

    public static void init() throws MarvelParser.MalformedDataException {
        nameToLong = new HashMap<String, String>();
        coordData = new HashMap<String, String>();
        g = new Graph();

        parseData();
    }

    public static void parseData() throws MarvelParser.MalformedDataException {
        DataParser parser = new DataParser();
        parser.parseBuildingData("campus_buildings.tsv", nameToLong, coordData);
        parser.parsePathData("small_paths.tsv", g);
    }

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

    public static @Nullable List<String> findShortestPath(String src, String dest)
            throws MarvelParser.MalformedDataException {
        init();
        boolean containsSRC = nameToLong.containsKey(src);
        boolean containsDEST = nameToLong.containsKey(dest);
        if (!containsSRC || !containsDEST) {
            if (!containsSRC) {
                System.out.println("Unknown Building: " + src);
            }
            if (!containsDEST) {
                System.out.println("Unknown Building: " + dest);
            }
            return null;
        }
        List<String> strList = new ArrayList<String>();
        strList.add("Path from " + nameToLong.get(src) + " to " + nameToLong.get(dest) + ":");

        String srcCoords = coordData.get(src);
        String endCoords = coordData.get(dest);
        List<Graph.GraphEdge> edgeList = MarvelPaths2.MarvelPaths2(srcCoords, endCoords, g);
        double totalDistance = 0.0;

        String[] srcSplit = srcCoords.split(",");
        boolean first = true; // for the fencepost to get coord data for source
        double x = 0.0;
        double y = 0.0;
        double x2 = 0.0;
        double y2 = 0.0;
        x = Double.valueOf(srcSplit[0]);
        y = Double.valueOf(srcSplit[1]);

        for (Graph.GraphEdge edge : edgeList) {
            String[] preFormated = ((String) edge.getDestination().getContent()).split(",");
            x2 = Double.valueOf(preFormated[0]);
            y2 = Double.valueOf(preFormated[1]);
            totalDistance += (double) edge.getLabel();
            String direction = getDirection(Math.atan2(y2 - y, x2 - x));
            String miniPath = String.format("\tWalk %.0f feet %s to %.0f, %.0f", edge.getLabel(), direction,
                    preFormated[0], preFormated[1]);
            strList.add(miniPath);
            x = x2;
            y = y2;
        }
        strList.add(String.format("Total distance: %.0 feet", totalDistance)); // includes total distance
        return strList;
    }

    public static String getDirection(double theta) {
        if ((theta > 0 && theta <= ONE_EIGHTH_PI) || (theta < 0 && theta > NEG_ONE_EIGHTH_PI)) {
            return "E";
        } else if (theta > ONE_EIGHTH_PI && theta <= THREE_EIGHTHS_PI) {
            return "NE";
        } else if (theta > THREE_EIGHTHS_PI && theta <= FIVE_EIGHTHS_PI) {
            return "N";
        } else if (theta > FIVE_EIGHTHS_PI && theta <= SEVEN_EIGHTHS_PI) {
            return "NW";
        } else if (theta >= NEG_SEVEN_EIGHTHS_PI && theta < NEG_FIVE_EIGHTHS_PI) {
            return "SW";
        } else if (theta >= NEG_FIVE_EIGHTHS_PI && theta < NEG_THREE_EIGHTHS_PI) {
            return "S";
        } else if (theta >= NEG_THREE_EIGHTHS_PI && theta < NEG_ONE_EIGHTH_PI) {
            return "SE";
        } else {
            return "W";
        }
    }
}
