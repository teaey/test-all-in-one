package com.taobao.teaey.socket;

import io.netty.util.Recycler;

/**
 * Created by xiaofei.wxf on 14-1-27.
 */
class NettyRecycler extends Recycler<NettyRecycler> {
    @Override
    protected NettyRecycler newObject(Handle handle) {
        return null;
    }
}
