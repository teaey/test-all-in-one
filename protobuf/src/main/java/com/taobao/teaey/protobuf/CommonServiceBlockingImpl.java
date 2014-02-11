package com.taobao.teaey.protobuf;

import com.google.protobuf.BlockingService;
import com.google.protobuf.Descriptors;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import sun.reflect.Reflection;

import java.sql.Ref;

/**
 * Created by xiaofei.wxf on 14-1-27.
 */
public class CommonServiceBlockingImpl implements ProtobufProto.CommonService.BlockingInterface {

    @Override
    public ProtobufProto.Login_S2C login(RpcController controller, ProtobufProto.Login_C2S request) throws ServiceException {
        System.out.println("Request:" + request);
        Class s = Reflection.getCallerClass();
        return ProtobufProto.Login_S2C.newBuilder().build();
    }

    public static void main(String[] args) throws ServiceException {
        BlockingService service = ProtobufProto.CommonService.newReflectiveBlockingService(new CommonServiceBlockingImpl());
        Descriptors.MethodDescriptor methodDescriptor = service.getDescriptorForType().findMethodByName("login");
        Object o = service.callBlockingMethod(methodDescriptor, null, ProtobufProto.Login_C2S.newBuilder().build());
        System.out.println("Response:" + o);
        Class s = Reflection.getCallerClass();
        System.out.println(s);
    }
}
