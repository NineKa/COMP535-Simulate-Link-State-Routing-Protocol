package socs.network.util.graph;

import org.javatuples.Pair;

import java.util.*;
import java.util.function.BinaryOperator;

public class Graph<Tag, Weight extends Comparable<Weight>> {
    protected Set<Vertex<Tag, Weight>> vertexSet = new HashSet<>();
    private final BinaryOperator<Weight> weightCalcFunction;
    private final Weight zeroWeight;

    public Graph(BinaryOperator<Weight> weightCalcFunction, Weight zeroWeight) {
        this.weightCalcFunction = weightCalcFunction;
        this.zeroWeight = zeroWeight;
    }

    protected final Vertex<Tag, Weight> selectNodeByTag(Tag tag) {
        for (Vertex<Tag, Weight> vertex : vertexSet) {
            if (vertex.getTag().equals(tag)) return vertex;
        }
        return null;
    }

    public Pair<Map<Vertex<Tag, Weight>, Weight>,
                Map<Vertex<Tag, Weight>, Vertex<Tag, Weight>>> dijkstra(Tag source) {
        Vertex<Tag, Weight> sourceVertex =
                Optional.ofNullable(selectNodeByTag(source))
                .orElseThrow(IllegalArgumentException::new);

        Set<Vertex<Tag, Weight>> searchSet = new HashSet<>(vertexSet);
        Map<Vertex<Tag, Weight>, Weight> distanceMap = new HashMap<>();
        Map<Vertex<Tag, Weight>, Vertex<Tag, Weight>> previousMap = new HashMap<>();

        for (Vertex<Tag, Weight> vertex : vertexSet) {
            distanceMap.put(vertex, null);
            previousMap.put(vertex, null);
        }
        distanceMap.put(sourceVertex, zeroWeight);
        while (!searchSet.isEmpty()) {
            Vertex<Tag, Weight> minDistanceVertex = null;
            for (Vertex<Tag, Weight> vertex : searchSet) {
                if (distanceMap.get(vertex) == null) continue;
                if (minDistanceVertex == null) {
                    minDistanceVertex = vertex;
                } else {
                    if (distanceMap.get(minDistanceVertex).compareTo(distanceMap.get(vertex)) > 0) {
                        minDistanceVertex = vertex;
                    }
                }
            }
            assert minDistanceVertex != null;
            searchSet.remove(minDistanceVertex);
            for (Pair<Vertex<Tag, Weight>, Weight> edge : minDistanceVertex.getNeighbourhood()) {
                Weight alterWeight = weightCalcFunction.apply(edge.getValue1(), distanceMap.get(minDistanceVertex));
                if (distanceMap.get(edge.getValue0()) == null) {
                    distanceMap.put(edge.getValue0(), alterWeight);
                    previousMap.put(edge.getValue0(), minDistanceVertex);
                } else if (distanceMap.get(edge.getValue0()).compareTo(alterWeight) > 0) {
                    distanceMap.put(edge.getValue0(), alterWeight);
                    previousMap.put(edge.getValue0(), minDistanceVertex);
                }
            }
        }

        return new Pair<>(distanceMap, previousMap);
    }

    public Weight getEdgeWeight(Tag source, Tag destination) {
        Vertex<Tag, Weight> sourceVertex =
                Optional.ofNullable(selectNodeByTag(source))
                        .orElseThrow(IllegalArgumentException::new);
        for (Pair<Vertex<Tag, Weight>, Weight> edge : sourceVertex.getNeighbourhood()) {
            if (edge.getValue0().getTag().equals(destination)) {
                return edge.getValue1();
            }
        }
        return null;
    }
}
