import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.concurrent.CountDownLatch;

/**
 * @author xiaofei.wxf
 */
public class Register {
    private static final Logger logger = LoggerFactory.getLogger(Register.class);

    private static final char IDX = (char) 164;

    private static final String PREFIX = "NODE" + IDX;

    private static final int TimeOut = 60 * 60 * 1000;

    private final String root;
    private final CountDownLatch sync = new CountDownLatch(1);
    private volatile String path;
    private final Watcher Watcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            logger.info("{}, {}", event.getType(), event.getState());
            if (event.getState() == Event.KeeperState.SyncConnected
                && event.getType() == Event.EventType.None) {
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
    private ZooKeeper zk;

    public Register(String path) {
        this.root = path;
    }

    public static void main(String[] args)
        throws IOException, KeeperException, InterruptedException {
        //        Register r = new Register("/g1/");
        //        r.register("10.68.175.171:2181", 8888);
        //        Thread.sleep(10000);
        //        r.unregister();
        //        Thread.sleep(300000000);

        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
        Enumeration<NetworkInterface> netInterfaces =
            NetworkInterface.getNetworkInterfaces();
        InetAddress inetAddr = null;
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                inetAddr = address.nextElement();
                if (!inetAddr.isLoopbackAddress() && inetAddr.getHostAddress().indexOf(":") == -1) {
                    System.out.println(inetAddr);
                }
            }
        }

    }

    void reWatch() {
        try {
            Register.this.zk.exists(Register.this.path, true);
        } catch (KeeperException e) {
            logger.error("", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void register(String connectString, int port) throws IOException, KeeperException {
        this.zk = new ZooKeeper(connectString, TimeOut, Watcher);
        try {
            sync.await();
            InetAddress inetAddress = InetAddress.getLocalHost();
            String nn = root + PREFIX + inetAddress.getHostAddress() + ":" + port;
            this.path =
                this.zk.create(nn, new byte[0], ZooDefs.Ids.READ_ACL_UNSAFE, CreateMode.EPHEMERAL);
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

}
