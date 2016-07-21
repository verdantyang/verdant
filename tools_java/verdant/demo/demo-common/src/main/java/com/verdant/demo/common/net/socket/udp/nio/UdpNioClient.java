package com.verdant.demo.common.net.socket.udp.nio;

import com.verdant.demo.common.net.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

/**
 * UDP NIO客户端
 *
 * @author verdant
 * @since 2016/06/20
 */
public class UdpNioClient {
    private static final Logger logger = LoggerFactory.getLogger(UdpNioClient.class);

    private DatagramChannel sendChannel;

    public UdpNioClient(String host, int port) throws IOException {
        sendChannel = DatagramChannel.open();
        sendChannel.configureBlocking(false);
        SocketAddress addr = new InetSocketAddress(host, port);
        sendChannel.connect(addr);

        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            logger.info("Please enter the send message:");
            String data = systemIn.readLine();
            sendChannel.write(Charset.forName("UTF-8").encode(data));
        }
    }

    public static void main(String[] args) {
        try {
            String serverHost = System.getProperty("host", "127.0.0.1");
            Integer serverPort = Constants.PORT_UDP_NIO_SERVER;
            new UdpNioClient(serverHost, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
