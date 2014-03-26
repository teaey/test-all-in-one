import org.junit.Test;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import xiaofei.asm.modify.AddTimeClassAdapter;
import xiaofei.asm.modify.C;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * User: xiaofei.wxf
 * Date: 13-11-6 上午11:26
 */
public class Generator extends ClassLoader {
    public static void main(String[] args)
        throws IOException, IllegalAccessException, InstantiationException {
        ClassReader cr = new ClassReader("xiaofei/asm/modify/C");
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassAdapter classAdapter = new AddTimeClassAdapter(cw);
        //使给定的访问者访问Java类的ClassReader
        cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
        byte[] data = cw.toByteArray();
        File file =
            new File("D:/projects/xiaofei/target/classes/xiaofei/asm/create/modify/C.class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();
        System.out.println("success!");
    }

    @Test
    public void test() throws InterruptedException {
        C c = new C();
        c.m();
    }
}
