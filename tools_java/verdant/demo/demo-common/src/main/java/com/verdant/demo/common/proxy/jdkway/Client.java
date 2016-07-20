package com.verdant.demo.common.proxy.jdkway;

import com.verdant.demo.common.proxy.jdkway.primary.User;
import com.verdant.demo.common.proxy.jdkway.primary.UserImpl;
import com.verdant.demo.common.proxy.jdkway.proxy.UserProxy;
import org.junit.Test;

/**
 * Author: verdant
 * Create: 2016/4/12
 * Func:   jdk提供的动态代理
 */
public class Client {
    @Test
    public void testProxy() {
        User user = new UserImpl();
        UserProxy poxy = new UserProxy();
        User userProxy = poxy.bind(user);
        userProxy.save();
    }
}
