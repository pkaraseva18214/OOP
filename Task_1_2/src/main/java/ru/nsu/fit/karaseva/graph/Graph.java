package ru.nsu.fit.karaseva.graph;

/**
 * A class that implements the creation of a graph and some methods of working with it.
 */
public class Graph {

    private int numberVertices;
    public NeighborVertices[] vertices;

    /**
     * Graph implementation method. If null is passed as a parameter "arr" to the method,
     * then the graph simply will not contain any edges. If null passed as an element of
     * the array of edges of the graph, then this edge will not be included in graph.
     * @param n number of vertices
     * @param arr array of edges
     */
    public Graph(int n, Edge[] arr) {
        numberVertices = n;
        vertices = new NeighborVertices[n];
        for (int i = 0; i < n; i++) {
            vertices[i] = new NeighborVertices();
        }
        if (arr != null)
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == null) {
                    continue;
                }
                addEdge(arr[i].getVertexFrom(), arr[i].getVertexTo(), arr[i].getDistance());
            }
    }

    private void addEdge(int v1, int v2, int d) {
        if (v1 < numberVertices || v2 < numberVertices) {
            vertices[v1].edges.add(v2);
            vertices[v1].dist.add(d);
        } else {
            System.out.println("Number of vertices more than expected.\n");
        }
    }

    /**
     * Getting number of vertices in the graph
     * @return number of vertices in graph
     */
    public int getNumberVertices() {
        return numberVertices;
    }

    /**
     * Method for getting number of neighbor vertex v
     * @param v vertex
     * @return number of neighboring vertices
     */
     public int getNumberOfNeighboringVertices(int v) {
         return vertices[v].edges.size();
    }

    /**
     * This method returns array of 2 where 1st element is number of next vertex
     * and 2nd is distance to this vertex.
     * @param v vertex
     * @param numberOfNeighbors number of neighbor of vertex v
     * @return array of two elements, first is number of the next vertex
     *         and second is distance to that vertex.
     */
    public int[] getNextVertexAndItsDistance(int v, int numberOfNeighbors) {
        int[] vertex = new int[2];
        if (getNumberOfNeighboringVertices(v) > numberOfNeighbors) {
            vertex[0] = vertices[v].edges.get(numberOfNeighbors);
            vertex[1] = vertices[v].dist.get(numberOfNeighbors);
        } else {
            vertex[0] = -1;
        }
        return vertex;
    }

}
