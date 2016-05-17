package com.verdant.demo.common.comm.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Administrator on 2016/5/17.
 */
public interface IHello extends Remote {

    String echo(String message) throws RemoteException;
}