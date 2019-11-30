package ru.nsu.fit.karaseva.graph;

/**
 * A class that implements the calculation of the shortest paths in a graph.
 */
class DistanceCalculator {

  int[] minDist;
  int[] mark;

  /**
   * Method for counting the shortest paths in a graph.
   *
   * @param graph      Given graph
   * @param vertexFrom
   */
  public DistanceCalculator(Graph graph, int vertexFrom) {
    if (graph != null && graph.getNumberVertices() > 0) {
      int numV = graph.getNumberVertices();
      minDist = new int[numV];
      mark = new int[numV];
      for (int i = 0; i < numV; ++i) {
        mark[i] = 0;
        minDist[i] = Integer.MAX_VALUE;
      }
      minDist[vertexFrom] = 0;
      mark[vertexFrom] = 1;
    }
    Dijkstra(graph);
  }

  private void Dijkstra(Graph graph) {
    int from;
    while ((from = GetMinEdge(graph.getNumberVertices())) != -1) {
      mark[from] = 2;
      for (int i = 0; i < graph.getNumberOfNeighboringVertices(from); i++) {
        int vertexTo = graph.getNextVertexAndItsDistance(from, i)[0];
        if (mark[vertexTo] == 2) {
          continue;
        }
        int distanceTo = graph.getNextVertexAndItsDistance(from, i)[1];
        mark[vertexTo] = 1;
        if (minDist[vertexTo] > distanceTo + minDist[from]) {
          minDist[vertexTo] = distanceTo + minDist[from];
        }
      }
    }
  }

  private int GetMinEdge(int numberOfVertices) {
    int min = Integer.MAX_VALUE;
    int index = -1;
      for (int i = 0; i < numberOfVertices; ++i) {
          if (mark[i] == 1 && minDist[i] < min) {
              min = minDist[i];
              index = i;
          }
      }
    if (min != Integer.MAX_VALUE) {
      return index;
    } else {
      return -1;
    }
  }

  /**
   * Method for obtaining the calculated shortest path from one vertex to another.
   *
   * @param vertexTo         The vertex from which you want to get the distance.
   * @param numberOfVertices Number of vertices in the graph.
   * @return shortest path if it exists, or else returns -1.
   */
  public int getDist(int vertexTo, int numberOfVertices) {
    if (vertexTo < numberOfVertices && mark[vertexTo] == 2) {
      return minDist[vertexTo];
    } else {
      return -1;
    }
  }

}
