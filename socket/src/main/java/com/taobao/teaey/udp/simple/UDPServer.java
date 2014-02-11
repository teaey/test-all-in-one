package com.taobao.teaey.udp.simple;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by xiaofei.wxf on 14-2-10.
 */
public class UDPServer {
    public static void main(String[] args) {
        try {
            DatagramSocket ser = new DatagramSocket(8888);
            DatagramPacket packet = new DatagramPacket(new byte[1024], 0, 3);
            while(true){
                System.out.println(packet.getAddress() + ":" + packet.getPort() + "->" + new String(Utils.recv(ser, packet)));
                packet.setAddress(InetAddress.getByName("localhost"));
                packet.setPort(8889);
                Utils.send(ser, packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
