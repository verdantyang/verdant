package com.verdant.demo.common.comm.rmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Author: verdant
 * Desc:   RMI服务端
 */
public class RmiServer {
    private static final Logger log = LoggerFactory.getLogger(RmiServer.class);
    private static final Integer PORT_SERVER = 7888;
    private static final String PATH_METHDO = "RHello";
    private static final String RMI_URI = "rmi://localhost:%s/%s";

    public RmiServer(Integer port, String path) {
        try {
            IHello rhello = new HelloImpl();
            Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind(path, rhello);
            //String rmiUri = String.format(RMI_URI, port, path);
            //Naming.bind(rmiUri, rhello);
            log.info("RMI Server listen on port: " + port);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new RmiServer(PORT_SERVER, PATH_METHDO);
    }
}

