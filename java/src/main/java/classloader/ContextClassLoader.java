package classloader;

/**
 * Created by xiaofei.wxf on 2014/3/19.
 */
public class ContextClassLoader {
    public static void main(String[] args) {
        ClassLoader cl1 = Thread.currentThread().getContextClassLoader();
        ClassLoader cl2 = ContextClassLoader.class.getClassLoader();
        ClassLoader cl3 = ClassLoader.getSystemClassLoader().getParent();
        System.out.println(cl1 == cl2);
    }
}
