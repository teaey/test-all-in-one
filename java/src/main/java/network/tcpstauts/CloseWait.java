package network.tcpstauts;

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
    public static void main(String[] args) throws InterruptedException {
        startServer();
        Thread.sleep(1000);
        startClient();
    }


    static void startServer() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    ServerSocket server;
                    server = new ServerSocket(8888);
                    List<Socket> clients = new ArrayList<Socket>();
                    Socket client;
                    while (null != (client = server.accept())) {
                        clients.add(client);
                        System.out.println(clients.size());
                        try {
                            Thread.sleep(35000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        client.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        t.setDaemon(false);
        t.start();

    }

    static void startClient() {
        try {
            Socket s = new Socket();
            s.connect(new InetSocketAddress("localhost", 8888));
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
