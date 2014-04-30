package serviceloader.singleton;

/**
 * @author xiaofei.wxf
 */
public class Singleton {
    public static void main(String[] args) {
        SingletonA a = SingletonA.a;
        SingletonB b = SingletonB.b;

        SingletonA a1 = SingletonB.b.a;
        SingletonB b1 = SingletonA.a.b;

        System.out.println(a == a1);
        System.out.println(b == b1);
    }
}
