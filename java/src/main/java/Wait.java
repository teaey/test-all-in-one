import sun.nio.ch.Interruptible;

import java.io.IOException;

/**
 * @author xiaofei.wxf
 */
public class Wait {
    public static void main(String[] args) throws InterruptedException, IOException {
        sun.misc.SharedSecrets.getJavaLangAccess()
            .blockedOn(Thread.currentThread(), (Interruptible) new Object());
    }
}
