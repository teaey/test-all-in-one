package com.taobao.teaey.lostrpc;

/**
 * Created by xiaofei.wxf on 14-2-13.
 */
public interface Dispatcher<C, T> {
    void dispatcher(C c, T p);
}
