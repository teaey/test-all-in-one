package interrupt;

import sun.nio.ch.Interruptible;

/**
 * @author xiaofei.wxf
 */
public abstract class InterruptSupport implements InterruptAble {
    private volatile boolean interrupted = false;
    private Interruptible interruptor = new Interruptible() {

        @Override
        public void interrupt() {

        }
    };

    // -- sun.misc.SharedSecrets --
    static void blockedOn(Interruptible intr) { // package-private
        sun.misc.SharedSecrets.getJavaLangAccess().blockedOn(Thread.currentThread(), intr);
    }

    public final boolean execute() throws InterruptedException {
        try {
            blockedOn(interruptor); // 位置1
            if (Thread.currentThread().isInterrupted()) { // 立马被interrupted
                //interruptor.interrupt(Thread.currentThread());
            }
            // 执行业务代码
            bussiness();


        } finally {
            blockedOn(null);   // 位置2
        }

        return interrupted;
    }

    public abstract void bussiness();

    public abstract void interrupt(Thread thread);
}
