package com.taobao.teaey.lostrpc.server;

import com.taobao.teaey.lostrpc.NettyChannelInitializer;

import java.io.IOException;

/**
 * Created by xiaofei.wxf on 14-2-13.
 */
public class ServerTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        new NettyServer(new NettyChannelInitializer.ProtobufInitializer(new NettyServerHandler())).bind(8888).run();
    }
}

