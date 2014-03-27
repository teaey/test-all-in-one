package serviceloader;

/**
 * @author xiaofei.wxf
 */
public class SingletonA {
    private SingletonA(){}
    static final SingletonA a = new SingletonA();
    static final SingletonB b = SingletonB.b;

}
