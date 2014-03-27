package classloader;

/**
 * @author xiaofei.wxf
 */
public class LoadClass {

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("classloader.SingletonA");
        SingletonA.getA();
    }
}
