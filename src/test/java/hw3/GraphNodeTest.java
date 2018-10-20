package hw3;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class GraphNodeTest {

    @Test
    public void oneConstructorTest() {
        GraphNode foo = new GraphNode("test");
    }

    @Test
    public void twoConstructorTest() {
        GraphNode boo = new GraphNode("test");
        GraphNode foo = new GraphNode("test2", boo);
    }

    @Test
    public void getContentTest() {
        GraphNode foo = new GraphNode("test");
        assertEquals(foo.getContent(), "test");
    }

    @Test
    public void getEdgesTest() {
        // 1 node case
        GraphNode boo = new GraphNode("test");
        GraphNode foo = new GraphNode("test2", boo);
        assertEquals(boo.getEdges().size(), 1);

        // 2 node case
        GraphNode foo2 = new GraphNode("test3", boo);
        assertEquals(boo.getEdges().size(), 2);
    }

    @Test
    public void clearTest() {
        // 1 edge case
        GraphNode boo = new GraphNode("test");
        GraphNode foo = new GraphNode("test2", boo);
        boo.clear();
        assertEquals(boo.getEdges().size(), 0);

        // 2 edges case
        GraphNode foo2 = new GraphNode("test3", boo);
        boo.clear();
        assertEquals(boo.getEdges().size(), 0);
    }

    @Test
    public void addTest() {
        // 1 edge case
        GraphNode boo = new GraphNode("test");
        boo.add(new GraphEdge(new GraphNode("test2"),"test3"));
        assertEquals(boo.getEdges().size(), 1);

        // 2 edge case
        boo.add(new GraphEdge(new GraphNode("test4"),"test5"));
        assertEquals(boo.getEdges().size(), 2);
    }

    @Test
    public void sizeTest() {
        GraphNode boo = new GraphNode("test");

        // 0 edge case
        assertEquals(boo.getEdges().size(), 0);

        // 1 edge case
        GraphEdge bar = new GraphEdge(new GraphNode("test2"),"test3");
        assertEquals(boo.getEdges().size(), 1);

        // 2 edge case
        GraphEdge bar2 = new GraphEdge(new GraphNode("test4"),"test5");
        assertEquals(boo.getEdges().size(), 2);

    }

    @Test
    public void removeTest() {
        // 2 edge case
        GraphNode boo = new GraphNode("test");
        GraphEdge bar = new GraphEdge(new GraphNode("test2"),"test3");
        GraphEdge bar2 = new GraphEdge(new GraphNode("test4"),"test5");
        boo.add(bar);
        boo.add(bar2);
        boo.remove(bar);
        assertEquals(boo.getEdges().size(), 1);

        // 1 edge case
        boo.remove(bar2);
        assertEquals(boo.getEdges().size(), 0);
    }

    @Test
    public void iteratorTest() {
        GraphNode boo = new GraphNode("test");
        GraphEdge bar = new GraphEdge(new GraphNode("test2"),"test3");
        GraphEdge bar2 = new GraphEdge(new GraphNode("test4"),"test5");

        Iterator<GraphEdge> iter = boo.iterator();
        while (iter.hasNext()) {
            iter.next();
        }
    }
}