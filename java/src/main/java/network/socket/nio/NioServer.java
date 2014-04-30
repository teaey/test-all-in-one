package network.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * @author xiaofei.wxf
 */
public class NioServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8888));
        final Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        new Thread(){
            @Override public void run() {
                for(;;){
                    try {
                        selector.select();
                        Set<SelectionKey> set = selector.selectedKeys();
                        for(SelectionKey each : set){
                            if(each.isAcceptable()){
                                final SocketChannel channel = ((ServerSocketChannel) each.channel()).accept();
                                channel.configureBlocking(false);
                                channel.socket().setSoLinger(true, 100);
                                channel.socket().setTcpNoDelay(true);
                                new Thread(){
                                    @Override public void run() {
                                        try {
                                            System.out.println("开始写数据");
                                            channel.write(ByteBuffer.allocate(100000000));
                                            //channel.socket().getOutputStream().flush();
                                            //Thread.sleep(1000);
                                            channel.close();
                                        } catch (Throwable e) {
                                            e.printStackTrace();
                                            System.out.println("关闭异常");
                                        }
                                        System.out.println("成功关闭");
                                    }
                                }.start();
                            }
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
