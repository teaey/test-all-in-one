import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author xiaofei.wxf
 */
public class ZKClient implements Watcher {
    private static final int SESSION_TIMEOUT = 10000;
    private static final String CONNECTION_STRING = "localhost:2181";
    private static final String ZK_PATH = "/zk/zz";
    private ZooKeeper zk = null;

    private CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) {

        ZKClient sample = new ZKClient();
        sample.createConnection(CONNECTION_STRING, SESSION_TIMEOUT);
        if (sample.createPath(ZK_PATH, "我是节点初始内容")) {
            System.out.println();
            System.out.println("数据内容: " + sample.readData(ZK_PATH) + "\n");
            sample.writeData(ZK_PATH, "更新后的数据");
            System.out.println("数据内容: " + sample.readData(ZK_PATH) + "\n");
        }
        sample.deleteNode(ZK_PATH);
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //sample.releaseConnection();
    }

    /**
     * 创建ZK连接
     *
     * @param connectString  ZK服务器地址列表
     * @param sessionTimeout Session超时时间
     */
    public void createConnection(String connectString, int sessionTimeout) {
        this.releaseConnection();
        try {
            zk = new ZooKeeper(connectString, sessionTimeout, this);
            connectedSemaphore.await();
        } catch (InterruptedException e) {
            System.out.println("连接创建失败，发生 InterruptedException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("连接创建失败，发生 IOException");
            e.printStackTrace();
        }
    }

    /**
     * 关闭ZK连接
     */
    public void releaseConnection() {
        if (null != this.zk) {
            try {
                this.zk.close();
            } catch (InterruptedException e) {
                // ignore
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建节点
     *
     * @param path 节点path
     * @param data 初始数据内容
     * @return
     */
    public boolean createPath(String path, String data) {
        try {
            System.out.println("节点创建成功, Path: "
                + this.zk.create(path, //
                data.getBytes(), //
                ZooDefs.Ids.OPEN_ACL_UNSAFE, //
                CreateMode.EPHEMERAL)
                + ", content: " + data);
        } catch (KeeperException e) {
            System.out.println("节点创建失败，发生KeeperException");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("节点创建失败，发生 InterruptedException");
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 读取指定节点数据内容
     *
     * @param path 节点path
     * @return
     */
    public String readData(String path) {
        try {
            System.out.println("获取数据成功，path：" + path);
            return new String(this.zk.getData(path, false, null));
        } catch (KeeperException e) {
            System.out.println("读取数据失败，发生KeeperException，path: " + path);
            e.printStackTrace();
            return "";
        } catch (InterruptedException e) {
            System.out.println("读取数据失败，发生 InterruptedException，path: " + path);
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 更新指定节点数据内容
     *
     * @param path 节点path
     * @param data 数据内容
     * @return
     */
    public boolean writeData(String path, String data) {
        try {
            System.out.println("更新数据成功，path：" + path + ", stat: " +
                this.zk.setData(path, data.getBytes(), -1));
        } catch (KeeperException e) {
            System.out.println("更新数据失败，发生KeeperException，path: " + path);
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("更新数据失败，发生 InterruptedException，path: " + path);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除指定节点
     *
     * @param path 节点path
     */
    public void deleteNode(String path) {
        try {
            this.zk.delete(path, -1);
            System.out.println("删除节点成功，path：" + path);
        } catch (KeeperException e) {
            System.out.println("删除节点失败，发生KeeperException，path: " + path);
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("删除节点失败，发生 InterruptedException，path: " + path);
            e.printStackTrace();
        }
    }

    /**
     * 收到来自Server的Watcher通知后的处理。
     */
    /*
        3.1状态：KeeperState.Disconnected(0)
        此时客户端处于断开连接状态，和ZK集群都没有建立连接。
        3.1.1事件：EventType.None(-1)
        触发条件：一般是在与服务器断开连接的时候，客户端会收到这个事件。

        3.2状态：KeeperState. SyncConnected(3)
        3.2.1事件：EventType.None(-1)
        触发条件：客户端与服务器成功建立会话之后，会收到这个通知。
        3.2.2事件：EventType. NodeCreated (1)
        触发条件：所关注的节点被创建。
        3.2.3事件：EventType. NodeDeleted (2)
        触发条件：所关注的节点被删除。
        3.2.4事件：EventType. NodeDataChanged (3)
        触发条件：所关注的节点的内容有更新。注意，这个地方说的内容是指数据的版本号dataVersion。因此，即使使用相同的数据内容来更新，还是会收到这个事件通知的。无论如何，调用了更新接口，就一定会更新dataVersion的。
        3.2.5事件：EventType. NodeChildrenChanged (4)
        触发条件：所关注的节点的子节点有变化。这里说的变化是指子节点的个数和组成，具体到子节点内容的变化是不会通知的。

        3.3状态 KeeperState. AuthFailed(4)
        3.3.1事件：EventType.None(-1)

        3.4状态 KeeperState. Expired(-112)
        3.4.1事件：EventType.None(-1)
     */
    @Override
    public void process(WatchedEvent event) {
        switch (event.getType()) {
            case None:
                //ignore
                break;
            case NodeCreated:
                break;
            case NodeDeleted:
                break;
            case NodeDataChanged:
                break;
            case NodeChildrenChanged:
                break;
        }
        System.out.println("收到事件通知：" + event.getState() + "\n");
        if (Event.KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
        this.zk.register(this);
    }
}
