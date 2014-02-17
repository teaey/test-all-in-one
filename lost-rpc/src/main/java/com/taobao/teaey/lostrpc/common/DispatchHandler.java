package com.taobao.teaey.lostrpc.common;

import com.taobao.teaey.lostrpc.Dispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author xiaofei.wxf on 14-2-13.
 */
@ChannelHandler.Sharable
public class DispatchHandler<ReqType> extends ChannelInboundHandlerAdapter {
    private final Dispatcher<Channel, ReqType> dispatcher;

    public DispatchHandler(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            this.dispatcher.dispatch(ctx.channel(), (ReqType) msg);
        } catch (ClassCastException e) {
            ctx.fireChannelRead(msg);
        }
    }
}
