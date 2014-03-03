package zk;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author xiaofei.wxf
 */
public class Register {
    private static final Logger logger = LoggerFactory.getLogger(Register.class);
    private static final int TimeOut = 60 * 60 * 1000;

    private final String root;

    private volatile String path;

    private ZooKeeper zk;

    private final CountDownLatch sync = new CountDownLatch(1);

    private final Watcher Watcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            logger.info("{}, {}", event.getType(), event.getState());
            if (event.getState() == Event.KeeperState.SyncConnected && event.getType() == Event.EventType.None) {
                logger.info("注册 [{}]", Register.this.zk.toString());
                sync.countDown();
            } else if (event.getType() == Event.EventType.NodeDataChanged) {
                logger.info("节点内容变更 [{}]", Register.this.zk.toString());
                if (null != path) {
                    reWatch();
                }
            }

        }
    };

    void reWatch() {
        try {
            Register.this.zk.exists(Register.this.path, true);
        } catch (KeeperException e) {
            logger.error("", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Register(String path) {
        this.root = path;
    }

    public void register(String connectString) throws IOException, KeeperException {
        this.zk = new ZooKeeper(connectString, TimeOut, Watcher);
        try {
            sync.await();
            this.path = this.zk.create(root + "node-", new byte[0], ZooDefs.Ids.READ_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            reWatch();
        } catch (KeeperException e) {
            logger.error("error create:", e);
            throw e;
        } catch (InterruptedException e) {
            logger.error("error create:", e);
            Thread.currentThread().interrupt();
        }
    }

    public void unregister() {
        try {
            this.zk.close();
            logger.info("注销 [{}]", Register.this.zk.toString());
        } catch (InterruptedException e) {
            logger.error("", e);
        }
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        Register r = new Register("/g1/");
        r.register("10.68.175.171:2181");
        Thread.sleep(10000);
        r.unregister();

        Thread.sleep(300000000);
    }

}
