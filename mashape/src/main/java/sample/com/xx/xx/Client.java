package com.xx.xx;

import com.google.protobuf.ByteString;
import com.taobao.teaey.lostrpc.Dispatcher;
import com.taobao.teaey.lostrpc.client.NettyClient;
import com.taobao.teaey.lostrpc.common.Connection;
import com.taobao.teaey.lostrpc.common.LostProto;
import com.taobao.teaey.lostrpc.common.ProtobufInitializer;

import java.net.InetSocketAddress;

/**
 * @author xiaofei.wxf
 */
public class Client {
    public static void main(String[] args) {
        //创建客户端
        NettyClient client = NettyClient.newInstance();
        //这里定义发送的消息报类型（PB包）
        client.initializer(ProtobufInitializer.newInstance(LostProto.Packet.getDefaultInstance()));
        //这里设置响应返回后怎么处理
        client.dispatcher(new Dispatcher<LostProto.Packet>() {
            @Override
            public void dispatch(Connection o, LostProto.Packet p) {
                System.out.println(p);
            }
        });
        client.connect(new InetSocketAddress(8888));
        client.run();

        //创建真是的请求，在test.proto中定义的
        ByteString realReq =
            TestProto.TestRequest_C2S.newBuilder().setTimestamp(System.currentTimeMillis()).build()
                .toByteString();
        //将真是请求放入消息包中
        LostProto.Packet p = LostProto.Packet.newBuilder().setPId(1).setMethodName("testMethod")
            .setServiceName("com.xx.xx.TestService").setTimestamp(System.currentTimeMillis())
            .setData(realReq).build();
        for (int i = 0; i < 100000; i++) {
            //发送消息包
            client.ask(p);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
