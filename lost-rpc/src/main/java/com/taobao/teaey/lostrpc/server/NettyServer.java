package com.taobao.teaey.lostrpc.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created by xiaofei.wxf on 14-2-13.
 */
public class NettyServer implements Server {
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private final ServerBootstrap b = newServerBootStrap();
    private SocketAddress address;

    private ChannelInitializer initializer;

    public NettyServer(ChannelInitializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public Server run() {
        if (null == address) {
            throw new NullPointerException("socket address");
        }
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(this.initializer);
        try {
            ChannelFuture f = b.bind(address).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            shutdown();
        }
        return this;
    }

    @Override
    public void shutdown() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    @Override
    public Server bind(int port) {
        this.address = new InetSocketAddress(port);
        return this;
    }

    @Override
    public Server bind(SocketAddress address) {
        this.address = address;
        return this;
    }

    @Override
    public void showdownNow() {
        shutdown();
    }

    protected ServerBootstrap newServerBootStrap() {
        return new ServerBootstrap().option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_BACKLOG, 100000)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.SO_LINGER, -1)
                .childOption(ChannelOption.TCP_NODELAY, true);
    }
}
