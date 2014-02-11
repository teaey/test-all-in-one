package com.taobao.teaey.udp.multicast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;

/**
 * Created by xiaofei.wxf on 14-2-10.
 */
public class MCClient {
    public static void main(String[] args) throws Exception {
        InetAddress addr = InetAddress.getByName("224.0.0.1");
        MulticastSocket socket = new MulticastSocket(8001);
        socket.joinGroup(addr);
        socket.setTimeToLive(1);
        socket.setLoopbackMode(false);
        socket.setReuseAddress(false);
        DatagramPacket p = new DatagramPacket("你好".getBytes(), "你好".getBytes().length, addr, 8001);

        new Reciver(socket);

        while (true) {
            socket.send(p);
            Thread.sleep(3000);
        }
    }
}
