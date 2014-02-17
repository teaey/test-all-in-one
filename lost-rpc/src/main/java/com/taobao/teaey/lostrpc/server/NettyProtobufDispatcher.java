package com.taobao.teaey.lostrpc.server;

import com.google.protobuf.*;
import com.taobao.teaey.lostrpc.Dispatcher;
import com.taobao.teaey.lostrpc.LostProto;
import io.netty.channel.Channel;

/**
 * @author xiaofei.wxf
 */
public class NettyProtobufDispatcher implements Dispatcher<Channel, LostProto.Packet> {
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
}
