package network.udp.simple;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

/**
 * Created by xiaofei.wxf on 14-2-10.
 */
public class Utils {
    final long TIMEOUT = 5000;

    public static byte[] recv(DatagramSocket socket, DatagramPacket packet) {
        final int length = packet.getLength();
        try {
            socket.receive(packet);
            byte[] ret = Arrays.copyOfRange(packet.getData(), 0, packet.getLength());
            packet.setLength(length);
            return ret;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void send(DatagramSocket socket, DatagramPacket packet) {
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
