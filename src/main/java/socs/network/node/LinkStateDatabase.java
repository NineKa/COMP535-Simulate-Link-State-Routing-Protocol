package socs.network.node;

import socs.network.message.LSA;
import socs.network.message.LinkDescription;
import socs.network.util.LSAGraph;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class LinkStateDatabase {

    //linkID => LSAInstance
    public HashMap<String, LSA> _store = new HashMap<String, LSA>();

    private RouterDescription rd = null;
    private Router router = null;

    public LinkStateDatabase(RouterDescription routerDescription, Router router) {
        rd = routerDescription;
        LSA l = initLinkStateDatabase();
        _store.put(l.linkStateID, l);
        this.router = router;
    }

    /**
     * output the shortest path from this router to the destination with the given IP address
     */
    String getShortestPath(String destinationIP) {
        LSAGraph lsaGraph = new LSAGraph();
        lsaGraph.build_graph(_store);
        lsaGraph.djistra_cost(rd.simulatedIPAddress);
        return lsaGraph.get_shortest_path(rd.simulatedIPAddress, destinationIP);
    }

    //initialize the linkstate database by adding an entry about the router itself
    private LSA initLinkStateDatabase() {
        LSA lsa = new LSA();
        lsa.linkStateID = rd.simulatedIPAddress;
        lsa.lsaSeqNumber = Integer.MIN_VALUE;
        LinkDescription ld = new LinkDescription(
                rd.simulatedIPAddress,
                rd.processPortNumber,
                0
        );
        lsa.links.add(ld);
        return lsa;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (LSA lsa : _store.values()) {
            sb.append(lsa.linkStateID).append("(" + lsa.lsaSeqNumber + ")").append(":\t");
            for (LinkDescription ld : lsa.links) {
                sb.append(ld.linkID).append(",").append(ld.portNumber).append(",").
                        append(ld.linkWeight).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public synchronized boolean addLSALink(LSA lsaLink) {
        long currentTimestamp = new Date().getTime();
        lsaLink.timestamp = currentTimestamp;
        if (_store.containsKey(lsaLink.linkStateID)) {
            if (_store.get(lsaLink.linkStateID).lsaSeqNumber >= lsaLink.lsaSeqNumber) {
                return false;
            } else {
                _store.put(lsaLink.linkStateID, lsaLink);
                return true;
            }
        } else {
            _store.put(lsaLink.linkStateID, lsaLink);
            return true;
        }
    }

    public synchronized int removeOutdatedLink(long time) {
        long current = new Date().getTime();
        Collection<String> removeKeySet = new HashSet<>();
        for (String key : _store.keySet()) {
            if (_store.get(key).timestamp + time < current) {
                removeKeySet.add(key);
            }
        }
        for (String keyToRemove : removeKeySet) {
            _store.remove(keyToRemove);
            Link linkToRemove = null;
            for (Link link : router.ports) {
                if (link.router2.simulatedIPAddress.equals(keyToRemove)) linkToRemove = link;
            }
            if (linkToRemove != null) {
                router.ports.remove(linkToRemove);
            }
        }
        return removeKeySet.size();
    }
}
