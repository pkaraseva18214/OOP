package ru.nsu.fit.karaseva.graph;

import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

public class GraphTests {

  @Test
  public void test1() {
    Graph graph = null;
    Distance distance = new Distance();
    int dist = distance.getTheShortestDistance(graph, 1, 1);
    Assert.assertEquals(-1, dist);
  }

  @Test
  public void test0() {
    Edge edge1= new Edge(0, 1, 1);
    Edge edge2 = new Edge(1, 2, 1);
    Edge edge3 = new Edge(2, 3, 5);
    ArrayList<Edge> arr = new ArrayList<>();
    arr.add(edge1);
    arr.add(edge2);
    arr.add(edge3);
    Graph graph = new Graph(4, arr);
    Distance distance = new Distance();
    int d = distance.getTheShortestDistance(graph, 1, 3);
    Assert.assertEquals(6, d);
  }

  @Test
  public void test2() {
    ArrayList<Edge> arr = new ArrayList<>();
    Edge e1 = new Edge(0, 1, 1);
    Edge e2 = new Edge(1, 2, 1);
    Edge e3 = new Edge(2, 9, 1);
    Edge e4 = new Edge(2, 7, 1);
    Edge e5 = new Edge(0, 10, 1);
    Edge e6 = new Edge(10, 3, 1);
    Edge e7 = new Edge(1, 8, 1);
    Edge e8 = new Edge(9, 7, 1);
    Edge e9 = new Edge(8, 3, 1);
    Edge e10 = new Edge(4, 3, 1);
    Edge e11 = new Edge(4, 6, 1);
    Edge e12 = new Edge(7, 5, 1);
    Edge e13 = new Edge(8, 6, 1);
    Edge e14 = new Edge(3, 6, 1);
    arr.add(e1);
    arr.add(e2);
    arr.add(e3);
    arr.add(e4);
    arr.add(e5);
    arr.add(e6);
    arr.add(e7);
    arr.add(e8);
    arr.add(e9);
    arr.add(e10);
    arr.add(e11);
    arr.add(e12);
    arr.add(e13);
    arr.add(e14);
    Graph graph = new Graph(11, arr);
    Distance distance = new Distance();
    int dist = distance.getTheShortestDistance(graph, 0, 3);
    Assert.assertEquals(2, dist);
  }

  @Test
  public void test3() {
    ArrayList<Edge> arr = new ArrayList<>();
    Edge e1 = new Edge(0, 2, 3);
    Edge e2 = new Edge(4, 2, 3);
    arr.add(e1);
    arr.add(e2);
    Graph graph = new Graph(5, arr);
    Distance d = new Distance();
    int dist = d.getTheShortestDistance(graph, 0, 4);
    Assert.assertEquals(-1, dist);
  }

  @Test
  public void test4() {
    ArrayList<Edge> arr = new ArrayList<>();
    Edge e1 = new Edge(0, 1, 2);
    Edge e2 = new Edge(1, 2, 3);
    Edge e3 = new Edge(2, 3, 4);
    Edge e4 = new Edge(3, 1, 6);
    Edge e5 = new Edge(0, 3, 1);
    Edge e6 = new Edge(0, 2, 5);
    arr.add(e1);
    arr.add(e2);
    arr.add(e3);
    arr.add(e4);
    arr.add(e5);
    arr.add(e6);
    Graph graph = new Graph(4, arr);
    Distance d = new Distance();
    int dist = d.getTheShortestDistance(graph, 1, 0);
    Assert.assertEquals(-1, dist);
  }

  @Test
  public void test5() {
    ArrayList<Edge> arr = new ArrayList<>();
    Edge e1 = new Edge(0, 1, 2);
    Edge e2 = new Edge(1, 2, 3);
    Edge e3 = new Edge(2, 3, 4);
    Edge e4 = new Edge(3, 1, 6);
    Edge e5 = new Edge(0, 3, 1);
    Edge e6 = new Edge(0, 2, 5);
    arr.add(e1);
    arr.add(e2);
    arr.add(e3);;
    arr.add(e4);
    arr.add(e5);
    arr.add(e6);
    Graph graph = new Graph(4, arr);
    Distance d = new Distance();
    int dist = d.getTheShortestDistance(graph, 1, 3);
    Assert.assertEquals(7, dist);
  }

}