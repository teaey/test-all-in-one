package com.taobao.teaey.lostrpc.hsf;

import com.taobao.hsf.util.RequestCtxUtil;

/**
 * @author xiaofei.wxf
 * @date 14-1-15
 */
public class Tmp {
    public static void main(String[] args) {
        String ip = RequestCtxUtil.getClientIp();
        System.out.println(ip);
    }
}
