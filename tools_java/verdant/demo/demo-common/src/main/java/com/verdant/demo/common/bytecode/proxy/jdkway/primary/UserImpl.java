package com.verdant.demo.common.bytecode.proxy.jdkway.primary;

/**
 * Author: verdant
 * Create: 2016/4/12
 * Desc:   实例化
 */
public class UserImpl implements User {
    @Override
    public void save() {
        System.out.println("User saved!");
    }
}
