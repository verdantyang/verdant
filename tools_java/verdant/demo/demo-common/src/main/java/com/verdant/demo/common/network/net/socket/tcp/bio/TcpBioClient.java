package com.verdant.demo.common.network.net.socket.tcp.bio;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Author: verdant
 * Desc:   TCP BIO客户端
 */
public class TcpBioClient {
    private static final String HOST = "127.0.0.1";
    private static final Integer PORT_SERVER = 7888;
    private static final String END = "quit";

    private volatile boolean flag = true;

    private Socket socket;

    public TcpBioClient() throws IOException {
        socket = new Socket(HOST, PORT_SERVER);
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter clientOut = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while (flag) {
            System.out.println("Please enter the send message:");
            String clientSay = systemIn.readLine();
            if (StringUtils.isEmpty(clientSay)) {
                continue;
            } else if (END.equalsIgnoreCase(clientSay)) {
                flag = false;
                break;
            }
            clientOut.println(clientSay);
            System.out.println("Server reply：" + serverIn.readLine());
        }
        System.out.println("Client quit !");
        serverIn.close();
        clientOut.close();
        systemIn.close();
        socket.close();
        System.exit(0);
    }

    public static void main(String[] args) {
        try {
            new TcpBioClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}