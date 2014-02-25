package com.taobao.teaey.backlog;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by xiaofei.wxf on 14-2-13.
 */
public class BacklogServer {
    public static void main(String[] args) throws InterruptedException, IOException {
        ServerSocket socket = new ServerSocket(8888, 2);
        Thread.sleep(10000000);
    }
}
