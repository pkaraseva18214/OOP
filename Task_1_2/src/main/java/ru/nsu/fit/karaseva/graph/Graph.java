package ru.nsu.fit.karaseva.graph;

public class Graph {
    private int numberVertices;
    private NeighborVertices[] vertices;
    private int[] numberOfNeighborV;
    /**
     * Graph implementation method
     * @param n number of vertices
     * @param arr array of edges
     */
    public Graph(int n, Edge[] arr) {
        numberVertices = n;
        vertices = new NeighborVertices[n];
        numberOfNeighborV = new int[n];
        for (int i = 0; i < n; i++) {
            numberOfNeighborV[i] = 0;
            vertices[i] = new NeighborVertices();
        }
        if (arr != null)
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == null) {
                    continue;
                }
                addEdge(arr[i].getVerticeFrom(), arr[i].getVerticeTo(), arr[i].getDistance());
            }
    }

    private void addEdge(int v1, int v2, int d) {
        if (v1 < numberVertices || v2 < numberVertices) {
            vertices[v1].edges.add(v2);
            vertices[v1].dist.add(d);
            numberOfNeighborV[v1]++;
        } else {
            System.out.println("Number of vertices more than expected.\n");
        }
    }
    /**
     * Getting number of vertices in the graph
     * @return number of vertices in graph
     */
    public int getNumberV() {
        return numberVertices;
    }
    /**
     * Method for getting number of neighbor vertex v
     * @param v vertex
     * @return
     */
    public int getNumberNeighborVertex(int v) {
        return numberOfNeighborV[v];
    }
    /**
     * Getting next neighbor of vertex v
     * @param v vertex
     * @param neighborNumb number of neighbor of vertex v
     * @return
     */
    public int[] getNextVertex(int v, int neighborNumb) {
        int[] vertex = new int[2];
        if (numberOfNeighborV[v] > neighborNumb) {
            vertex[0] = vertices[v].edges.get(neighborNumb);
            vertex[1] = vertices[v].dist.get(neighborNumb);
        } else vertex[0] = -1;
        return vertex;
    }
}
