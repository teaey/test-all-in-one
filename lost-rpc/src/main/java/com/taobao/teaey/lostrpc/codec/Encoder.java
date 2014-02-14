package com.taobao.teaey.lostrpc.codec;

/**
 * Encoder Interface
 * 
 */
public interface Encoder {

    /**
     * Encode Object to byte[]
     */
    byte[] encode(Object object) throws Exception;

}
