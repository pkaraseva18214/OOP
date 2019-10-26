package ru.nsu.fit.karaseva.graph;

public class Edge {
   private int v1;
   private int v2;
   private int distance;

    /**
     * Construct edges of the graph
     * @param newV1 The vertex from which the edge of the graph
     * @param newV2 The vertex to which the edge of the graph
     * @param nDist Distance between vertices
     */
   public Edge(int newV1, int newV2, int nDist) {
        v1 = newV1;
        v2 = newV2;
        distance = nDist;
    }
    /**
     * @return The vertex from which the edge of the graph
     */
    public int getVerticeFrom() {
        return v1;
    }
    /**
     * @return The vertex to which the edge of the graph
     */
    public int getVerticeTo() {
        return v2;
    }
    /**
     * @return Distance between two vertices
     */
    public int getDistance() {
        return distance;
    }
}
