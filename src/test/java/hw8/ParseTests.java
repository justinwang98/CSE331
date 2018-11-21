package hw8;

import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import hw3.Graph;
import hw6.MarvelParser;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ParseTests {

    @Rule public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    Graph g = new Graph();
    Map<String, String> nameMap = new HashMap<String,String>();
    Map<String, Coordinates> dataMap = new HashMap<String, Coordinates>();
    Map<String, Coordinates> pathsMap = new HashMap<String, Coordinates>();
    DataParser parser = new DataParser();

    public void setupBuildings() throws MarvelParser.MalformedDataException {
        parser.parseBuildingData("small_buildings", nameMap, dataMap);
    }

    public void setupPaths()  throws MarvelParser.MalformedDataException, CsvDataTypeMismatchException,
            CsvConstraintViolationException {
        parser.parsePathData("small_paths", g, pathsMap);
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
    public void testPathsMapKeys() throws MarvelParser.MalformedDataException {
        setupBuildings();
        
    }
}
