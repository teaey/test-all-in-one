package com.taobao.teaey.lostrpc.server;

import com.taobao.teaey.lostrpc.Dispatcher;
import com.taobao.teaey.lostrpc.LostProto;
import com.taobao.teaey.lostrpc.NettyChannelInitializer;
import io.netty.channel.Channel;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by xiaofei.wxf on 14-2-14.
 */
public class ServerTest {
    @Test
    public void protobufServer() throws IOException, InterruptedException {
        NettyChannelInitializer initializer = NettyChannelInitializer.newProtobufInitializer(LostProto.Packet.getDefaultInstance(),new NettyServerHandler(new Dispatcher<Channel, LostProto.Packet>() {
            @Override
            public void dispatcher(Channel o, LostProto.Packet p) {
                o.writeAndFlush(p);
            }
        }));
        new NettyServer().initializer(initializer).bind(8888).run();
    }

    @Test
    public void jsonServer() throws IOException, InterruptedException {
        NettyChannelInitializer initializer = NettyChannelInitializer.newJsonInitializer(new NettyServerHandler(new Dispatcher<Channel, Object>() {
            @Override
            public void dispatcher(Channel o, Object p) {
                o.writeAndFlush(p);
            }
        }));
        new NettyServer().initializer(initializer).bind(8888).run();
    }
}
