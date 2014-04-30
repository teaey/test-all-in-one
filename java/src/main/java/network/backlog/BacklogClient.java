package network.backlog;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by xiaofei.wxf on 14-2-13.
 */
public class BacklogClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        for (int i = 0; i < 10; i++) {
            System.out.println("第 " + (i + 1) + " 个链接开始");
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(8888));
            System.out.println("第 " + (i + 1) + " 个链接成功");
        }
    }
}
