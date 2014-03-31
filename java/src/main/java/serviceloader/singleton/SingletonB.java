package serviceloader.singleton;

/**
 * @author xiaofei.wxf
 */
public class SingletonB {
    private SingletonB(){}
    static final SingletonB b = new SingletonB();
    static{
        b.a = SingletonA.a;
    }
    SingletonA a;
}
