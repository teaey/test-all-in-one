package serviceloader;

/**
 * @author xiaofei.wxf
 */
public class SingletonB {
    private SingletonB(){}
    static final SingletonB b = new SingletonB();
    static final SingletonA a = SingletonA.a;

}
