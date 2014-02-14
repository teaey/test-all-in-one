package com.taobao.teaey.lostrpc;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaofei.wxf on 14-2-14.
 */
public abstract class NettyChannelInitializer extends ChannelInitializer {

    public static final class ProtobufInitializer extends NettyChannelInitializer {
        private final static Logger logger = LoggerFactory.getLogger(NettyChannelInitializer.class);
        private final ChannelHandler protobufEncoder = new ProtobufEncoder();
        private final ChannelHandler frameEncoder = new ProtobufVarint32LengthFieldPrepender();
        private final ChannelHandler protobufDecoder = new ProtobufDecoder(LostProto.Packet.getDefaultInstance());

        public ProtobufInitializer(ChannelHandler[] handlers) {
            super(handlers);
        }

        public ProtobufInitializer(ChannelHandler handler) {
            super(handler);
        }

        @Override
        protected void decoders(Channel ch) throws Exception {
            ch.pipeline().addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
            ch.pipeline().addLast("protobufDecoder", protobufDecoder);
        }

        @Override
        protected void encoders(Channel ch) throws Exception {
            ch.pipeline().addLast("frameEncoder", frameEncoder);
            ch.pipeline().addLast("protobufEncoder", protobufEncoder);
        }

        @Override
        protected void handlers(Channel ch) throws Exception {
            for (int i = 1; i <= handlers.length; i++) {
                ch.pipeline().addLast("handler-" + i, handlers[i - 1]);
            }
        }

        @Override
        protected Logger getLogger() {
            return logger;
        }
    }

    /**
     * 如果SLF4J的日志级别为debug，会自动在业务处理handler前加上debug日志handler
     */
    private static final ChannelHandler LOGGING_HANDLER = new TightLoggingHandler();

    protected final ChannelHandler[] handlers;

    public NettyChannelInitializer(ChannelHandler[] handlers) {
        this.handlers = handlers;
    }

    public NettyChannelInitializer(ChannelHandler handler) {
        this(new ChannelHandler[]{handler});
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        decoders(ch);
        encoders(ch);
        if (getLogger().isDebugEnabled()) {
            ch.pipeline().addLast("LOGGING_HANDLER", LOGGING_HANDLER);
        }
        handlers(ch);
    }

    protected abstract void decoders(Channel ch) throws Exception;

    protected abstract void encoders(Channel ch) throws Exception;

    protected abstract void handlers(Channel ch) throws Exception;

    protected abstract Logger getLogger();
}
