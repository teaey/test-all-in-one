import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import xiaofei.asm.modify.AopAo;
import xiaofei.asm.modify.AopClassAdaptor;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * User: xiaofei.wxf
 * Date: 13-11-6 上午9:33
 */
public class Sample extends ClassLoader {
    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException {
        ClassReader cr = new ClassReader("xiaofei/asm/modify/AopAo");
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cr.accept(new AopClassAdaptor(cw), ClassReader.SKIP_DEBUG);
        byte[] classBytecode = cw.toByteArray();
        FileOutputStream fos = new FileOutputStream("D:\\projects\\xiaofei\\target\\classes\\xiaofei\\asm\\create\\modify\\AopAo.class");
        fos.write(classBytecode);
        fos.flush();
        fos.close();
    }

    @Test
    public void test() {
        AopAo aopAo = new AopAo();
        aopAo.exec();
    }
}
