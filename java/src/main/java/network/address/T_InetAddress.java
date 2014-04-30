package network.address;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author xiaofei.wxf
 */
public class T_InetAddress {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");
        System.out.println(Arrays.toString(addresses));
        //[www.baidu.com/115.239.210.27, www.baidu.com/115.239.210.26]

        InetAddress[] addresses1 = InetAddress.getAllByName("115.239.210.26");
        System.out.println(Arrays.toString(addresses1));
        //[/115.239.210.26]

        System.out.println(InetAddress.getByName("www.baidu.com"));
        //www.baidu.com/115.239.210.27

        System.out.println(InetAddress.getLocalHost());
        //MININT-QG7RUB5/192.168.73.1

    }
}
