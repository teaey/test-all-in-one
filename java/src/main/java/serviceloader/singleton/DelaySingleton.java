package serviceloader.singleton;

/**
 * @author xiaofei.wxf
 */
public class DelaySingleton {
    public static void main(String[] args) {
        DelaySingletonA a = DelaySingletonA.get();
        DelaySingletonB b = DelaySingletonB.get();

        DelaySingletonA a1 = b.a;
        DelaySingletonB b1 = a.b;

        System.out.println();
    }
}
