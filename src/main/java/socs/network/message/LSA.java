package socs.network.message;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class LSA implements Serializable {

    //IP address of the router originate this LSA
    public String linkStateID;
    public int lsaSeqNumber = Integer.MIN_VALUE;
    public long timestamp = Long.MIN_VALUE;

    public LinkedList<LinkDescription> links = new LinkedList<LinkDescription>();

    @Override
    public String toString() {
        return new StringBuilder().append(linkStateID)
                .append("(" + lsaSeqNumber + ")")
                .append("->").append('{')
                .append(links.stream().map(LinkDescription::toString).collect(Collectors.joining(",")))
                .append('}').toString();
    }

    public LSA addLink(LinkDescription linkDescription) {
        links.add(linkDescription);
        return this;
    }

    public LSA addLink(String linkID, int portNumber, int linkWeight) {
        links.add(new LinkDescription(linkID, portNumber, linkWeight));
        return this;
    }
}
