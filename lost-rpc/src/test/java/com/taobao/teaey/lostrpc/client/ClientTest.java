package com.taobao.teaey.lostrpc.client;

import com.taobao.teaey.lostrpc.Dispatcher;
import com.taobao.teaey.lostrpc.LostProto;
import com.taobao.teaey.lostrpc.NettyChannelInitializer;
import com.taobao.teaey.lostrpc.codec.Pojo;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import org.junit.Test;

import java.net.InetSocketAddress;

/**
 * Created by xiaofei.wxf on 14-2-14.
 */
public class ClientTest {
    @Test
    public void protobufClient() throws InterruptedException {
        Client client = new NettyClient().initializer(NettyChannelInitializer.newProtobufInitializer(
                LostProto.Packet.getDefaultInstance())).
                dispatcher(new Dispatcher<Channel, LostProto.Packet>() {
                    @Override
                    public void dispatcher(Channel channel, LostProto.Packet p) {
                        System.out.println("来自服务器的响应:" + p);
                    }
                }).connect(new InetSocketAddress(8888)).run();
        while (true) {
            client.send(LostProto.Packet.newBuilder().setId(System.currentTimeMillis()).build());
            Thread.sleep(3000);
        }

    }

    @Test
    public void jsonClient() throws InterruptedException {
        Client client =
        new NettyClient().initializer(NettyChannelInitializer.newJsonInitializer()).dispatcher(new Dispatcher<Channel, Object>() {
            @Override
            public void dispatcher(Channel channel, Object p) {
                System.out.println("来自服务器的响应:" + p);
            }
        }).connect(new InetSocketAddress(8888)).run();
        while (true) {
            client.send(new Pojo());
            Thread.sleep(3000);
        }

    }
}
