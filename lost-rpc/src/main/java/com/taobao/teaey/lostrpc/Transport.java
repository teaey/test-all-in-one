package com.taobao.teaey.lostrpc;

/**
 * Created by xiaofei.wxf on 14-2-13.
 */
public interface Transport<PacketType> {
    void send(PacketType p);

    void recv(PacketType p);
}
