/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Keeps sending random data to the specified address.
 */
public class MultiThreadClient {
    private static final EventLoopGroup group = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
    private final String host;
    private final int port;
    private final int index;

    public MultiThreadClient(String host, int port, int index) {
        this.host = host;
        this.port = port;
        this.index = index;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Runtime.getRuntime().availableProcessors());
        for(int i=1;i<=10;i++){
            new MultiThreadClient("localhost", 8888, i).run();
            Thread.sleep(1000);
        }

        Thread.sleep(20000);
        group.shutdownGracefully();
    }

    public void run() throws Exception {
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelDuplexHandler() {
                    @   Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        System.out.println(index + "-响应数据:" + msg + "Read县城:" + Thread.currentThread());
                    }

                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                        ChannelFuture future = ctx.writeAndFlush(msg, promise);
                        System.out.println(index + "-Write县城:" + Thread.currentThread());
                        Thread.yield();
                        Thread.yield();
                        future.addListener(new ChannelFutureListener() {
                            @Override
                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                System.out.println(index +
                                    "-Listener执行完毕" + "Lintener县城:" + Thread.currentThread());
                            }
                        });
                    }
                });

            // Make the connection attempt.
            ChannelFuture f = b.connect(host, port).sync();
            for (int i = 0; i < 10; i++) {
                f.channel().writeAndFlush(Unpooled.wrappedBuffer(new byte[10]));
            }
            // Wait until the connection is closed.

            f.channel().closeFuture();
        } finally {
            //group.shutdownGracefully();
        }
    }
}
