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
 * BIO服务端线程
 *
 * @author verdant
 * @since 2016/06/20
 */
public class ServerConnThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ServerConnThread.class);

    private static final Integer TIME_OUT = 2000;

    private Socket server;
    private volatile boolean flag = true;

    public ServerConnThread(Socket server) {
        this.server = server;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(server.getInputStream()));
//            client.setSoTimeout(TIME_OUT);
            while (flag) {
                String clientSay = reader.readLine();
                if (StringUtils.isEmpty(clientSay)) {
                    break;
                }
                if (Constants.COMMAND_END.equals(clientSay)) {
                    flag = false;
                    break;
                }
                PrintWriter out = new PrintWriter(server.getOutputStream(), true);
                logger.info("Client send：" + clientSay);
                out.println("Get message (" + clientSay + ")");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
