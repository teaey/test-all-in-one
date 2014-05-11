import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wxf on 14-5-5.
 */
public class SimpleTest {

    public static void main(String[] args) throws InterruptedException, IOException {
        test1();
        test2();
        System.in.read();
        test1();
        System.in.read();
        test2();
    }

    static int threadNum = 100;
    static final AtomicInteger atomicInteger = new AtomicInteger();

    static void test1() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    atomicInteger.incrementAndGet();
                }
                countDownLatch.countDown();
            }
        };



        List<Thread> list = new ArrayList<Thread>();
        for (int i = 0; i < threadNum; i++) {
            list.add(new Thread(r));
        }
        long s = System.currentTimeMillis();
        for (Thread each : list) {
            each.start();
        }
        countDownLatch.await();
        System.out.println("Atomic:" + (System.currentTimeMillis() - s));
    }

    static int intValue = 0;
    static final Object obj = new Object();

    static void test2() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    synchronized (obj) {
                        intValue++;
                    }
                }
                countDownLatch.countDown();
            }
        };



        List<Thread> list = new ArrayList<Thread>();
        for (int i = 0; i < threadNum; i++) {
            list.add(new Thread(r));
        }
        long s = System.currentTimeMillis();
        for (Thread each : list) {
            each.start();
        }
        countDownLatch.await();
        System.out.println("Sync:" + (System.currentTimeMillis() - s));
    }
}
