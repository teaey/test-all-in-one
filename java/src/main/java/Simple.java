import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * <p>Company: www.taobao.com</p>
 *
 * @author wxf
 * @date 14-6-16
 */
public class Simple implements java.io.Serializable {
    private String name;
    private int age;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    static Simple getSimple() {
        Simple simple = new Simple();
        simple.setAge(10);
        simple.setName("XiaoMing");
        return simple;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(false);
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(new FileOutputStream("file.bin"));
        kryo.writeClassAndObject(output, Simple.getSimple());
        output.flush();
        byte[] bytes = baos.toByteArray();
        for(byte b : bytes){
            System.out.println(Integer.toHexString(b));
        }
    }
}
