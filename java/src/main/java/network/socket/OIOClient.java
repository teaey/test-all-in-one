package network.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author xiaofei.wxf
 */
public class OIOClient {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("10.68.172.34", 8889);
        //        Socket s = new Socket("localhost", 8888);
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader bi = new BufferedReader(in);
        for (; ; ) {
            try {
                int i = Integer.parseInt(bi.readLine());
                s.close();
                System.out.println("i=" + i);
                i = i % 255;
                s.getOutputStream().write(i);
                s.getOutputStream().flush();
            } catch (Exception e) {
                System.out.println("eeee");
                e.printStackTrace();
                System.out.println(s.isInputShutdown());
                System.out.println(s.isOutputShutdown());
                System.out.println(s.isBound());
                System.out.println(s.isClosed());
                System.out.println(s.isConnected());
            }
        }
    }
}
