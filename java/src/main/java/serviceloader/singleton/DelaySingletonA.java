package serviceloader.singleton;

/**
 * @author xiaofei.wxf
 */
public class DelaySingletonA {
    private DelaySingletonA() {
    }

    private static class Holder {
        static final DelaySingletonA i = new DelaySingletonA();
    }

    public static DelaySingletonA get() {
        return Holder.i;
    }
    static final DelaySingletonB b = DelaySingletonB.get();
}
