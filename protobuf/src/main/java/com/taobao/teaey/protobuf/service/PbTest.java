package com.taobao.teaey.protobuf.service;

import com.google.protobuf.BlockingService;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Service;
import com.google.protobuf.ServiceException;
import com.taobao.teaey.protobuf.PbDemoProto;

/**
 * Created by xiaofei.wxf on 14-2-12.
 */
public class PbTest {
    public static void main(String[] args) throws ServiceException, ClassNotFoundException {
        testClient();
        testServer();
    }

    public static void testServer() throws ServiceException, ClassNotFoundException {
        PbRpcController controller = new PbRpcController();

        BlockingService blockingService = PbDemoProto.LoginService
            .newReflectiveBlockingService(new PbServices.PbBlockingService());

        Descriptors.ServiceDescriptor descriptor = blockingService.getDescriptorForType();

        System.out.println(
            descriptor.getFullName() + " " + descriptor.getName() + " " + descriptor.getFile()
                .getName() + " " + descriptor.getIndex() + " " + descriptor.getOptions());

        Service service = PbDemoProto.LoginService.newReflectiveService(new PbServices.PbService());

        Descriptors.MethodDescriptor md =
            blockingService.getDescriptorForType().findMethodByName("login");

        System.out.println(blockingService.callBlockingMethod(md, controller,
            PbDemoProto.Login_C2S.newBuilder().setTimestamp(System.currentTimeMillis()).build()));

        service.callMethod(md, controller,
            PbDemoProto.Login_C2S.newBuilder().setTimestamp(System.currentTimeMillis()).build(),
            new PbRpcCallback());
    }

    public static void testClient() throws ServiceException {
        PbRpcChannel channel = new PbRpcChannel();
        PbDemoProto.LoginService.Interface noBlockingService =
            PbDemoProto.LoginService.newStub(channel);
        PbDemoProto.LoginService.BlockingInterface blockingService =
            PbDemoProto.LoginService.newBlockingStub(channel);

        PbRpcCallback<PbDemoProto.Login_S2C> pbr = new PbRpcCallback<PbDemoProto.Login_S2C>();

        PbRpcController controller = new PbRpcController();

        noBlockingService.login(controller,
            PbDemoProto.Login_C2S.newBuilder().setTimestamp(System.currentTimeMillis()).build(),
            pbr);

        blockingService.login(controller,
            PbDemoProto.Login_C2S.newBuilder().setTimestamp(System.currentTimeMillis()).build());
    }
}
