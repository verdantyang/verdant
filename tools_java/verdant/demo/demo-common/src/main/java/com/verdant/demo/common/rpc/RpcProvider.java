package com.verdant.demo.common.rpc;

/**
 * RPC提供者
 *
 * @author verdant
 * @since 2016/08/29
 */
public class RpcProvider {
    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        RpcFramework.export(service, 1234);
    }
}
