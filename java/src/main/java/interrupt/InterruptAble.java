package interrupt;

/**
 * @author xiaofei.wxf
 */
public interface InterruptAble {
    void interrupt(Thread thread) throws InterruptedException;
}
