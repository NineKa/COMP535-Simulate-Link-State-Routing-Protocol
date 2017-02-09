package socs.network.node;

import org.antlr.v4.runtime.*;
import socs.network.message.SOSPFPacket;
import socs.network.util.Configuration;
import socs.network.util.parser.RouterCommandBaseListener;
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


public class Router {

    protected LinkStateDatabase lsd;

    private RouterDescription rd = new RouterDescription();

    final private List<Link> ports = new LinkedList<>();
    final private List<Link> initPorts = new LinkedList<>();
    private final RouterActionListener listener;

    private final String KEY_PROCESS_IP = "socs.network.router.processIP";
    private final String KEY_PROCESS_PORT = "socs.network.router.processPort";
    private final String KEY_SIMULATE_IP = "socs.network.router.ip";

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

    @SuppressWarnings("deprecation")
    public Router(Configuration config) {

        rd.simulatedIPAddress = config.getString(KEY_SIMULATE_IP);
        rd.processIPAddress = config.getString(KEY_PROCESS_IP);
        rd.processPortNumber = config.getShort(KEY_PROCESS_PORT);
        lsd = new LinkStateDatabase(rd);
        listener = new RouterActionListener(this, new Consumer<SOSPFPacket>() {
            @Override
            public void accept(SOSPFPacket packet) {
                if (packet.sospfType == SOSPFPacket.HELLO_TYPE) {
                    String remoteSimulatedIP = packet.routerID;
                    String localSimulatedIP = packet.neighborID;
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
                    // IGNORE
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

    public void terminal() {
        try {
            InputStreamReader isReader = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isReader);
            do {
                String command = br.readLine();
                final RouterCommandLexer lexer = new RouterCommandLexer(new ANTLRInputStream(command));
                final RouterCommandParser parser = new RouterCommandParser(new CommonTokenStream(lexer));
                lexer.removeErrorListeners();
                parser.removeErrorListeners();
                parser.addParseListener(new RouterCommandBaseListener() {
                    @Override
                    public void exitCmdAttach(RouterCommandParser.CmdAttachContext cmdAttachContext) {
                        processAttach(
                                cmdAttachContext.processIP.getText(),
                                Short.parseShort(cmdAttachContext.processPort.getText()),
                                cmdAttachContext.simulateIP.getText(),
                                Short.parseShort(cmdAttachContext.weight.getText())
                        );
                    }

                    @Override
                    public void exitCmdAttachFile(RouterCommandParser.CmdAttachFileContext cmdAttachFileContext) {
                        String filepath = cmdAttachFileContext.path.getText();
                        filepath = filepath.substring(1, filepath.length() - 1).trim();
                        if (!new File(filepath).exists()) {
                            return;
                        }
                        Configuration configuration = new Configuration(filepath);
                        processAttach(
                                configuration.getString(KEY_PROCESS_IP),
                                configuration.getShort(KEY_PROCESS_PORT),
                                configuration.getString(KEY_SIMULATE_IP),
                                Short.parseShort(cmdAttachFileContext.weight.getText())
                        );
                    }

                    @Override
                    public void exitCmdStart(RouterCommandParser.CmdStartContext cmdStartContext) {
                        processStart();
                    }

                    @Override
                    public void exitCmdNeighbors(RouterCommandParser.CmdNeighborsContext cmdNeighborsContext) {
                        processNeighbors();
                    }

                    @Override
                    public void exitCmdExit(RouterCommandParser.CmdExitContext ctx) {
                        System.exit(0);
                    }
                });
                parser.command();
            } while (true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}