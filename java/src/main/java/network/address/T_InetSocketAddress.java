package network.address;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author xiaofei.wxf
 */
public class T_InetSocketAddress {
    public static void main(String[] args) throws IOException {
        InetSocketAddress a1 = InetSocketAddress.createUnresolved("www.baidu.com", 80);
        InetSocketAddress a2 = new InetSocketAddress("www.baidu.com", 80);

        System.out.println(a1);
        //www.baidu.com:80

        System.out.println(a2);
        //www.baidu.com/115.239.210.27:80
    }
}
