package socs.network.node;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import socs.network.message.LSA;
import socs.network.message.SOSPFPacket;
import socs.network.util.Configuration;
import socs.network.util.Logger;
import socs.network.util.parser.RouterCommandBaseVisitor;
import socs.network.util.parser.RouterCommandLexer;
import socs.network.util.parser.RouterCommandParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.*;
import java.util.stream.IntStream;

public class Router {
    private LinkStateDatabase lsd;

    private RouterDescription rd = new RouterDescription();

    final public List<Link> ports = new LinkedList<>();
    final private List<Link> initPorts = new LinkedList<>();
    private final RouterActionListener listener;

    private final String KEY_PROCESS_IP = "socs.network.router.processIP";
    private final String KEY_PROCESS_PORT = "socs.network.router.processPort";
    private final String KEY_SIMULATE_IP = "socs.network.router.ip";

    private Lock lsaSequenceNumberAccessLock = new ReentrantLock();
    private Iterator<Integer> lsaSequenceNumber = IntStream.iterate(0, x -> x + 1).iterator();
    private Timer lsaUpdateTimer = new Timer();
    private final int LSA_SEND_INTERVAL_MS = 1000;

    private String generateTabbing(int length) {
        if (length <= 0) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) stringBuilder.append(' ');
        return stringBuilder.toString();
    }

    private static SOSPFPacket constructLinkWeightUpdatePacket(Link link, short update) {
        SOSPFPacket packet = new SOSPFPacket();
        packet.srcProcessIP = link.router1.processIPAddress;
        packet.srcProcessPort = link.router1.processPortNumber;
        packet.srcWeight = update;
        packet.srcIP = link.router1.simulatedIPAddress;
        packet.dstIP = link.router2.simulatedIPAddress;
        packet.sospfType = SOSPFPacket.LINKSTATE_CONNECT_UPDATE;
        packet.updatedWeight = update;
        return packet;
    }

    private static SOSPFPacket constructDisconnectPacket(Link link) {
        SOSPFPacket packet = new SOSPFPacket();
        packet.srcProcessIP = link.router1.processIPAddress;
        packet.srcProcessPort = link.router1.processPortNumber;
        packet.srcWeight = link.weight;
        packet.srcIP = link.router1.simulatedIPAddress;
        packet.dstIP = link.router2.simulatedIPAddress;
        packet.sospfType = SOSPFPacket.LINKSTATE_DISCONNECT;
        return packet;
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

    private static SOSPFPacket constructLSAPacket(LSA updateLinks, Link link, List<Link> ports,
                                                  RouterDescription routerDescription, int LSASequenceNumber) {
        SOSPFPacket packet = new SOSPFPacket();

        if (updateLinks == null) {
            packet.lsa = new LSA();
            packet.lsa.lsaSeqNumber = LSASequenceNumber;
            packet.lsa.linkStateID = routerDescription.simulatedIPAddress;
            for (Link linkIter : ports) {
                packet.lsa.addLink(
                        linkIter.router2.simulatedIPAddress,
                        linkIter.router2.processPortNumber,
                        linkIter.weight
                );
            }
        } else {
            packet.lsa = updateLinks;
        }

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

    private void routerSendLSAInit() {
        LSA current = new LSA();
        lsaSequenceNumberAccessLock.lock();
        final int assignedLSASequenceNumber = lsaSequenceNumber.next();
        lsaSequenceNumberAccessLock.unlock();
        current.lsaSeqNumber = assignedLSASequenceNumber;
        current.linkStateID = rd.simulatedIPAddress;
        synchronized (ports) {
            for (Link link : ports) {
                current.addLink(link.router2.simulatedIPAddress, link.router2.processPortNumber, link.weight);
            }
            lsd.addLSALink(current);
            for (Link link : ports) {
                if (link.router2.status == RouterStatus.TWO_WAY) {
                    SOSPFPacket packet = constructLSAPacket(null, link, ports, rd, assignedLSASequenceNumber);
                    try {
                        listener.sendMessage(packet, packet.neighborID);
                    } catch (IOException exception) {
                        // leap of faith
                    }
                }
            }
        }
    }

    private void routerSendLSAFollower(LSA updateLinks, String sourceSimulateIP) {
        if (!lsd.addLSALink(updateLinks)) return;

        synchronized (ports) {
            for (Link link : ports) {
                lsaSequenceNumberAccessLock.lock();
                final int assignedLSASequenceNumber = lsaSequenceNumber.next();
                lsaSequenceNumberAccessLock.unlock();
                if (sourceSimulateIP.equals(link.router2.simulatedIPAddress)) continue;
                if (link.router2.status != RouterStatus.TWO_WAY) continue;

                SOSPFPacket packet = constructLSAPacket(updateLinks, link, ports, rd, assignedLSASequenceNumber);
                try {
                    listener.sendMessage(packet, packet.neighborID);
                } catch (IOException exception) {
                    // leap of faith
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    public Router(Configuration config) {

        rd.simulatedIPAddress = config.getString(KEY_SIMULATE_IP);
        rd.processIPAddress = config.getString(KEY_PROCESS_IP);
        rd.processPortNumber = config.getShort(KEY_PROCESS_PORT);
        lsd = new LinkStateDatabase(rd, this);

        lsaUpdateTimer.schedule(new TimerTask() {
            private final int LSA_TIMEOUT_INTERVAL_MS = 2000;

            @Override
            public void run() {
                routerSendLSAInit();
                lsd.removeOutdatedLink(LSA_TIMEOUT_INTERVAL_MS);
            }
        }, 0, LSA_SEND_INTERVAL_MS);

        listener = new RouterActionListener(this, new Consumer<SOSPFPacket>() {
            @Override
            public void accept(SOSPFPacket packet) {
                String remoteSimulatedIP = packet.routerID;
                String localSimulatedIP = packet.neighborID;

                if (packet.sospfType == SOSPFPacket.HELLO_TYPE) {
                    if (!rd.simulatedIPAddress.equals(localSimulatedIP)) {
                        throw new RuntimeException();
                    }

                    Logger.getSingleton().write(String.format(
                            "[%s] received HELLO from %s;",
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
                            Logger.getSingleton().write(String.format(
                                    "[%s] set %s state to %s;",
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
                            Logger.getSingleton().write(String.format(
                                    "[%s] set %s state to %s;",
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
                    routerSendLSAFollower(packet.lsa, packet.srcIP);
                } else if (packet.sospfType == SOSPFPacket.LINKSTATE_CONNECT_UPDATE) {
                    synchronized (ports) {
                        for (Link link : ports) {
                            if (link.router2.simulatedIPAddress.equals(packet.srcIP)) {
                                link.weight = packet.updatedWeight;
                            }
                        }
                    }
                } else if (packet.sospfType == SOSPFPacket.LINKSTATE_DISCONNECT) {
                    Link linkToRemove = null;
                    synchronized (ports) {
                        for (Link link : ports) {
                            if (link.router2.simulatedIPAddress.equals(packet.srcIP)) {
                                linkToRemove = link;
                                break;
                            }
                        }
                        if (linkToRemove == null) throw new AssertionError();
                        ports.remove(linkToRemove);
                    }
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
        Logger.getSingleton().write(lsd.getShortestPath(destinationIP));
    }

    /**
     * disconnect with the router identified by the given destination ip address
     * Notice: this command should trigger the synchronization of database
     *
     * @param portNumber the port number which the link attaches at
     */
    private void processDisconnect(short portNumber) {
        Link removeLink = null;
        for (int c = 0; c < ports.size(); c++) {
            if (ports.get(c).router2.status == RouterStatus.TWO_WAY) {
                if (portNumber == 0) {
                    removeLink = ports.get(c);
                    break;
                } else {
                    portNumber = (short) (portNumber - 1);
                }
            }
        }
        if (removeLink != null) {
            SOSPFPacket packet = constructDisconnectPacket(removeLink);
            try {
                listener.sendMessage(packet, removeLink.router2.simulatedIPAddress);
            } catch (IOException exception) {
                /* leap of faith */
            }
            ports.remove(removeLink);
        } else {
            Logger.getSingleton().write("invalid port number");
        }
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
            Logger.getSingleton().write("duplicate attach -> ignored");
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
        if (ports.stream().anyMatch(link -> link.router2.simulatedIPAddress.equals(simulatedIP))) {
            for (Link link : ports) {
                if (link.router2.simulatedIPAddress.equals(simulatedIP)) {
                    assert link.router2.processIPAddress.equals(processIP);
                    assert link.router2.processPortNumber == processPort;
                    SOSPFPacket packet = constructLinkWeightUpdatePacket(link, weight);
                    try {
                        listener.sendMessage(packet, simulatedIP);
                    } catch (IOException exception) {
                        /* leap of faith */
                    }
                }
            }
        } else {
            processAttach(processIP, processPort, simulatedIP, weight);
            processStart();
        }
    }

    /**
     * output the neighbors of the routers
     */
    private void processNeighbors() {
        Function<Link, Integer> getID = link -> {
            for (int i = 0; i < ports.size(); i++) {
                if (ports.get(i) == link) return i;
            }
            return -1;
        };

        synchronized (ports) {
            for (Link link : ports.stream().filter(link -> link.router2.status == RouterStatus.TWO_WAY).collect(Collectors.toList())) {
                int pos = getID.apply(link);
                assert pos >= 0;
                Logger.getSingleton().write(String.format(
                        "[%s] Router %s @ [%s:%s] status: %s",
                        Integer.toString(pos) + generateTabbing(3 - Integer.toString(pos).length()),
                        link.router2.simulatedIPAddress + generateTabbing(15 - link.router2.simulatedIPAddress.length()),
                        link.router2.processIPAddress + generateTabbing(15 - link.router2.processIPAddress.length()),
                        link.router2.processPortNumber + generateTabbing(4 - Short.toString(link.router2.processPortNumber).length()).length(),
                        link.router2.status.toString()
                ));
            }
        }
    }

    /**
     * disconnect with all neighbors and quit the program
     */
    private void processQuit() {
        lsaUpdateTimer.cancel();
        System.exit(0);
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
            public Void visitCmdConnect(RouterCommandParser.CmdConnectContext cmdConnectContext) {
                processConnect(
                        cmdConnectContext.processIP.getText(),
                        Short.parseShort(cmdConnectContext.processPort.getText()),
                        cmdConnectContext.simlateIP.getText(),
                        Short.parseShort(cmdConnectContext.weight.getText())
                );
                return null;
            }

            @Override
            public Void visitCmdConnectFile(RouterCommandParser.CmdConnectFileContext cmdConnectFileContext) {
                String filepath = cmdConnectFileContext.path.getText();
                filepath = filepath.substring(1, filepath.length() - 1).trim();
                if (new File(filepath).exists()) {
                    Configuration configuration = new Configuration(filepath);
                    processConnect(
                            configuration.getString(KEY_PROCESS_IP),
                            configuration.getShort(KEY_PROCESS_PORT),
                            configuration.getString(KEY_SIMULATE_IP),
                            Short.parseShort(cmdConnectFileContext.weight.getText())
                    );
                }
                return null;
            }

            @Override
            public Void visitCmdDisconnect(RouterCommandParser.CmdDisconnectContext cmdDisconnectContext) {
                processDisconnect(Short.parseShort(cmdDisconnectContext.port.getText()));
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
            public Void visitCmdQuit(RouterCommandParser.CmdQuitContext cmdQuitContext) {
                processQuit();
                return null;
            }

            @Override
            public Void visitCmdDetect(RouterCommandParser.CmdDetectContext cmdDetectContext) {
                processDetect(cmdDetectContext.simulateIP.getText());
                return null;
            }

            @Override
            public Void visitCmdDebug(RouterCommandParser.CmdDebugContext cmdDebugContext) {
                switch (cmdDebugContext.select.getText()) {
                    case "info" : {
                        Logger.getSingleton().write("Simulated IP Address: " + rd.simulatedIPAddress);
                        Logger.getSingleton().write("Process IP Address: " + rd.processIPAddress);
                        Logger.getSingleton().write("Process Port Number: " + rd.processPortNumber);
                        return null;
                    }
                    case "lsd" : {
                        for (String key : lsd._store.keySet()) {
                            Logger.getSingleton().write(lsd._store.get(key).toString());
                        }
                        return null;
                    }
                }
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
            for (;;) {
                try {
                    String command = br.readLine();
                    final RouterCommandLexer lexer = new RouterCommandLexer(new ANTLRInputStream(command));
                    final RouterCommandParser parser = new RouterCommandParser(new CommonTokenStream(lexer));
                    lexer.removeErrorListeners();
                    lexer.addErrorListener(routerCommandErrorListener());
                    parser.removeErrorListeners();
                    parser.addErrorListener(routerCommandErrorListener());

                    RouterCommandParser.CommandContext commandContext = parser.command();
                    routerCommandBaseVisitor().visit(commandContext);
                } catch (ParseCancellationException exception) {
                    /* parsing error has occurred */
                    /* ignored */
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
