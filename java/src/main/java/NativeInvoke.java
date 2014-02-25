/**
 * @author xiaofei.wxf
 */
public class NativeInvoke {

    static {
        //NativeInvoke.class.getClassLoader().getResource("NativeInvoke.dll");
        System.load("D:/bin/NativeInvoke.dll");
    }

    public native static long getTime();

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        System.out.println(NativeInvoke.getTime());
    }
}
