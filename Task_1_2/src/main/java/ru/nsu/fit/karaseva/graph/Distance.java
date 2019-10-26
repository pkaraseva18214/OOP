package ru.nsu.fit.karaseva.graph;

public class Distance {
    private int vertex;
    private int numV;
    private int[] minDist;
    private int[] flag;
    /**
     * Distance implementation method: looking for the shortest distance
     * @param from vertex
     * @param graph given graph
     */
    public Distance(int from, Graph graph) {
        if (graph != null && graph.getNumberV() > 0 ) {
            vertex = from;
            numV = graph.getNumberV();
            minDist = new int[numV];
            flag = new int[numV];
            for (int i = 0; i < numV; ++i) {
                flag[i] = 0;
                minDist[i] = Integer.MAX_VALUE;
            }
            minDist[vertex] = 0;
            flag[vertex] = 1;
        }
        Dijkstra(graph);
    }
    /**
     * Find the shortest path from vertex v1, if it exists
     * @param vTo given vertex
     * @return
     */
    public int getDist(int vTo) {
        if (vTo < numV && flag[vTo] == 2)
            return minDist[vTo];
        else return -1;
    }

    private void Dijkstra(Graph graph) {
        int from;
        while ((from = GetMinEdge()) != -1) {
            flag[from] = 2;
            for (int i = 0; i < graph.getNumberNeighborVertex(from); i++) {
                int vTo = graph.getNextVertex(from, i)[0];
                if (flag[vTo] == 2)
                    continue;
                int dTo = graph.getNextVertex(from, i)[1];
                flag[vTo] = 1;
                if (minDist[vTo] > dTo + minDist[from])
                    minDist[vTo] = dTo + minDist[from];
            }
        }
    }

    private int GetMinEdge() {
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < numV; ++i)
            if (flag[i] == 1 && minDist[i] < min) {
                min = minDist[i];
                index = i;
            }
        if (min != Integer.MAX_VALUE) {
            return index;
        } else {
            return -1;
        }
    }
}
