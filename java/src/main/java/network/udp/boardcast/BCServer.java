package network.udp.boardcast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by xiaofei.wxf on 14-2-10.
 */
public class BCServer {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(8888);
            DatagramPacket packet = new DatagramPacket(new byte[1024], 0, 1024);
            while (true) {
                try {
                    socket.receive(packet);
                    System.out.println(packet.getAddress() + ":" + new String(packet.getData(), 0, packet.getLength()));
                    packet.setLength(1024);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
