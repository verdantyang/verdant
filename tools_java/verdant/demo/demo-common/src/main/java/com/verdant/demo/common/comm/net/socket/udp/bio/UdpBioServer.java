package com.verdant.demo.common.comm.net.socket.udp.bio;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Author: verdant
 * Desc:   UDP BIO服务端
 */
public class UdpBioServer {
    private static final Integer PORT_SERVER = 7001;
    private static final Integer readBufferSize = 1024;

    private DatagramSocket serverSocket;

    public UdpBioServer() throws IOException {
        serverSocket = new DatagramSocket(PORT_SERVER);
        System.out.println("Server listen on port: " + PORT_SERVER);

        while (true) {
            byte[] buffer = new byte[readBufferSize];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            serverSocket.receive(receivePacket);
            if (buffer.length > 0)
                System.out.println("Get message (" + new String(buffer, "UTF-8") + ")");
        }
    }

    public static void main(String[] args) {
        try {
            new UdpBioServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
