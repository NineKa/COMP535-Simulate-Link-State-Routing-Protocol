package socs.network.message;

import socs.network.node.Link;

import java.io.Serializable;

public class LinkDescription implements Serializable {
    public String link_id;
    public int port_num;
    public int link_weight;
    public String toString() {
        return link_id + "," + port_num + "," + link_weight;
    }

    public LinkDescription(String link_id, int port_num, int link_weight){
        this.link_id = link_id;
        this.port_num = port_num;
        this.link_weight = link_weight;
    }

    public LinkDescription(){

    }
}
