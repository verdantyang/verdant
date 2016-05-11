package com.verdant.demo.common.socket.utils;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Author: verdant
 * Desc:   Socket工具类
 */
public class SocketUtils2 {

    private static final Integer BUFFER_SIZE = 1024;
    private static final String FORMAT = "%s/%s send message: %s";
    /**
     * 从SocketChannel中读取数据
     *
     * @param sc
     * @throws IOException
     */
    public static String readFromChannel(SocketChannel sc) throws IOException {
        String message = "";
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        int readBytes = 0;
        try {
            int ret = 0;
            try {
                while ((ret = sc.read(buffer)) > 0) {
                    readBytes += ret;
                }
            } finally {
                buffer.flip();
            }
            if (readBytes > 0) {
                message = Charset.forName("UTF-8").decode(buffer).toString();
                buffer = null;
            }
        } finally {
            if (buffer != null) {
                buffer.clear();
            }
        }
        return message;
    }

    public static ClientMessage readFromChannel(DatagramChannel sc) throws IOException {
        ClientMessage cm = new ClientMessage();
        String message = "";
        ByteBuffer bufIn = ByteBuffer.allocate(BUFFER_SIZE);
        bufIn.clear();
        try {
            SocketAddress socketAddress = sc.receive(bufIn);      // read into buffer
            String [] address = socketAddress.toString().replace("/", "").split(":");
            cm.setHost(address[0]);
            cm.setPort(Integer.valueOf(address[1]));
            bufIn.flip();      // make buffer ready for read
            while (bufIn.hasRemaining()) {
                bufIn.get(new byte[bufIn.limit()]);// read 1 byte at a time
                message += new String(bufIn.array());
            }
            message = String.format(FORMAT, address[0], address[1], message);
            cm.setMessage(message);
        } finally {

        }
        return cm;
    }
}