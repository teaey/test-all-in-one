import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;

/**
 * @author xiaofei.wxf
 */
public class TypesafeConfig {
    public static void main(String[] args) {
        System.setProperty("simple-lib.whatever", "This value comes from a system property");
        Config conf = ConfigFactory.load("xxx");
        ConfigFactory.defaultOverrides();
        ConfigFactory.defaultReference();
        System.out.println(conf.getString("a"));
        System.out.println(conf.getString("b"));
        ConfigValue cv = conf.getValue("a");
        System.out.println(cv);

        System.out.println(conf.getConfig("c").getString("c1"));
        System.out.println(conf.getString("simple-lib.foo"));
        System.out.println(conf.getString("simple-lib.whatever"));
    }
}
