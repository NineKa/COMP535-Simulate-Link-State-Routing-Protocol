package socs.network.node;

import socs.network.message.SOSPFPacket;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class RouterActionListener extends Thread {
    private final Router router;
    private final ServerSocket serverSocket;

    private final String processIPAddress;
    private final int processPortNumber;

    private final Consumer<SOSPFPacket> sospfPacketConsumer;

    public volatile boolean shouldQuit = false;

    @Deprecated
    public RouterActionListener(Router router, Consumer<SOSPFPacket> handler) {
        try {
            this.router = router;
            this.processIPAddress = router.getRouterDescription().processIPAddress;
            this.processPortNumber = router.getRouterDescription().processPortNumber;
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(processIPAddress, processPortNumber));
            sospfPacketConsumer = handler;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void sendMessage(SOSPFPacket packet, String simulatedIP) throws IOException {
        if (packet == null) throw new NullPointerException();
        if (simulatedIP == null) throw new NullPointerException();
        String targetIPAddress = null;
        int targetPortNumber = -1;
        RouterStatus routerStatus = null;
        for (Link link : router.getLinks()) {
            if (link.router2.simulatedIPAddress.equals(simulatedIP)) {
                targetIPAddress = link.router2.processIPAddress;
                targetPortNumber = link.router2.processPortNumber;
                routerStatus = link.router2.status;
                break;
            }
        }
        if (targetIPAddress == null || targetPortNumber == -1 || routerStatus == null)
            throw new IllegalArgumentException();

        Socket socket = new Socket(targetIPAddress, targetPortNumber);
        ObjectOutput objectOutput = new ObjectOutputStream(socket.getOutputStream());
        objectOutput.writeObject(packet);
        objectOutput.flush();
        objectOutput.close();
        socket.close();
    }

    @Override
    public void run() {
        try {
            for (;;) {
                Socket socket = serverSocket.accept();
                if (socket == null) {

                } else {
                    Thread workerThread = new Thread(() -> {
                        try {
                            ObjectInput objectInput = new ObjectInputStream(socket.getInputStream());
                            SOSPFPacket packet = (SOSPFPacket) objectInput.readObject();
                            sospfPacketConsumer.accept(packet);
                        } catch (IOException|ClassNotFoundException exception) {
                            throw new RuntimeException(exception);
                        }
                    });
                    workerThread.start();
                }
                if (shouldQuit) return;
                Thread.yield();
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
