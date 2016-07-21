package com.verdant.demo.common.net.socket.udp.bio;

import com.verdant.demo.common.net.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * UDP BIO客户端
 *
 * @author verdant
 * @since 2016/06/20
 */
public class UdpBioClient {
    private static final Logger logger = LoggerFactory.getLogger(UdpBioClient.class);

    private DatagramSocket socket;

    public UdpBioClient(String host, int port) throws IOException {
        socket = new DatagramSocket();
        SocketAddress addr = new InetSocketAddress(host, port);
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            logger.info("Please enter the send message:");
            byte[] datas = systemIn.readLine().getBytes();
            DatagramPacket packet = new DatagramPacket(datas, datas.length, addr);
            socket.send(packet);
        }
    }

    public static void main(String[] args) {
        try {
            String serverHost = System.getProperty("host", "127.0.0.1");
            Integer serverPort = Constants.PORT_UDP_BIO_SERVER;
            new UdpBioClient(serverHost, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
