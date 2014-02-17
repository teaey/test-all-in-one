package com.taobao.teaey.lostrpc.client;

import com.taobao.teaey.lostrpc.Dispatcher;
import com.taobao.teaey.lostrpc.LostProto;
import com.taobao.teaey.lostrpc.codec.Pojo;
import com.taobao.teaey.lostrpc.common.JsonInitializer;
import com.taobao.teaey.lostrpc.common.ProtobufInitializer;
import io.netty.channel.Channel;
import org.junit.Test;

import java.net.InetSocketAddress;

/**
 * Created by xiaofei.wxf on 14-2-14.
 */
public class ClientTest {
    @Test
    public void protobufClient() throws InterruptedException {
        Client client = NettyClient.newInstance().initializer(ProtobufInitializer.newInstance(
                LostProto.Packet.getDefaultInstance())).
                dispatcher(new Dispatcher<Channel, LostProto.Packet>() {
                    @Override
                    public void dispatch(Channel channel, LostProto.Packet p) {
                        System.out.println("来自服务器的响应:" + p);
                    }
                }).connect(new InetSocketAddress(8888)).run();
        while (true) {
            client.send(LostProto.Packet.newBuilder().setPId(System.currentTimeMillis()).setData(LostProto.Login_C2S.newBuilder().setTimestamp(System.currentTimeMillis()).build().toByteString()).setTimestamp(System.currentTimeMillis()).setMethodName("login").setServiceName("LoginService").build());
            Thread.sleep(3000);
        }

    }

    @Test
    public void jsonClient() throws InterruptedException {
        Client client =
                NettyClient.newInstance().initializer(JsonInitializer.newInstance()).dispatcher(new Dispatcher<Channel, Object>() {
                    @Override
                    public void dispatch(Channel channel, Object p) {
                        System.out.println("来自服务器的响应:" + p);
                    }
                }).connect(new InetSocketAddress(8888)).run();
        while (true) {
            client.send(new Pojo());
            Thread.sleep(3000);
        }

    }
}
