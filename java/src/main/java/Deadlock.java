import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaofei.wxf
 */
public class Deadlock {
    public static void main(String[] args) {
       final Lock l1 = new ReentrantLock();
       final Lock l2 = new ReentrantLock();
        Thread t1 = new Thread(){
            @Override public void run() {
                l1.lock();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                l2.lock();
            }
        };

        Thread t2 = new Thread(){
            @Override public void run() {
                l2.lock();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                l1.lock();
            }
        };

        t1.start();
        t2.start();
    }
}
