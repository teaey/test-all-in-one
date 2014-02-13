package com.taobao.teaey.lostrpc.server;

import java.net.SocketAddress;

/**
 * Created by xiaofei.wxf on 14-2-13.
 */
public interface Server {

    public Server run();

    public void shutdown();

    public Server bind(int port);

    public Server bind(SocketAddress address);

    public void showdownNow();

}
