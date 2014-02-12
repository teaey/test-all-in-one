package com.taobao.teaey.protobuf.service;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

/**
 * Created by xiaofei.wxf on 14-2-12.
 */
public class PbRpcController implements RpcController {
    @Override
    public void reset() {

    }

    @Override
    public boolean failed() {
        return false;
    }

    @Override
    public String errorText() {
        return null;
    }

    @Override
    public void startCancel() {

    }

    @Override
    public void setFailed(String reason) {

    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public void notifyOnCancel(RpcCallback<Object> callback) {

    }
}
