package com.verdant.demo.common.net.socket.udp.bio;

import com.verdant.demo.common.net.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * UDP BIO服务端
 *
 * @author verdant
 * @since 2016/06/20
 */
public class UdpBioServer {
    private static final Logger logger = LoggerFactory.getLogger(UdpBioServer.class);

    private static final Integer readBufferSize = 1024;

    private DatagramSocket serverSocket;

    public UdpBioServer(int port) throws IOException {
        serverSocket = new DatagramSocket(port);
        logger.info("Server listen on port: " + port);

        while (true) {
            byte[] buffer = new byte[readBufferSize];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            serverSocket.receive(receivePacket);
            if (buffer.length > 0)
                logger.info("Get message (" + new String(buffer, "UTF-8") + ")");
        }
    }

    public static void main(String[] args) {
        try {
            final Integer port = Constants.PORT_UDP_BIO_SERVER;
            new UdpBioServer(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
