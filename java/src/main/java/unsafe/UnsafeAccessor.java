package unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

/**
 * @author xiaofei.wxf
 * @date 13-12-19
 */
public class UnsafeAccessor {

    private static Unsafe UNSAFE = getUnsafe();

    public static final Unsafe getUnsafe() {
        try {
            final PrivilegedExceptionAction<Unsafe> action =
                new PrivilegedExceptionAction<Unsafe>() {
                    public Unsafe run() throws Exception {
                        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                        theUnsafe.setAccessible(true);
                        return (Unsafe) theUnsafe.get(null);
                    }
                };
            return AccessController.doPrivileged(action);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load unsafe", e);
        }
    }

    public static final Unsafe get() {
        return UNSAFE;
    }
}
