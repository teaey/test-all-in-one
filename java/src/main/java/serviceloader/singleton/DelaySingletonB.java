package serviceloader.singleton;

/**
 * @author xiaofei.wxf
 */
public class DelaySingletonB {
    private DelaySingletonB() {
    }

    private static class Holder {
        static final DelaySingletonB i = new DelaySingletonB();
    }

    public static DelaySingletonB get() {
        return Holder.i;
    }

    static final DelaySingletonA a = DelaySingletonA.get();
}
