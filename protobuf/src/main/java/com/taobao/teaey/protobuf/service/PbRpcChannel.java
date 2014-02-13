package com.taobao.teaey.protobuf.service;

import com.google.protobuf.*;
import com.taobao.teaey.protobuf.PbDemoProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaofei.wxf on 14-2-12.
 */
public class PbRpcChannel implements RpcChannel, BlockingRpcChannel {
    private static final Logger log = LoggerFactory.getLogger(PbRpcChannel.class);

    @Override
    public void callMethod(Descriptors.MethodDescriptor method, RpcController controller, Message request, Message responsePrototype, RpcCallback<Message> done) {
        log.debug("---> callMethod sName={}, sIndex={}, mName={}, mIndex={}", method.getService().getFullName(), method.getService().getIndex(), method.getFullName(), method.getIndex());
        done.run(PbDemoProto.Login_S2C.getDefaultInstance());
    }

    @Override
    public Message callBlockingMethod(Descriptors.MethodDescriptor method, RpcController controller, Message request, Message responsePrototype) throws ServiceException {
        log.debug("---> callBlockingMethod sName={}, sIndex={}, mName={}, mIndex={}", method.getService().getFullName(), method.getService().getIndex(), method.getFullName(), method.getIndex());
        return null;
    }
}
