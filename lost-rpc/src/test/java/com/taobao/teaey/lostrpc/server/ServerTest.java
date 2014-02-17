package com.taobao.teaey.lostrpc.server;

import com.google.protobuf.*;
import com.taobao.teaey.lostrpc.Dispatcher;
import com.taobao.teaey.lostrpc.LostProto;
import com.taobao.teaey.lostrpc.NettyChannelInitializer;
import com.taobao.teaey.lostrpc.common.JsonInitializer;
import com.taobao.teaey.lostrpc.common.ProtobufInitializer;
import io.netty.channel.Channel;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by xiaofei.wxf on 14-2-14.
 */
public class ServerTest {
    @Test
    public void protobufServer() throws IOException, InterruptedException {
        Dispatcher<Channel, LostProto.Packet> d = new Dispatcher<Channel, LostProto.Packet>() {
            private BlockingService service = LostProto.LoginService.newReflectiveBlockingService(new LostProto.LoginService.BlockingInterface() {

                @Override
                public LostProto.Login_S2C login(RpcController controller, LostProto.Login_C2S request) throws ServiceException {
                    LostProto.Login_S2C resp = LostProto.Login_S2C.newBuilder().setTimestamp(System.currentTimeMillis()).build();
                    return resp;
                }

            });

            @Override
            public void dispatch(Channel o, LostProto.Packet p) {
                String methodName = p.getMethodName();
                String serviceName = p.getServiceName();
                Descriptors.MethodDescriptor md = service.getDescriptorForType().findMethodByName(methodName);
                try {
                    Message resp = service.callBlockingMethod(md, null, service.getRequestPrototype(md).getParserForType().parseFrom(p.getData()));
                    o.writeAndFlush(LostProto.Packet.newBuilder(p).setData(resp.toByteString()).build());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        NettyChannelInitializer initializer = ProtobufInitializer.newInstance(LostProto.Packet.getDefaultInstance());

        NettyServer.newInstance()
                .initializer(initializer)
                .dispatcher(d)
                .bind(8888)
                .run();

    }

    @Test
    public void jsonServer() throws IOException, InterruptedException {
        NettyServer.newInstance().initializer(JsonInitializer.newInstance()).dispatcher(new Dispatcher<Channel, Object>() {
            @Override
            public void dispatch(Channel o, Object p) {
                o.writeAndFlush(p);
            }
        }).bind(8888).run();
    }
}
