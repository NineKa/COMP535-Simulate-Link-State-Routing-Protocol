package socs.network;

import socs.network.node.Router;
import socs.network.util.Configuration;

import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("usage: program [router configure]");
            System.exit(1);
        }
        Router router = new Router(new Configuration(args[0]));
        router.terminal();
        System.exit(0);
    }
}
