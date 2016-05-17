package com.verdant.demo.common.comm.rmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Author: verdant
 * Desc:   RMI客户端
 */
public class RmiClient {
    private static final Logger log = LoggerFactory.getLogger(RmiClient.class);
    private static final String HOST_SERVER = "localhost";
    private static final Integer PORT_SERVER = 7888;
    private static final String PATH_METHDO = "RHello";

    public RmiClient(Integer port, String path) {
        try {
            Registry registry = LocateRegistry.getRegistry(HOST_SERVER, port);
            IHello rhello = (IHello) registry.lookup(path);
//            IHello rhello = (IHello) Naming.lookup("rmi://localhost:8888/RHello");
            System.out.println(rhello.echo("hello"));
        } catch (NotBoundException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new RmiClient(PORT_SERVER, PATH_METHDO);
    }
}
