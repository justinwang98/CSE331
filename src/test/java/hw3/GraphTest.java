package hw3;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

import java.util.Iterator;

import static org.junit.Assert.*;

public class GraphTest {
    @Rule public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    @Test
    public void constructorTest() {
        Graph g = new Graph();
    }

    @Test
    public void addTest() {
        Graph g = new Graph();

        // 0 node case
        assertEquals(g.size(), 0);

        // 1 node case
        g.add(new GraphNode("test1"));
        assertEquals(g.size(), 1);

        // 2 node case
        g.add(new GraphNode("test2"));
        assertEquals(g.size(), 2);
    }

    @Test
    public void getNodesTest(){
        Graph g = new Graph();

        // 0 node case
        assertEquals(g.getNodes().size(), 0);

        // 1 node case
        g.add(new GraphNode("test3"));
        assertEquals(g.getNodes().size(), 1);

        // 2 node case
        g.add(new GraphNode("test4"));
        assertEquals(g.getNodes().size(), 2);
    }

    @Test
    public void removeTest() {
        Graph g = new Graph();
        GraphNode foo = new GraphNode("test5");
        GraphNode foo2 = new GraphNode("test6");
        g.add(foo);
        g.add(foo2);

        // 2 node case
        g.remove(foo);
        assertEquals(g.size(), 1);

        // 1 node case
        g.remove(foo2);
        assertEquals(g.size(), 0);
    }

    @Test
    public void isEmptyTest() {
        Graph g = new Graph();
        assertTrue(g.isEmpty());

        g.add(new GraphNode("foo"));
        assertFalse(g.isEmpty());
    }

    @Test
    public void clearTest() {
        // 1 node case
        Graph g = new Graph();
        GraphNode foo = new GraphNode("test5");
        g.add(foo);
        g.clear();
        assertTrue(g.isEmpty());

        // 2 node case
        GraphNode foo2 = new GraphNode("test6");
        g.add(foo);
        g.add(foo2);
        g.clear();
        assertTrue(g.isEmpty());
    }

    @Test
    public void sizeTest() {
        Graph g = new Graph();

        // 0 node case
        assertEquals(g.size(), 0);

        // 1 node case
        g.add(new GraphNode("test7"));
        assertEquals(g.size(), 1);

        // 2 node case
        g.add(new GraphNode("test8"));
        assertEquals(g.size(), 2);
    }

}
