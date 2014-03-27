package classloader;

/**
 * @author xiaofei.wxf
 */
public class SingletonA {

    public SingletonA(){
        System.out.println("SingletonA...");
    }

    static class B{
        private static SingletonA a = new SingletonA();
    }

    public static SingletonA getA(){
        return B.a;
    }

}
