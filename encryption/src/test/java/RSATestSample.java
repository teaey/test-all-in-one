import encrypt.RSAEncryptionAndDecryption;
import org.junit.Test;

/**
 * @author xiaofei.wxf
 */
public class RSATestSample {

    @Test
    public void rsaPubkey() throws Exception {
        System.out.println("-----");
        RSAEncryptionAndDecryption encryptionAndDecryption = new RSAEncryptionAndDecryption();
        encryptionAndDecryption.loadPubkeyFromFile("D:\\Workspace\\test-all-in-one\\encryption\\src\\main\\resources\\rsapubkey.pem");
        encryptionAndDecryption.loadPrivkeyFromFile("D:\\Workspace\\test-all-in-one\\encryption\\src\\main\\resources\\rsa_pkcs8.pem");
        //String srcStr = "这就是个测试";
        byte[] src = new byte[245];

        byte[] dest = encryptionAndDecryption.encrypt(src);

        System.out.println("加密前长度:" + src.length + " 加密后长度:" + dest.length);

        System.out.println("src_b:" + new String(src));
        System.out.println("dest:" + new String(dest));

        System.out.println("src_a:" + new String(encryptionAndDecryption.decrypt(dest)));



    }
}
