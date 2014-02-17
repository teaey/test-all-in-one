package com.taobao.teaey.lostrpc.hsf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author xiaofei.wxf
 * @date 14-1-13
 */
public class LoginServiceImpl implements LoginService {
    static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
    @Override
    public void unreturn(Date d) {
        log.info("unreturn {}", d);
    }

    @Override
    public Date withreturn(Date d) {
        log.info("withreturn {}", d);
        return d;
    }

    public static void main(String[] args) {
        new LoginServiceImpl().unreturn(new Date());
    }
}
