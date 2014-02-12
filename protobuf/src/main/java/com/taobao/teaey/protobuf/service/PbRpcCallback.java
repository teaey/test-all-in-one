package com.taobao.teaey.protobuf.service;

import com.google.protobuf.RpcCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaofei.wxf on 14-2-12.
 */
public class PbRpcCallback implements RpcCallback {
    private static final Logger log = LoggerFactory.getLogger(PbRpcCallback.class);

    @Override
    public void run(Object parameter) {
        log.debug("<--- mName=login, request={}", parameter);
    }
}
