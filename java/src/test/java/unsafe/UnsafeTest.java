package unsafe;

import org.junit.Test;
import sun.misc.VM;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xiaofei.wxf
 * @date 13-12-26
 */
public class UnsafeTest {
    @Test
    public void testBits() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method swapInt = Class.forName("java.nio.Bits").getDeclaredMethod("swap", Integer.TYPE);
        swapInt.setAccessible(true);
        Method swapShort = Class.forName("java.nio.Bits").getDeclaredMethod("swap", Integer.TYPE);
        swapShort.setAccessible(true);

//        int x1 = Integer.MIN_VALUE;
//        System.out.println(Integer.toBinaryString(x1));
//        System.out.println(Integer.toBinaryString((x1 >> 8)));
        int s1 = -32;
        System.out.println((short) s1 << 8);
        System.out.println(Integer.toBinaryString(-32));
        int x1 = Short.MIN_VALUE;
        System.out.println(Integer.valueOf("1110000011111111", 2));
//        System.out.println(Integer.toBinaryString(x1));
//        System.out.println(Integer.toBinaryString(x1 >>> 16));
        for (short i = Short.MIN_VALUE / 1000; i <= Short.MAX_VALUE / 1000; i++) {
//            System.out.println(Integer.toBinaryString(i));
//            System.out.println(Integer.toBinaryString(i >>> 16));
            short i1 = swap(i);
            short i2 = swap1(i);
            if (i1 != i2) {
                System.out.println(i + " " + i1 + " " + i2);
            }
        }
    }

    @Test
    public void testUnsafe() {
        System.out.println(VM.maxDirectMemory());
    }

    @Test
    public void allocateMemory() {
        long addr = UnsafeAccessor.get().allocateMemory(1024);
        for (int i = 0; i < 1024; i++) {
            System.out.print(UnsafeAccessor.get().getByte(addr + i) + " ");
        }
        System.out.println();
        UnsafeAccessor.get().setMemory(addr, 1024, (byte) 88);
        for (int i = 0; i < 1024; i++) {
            System.out.print(UnsafeAccessor.get().getByte(addr + i) + " ");
        }
        System.out.println();
        addr = UnsafeAccessor.get().reallocateMemory(addr, 2048);
        for (int i = 0; i < 2048; i++) {
            System.out.print(UnsafeAccessor.get().getByte(addr + i) + " ");
        }
        long ad2 = UnsafeAccessor.get().allocateMemory(1024);
        System.out.println();
        for (int i = 0; i < 2048; i++) {
            System.out.print(UnsafeAccessor.get().getByte(ad2 + i) + " ");
        }
        System.out.println();
        System.out.println(addr);
        UnsafeAccessor.get().putInt(addr, 8232);
        System.out.println(UnsafeAccessor.get().getInt(addr));
        UnsafeAccessor.get().freeMemory(addr);
    }

    @Test
    public void compareAndSwapObject() {
        Object o = new Object();
        Object o2 = null;
        UnsafeAccessor.get().putObject(o2, 0, o);
        System.out.println(o == o2);
    }

    static short swap(short x) {
        return (short) ((x << 8) | ((x >> 8) & 0xff));
    }

    static int swap(int x) {
        return (int) ((swap((short) x) << 16) |
                (swap((short) (x >> 16)) & 0xffff));
    }

    static short swap1(short x) {
        return (short) ((x << 8) | (x >>> 8));
    }

    static int swap1(int x) {
        return (int) ((swap((short) x) << 16) |
                (swap((short) (x >>> 16))));
    }

}
