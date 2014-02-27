/**
 * @author xiaofei.wxf
 */
public class Wait {
    public static void main(String[] args) throws InterruptedException {
        Object o = new Object();
        synchronized (o) {
            o.wait();
        }
    }
}
