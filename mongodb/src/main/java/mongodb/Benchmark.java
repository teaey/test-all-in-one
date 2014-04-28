package mongodb;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaofei.wxf
 */
public class Benchmark {
    static class Task implements Runnable {
        private Runnable r;
        private String taskName;

        Task(Runnable r, String taskName) {
            this.r = r;
            this.taskName = taskName;
        }

        @Override public void run() {
            try {
                long s1 = System.nanoTime();
                r.run();
                long s2 = System.nanoTime();
                System.out.println(taskName + "耗时" + (TimeUnit.NANOSECONDS.toMillis(s2 - s1)) + "ms");
            } catch (Exception e) {
                System.err.println("执行" + taskName + " 时发生错误");
                e.printStackTrace(System.err);
            }
        }
    }

    public static void singelThread(String testName, Runnable runnable) {
        new Thread(new Task(runnable, testName)).start();
    }

    public static void multiThread(String testName, Runnable runnable, int threadNum) {
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(new Task(runnable, testName + "-" + i));
        }
        for (int i = 0; i < threadNum; i++) {
            threads[i].start();
        }
    }
}
