package hw3;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Iterator;

public class GraphEdgeTest {
    @Test
    public void testConstructor() {
        new GraphEdge(new GraphNode("bar"), "boo");
    }
    @Test
    public void testGetDestination() {
        GraphNode boo = new GraphNode("bar");
        GraphNode loo = new GraphNode("test");
        GraphEdge temp = new GraphEdge(loo, "testedge");
        boo.add(temp);
        assertEquals(temp.getDestination(), loo);
    }

    @Test
    public void testGetLabel(){
        GraphEdge bar = new GraphEdge(new GraphNode("test"), "test2");
        assertEquals(bar.getLabel(), "test2");
    }
}
