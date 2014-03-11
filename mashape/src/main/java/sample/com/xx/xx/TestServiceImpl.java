package com.xx.xx;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;

/**
 * @author xiaofei.wxf
 */
public class TestServiceImpl implements TestProto.TestService.BlockingInterface {
    @Override
    public TestProto.TestResponse_S2C testMethod(RpcController controller, TestProto.TestRequest_C2S request) throws ServiceException {
        return null;
    }
}
