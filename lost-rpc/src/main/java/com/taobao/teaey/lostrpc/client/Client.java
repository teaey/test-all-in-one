package com.taobao.teaey.lostrpc.client;

import com.taobao.teaey.lostrpc.Dispatcher;

import java.net.InetSocketAddress;

/**
 * Created by xiaofei.wxf on 14-2-13.
 */
public interface Client<A, B> {

    Client run();

    void shutdown();

    Client connect(InetSocketAddress address);

    void showdownNow();

    Client send(A p);

    Client recv(B p);

    Client dispatcher(Dispatcher dispatcher);

}
