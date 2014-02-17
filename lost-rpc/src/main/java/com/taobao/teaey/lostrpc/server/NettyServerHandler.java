package com.taobao.teaey.lostrpc.server;

import com.taobao.teaey.lostrpc.Dispatcher;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaofei.wxf on 14-2-13.
 */
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private final Dispatcher dispatcher;

    public NettyServerHandler(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

}
