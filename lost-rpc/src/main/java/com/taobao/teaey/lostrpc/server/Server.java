package com.taobao.teaey.lostrpc.server;

import com.taobao.teaey.lostrpc.Dispatcher;

import java.net.SocketAddress;

/**
 * Created by xiaofei.wxf on 14-2-13.
 */
public interface Server<Channel, ReqType> {

    public Server run();

    public Server shutdown();

    public Server bind(int port);

    public Server bind(SocketAddress address);

    public Server showdownNow();

    Server dispatcher(Dispatcher<Channel, ReqType> dispatcher);

}
