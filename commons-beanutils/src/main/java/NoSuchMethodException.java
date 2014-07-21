import org.apache.commons.beanutils.MethodUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by wxf on 14-5-13.
 */
public class NoSuchMethodException {
    public static void main(String[] args) throws java.lang.NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//        Method m = MethodUtils.getAccessibleMethod(NoSuchMethodException.class, "reflectInvoke", new Class[]{String.class});
        MethodUtils.invokeMethod(NoSuchMethodException.class, "reflectInvoke", new String[]{"aaa"});
    }
    public static void reflectInvoke(String a){
        System.out.println("reflectInvoke invoked");
    }
}
