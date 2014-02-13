package com.taobao.teaey.lostrpc;

/**
 * Created by xiaofei.wxf on 14-2-13.
 */
public interface Dispatcher<T> {
    void dispatcher(T p);
}
