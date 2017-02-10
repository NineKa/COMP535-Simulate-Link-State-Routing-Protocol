package socs.network;

import socs.network.node.Router;
import socs.network.util.Configuration;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("usage: program [router configure]");
            System.exit(1);
        }
        if (!new File(args[0]).exists()) {
            System.out.println("Configuration file not exist, abort");
        }
        Router router = new Router(new Configuration(args[0]));
        router.terminal();
        System.exit(0);
    }
}
