package hw3;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;
import hw3.Graph.GraphEdge;
import hw3.Graph.GraphNode;


public class GraphEdgeTest {
    @Rule public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

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
