package com.verdant.demo.common.socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Author: verdant
 * Desc:   Socket工具类
 */
public class SocketUtils2 {
    /**
     * 从SocketChannel中读取数据
     *
     * @param sc
     * @throws IOException
     */
    public static String readFromChannel(SocketChannel sc) throws IOException {
        String message = "";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
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
}