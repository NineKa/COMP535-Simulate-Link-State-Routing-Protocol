package socs.network.node;
import socs.network.message.*;
import java.util.HashMap;
import java.util.*;

/**
 * Created by Xiru on 3/1/2017.
 */
public class lsa_graph{
    HashMap<String, lsa_link> link_table;
    HashMap<String, lsa_node> node_table;
    private class lsa_link{
        lsa_node node_1;
        lsa_node node_2;
        int link_weight;
        public lsa_link(lsa_node n1, lsa_node n2, int weight){
            node_1 = n1;
            node_2 = n2;
            link_weight = weight;
        }

        public lsa_node get_other_node(lsa_node source){
            if(node_1 == source){
                return node_2;
            }else{
                return node_1;
            }
        }
    }

    private class lsa_node{
        String node_id;
        int cost;
        LinkedList<lsa_link> neighbor_links;
        lsa_link opt_source_link;
        public lsa_node(String id){
            neighbor_links = new LinkedList<>();
            node_id = id;
            cost = Integer.MAX_VALUE;
            opt_source_link = null;
        }
        public void add_link(lsa_link l){
            neighbor_links.add(l);
        }

        public List<lsa_node> get_neighbors(){
            List<lsa_node> ret = new LinkedList<>();
            for(lsa_link l : neighbor_links){
                if(l.node_1 == this){
                    ret.add(l.node_2);
                }else{
                    ret.add(l.node_1);
                }
            }
            return ret;
        }

        public void update_cost(lsa_node source){
            for(lsa_link l: neighbor_links){
                if(l.node_1 == source || l.node_2 == source){
                    if(source.cost + l.link_weight < cost){
                        cost = source.cost + l.link_weight;
                        opt_source_link = l;
                    }
                    break;
                }
            }
        }
    }

    public lsa_link get_link(lsa_node n1, lsa_node n2){
        if(link_table.get(n1.node_id + n2.node_id) != null){
            return link_table.get(n1.node_id + n2.node_id);
        }
        if(link_table.get(n2.node_id + n1.node_id) != null){
            return link_table.get(n2.node_id + n1.node_id);
        }
        return null;
    }

    public synchronized void build_graph(HashMap<String, LSA> raw_table){
        link_table = new HashMap<>();
        node_table = new HashMap<>();
        for(String key: raw_table.keySet()){
            node_table.put(key, new lsa_node(key));
        }
        for(String key: raw_table.keySet()){
            for(LinkDescription l : raw_table.get(key).links){
                if(get_link(node_table.get(key), node_table.get(l.link_id)) == null){
                    link_table.put(key + l.link_id,new lsa_link(node_table.get(key), node_table.get(l.link_id), l.link_weight));
                    node_table.get(key).add_link(link_table.get(key + l.link_id));
                    node_table.get(l.link_id).add_link(link_table.get(key + l.link_id));
                }
            }
        }
    }

    public synchronized void djistra_cost(String node_id) {
        HashMap<String, lsa_node> node_list = new HashMap<>();
        HashMap<String, lsa_node> remove_list = new HashMap<>();
        List <lsa_node> temp = new LinkedList<>();
        int min_cost;
        lsa_node min_node = null;

        node_table.get(node_id).cost = 0;
        node_list.put(node_id, node_table.get(node_id));
        while(node_list.size() > 0){
            min_cost = Integer.MAX_VALUE;
            min_node = null;
            for(lsa_node n : node_list.values()) {
                for (lsa_node neighbor : n.get_neighbors()) {
                    if (remove_list.get(neighbor.node_id) == null) {
                        neighbor.update_cost(n);
                        if (min_cost > neighbor.cost && node_list.get(neighbor.node_id) == null) {
                            min_cost = neighbor.cost;
                            min_node = neighbor;
                        }
                    }
                }
            }
            temp = new LinkedList<>();
            for(lsa_node n : node_list.values()) {
                boolean flag = true;
                for (lsa_node neighbor : n.get_neighbors()) {
                    if (node_list.get(neighbor.node_id) == null && remove_list.get(neighbor.node_id) == null) {
                        flag = false;
                        break;
                    }
                }
                if(flag) {
                    temp.add(n);
                }
            }
            for(lsa_node n: temp){
                node_list.remove(n.node_id);
                remove_list.put(n.node_id, n);
            }
            if(min_node != null) {
                node_list.put(min_node.node_id, min_node);
            }
        }
    }

    public synchronized String get_shortest_path(String source_id, String target_id){
        lsa_node source = node_table.get(source_id);
        lsa_node target = node_table.get(target_id);
        List<lsa_node> list = new LinkedList<>();
        String ret = "";
        lsa_node current = target;
        for(;;){
            list.add(current);
            if(current == source){
                break;
            }
            current = current.opt_source_link.get_other_node(current);
        }
        for(int i = list.size() - 1; i >= 0; i--){
            if(list.get(i).opt_source_link != null) {
                ret += " ->(" + list.get(i).opt_source_link.link_weight + ") " + list.get(i).node_id;
            }else{
                ret += list.get(i).node_id;
            }
        }
        return ret;
    }
}