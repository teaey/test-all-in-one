package com.taobao.teaey.protobuf;

import com.google.protobuf.*;

/**
 * Created by xiaofei.wxf on 14-1-27.
 */
public class CommonServiceNonBlockingImpl implements ProtobufProto.CommonService.Interface {
    @Override
    public void login(RpcController controller, ProtobufProto.Login_C2S request, RpcCallback<ProtobufProto.Login_S2C> done) {

    }

    public static void main(String[] args) throws ServiceException {
        Service service = ProtobufProto.CommonService.newReflectiveService(new CommonServiceNonBlockingImpl());
        Descriptors.MethodDescriptor methodDescriptor = service.getDescriptorForType().findMethodByName("login");
        service.callMethod(methodDescriptor, null, ProtobufProto.Login_C2S.newBuilder().build(), null);

        ProtobufProto.CommonService.Stub stub = ProtobufProto.CommonService.Stub.newStub(null);
    }
}
