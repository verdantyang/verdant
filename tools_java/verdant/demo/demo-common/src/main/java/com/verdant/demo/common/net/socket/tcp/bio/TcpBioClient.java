package com.verdant.demo.common.net.socket.tcp.bio;

import com.verdant.demo.common.net.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * TCP BIO客户端
 *
 * @author verdant
 * @since 2016/06/20
 */
public class TcpBioClient {
    private static final Logger logger = LoggerFactory.getLogger(TcpBioClient.class);

    volatile boolean flag = true;

    private Socket socket;

    public TcpBioClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter clientOut = new PrintWriter(socket.getOutputStream(), true);

        while (flag) {
            logger.info("Please enter the send message:");
            String clientSay = systemIn.readLine();
            if (StringUtils.isEmpty(clientSay)) {
                continue;
            } else if (Constants.COMMAND_END.equalsIgnoreCase(clientSay)) {
                flag = false;
                break;
            }
            clientOut.println(clientSay);
            logger.info("Server reply：" + serverIn.readLine());
        }
        logger.info("Client quit !");
        clientOut.close();
        serverIn.close();
        systemIn.close();
        socket.close();
    }

    public static void main(String[] args) {
        try {
            String serverHost = System.getProperty("host", "127.0.0.1");
            Integer serverPort = Constants.PORT_TCP_BIO_SERVER;
            new TcpBioClient(serverHost, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}