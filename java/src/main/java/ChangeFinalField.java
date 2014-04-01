import java.lang.reflect.Field;

/**
 * @author xiaofei.wxf
 */
public class ChangeFinalField {
    //static final int i = 1;
    final int i = 1;

    public int get(){
        return i;
    }

    /**
     * 1. 通过反射设置final字段，设置的值，只能通过反射可见，get方法或者直接访问仍然返回原值，并且在设置前，必须setAccessible为true
     * 2. 通过反射获取静态final字段，抛异常
     * @param args
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        ChangeFinalField cff = new ChangeFinalField();
        System.out.println("改变前:" + cff.i);
        Field f = ChangeFinalField.class.getDeclaredField("i");
        f.setAccessible(true);
        f.set(cff, 23232);
        System.out.println("反射获取:" + f.get(cff));
        System.out.println("直接获取:" + cff.get());
    }
}
