package com.taobao.teaey.lostrpc.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by xiaofei.wxf on 14-2-13.
 */
@ChannelHandler.Sharable
public class NettyClientDispatchHandler extends ChannelInboundHandlerAdapter {
    private final NettyClient client;

    public NettyClientDispatchHandler(NettyClient client) {
        this.client = client;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.client.recv(msg);
    }
}
