package com.verdant.demo.common.network.net.socket.tcp.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author: verdant
 * Desc:   TCP BIO服务端
 */
public class TcpBioServer {
    private static final Integer PORT_SERVER = 7888;

    private volatile Integer clientCounter = 0;

    private ServerSocket serverSocket;
    private Socket socket;

    public TcpBioServer() throws IOException {
        serverSocket = new ServerSocket(PORT_SERVER);
        System.out.println("Server listen on port: " + PORT_SERVER);

        while (true) {
            socket = serverSocket.accept();
            System.out.println("Client counter：" + (++clientCounter));
            new Thread(new ServerConnThread(socket)).start();
        }
    }

    public static void main(String[] args) {
        try {
            new TcpBioServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
