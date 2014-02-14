package com.taobao.teaey.lostrpc.codec;

/**
 * Decoder Interface
 */
public interface Decoder {

    /**
     * decode byte[] to Object
     */
    Object decode(byte[] bytes) throws Exception;

}
