package com.verdant.demo.common.net.socket.tcp.bio;

import com.verdant.demo.common.net.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP BIO服务端
 *
 * @author verdant
 * @since 2016/06/20
 */
public class TcpBioServer {
    private static final Logger logger = LoggerFactory.getLogger(TcpBioServer.class);

    private volatile Integer clientCounter = 0;

    private ServerSocket serverSocket;
    private Socket socket;

    public TcpBioServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        logger.info("Server listen on port: " + port);

        while (true) {
            socket = serverSocket.accept();
            logger.info("Client counter：" + (++clientCounter));
            new Thread(new ServerConnThread(socket)).start();
        }
    }

    public static void main(String[] args) {
        try {
            Integer serverPort = Constants.PORT_TCP_BIO_SERVER;
            new TcpBioServer(serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
