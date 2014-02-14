package com.taobao.teaey.lostrpc.codec;

/**
 * Created by xiaofei.wxf on 14-2-14.
 */
public class Hessian2Codec implements Codec {
    private Hessian2Codec() {
    }

    public static final Hessian2Codec INSTANCE = new Hessian2Codec();

    @Override
    public Object decode(byte[] bytes) throws Exception {
        return null;
    }

    @Override
    public byte[] encode(Object object) throws Exception {
        return new byte[0];
    }
}
