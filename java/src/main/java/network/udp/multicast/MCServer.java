package network.udp.multicast;

import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by xiaofei.wxf on 14-2-10.
 */
public class MCServer {
    public static void main(String[] args) throws Exception {
        InetAddress addr = InetAddress.getByName("224.0.0.1");
        MulticastSocket socket = new MulticastSocket(8008);
        socket.joinGroup(addr);
        new Reciver(socket);

    }
}
