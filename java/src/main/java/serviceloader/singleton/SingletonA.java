package serviceloader.singleton;

/**
 * @author xiaofei.wxf
 */
public class SingletonA {
    private SingletonA(){}
    static final SingletonA a = new SingletonA();
    static {
        a.b  = SingletonB.b;
    }
    SingletonB b;
}
