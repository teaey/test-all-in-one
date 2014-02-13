package com.taobao.teaey.lostrpc;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaofei.wxf on 14-2-13.
 */
public class NettyProtobufInitializer extends ChannelInitializer {
    private final static Logger logger = LoggerFactory.getLogger(NettyProtobufInitializer.class);
    private final ChannelHandler loggingHandler = new TightLoggingHandler();
    private final ChannelHandler protobufEncoder = new ProtobufEncoder();
    private final ChannelHandler frameEncoder = new ProtobufVarint32LengthFieldPrepender();
    private final ChannelHandler protobufDecoder = new ProtobufDecoder(LostProto.Packet.getDefaultInstance());
    private ChannelHandler[] channelHandler;

    public NettyProtobufInitializer(ChannelHandler... handler) {
        this.channelHandler = handler;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
//        if (logger.isDebugEnabled()) {
//            p.addLast("loggingHandler-before", loggingHandler);
//        }
        p.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
        p.addLast("protobufDecoder", protobufDecoder);

        p.addLast("frameEncoder", frameEncoder);
        p.addLast("protobufEncoder", protobufEncoder);

        if (logger.isDebugEnabled()) {
            p.addLast("loggingHandler-after", loggingHandler);
        }
        for (int i = 1; i <= channelHandler.length; i++) {
            p.addLast("handler-" + i, channelHandler[i-1]);
        }

    }
}
