package com.taobao.teaey.lostrpc.client;

import com.taobao.teaey.lostrpc.Dispatcher;

import java.net.InetSocketAddress;

/**
 * Created by xiaofei.wxf on 14-2-13.
 */
public interface Client<A, B> {

    Client run();

    Client shutdown();

    Client connect(InetSocketAddress address);

    Client showdownNow();

    Client send(A p);

    Client recv(B p);

    Client dispatcher(Dispatcher dispatcher);

}
