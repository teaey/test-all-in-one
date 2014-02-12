package com.taobao.teaey.protobuf.service;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.taobao.teaey.protobuf.PbDemoProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaofei.wxf on 14-2-12.
 */
public class PbServices {
    private static final Logger log = LoggerFactory.getLogger(PbServices.class);

    public static class PbBlockingService implements PbDemoProto.LoginService.BlockingInterface {
        @Override
        public PbDemoProto.Login_S2C login(RpcController controller, PbDemoProto.Login_C2S request) throws ServiceException {
            log.debug("<--- mName=login, request={}", request);
            return PbDemoProto.Login_S2C.newBuilder().setTimestamp(System.currentTimeMillis()).build();
        }
    }

    public static class PbService implements PbDemoProto.LoginService.Interface{

        @Override
        public void login(RpcController controller, PbDemoProto.Login_C2S request, RpcCallback<PbDemoProto.Login_S2C> done) {
            done.run(PbDemoProto.Login_S2C.newBuilder().setTimestamp(System.currentTimeMillis()).build());
        }
    }
}
