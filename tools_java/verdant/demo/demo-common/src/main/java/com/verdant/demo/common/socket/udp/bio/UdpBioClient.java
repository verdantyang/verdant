package com.verdant.demo.common.socket.udp.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * Author: verdant
 * Desc:   UDP BIO客户端
 */
public class UdpBioClient {
    private static final Integer PORT_SERVER = 7001;

    private DatagramSocket socket;

    public UdpBioClient() throws IOException {
        socket = new DatagramSocket();
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        InetAddress host = Inet4Address.getLocalHost();
        while (true) {
            System.out.println("Please enter the send message:");
            byte [] datas = systemIn.readLine().getBytes();
            DatagramPacket packet = new DatagramPacket(datas, datas.length, host, PORT_SERVER);
            socket.send(packet);
        }
    }

    public static void main(String[] args) {
        try {
            new UdpBioClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
