package com.verdant.demo.common.net.socket.udp.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

/**
 * Author: verdant
 * Desc:   UDP NIO客户端
 */
public class UdpNioClient {
    private static final String HOST = "127.0.0.1";
    private static final Integer PORT_SERVER = 7001;

    private DatagramChannel sendChannel;

    public UdpNioClient() throws IOException {
        sendChannel = DatagramChannel.open();
        sendChannel.configureBlocking(false);
        SocketAddress addr = new InetSocketAddress(HOST, PORT_SERVER);
        sendChannel.connect(addr);

        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Please enter the send message:");
            String data = systemIn.readLine();
            sendChannel.write(Charset.forName("UTF-8").encode(data));
        }
    }

    public static void main(String[] args) {
        try {
            new UdpNioClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
