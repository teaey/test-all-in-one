package encrypt;

/**
 * @author xiaofei.wxf
 */
public interface EncryptionAndDecryption {
    byte[] encrypt(byte[] data) throws Exception;

    byte[] decrypt(byte[] data) throws Exception;

    String getAlgorithm();
}
