package socs.network.message;

import java.io.*;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import socs.network.node.*;
import java.util.List;

public class SOSPFPacket implements Serializable {

    //for inter-process communication
    public String srcProcessIP;
    public short srcProcessPort;
    public short srcWeight;

    //simulated IP address
    public String srcIP;
    public String dstIP;

    public static final short HELLO_TYPE = 0;
    public static final short LINKSTATE_UPDATE_TYPE = 1;

    //common header
    public short sospfType; //0 - HELLO, 1 - LinkState Update
    public String routerID;

    //used by HELLO message to identify the sender of the message
    //e.g. when router A sends HELLO to its neighbor, it has to fill this field with its own
    //simulated IP address
    public String neighborID; //neighbor's simulated IP address

    //used by LSAUPDATE
    public LSA lsaArray = null;

    public SOSPFPacket(LSA update_links, List<Link> ports, RouterDescription current, int seq_num){
        if(update_links == null) {
            lsaArray = new LSA();
            lsaArray.lsaSeqNumber = seq_num;
            lsaArray.linkStateID = current.simulatedIPAddress;
            for (Link l : ports) {
                lsaArray.add_link(l.router2.simulatedIPAddress, l.router2.processPortNumber, l.weight);
            }
        }else{
            lsaArray = update_links;
        }
    }

    public SOSPFPacket(){

    }
}
