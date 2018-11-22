package hw8;

import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import hw3.Graph;
import hw6.MarvelParser;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParseTests {

    @Rule public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    Graph g = new Graph();
    Map<String, String> nameMap = new HashMap<String,String>();
    Map<String, Coordinates> dataMap = new HashMap<String, Coordinates>();
    DataParser parser = new DataParser();

    public void setupBuildings() throws MarvelParser.MalformedDataException {
        parser.parseBuildingData("small_buildings.tsv", nameMap, dataMap);
    }

    public void setupPaths()  throws MarvelParser.MalformedDataException, CsvDataTypeMismatchException,
            CsvConstraintViolationException {
        parser.parsePathData("small_paths.tsv", g);
    }
    
    @Test
    public void testBuildingNamesMapKeys()throws MarvelParser.MalformedDataException {
        setupBuildings();
        assertTrue(nameMap.containsKey("home"));
        assertTrue(nameMap.containsKey("home2"));
        assertTrue(nameMap.containsKey("uw"));
        assertTrue(nameMap.containsKey("Seatac"));
        assertTrue(nameMap.containsKey("BEI"));
        assertTrue(nameMap.containsKey("TPE"));
        assertTrue(nameMap.containsKey("SF"));
    }
    
    @Test
    public void testBuildingNamesMapValues() throws MarvelParser.MalformedDataException {
        setupBuildings();
        assertEquals("home in Mukilteo", nameMap.get("home"));
        assertEquals("home in Seattle", nameMap.get("home2"));
        assertEquals("university of washington", nameMap.get("uw"));
        assertEquals("seattle tacoma airport", nameMap.get("Seatac"));
        assertEquals("beijing, china", nameMap.get("BEI"));
        assertEquals("taipei, taiwan", nameMap.get("TPE"));
        assertEquals("san francisco", nameMap.get("SF"));
    }
    
    @Test
    public void testBuildingMapSize() throws MarvelParser.MalformedDataException {
        setupBuildings();
        assertEquals(7, nameMap.size());
    }
    
    @Test
    public void testPathsNodes() throws MarvelParser.MalformedDataException, CsvDataTypeMismatchException,
            CsvConstraintViolationException {
        setupPaths();
//        assertTrue(g.getNodes().containsKey(new Coordinates(100.2,300.6)));
//        assertTrue(g.getNodes().containsKey(new Coordinates(400.64,729.8)));
//        assertTrue(g.getNodes().containsKey(new Coordinates(2243.48,4697.23)));
//        assertTrue(g.getNodes().containsKey(new Coordinates(2384.43,1928.97)));


        assertTrue(g.getNodes().containsKey("100.2,300.6"));
        assertTrue(g.getNodes().containsKey("400.64,729.8"));
        assertTrue(g.getNodes().containsKey("2243.48,4697.23"));
        assertTrue(g.getNodes().containsKey("2384.43,1928.97"));
    }

    @Test
    public void testPathsEdges() throws MarvelParser.MalformedDataException, CsvDataTypeMismatchException,
            CsvConstraintViolationException {
        setupPaths();
        Graph.GraphNode temp1 = g.get("100.2,300.6");
        Graph.GraphNode temp2 = g.get("400.64,729.8");
        Graph.GraphNode temp3 = g.get("2243.48,4697.23");
        Graph.GraphNode temp4 = g.get("2384.43,1928.97");

        assertEquals(300.12, temp1.get(temp2.getContent()).getLabel());
        assertEquals(1900.13, temp1.get(temp3.getContent()).getLabel());
        assertEquals(1800.14, temp2.get(temp3.getContent()).getLabel());
        assertEquals(2100.15, temp3.get(temp1.getContent()).getLabel());
        assertEquals(100.47, temp4.get(temp3.getContent()).getLabel());
    }
}
