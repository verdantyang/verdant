package com.verdant.demo.common.socket.udp.bio;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Author: verdant
 * Desc:   UDP BIO服务端
 */
public class UdpBioServer {
    private static final Integer PORT_SERVER = 7001;
    private static final Integer BUFFER_SIZE = 1024;

    public UdpBioServer() throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(PORT_SERVER);
        System.out.println("Server listen on port: " + PORT_SERVER);

        while (true) {
            byte[] buffer = new byte[BUFFER_SIZE];
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
