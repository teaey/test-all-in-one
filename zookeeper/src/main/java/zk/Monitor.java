package zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiaofei.wxf
 */
public class Monitor {

    public Monitor(String root) {
        this.root = root;
    }

    public static interface ChangedHandler {
        void handle(List<String> data);
    }

    private static final Logger logger = LoggerFactory.getLogger(Monitor.class);

    private static final AtomicInteger CT = new AtomicInteger(1);

    private ZooKeeper zk;

    private final String root;

    private final Watcher Watcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            logger.info("{}, {}", event.getType(), event.getState());
            if (event.getState() == Event.KeeperState.SyncConnected && event.getType() == Event.EventType.NodeChildrenChanged) {
                InComQ.add(root);
            }
            try {
                Monitor.this.zk.getChildren(root, true);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private final BlockingQueue<String> InComQ = new LinkedBlockingQueue();

    private final Thread MonitorHandlerThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String path = InComQ.take();
                    List<String> kids = Monitor.this.zk.getChildren(path, false);
                    for (ChangedHandler handler : handlers) {
                        handler.handle(kids);
                    }
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        }
    }, "MonitorHandlerThread-" + CT.getAndIncrement());

    private final Set<ChangedHandler> handlers = new HashSet<ChangedHandler>();

    public void addHandler(ChangedHandler handler) {
        synchronized (handlers) {
            handlers.add(handler);
        }
    }

    public void removeHandler(ChangedHandler handler) {
        synchronized (handlers) {
            handlers.remove(handler);
        }
    }

    private final CountDownLatch sync = new CountDownLatch(1);

    private static final int TimeOut = 60 * 60 * 1000;

    public void monitor(String connectString) throws IOException, KeeperException {
        MonitorHandlerThread.start();
        this.zk = new ZooKeeper(connectString, TimeOut, Watcher);
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        Monitor m = new Monitor("/g1");
        m.addHandler(new ChangedHandler() {
            @Override
            public void handle(List<String> data) {
                System.out.println(Arrays.toString(data.toArray()));
            }
        });
        m.monitor("localhost:2181");
        Thread.sleep(1111111111);
    }
}
