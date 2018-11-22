package hw8;

import hw3.Graph;
import hw6.MarvelParser;

import java.util.*;

public class CampusPathModel {
    private static Map<String, String> nameToLong;
    private static Map<String, Coordinates> coordData;
    private static Graph g;

    public static void init() throws MarvelParser.MalformedDataException {
        nameToLong = new HashMap<String, String>();
        coordData = new HashMap<String, Coordinates>();
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
}
