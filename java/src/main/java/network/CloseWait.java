package network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxf on 14-5-9.
 */
public class CloseWait {
    public static void main(String[] args) throws InterruptedException, IOException {
        startServer("localhost", 8888);
        startClient("localhost", 8888);
    }


    static void startServer(final String host, final int port) {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    ServerSocket server = new ServerSocket();
                    server.bind(new InetSocketAddress(host, port));
                    List<Socket> clients = new ArrayList<Socket>();
                    Socket client;
                    while (null != (client = server.accept())) {
                        clients.add(client);
                        //                        client.setKeepAlive(true);
                        printSockt("Server", client);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.setDaemon(false);
        t.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void startClient(final String host, final int port) {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket();
                    s.connect(new InetSocketAddress(host, port));
                    s.close();
                    printSockt("Client", s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.setDaemon(false);
        t.start();

    }

    static void printSockt(String s, Socket socket) {
        System.out.print(s + "-[Connected]=" + socket.isConnected());
        System.out.print(" [Closed]=" + socket.isClosed());
        System.out.print(" [Bound]=" + socket.isBound());
        System.out.print(" [OutputShutdown]=" + socket.isOutputShutdown());
        System.out.println(" [InputShutdown]=" + socket.isInputShutdown());
    }
}
