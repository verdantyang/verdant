package com.verdant.demo.common.proxy.cglib;

import com.verdant.demo.common.proxy.cglib.impl.UserImpl;
import com.verdant.demo.common.proxy.cglib.proxy.UserCglib;

/**
 * Author: verdant
 * Create: 2016/4/12
 * Func:   基于cglib的动态代理
 */
public class Client {

    public static void main(String[] args) {
        UserCglib cglib = new UserCglib();
        UserImpl userCglib = (UserImpl) cglib.getInstance(new UserImpl());
        userCglib.save();
    }
}
