package ru.nsu.fit.karaseva.graph;

/**
 * A class that implements the getting the shortest paths in a graph.
 */
public class Distance {

  /**
   * Distance class constructor
   */
  public Distance() {
  }

  /**
   * Method for getting the shortest path from one vertex to another
   *
   * @param graph      Given graph
   * @param vertexFrom The vertex from which you want to get the distance.
   * @param vertexTo   The vertex to which you want to get the distance.
   * @return the shortest distance if it exists, or else returns -1.
   */
  public int getTheShortestDistance(Graph graph, int vertexFrom, int vertexTo) {
    if (graph != null) {
      DistanceCalculator d = new DistanceCalculator(graph, vertexFrom);
      int dist = d.getDist(vertexTo, graph.getNumberVertices());
      return dist;
    }
    return -1;
  }

}