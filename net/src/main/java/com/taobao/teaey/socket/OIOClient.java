package com.taobao.teaey.socket;

import java.io.IOException;
import java.net.Socket;

/**
 * @author xiaofei.wxf
 */
public class OIOClient {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 8888);
        s.getInputStream().read();
    }
}
