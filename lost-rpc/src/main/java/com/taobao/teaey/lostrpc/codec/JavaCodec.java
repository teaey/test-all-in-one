package com.taobao.teaey.lostrpc.codec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by xiaofei.wxf on 14-2-14.
 */
public class JavaCodec implements Codec {
    private JavaCodec() {
    }

    public static final JavaCodec INSTANCE = new JavaCodec();

    @Override
    public Object decode(byte[] bytes) throws Exception {
        ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Object resultObject = objectIn.readObject();
        objectIn.close();
        return resultObject;
    }

    @Override
    public byte[] encode(Object object) throws Exception {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ObjectOutputStream output = new ObjectOutputStream(byteArray);
        output.writeObject(object);
        output.flush();
        output.close();
        return byteArray.toByteArray();
    }
}
