package ru.nsu.fit.karaseva.graph;

import java.util.*;

/**
 * A class that contains neighboring vertices of the graph and the distance to them.
 */
public class NeighborVertices {

  private int edge;
  private int dist;

  public List<NeighborVertices> neighbors = new ArrayList<NeighborVertices>();

  public NeighborVertices() {
  }

  public void setNeighboringVertex(int newEdge) {
    edge = newEdge;
  }

  public void setDistanceToNeighboringVertex(int newDist) {
    dist = newDist;
  }

  public int getNeighboringEdge() {
    return edge;
  }

  public int getDistanceToNeighboringVertex() {
    return dist;
  }

}

