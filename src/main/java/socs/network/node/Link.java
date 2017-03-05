package socs.network.node;

public class Link {

    public RouterDescription router1;
    public RouterDescription router2;

    public short weight = 0;

    public Link(RouterDescription r1, RouterDescription r2) {
        router1 = r1;
        router2 = r2;
    }

    public Link(RouterDescription r1, RouterDescription r2, short w) {
        router1 = r1;
        router2 = r2;
        weight = w;
    }
}
