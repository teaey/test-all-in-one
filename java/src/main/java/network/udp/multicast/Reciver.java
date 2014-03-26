package network.udp.multicast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by xiaofei.wxf on 14-2-10.
 */
public class Reciver {
    final DatagramSocket socket;
    final Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            DatagramPacket p = new DatagramPacket(new byte[1024], 1024);
            while (true) {
                try {
                    socket.receive(p);
                    System.out.println(new String(p.getData(), 0, p.getLength()));
                    p.setLength(1024);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }, "reciver");

    public Reciver(DatagramSocket socket) {
        this.socket = socket;
        this.t.start();
    }
}
