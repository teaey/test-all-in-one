import org.junit.Test;

/**
 * @author xiaofei.wxf
 */
public class AESTestSample {
    @Test
    public void key() throws Exception {
        byte[] key = AESEncryptionAndDecryption.newKey(256);
        AESEncryptionAndDecryption aes = new AESEncryptionAndDecryption(key);
        aes.init();

        String str = "这是在测试AES加密";

        byte[] src = new byte[1024 * 1024 * 200];

        byte[] dest = aes.encrypt(src);

        byte[] src1 = aes.decrypt(dest);

        System.out.println("加密前长度" + src.length + " 加密后长度" + dest.length + " 解密后长度" + src1.length);

        for (int i = 0; i < src.length; i++) {
            if (src[i] != src1[i]) {
                throw new AssertionError("" + i);
            }
        }
    }
}
