package com.taobao.teaey.lostrpc.client;

import com.taobao.teaey.lostrpc.Dispatcher;
import com.taobao.teaey.lostrpc.LostProto;
import com.taobao.teaey.lostrpc.NettyProtobufInitializer;

import java.net.InetSocketAddress;

/**
 * Created by xiaofei.wxf on 14-2-13.
 */
public class ClientTest {
    public static void main(String[] args) {
        NettyClient client = new NettyClient();
        client.initializer(new NettyProtobufInitializer(new NettyClientHandler(client))).dispatcher(new Dispatcher<LostProto.Packet>() {
            @Override
            public void dispatcher(LostProto.Packet p) {
                System.out.println(p);
            }
        }).connect(new InetSocketAddress(8888)).run();

        client.send(LostProto.Packet.newBuilder().setId(System.currentTimeMillis()).build());
    }
}
