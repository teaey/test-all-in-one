package com.taobao.teaey.socket;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by xiaofei.wxf on 14-1-27.
 */
public class LoopbackInterface {
    public static void main(String[] args) {
        NetworkInterface loopbackIface = null;
        try {
            for (Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
                 ifaces.hasMoreElements(); ) {

                NetworkInterface iface = ifaces.nextElement();
                if (iface.isLoopback()) {
                    // Found
                    loopbackIface = iface;
                    break;
                }
            }
            if (loopbackIface == null) {
            }
        } catch (SocketException e) {
        }

        System.out.println(loopbackIface.getDisplayName());
        for (Enumeration<InetAddress> addrs = loopbackIface.getInetAddresses();
             addrs.hasMoreElements(); ) {
            System.out.println(addrs.nextElement());
        }
    }
}
