package com.taobao.teaey.lostrpc;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.MessageLite;
import com.taobao.teaey.lostrpc.codec.JsonCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by xiaofei.wxf on 14-2-14.
 */
public abstract class NettyChannelInitializer extends ChannelInitializer {

    /**
     * JSON *
     */

    public static NettyChannelInitializer newJsonInitializer(ChannelHandler... handler) {
        return new JsonInitializer(handler);
    }

    public static final class JsonInitializer extends NettyChannelInitializer {
        private final static Logger logger = LoggerFactory.getLogger(NettyChannelInitializer.class);

        @Sharable
        private class Encoder extends MessageToByteEncoder<Object> {
            @Override
            protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
                byte[] body = JsonCodec.INSTANCE.encode(msg);
                int bodyLen = body.length;
                out.ensureWritable(4 + bodyLen);
                out.writeInt(bodyLen);
                out.writeBytes(body);
            }
        }

        private class Decoder extends ByteToMessageDecoder {

            @Override
            protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
                if (in.readableBytes() <= 4) return;

                in.markReaderIndex();

                int bodyLen = in.readInt();

                if (in.readableBytes() < bodyLen) {
                    in.resetReaderIndex();
                    return;
                }

                byte[] body = new byte[bodyLen];

                in.readBytes(body);

                out.add(JsonCodec.INSTANCE.decode(body));
            }
        }

        private final ChannelHandler encoder = new Encoder();
        private final ChannelHandler protobufDecoder = new ProtobufDecoder(LostProto.Packet.getDefaultInstance());

        private JsonInitializer(ChannelHandler[] handlers) {
            super(handlers);
        }

        private JsonInitializer(ChannelHandler handler) {
            super(handler);
        }

        @Override
        protected void decoders(Channel ch) throws Exception {
            ch.pipeline().addLast("decoder", new Decoder());
        }

        @Override
        protected void encoders(Channel ch) throws Exception {
            ch.pipeline().addLast("encoder", encoder);
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
     * PROTOCOL BUFFERS *
     */
    public static NettyChannelInitializer newProtobufInitializer(ChannelHandler... handler) {
        return new ProtobufInitializer(handler);
    }

    public static final class ProtobufInitializer extends NettyChannelInitializer {
        private final static Logger logger = LoggerFactory.getLogger(NettyChannelInitializer.class);

        private static final boolean HAS_PARSER;

        static {
            boolean hasParser = false;
            try {
                // MessageLite.getParsetForType() is not available until protobuf 2.5.0.
                MessageLite.class.getDeclaredMethod("getParserForType");
                hasParser = true;
            } catch (Throwable t) {
                // Ignore
            }

            HAS_PARSER = hasParser;
        }

        @Sharable
        private class Encoder extends MessageToByteEncoder<MessageLite> {
            @Override
            protected void encode(ChannelHandlerContext ctx, MessageLite msg, ByteBuf out) throws Exception {
                byte[] body = msg.toByteArray();
                int bodyLen = body.length;
                out.ensureWritable(4 + bodyLen);
                out.writeInt(bodyLen);
                out.writeBytes(body);
            }
        }

        private class Decoder extends ByteToMessageDecoder {


            private final MessageLite prototype;
            private final ExtensionRegistry extensionRegistry;

            /**
             * Creates a new instance.
             */
            public Decoder(MessageLite prototype) {
                this(prototype, null);
            }

            public Decoder(MessageLite prototype, ExtensionRegistry extensionRegistry) {
                if (prototype == null) {
                    throw new NullPointerException("prototype");
                }
                this.prototype = prototype.getDefaultInstanceForType();
                this.extensionRegistry = extensionRegistry;
            }

            @Override
            protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
                if (in.readableBytes() <= 4) return;

                in.markReaderIndex();

                int bodyLen = in.readInt();

                if (in.readableBytes() < bodyLen) {
                    in.resetReaderIndex();
                    return;
                }

                byte[] body = new byte[bodyLen];

                in.readBytes(body);

                if (extensionRegistry == null) {
                    if (HAS_PARSER) {
                        out.add(prototype.getParserForType().parseFrom(body));
                    } else {
                        out.add(prototype.newBuilderForType().mergeFrom(body).build());
                    }
                } else {
                    if (HAS_PARSER) {
                        out.add(prototype.getParserForType().parseFrom(body, extensionRegistry));
                    } else {
                        out.add(prototype.newBuilderForType().mergeFrom(body, extensionRegistry).build());
                    }
                }
            }
        }

        private final ChannelHandler encoder = new Encoder();

        private ProtobufInitializer(ChannelHandler[] handlers) {
            super(handlers);
        }

        private ProtobufInitializer(ChannelHandler handler) {
            super(handler);
        }

        @Override
        protected void decoders(Channel ch) throws Exception {
            ch.pipeline().addLast("decoder", new Decoder(LostProto.Packet.getDefaultInstance()));
        }

        @Override
        protected void encoders(Channel ch) throws Exception {
            ch.pipeline().addLast("encoder", encoder);
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
