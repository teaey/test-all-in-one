package network.socket.nio;

import network.socket.Client;
import network.socket.ResponseDispatcher;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @author xiaofei.wxf
 */
public class NioClient implements Client<NioClient> {


    public int getId() {
        return System.identityHashCode(this.socketChannel);
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    private SocketChannel socketChannel;

    public void setDispatcher(ResponseDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    private ResponseDispatcher dispatcher;


    NioClient() {
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public NioClient connect(String host, int port) throws IOException {
        return this.connect(new InetSocketAddress(host, port));
    }

    @Override
    public NioClient connect(InetSocketAddress addr) throws IOException {
        NioClientFactory.register(socketChannel, SelectionKey.OP_CONNECT);
        socketChannel.connect(addr);
        return this;
    }

    private final CountDownLatch latch = new CountDownLatch(1);

    public void sync() throws InterruptedException {
        latch.await();
    }

    void doConnect() throws IOException {
        if (socketChannel.isConnectionPending()) {
            socketChannel.finishConnect();
            latch.countDown();
            NioClientFactory.register(socketChannel, SelectionKey.OP_READ);
        }
    }

    private int msgLen;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    private ByteBuffer msg;

    void doRead() throws IOException {
        while (true) {
            //清空Buffer
            buffer.clear();
            //读取socket的数据到Buffer
            int readed = this.socketChannel.read(buffer);
            //处理读到的数据
            if (readed > 0) {
                buffer.flip();
                while (buffer.remaining() > 0) {
                    if (msgLen == 0) {
                        //获取包体长度
                        msgLen = buffer.getInt();
                        //数据包不完整，缓存数据
                        int remaining = buffer.remaining();
                        if (msgLen > remaining) {
                            msg = ByteBuffer.allocate(msgLen);
                            msg.put(buffer);
                        } else {
                            //封包
                            byte[] resp = new byte[msgLen];
                            buffer.get(resp);
                            msgLen = 0;
                            answer(resp);
                        }

                    } else {
                        //继续粘包
                        int remaining = buffer.remaining();
                        int lack = msg.limit() - msg.position();
                        if (lack > remaining) {
                            msg.put(buffer);
                        } else {
                            msg.put(buffer.array(), buffer.position(), lack);
                            buffer.position(buffer.position() + lack);
                            msgLen = 0;
                            answer(msg);
                            msg = null;
                        }
                    }
                }
                if (readed == 1024) {
                    continue;
                }
            }
            return;
        }
    }

    @Override
    public NioClient shutdown() {
        if (null != socketChannel) {
            try {
                socketChannel.close();
            } catch (IOException e) {
            }
        }
        NioClientFactory.removeClient(System.identityHashCode(socketChannel));
        return this;
    }

    @Override
    public NioClient ask(Object req) throws IOException {
        if (req instanceof byte[]) {
            req = ByteBuffer.wrap((byte[]) req);
        }
        this.socketChannel.write((ByteBuffer) req);
        return this;
    }

    @Override
    public NioClient answer(Object resp) {
        dispatcher.dispatcher(resp);
        return this;
    }
}
