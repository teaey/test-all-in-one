package interrupt;

import java.util.concurrent.locks.Lock;

/**
 * @author xiaofei.wxf
 */
public class InterruptTest extends InterruptSupport {
    private Lock lock;

    public InterruptTest(Lock lock) {
        this.lock = lock;
    }

    public static void main(String args[]) throws Exception {
        //        Lock lock1 = new ReentrantLock();
        //        lock1.lock();
        final InterruptTest test = new InterruptTest(null);
        Thread t = new Thread() {

            @Override
            public void run() {
                long start = System.currentTimeMillis();
                try {
                    System.out.println("InterruptRead start!");
                    test.execute();
                    System.out.println("处理结束");
                } catch (InterruptedException e) {
                    System.out.println(
                        "InterruptRead end! cost time : " + (System.currentTimeMillis() - start));
                    e.printStackTrace();
                }
            }
        };

        t.start();

        // 先让Read执行2秒
        Thread.sleep(2000);
        // 发出interrupt中断
        t.interrupt();
    }

    @Override
    public void bussiness() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void interrupt(Thread thread) {
        System.out.println(Thread.currentThread() + "子线程被中断:" + thread);
    }
}
