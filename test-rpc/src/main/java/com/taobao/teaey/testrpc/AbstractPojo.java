package com.taobao.teaey.testrpc;

/**
 * @author xiaofei.wxf
 */
public abstract class AbstractPojo<T extends AbstractPojo<T>> {
    public abstract void get(T t);
}
