package ru.nsu.fit.karaseva.graph;

import java.util.*;

/**
 * A class that contains neighboring vertices of the graph and the distance to them.
 */
public class NeighborVertices{

    public List<Neighbor> neighbors = new ArrayList<Neighbor>();
}

class Neighbor{
    int edge;
    int dist;
}
