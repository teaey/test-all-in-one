package com.taobao.teaey.socket.nio;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiaofei.wxf
 */
public class NioClientFactory {

    private static final Selector selector;

    private static final ConcurrentMap<Integer, NioClient> CACHE = new ConcurrentHashMap<Integer, NioClient>();

    private static final HashSet<Runnable> Registions = new HashSet<Runnable>();

    private static final Thread boss = new Thread(new Runnable() {
        @Override
        public void run() {
            for (; ; ) {
                try {
                    int channels = selector.select();
                    synchronized (Registions) {
                        for (Runnable r : Registions) {
                            r.run();
                        }
                        Registions.clear();
                    }
                    if (channels > 0) {
                        Set<SelectionKey> keys = selector.selectedKeys();
                        Iterator<SelectionKey> iter = keys.iterator();
                        while (iter.hasNext()) {
                            final SelectionKey key = iter.next();
                            worker.execute(new Runnable() {
                                @Override
                                public void run() {
                                    NioClient value = CACHE.get(System.identityHashCode(key.channel()));
                                    try {

                                        if (null != value) {
                                            if (key.isConnectable()) {
                                                value.doConnect();
                                            }
                                            if (key.isReadable()) {
                                                value.doRead();
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        value.shutdown();
                                    }
                                }
                            });
                            iter.remove();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    });

    static {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boss.start();
    }

    private static final ExecutorService worker = Executors.newSingleThreadExecutor();

    public static NioClient open() {
        NioClient ret = new NioClient();
        CACHE.put(System.identityHashCode(ret.getSocketChannel()), ret);
        return ret;
    }

    public static Selector getSelector() {
        return selector;
    }

    public static void register(final SocketChannel channel, final int ops) {
        synchronized (Registions) {
            Registions.add(new Runnable() {
                @Override
                public void run() {
                    try {
                        channel.register(selector, ops);
                    } catch (ClosedChannelException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        selector.wakeup();
    }

    public static NioClient getClient(int id){
        return CACHE.get(id);
    }

    public static NioClient removeClient(int id){
        return CACHE.remove(id);
    }
}
