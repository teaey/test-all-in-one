package network.socket;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author xiaofei.wxf
 */
public class OIOClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket s = new Socket("127.0.0.1", 8888);
        InputStreamReader in = new InputStreamReader(System.in);
        Thread.sleep(100000);
    }
}
