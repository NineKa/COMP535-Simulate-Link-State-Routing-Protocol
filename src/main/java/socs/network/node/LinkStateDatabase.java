package socs.network.node;

import socs.network.message.LSA;
import socs.network.message.LinkDescription;

import java.util.HashMap;
import java.util.Date;
public class LinkStateDatabase {

    //linkID => LSAInstance
    HashMap<String, LSA> _store = new HashMap<String, LSA>();

    private RouterDescription rd = null;

    public LinkStateDatabase(RouterDescription routerDescription) {
        rd = routerDescription;
        LSA l = initLinkStateDatabase();
        _store.put(l.linkStateID, l);
    }

    //initialize the linkstate database by adding an entry about the router itself
    private LSA initLinkStateDatabase() {
        LSA lsa = new LSA();
        lsa.linkStateID = rd.simulatedIPAddress;
        lsa.lsaSeqNumber = Integer.MIN_VALUE;
        LinkDescription ld = new LinkDescription();
        ld.link_id = rd.simulatedIPAddress;
        ld.port_num = -1;
        ld.link_weight = 0;
        lsa.links.add(ld);
        return lsa;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (LSA lsa : _store.values()) {
            sb.append(lsa.linkStateID).append("(" + lsa.lsaSeqNumber + ")").append(":\t");
            for (LinkDescription ld : lsa.links) {
                sb.append(ld.link_id).append(",").append(ld.port_num).append(",").
                        append(ld.link_weight).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public synchronized boolean add_lsa_link(LSA add){
        LSA link_state = _store.get(add.linkStateID);
        add.timestamp = new Date().getTime();
        if(link_state == null){
            _store.put(add.linkStateID, add);
        }else if(link_state.lsaSeqNumber >= add.lsaSeqNumber){
            return false;
        }else{
            _store.put(add.linkStateID, add);
        }
        return true;
    }

    public synchronized void remove_outdated_link(int elapsed_time){
        long current = new Date().getTime();
        for(String key : _store.keySet()){
            if(_store.get(key).timestamp + elapsed_time < current){
                _store.remove(key);
            }
        }
    }

    public synchronized String get_shortest_path(String target){
        lsa_graph graph = new lsa_graph();
        graph.build_graph(_store);
        graph.djistra_cost(rd.simulatedIPAddress);
        return graph.get_shortest_path(rd.simulatedIPAddress , target);
    }
}
