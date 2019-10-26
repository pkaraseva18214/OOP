package ru.nsu.fit.karaseva.graph;

import org.junit.Assert;
import org.junit.Test;
public class GraphTests {
    @Test
    public void test1() {
        Graph graph = null;
        Distance distance = new Distance(0, graph);
        int dist = distance.getDist(1);
        Assert.assertEquals(-1, dist);
    }

    @Test
    public void test2() {
        Edge[] arr  = new Edge[14];
        arr[0] = new Edge(0,1, 1);
        arr[1] = new Edge(1,2, 1);
        arr[2] = new Edge(2,9, 1);
        arr[3] = new Edge(2,7, 1);
        arr[4] = new Edge(0,10, 1);
        arr[5] = new Edge(10,3, 1);
        arr[6] = new Edge(1,8, 1);
        arr[7] = new Edge(9,7, 1);
        arr[8] = new Edge(8,3, 1);
        arr[9] = new Edge(4,3, 1);
        arr[10] = new Edge(4,6, 1);
        arr[11] = new Edge(7,5, 1);
        arr[12] = new Edge(8,6, 1);
        arr[13] = new Edge(3,6, 1);
        Graph graph = new Graph(11, arr);
        Distance distance = new Distance(0, graph);
        int dist = distance.getDist(3);
        Assert.assertEquals(2, dist);
    }

    @Test
    public void test3() {
        Edge[] arr = new Edge[2];
        arr[0] = new Edge(0, 2, 3);
        arr[1] = new Edge(4, 2, 3);
        Graph graph = new Graph(5, arr);
        Distance d = new Distance(0, graph);
        int dist = d.getDist(4);
        Assert.assertEquals(-1, dist);
    }

    @Test
    public void test4() {
        Edge[] arr = new Edge[6];
        arr[0] = new Edge(0, 1, 2);
        arr[1] = new Edge(1, 2, 3);
        arr[2] = new Edge(2, 3, 4);
        arr[3] = new Edge(3, 1, 6);
        arr[4] = new Edge(0, 3, 1);
        arr[4] = new Edge(0, 2, 5);
        Graph graph = new Graph(4,arr);
        Distance d = new Distance(1, graph);
        int dist = d.getDist(0);
        Assert.assertEquals(-1, dist);
    }

    @Test
    public void test5() {
        Edge[] arr = new Edge[6];
        arr[0] = new Edge(0, 1, 2);
        arr[1] = new Edge(1, 2, 3);
        arr[2] = new Edge(2, 3, 4);
        arr[3] = new Edge(3, 1, 6);
        arr[4] = new Edge(0, 3, 1);
        arr[4] = new Edge(0, 2, 5);
        Graph graph = new Graph(4,arr);
        Distance d = new Distance(1, graph);
        int dist = d.getDist(3);
        Assert.assertEquals(7, dist);
    }

    @Test
    public void test6() {
        Edge[] arr = new Edge[5];
        for (int i = 0; i < 4; ++i)
            arr[i] = new Edge(i, i + 1, 5);
        arr[4] = new Edge(4, 0, 1);
        Graph graph = new Graph(5, arr);
        Distance d = new Distance(2, graph);
        int dist = d.getDist(1);
        Assert.assertEquals(16, dist);
    }

    @Test
    public void test7() {
        Edge[] arr = new Edge[10];
        for (int i = 0; i < 4; ++i)
            arr[i] = new Edge(i, i + 1, 2);
        arr[4] = new Edge(4, 0, 2);
        arr[5] = new Edge(2, 0, 1);
        arr[6] = new Edge(2, 4, 3);
        arr[7] = new Edge(4, 1, 3);
        arr[8] = new Edge(0, 3, 1);
        arr[9] = new Edge(1, 3, 1);
        Graph graph = new Graph(5, arr);
        Distance d = new Distance(4, graph);
        int dist = d.getDist(2);
        Assert.assertEquals(5, dist);
    }
}
