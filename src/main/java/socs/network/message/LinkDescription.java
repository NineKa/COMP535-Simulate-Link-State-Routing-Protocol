package socs.network.message;

import java.io.Serializable;

public class LinkDescription implements Serializable {
    public String linkID;
    public int portNumber;
    public int linkWeight;

    public String toString() {
        return "<" + linkID + ", " + portNumber + ", " + linkWeight + ">";
    }

    public LinkDescription(String linkID, int portNumber, int linkWeight) {
        this.linkID = linkID;
        this.portNumber = portNumber;
        this.linkWeight = linkWeight;
    }
}
