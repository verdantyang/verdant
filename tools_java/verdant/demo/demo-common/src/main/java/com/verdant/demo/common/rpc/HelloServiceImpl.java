package com.verdant.demo.common.rpc;

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "Display " + name;
    }
}
