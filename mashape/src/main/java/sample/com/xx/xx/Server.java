package com.xx.xx;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.taobao.teaey.lostrpc.common.LostProto;
import com.taobao.teaey.lostrpc.common.ProtobufInitializer;
import com.taobao.teaey.lostrpc.concurrent.AsyncExecutor;
import com.taobao.teaey.lostrpc.server.NettyServer;
import com.taobao.teaey.lostrpc.server.protobuf.ProtobufRegisterCenter;
import com.taobao.teaey.lostrpc.server.protobuf.ServerProtobufDispatcher;

import java.io.IOException;

/**
 * @author xiaofei.wxf
 */
public class Server {
    public static void main(String[] args) throws UnirestException, IOException {
        //注册请求处理的类，类需要继承具体Service接口
        ProtobufRegisterCenter
            .addService(TestProto.TestService.newReflectiveBlockingService(new TestServiceImpl()));
        //创建服务器
        NettyServer server = NettyServer.newInstance();
        //设置消息分发器，这里创建一个两个线程的一部分发器
        server.dispatcher(new ServerProtobufDispatcher(new AsyncExecutor(2)));
        //初始化消息格式（PB）
        server.initializer(ProtobufInitializer.newInstance(LostProto.Packet.getDefaultInstance()));
        server.bind(8888);
        server.run();
    }
}
