package com.taobao.teaey.lostrpc.codec;

import java.nio.charset.Charset;

/**
 * Created by xiaofei.wxf on 14-2-14.
 */
public interface Codec extends Decoder, Encoder {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    @Override
    Object decode(byte[] bytes) throws Exception;

    @Override
    byte[] encode(Object object) throws Exception;
}
