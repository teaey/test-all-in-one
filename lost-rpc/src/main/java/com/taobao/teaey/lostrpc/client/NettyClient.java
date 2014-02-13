package com.taobao.teaey.lostrpc.client;

import com.taobao.teaey.lostrpc.Dispatcher;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created by xiaofei.wxf on 14-2-13.
 */
public class NettyClient<A,B> implements Client<A, B> {
    private final Bootstrap b = newBootstrap();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private SocketAddress socketAddress;
    private ChannelInitializer initializer;
    private Channel channel;

    private Dispatcher<B> dispatcher;

    public NettyClient() {
    }

    public Client initializer(ChannelInitializer initializer) {
        this.initializer = initializer;
        return this;
    }

    public Client dispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
        return this;
    }

    @Override
    public Client run() {
        try {
            this.channel = b.group(workerGroup).channel(NioSocketChannel.class).handler(this.initializer).connect(socketAddress).sync().channel();
        } catch (InterruptedException e) {
            shutdown();
        }
        return this;
    }

    @Override
    public void shutdown() {
        workerGroup.shutdownGracefully();
    }

    @Override
    public Client connect(InetSocketAddress address) {
        this.socketAddress = address;
        return this;
    }

    @Override
    public void showdownNow() {
        shutdown();
    }

    @Override
    public Client send(A p) {
        this.channel.writeAndFlush(p);
        return this;
    }

    @Override
    public Client recv(B p) {
        this.dispatcher.dispatcher(p);
        return this;
    }

    protected Bootstrap newBootstrap() {
        return new Bootstrap().option(ChannelOption.SO_LINGER, -1)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .option(ChannelOption.TCP_NODELAY, true);
    }
}
