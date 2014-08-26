package consistence;

/**
 * <p>Company: www.taobao.com</p>
 *
 * @author wxf
 * @date 14-7-21
 */
public class JdkHashFunction implements HashFunction{
    @Override
    public int hash(Object key) {
        return key.hashCode();
    }
}
