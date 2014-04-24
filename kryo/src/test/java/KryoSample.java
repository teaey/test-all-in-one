import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.junit.Test;

/**
 * @author xiaofei.wxf
 */
public class KryoSample {
    static class C1{
        C1(){

        }
    }
    @Test
    public void t1(){
        Kryo kryo = new Kryo();
        Output output = new Output(1024);
        kryo.writeClassAndObject(output, new C1());

        byte[] bytes = output.toBytes();
        output.close();
        Input input = new Input(bytes);

        Object o = kryo.readClassAndObject(input);

        System.out.println(o);
    }
}
