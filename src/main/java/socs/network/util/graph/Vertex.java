package socs.network.util.graph;

import org.javatuples.Pair;

import java.util.Set;

public interface Vertex<Tag, Weight> {
    Tag getTag();
    Set<Pair<Vertex<Tag, Weight>, Weight>> getNeighbourhood();
    void addEdge(Vertex<Tag, Weight> neighbourVertex, Weight edge);
    void removeEdge(Vertex<Tag, Weight> neighbourVertex);
}
