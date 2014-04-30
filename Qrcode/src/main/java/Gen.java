import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaofei.wxf
 */
public class Gen {
    public static void main(String[] args) {
        // override the image type to be JPG
        int times = 100000;
        final long s1 = System.nanoTime();
        for (int i = 0; i < times; i++) {
            ByteArrayOutputStream bos = QRCode.from("Hello World").to(ImageType.JPG).stream();
        }
        final long s2 = System.nanoTime();
        System.out.println(TimeUnit.NANOSECONDS.toMillis((s2 - s1) / times));
    }
}
