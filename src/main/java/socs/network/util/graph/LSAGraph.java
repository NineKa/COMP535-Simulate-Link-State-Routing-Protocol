package socs.network.util.graph;

import org.javatuples.Pair;
import socs.network.message.LSA;
import socs.network.message.LinkDescription;

import java.util.*;

public class LSAGraph extends Graph<String, Integer> {
    public LSAGraph(HashMap<String, LSA> rawMap) {
        super(Integer::sum, 0);
        for (AbstractMap.Entry<String, LSA> entry : rawMap.entrySet()) {
            vertexSet.add(new Vertex<String, Integer>() {
                private Set<Pair<Vertex<String, Integer>, Integer>> neighbourhoodSet = new HashSet<>();
                private String tag = entry.getKey();

                @Override
                public String getTag() {
                    return tag;
                }

                @Override
                public Set<Pair<Vertex<String, Integer>, Integer>> getNeighbourhood() {
                    return Collections.unmodifiableSet(neighbourhoodSet);
                }

                @Override
                public void addEdge(Vertex<String, Integer> neighbourVertex, Integer edge) {
                    neighbourhoodSet.add(new Pair<>(neighbourVertex, edge));
                }

                @Override
                public void removeEdge(Vertex<String, Integer> neighbourVertex) {
                    Pair<Vertex<String, Integer>, Integer> target = null;
                    for (Pair<Vertex<String, Integer>, Integer> vertex : neighbourhoodSet) {
                        if (vertex.getValue0().equals(target)) target = vertex;
                    }
                    if (target != null) neighbourhoodSet.remove(target);
                }

                @Override
                public String toString() {
                    return String.format("<Vertex IP:%s>", tag);
                }
            });
        }
        for (AbstractMap.Entry<String, LSA> entry : rawMap.entrySet()) {
            Vertex<String, Integer> vertex = selectNodeByTag(entry.getKey());
            for (LinkDescription linkDescription : entry.getValue().links) {
                vertex.addEdge(selectNodeByTag(linkDescription.linkID), linkDescription.linkWeight);
            }
        }
    }

    public String getTraceString(String source, String destination) {
        Pair<Map<Vertex<String, Integer>, Integer>,
                Map<Vertex<String, Integer>, Vertex<String, Integer>>> dijkstraInfo = dijkstra(source);

        LinkedList<Vertex<String, Integer>> traceList = new LinkedList<>();
        Vertex<String, Integer> destinationVertex = selectNodeByTag(destination);
        traceList.add(destinationVertex);
        while (!( dijkstraInfo.getValue1().get(traceList.getFirst()) == null                   ||
                  dijkstraInfo.getValue1().get(traceList.getFirst()).getTag().equals(source)     )) {
            traceList.addFirst(dijkstraInfo.getValue1().get(traceList.getFirst()));
        }

        if (dijkstraInfo.getValue1().get(traceList.getFirst()) == null) {
            return "Unable to find a route";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(source);
            Vertex<String, Integer> previousVertex = selectNodeByTag(source);
            for (Vertex<String, Integer> vertex : traceList) {
                stringBuilder.append(String.format("->(%d) %s",
                        getEdgeWeight(previousVertex.getTag(), vertex.getTag()),
                        vertex.getTag()
                ));
                previousVertex = vertex;
            }
            return stringBuilder.toString();
        }
    }


}
