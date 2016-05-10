package com.verdant.demo.common.socket.tcp.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author: verdant
 * Desc:   TCP BIO服务端
 */
public class TcpBioServer {
    private static final Integer PORT = 7888;

    private volatile Integer clientCounter = 0;

    private ServerSocket serverSocket;
    private Socket socket;

    public TcpBioServer() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Server listen on port: " + PORT);

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
