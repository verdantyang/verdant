package com.verdant.demo.common.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements IHello {
    private static final String responseFormat = "<- Server response: %s";

    public HelloImpl() throws RemoteException {
    }

    @Override
    public String echo(String message) throws RemoteException {
        return String.format(responseFormat, message);
    }
}
