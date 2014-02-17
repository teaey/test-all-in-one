package com.taobao.teaey.lostrpc.hsf;

import java.util.Date;

/**
 * @author xiaofei.wxf
 * @date 14-1-13
 */
public interface LoginService {
    void unreturn(Date d);
    Date withreturn(Date d);
}
