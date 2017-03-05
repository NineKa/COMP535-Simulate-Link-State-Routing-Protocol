package socs.network.node;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import socs.network.message.SOSPFPacket;
import socs.network.util.Configuration;
import socs.network.util.parser.RouterCommandBaseVisitor;
import socs.network.util.parser.RouterCommandLexer;
import socs.network.util.parser.RouterCommandParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import socs.network.message.LSA;


public class Router {

    protected LinkStateDatabase lsd;

    private RouterDescription rd = new RouterDescription();

    final private List<Link> ports = new LinkedList<>();
    final private List<Link> initPorts = new LinkedList<>();
    private final RouterActionListener listener;

    private final String KEY_PROCESS_IP = "socs.network.router.processIP";
    private final String KEY_PROCESS_PORT = "socs.network.router.processPort";
    private final String KEY_SIMULATE_IP = "socs.network.router.ip";

    private int lsa_seq_num = Integer.MIN_VALUE;
    private lsa_timer timer;

    private String generateTabbing(int length) {
        if (length <= 0) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) stringBuilder.append(' ');
        return stringBuilder.toString();
    }

    private static SOSPFPacket constructHandshakePacket(Link link) {
        SOSPFPacket packet = new SOSPFPacket();
        packet.srcProcessIP = link.router1.processIPAddress;
        packet.srcProcessPort = link.router1.processPortNumber;
        packet.srcWeight = link.weight;
        packet.srcIP = link.router1.simulatedIPAddress;
        packet.dstIP = link.router2.simulatedIPAddress;
        packet.sospfType = SOSPFPacket.HELLO_TYPE;
        packet.routerID = link.router1.simulatedIPAddress;
        packet.neighborID = link.router2.simulatedIPAddress;
        return packet;
    }

    private static SOSPFPacket construct_lsa_packet(LSA update_links, Link link, List<Link> ports, RouterDescription rd, int lsa_seq_num) {
        SOSPFPacket packet = new SOSPFPacket(update_links, ports, rd, lsa_seq_num);
        packet.srcProcessIP = link.router1.processIPAddress;
        packet.srcProcessPort = link.router1.processPortNumber;
        packet.srcWeight = link.weight;
        packet.srcIP = link.router1.simulatedIPAddress;
        packet.dstIP = link.router2.simulatedIPAddress;
        packet.sospfType = SOSPFPacket.LINKSTATE_UPDATE_TYPE;
        packet.routerID = link.router1.simulatedIPAddress;
        packet.neighborID = link.router2.simulatedIPAddress;
        return packet;
    }

    //Sends lsa to all neighboring nodes
    public void router_send_lsa_init(){
        LSA current = new LSA();
        current = new LSA();
        current.lsaSeqNumber = lsa_seq_num;
        current.linkStateID = rd.simulatedIPAddress;
        for (Link l : ports) {
            current.add_link(l.router2.simulatedIPAddress, l.router2.processPortNumber, l.weight);
        }
        lsd.add_lsa_link(current);
        for(Link l: ports){
            if(l.router2.status == RouterStatus.TWO_WAY) {
                SOSPFPacket send_packets = Router.construct_lsa_packet(null, l, ports, rd, lsa_seq_num);
                try {
                    listener.sendMessage(send_packets, send_packets.neighborID);
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }
            }
        }
        lsa_seq_num++;
    }
    //Sends lsa to all neighboring nodes
    public void router_send_follower(LSA update_links, String source_sim_ip){
        if(!lsd.add_lsa_link(update_links)){
            return;
        }
        for(Link l: ports){
            if(l.router2.status == RouterStatus.TWO_WAY && !source_sim_ip.equals(l.router2.simulatedIPAddress)) {
                SOSPFPacket send_packets = Router.construct_lsa_packet(update_links, l, ports, rd, lsa_seq_num);
                try {
                    listener.sendMessage(send_packets, send_packets.neighborID);
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }
            }
        }
        lsa_seq_num++;
    }

    @SuppressWarnings("deprecation")
    public Router(Configuration config) {
        rd.simulatedIPAddress = config.getString(KEY_SIMULATE_IP);
        rd.processIPAddress = config.getString(KEY_PROCESS_IP);
        rd.processPortNumber = config.getShort(KEY_PROCESS_PORT);
        lsd = new LinkStateDatabase(rd);

        timer = new lsa_timer(this);
        new Thread(timer).start();

        listener = new RouterActionListener(this, new Consumer<SOSPFPacket>() {
            @Override
            public void accept(SOSPFPacket packet) {
                String remoteSimulatedIP = packet.routerID;
                String localSimulatedIP = packet.neighborID;
                if (packet.sospfType == SOSPFPacket.HELLO_TYPE) {
                    if (!rd.simulatedIPAddress.equals(localSimulatedIP)) {
                        throw new RuntimeException();
                    }

                    System.out.println(String.format(
                            ": [%s] received HELLO from %s;",
                            localSimulatedIP + generateTabbing(15 - localSimulatedIP.length()),
                            remoteSimulatedIP
                    ));

                    Link link = null;

                    synchronized (ports) {
                        for (Link linkIterator : ports) {
                            if (linkIterator.router2.simulatedIPAddress.equals(remoteSimulatedIP)) {
                                link = linkIterator;
                                break;
                            }
                        }
                        if (link == null) {
                            RouterDescription localRouter = rd;
                            RouterDescription remoteRouter = new RouterDescription();
                            remoteRouter.processIPAddress = packet.srcProcessIP;
                            remoteRouter.processPortNumber = packet.srcProcessPort;
                            remoteRouter.simulatedIPAddress = remoteSimulatedIP;
                            remoteRouter.status = RouterStatus.UNKNOWN;
                            Link newLink = new Link(localRouter, remoteRouter, packet.srcWeight);
                            ports.add(newLink);
                            link = newLink;
                        }
                    }

                    synchronized (link) {
                        if (link.router2.status == RouterStatus.UNKNOWN) {
                            link.router2.status = RouterStatus.INIT;
                            System.out.println(String.format(
                                    ": [%s] set %s state to %s;",
                                    localSimulatedIP + generateTabbing(15 - localSimulatedIP.length()),
                                    remoteSimulatedIP,
                                    link.router2.status.toString()
                            ));

                            SOSPFPacket replyPacket = Router.constructHandshakePacket(link);
                            try {
                                listener.sendMessage(replyPacket, remoteSimulatedIP);
                            } catch (IOException exception) {
                                throw new RuntimeException(exception);
                            }
                        } else if (link.router2.status == RouterStatus.INIT) {
                            link.router2.status = RouterStatus.TWO_WAY;
                            System.out.println(String.format(
                                    ": [%s] set %s state to %s;",
                                    localSimulatedIP + generateTabbing(15 - localSimulatedIP.length()),
                                    remoteSimulatedIP,
                                    link.router2.status.toString()
                            ));

                            synchronized (initPorts) {
                                if (!initPorts.contains(link)) {
                                    return;
                                }
                            }

                            SOSPFPacket replyPacket = Router.constructHandshakePacket(link);
                            try {
                                listener.sendMessage(replyPacket, remoteSimulatedIP);
                            } catch (IOException exception) {
                                throw new RuntimeException(exception);
                            }
                        } else {
                            throw new AssertionError();
                        }
                    }

                } else if (packet.sospfType == SOSPFPacket.LINKSTATE_UPDATE_TYPE) {
                    router_send_follower(packet.lsaArray, packet.srcIP);
                } else {
                    // IGNORE
                }
            }
        });
        listener.start();
    }

    public RouterDescription getRouterDescription() {
        return rd;
    }

    public List<Link> getLinks() {
        return Collections.unmodifiableList(ports);
    }

    /**
     * output the shortest path to the given destination ip
     * <p/>
     * format: source ip address  -> ip address -> ... -> destination ip
     *
     * @param destinationIP the ip adderss of the destination simulated router
     */
    private void processDetect(String destinationIP) {

    }

    /**
     * disconnect with the router identified by the given destination ip address
     * Notice: this command should trigger the synchronization of database
     *
     * @param portNumber the port number which the link attaches at
     */
    private void processDisconnect(short portNumber) {

    }

    /**
     * attach the link to the remote router, which is identified by the given simulated ip;
     * to establish the connection via socket, you need to identify the process IP and process Port;
     * additionally, weight is the cost to transmitting data through the link
     * <p/>
     * NOTE: this command should not trigger link database synchronization
     */
    private void processAttach(String processIP, short processPort,
                               String simulatedIP, short weight) {
        if (ports.stream().anyMatch(router -> (router.router2.processIPAddress.equals(processIP) &&
                        router.router2.processPortNumber == processPort) ||
                        router.router2.simulatedIPAddress.equals(simulatedIP)
        )) {
            System.out.println(": duplicate attach -> ignored");
        }

        RouterDescription localRouter = this.rd;
        RouterDescription remoteRouter = new RouterDescription();
        remoteRouter.processIPAddress = processIP;
        remoteRouter.processPortNumber = processPort;
        remoteRouter.simulatedIPAddress = simulatedIP;
        remoteRouter.status = RouterStatus.UNKNOWN;

        Link link = new Link(localRouter, remoteRouter, weight);
        synchronized (ports) {
            ports.add(link);
        }
        synchronized (initPorts) {
            initPorts.add(link);
        }
    }

    /**
     * broadcast Hello to neighbors
     */
    private void processStart() {
        List<Link> newRouters;
        synchronized (ports) {
            newRouters = Collections.unmodifiableList(ports.stream()
                    .filter(router -> router.router2.status != RouterStatus.TWO_WAY)
                    .collect(Collectors.toList())
            );
        }

        for (Link linkIterator : newRouters) {
            linkIterator.router2.status = RouterStatus.INIT;
            SOSPFPacket replyPacket = Router.constructHandshakePacket(linkIterator);
            try {
                listener.sendMessage(replyPacket, replyPacket.dstIP);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    /**
     * attach the link to the remote router, which is identified by the given simulated ip;
     * to establish the connection via socket, you need to identity the process IP and process Port;
     * additionally, weight is the cost to transmitting data through the link
     * <p/>
     * This command does trigger the link database synchronization
     */
    private void processConnect(String processIP, short processPort,
                                String simulatedIP, short weight) {

    }

    /**
     * output the neighbors of the routers
     */
    private void processNeighbors() {
        synchronized (ports) {
            for (Link link : ports.stream().filter(link -> link.router2.status == RouterStatus.TWO_WAY).collect(Collectors.toList())) {
                String routerString = String.format(
                        ": Router %s @ [%s:%s] status: %s",
                        link.router2.simulatedIPAddress + generateTabbing(15 - link.router2.simulatedIPAddress.length()),
                        link.router2.processIPAddress + generateTabbing(15 - link.router2.processIPAddress.length()),
                        link.router2.processPortNumber + generateTabbing(4 - Short.toString(link.router2.processPortNumber).length()).length(),
                        link.router2.status.toString()
                );
                System.out.println(routerString);
            }
        }
    }

    /**
     * disconnect with all neighbors and quit the program
     */
    private void processQuit() {

    }

    private void detect_path(String simulated_ip){
        System.out.println(lsd.get_shortest_path(simulated_ip));
    }

    private RouterCommandBaseVisitor<Void> routerCommandBaseVisitor() {
        return new RouterCommandBaseVisitor<Void>() {
            @Override
            public Void visitCmdAttach(RouterCommandParser.CmdAttachContext cmdAttachContext) {
                processAttach(
                        cmdAttachContext.processIP.getText(),
                        Short.parseShort(cmdAttachContext.processPort.getText()),
                        cmdAttachContext.simulateIP.getText(),
                        Short.parseShort(cmdAttachContext.weight.getText())
                );
                return null;
            }

            @Override
            public Void visitCmdAttachFile(RouterCommandParser.CmdAttachFileContext cmdAttachFileContext) {
                String filepath = cmdAttachFileContext.path.getText();
                filepath = filepath.substring(1, filepath.length() - 1).trim();
                if (new File(filepath).exists()) {
                    Configuration configuration = new Configuration(filepath);
                    processAttach(
                            configuration.getString(KEY_PROCESS_IP),
                            configuration.getShort(KEY_PROCESS_PORT),
                            configuration.getString(KEY_SIMULATE_IP),
                            Short.parseShort(cmdAttachFileContext.weight.getText())
                    );
                }
                return null;
            }

            @Override
            public Void visitCmdStart(RouterCommandParser.CmdStartContext cmdStartContext) {
                processStart();
                return null;
            }

            @Override
            public Void visitCmdNeighbors(RouterCommandParser.CmdNeighborsContext cmdNeighborsContext) {
                processNeighbors();
                return null;
            }

            @Override
            public Void visitCmdExit(RouterCommandParser.CmdExitContext cmdExitContext) {
                System.exit(0);
                return null;
            }

        };
    }

    private ANTLRErrorListener routerCommandErrorListener() {
        return new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg,
                                    RecognitionException e) throws ParseCancellationException {
                System.out.println(String.format(
                        "Syntax error at character %d, %s",
                        charPositionInLine,
                        msg
                ));
                throw new ParseCancellationException();
            }
        };
    }

    public void terminal() {
        try {
            InputStreamReader isReader = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isReader);
            do {
                try {
                    String command = br.readLine();
                    if(command.indexOf("detect") == -1) {
                        final RouterCommandLexer lexer = new RouterCommandLexer(new ANTLRInputStream(command));
                        final RouterCommandParser parser = new RouterCommandParser(new CommonTokenStream(lexer));
                        lexer.removeErrorListeners();
                        lexer.addErrorListener(routerCommandErrorListener());
                        parser.removeErrorListeners();
                        parser.addErrorListener(routerCommandErrorListener());

                        RouterCommandParser.CommandContext commandContext = parser.command();
                        routerCommandBaseVisitor().visit(commandContext);
                    }else{
                        detect_path(command.split(" ")[1]);
                    }
                } catch (ParseCancellationException exception) {
                    /* parsing error has occurred */
                    /* ignored */
                }
            } while (true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
