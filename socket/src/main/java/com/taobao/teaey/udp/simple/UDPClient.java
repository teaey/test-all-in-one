package com.taobao.teaey.udp.simple;

import java.net.*;

/**
 * Created by xiaofei.wxf on 14-2-10.
 */
public class UDPClient {
    public static void main(String[] args) throws UnknownHostException {

        byte[] req = "我是请求".getBytes();
        try {
            DatagramSocket cli = new DatagramSocket(8889);
            DatagramPacket packet = new DatagramPacket(req, req.length, InetAddress.getByName("localhost"), 8888);
            DatagramPacket response = new DatagramPacket(new byte[1024], 0, 1024);
            while(true){
                Utils.send(cli, packet);
                System.out.println(response.getAddress() + ":" + response.getPort() + "->" + new String(Utils.recv(cli, response)));
                Thread.sleep(1000);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
