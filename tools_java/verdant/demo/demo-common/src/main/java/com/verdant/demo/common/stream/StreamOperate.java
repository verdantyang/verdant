package com.verdant.demo.common.stream;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * Created by Administrator on 2016/4/6.
 */
public class StreamOperate {

    public InputStream getInputStream(String string) {
        InputStream is = new ByteArrayInputStream(string.getBytes());
        return is;
    }

    public ByteArrayOutputStream getOutputStream() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        return bos;
    }

    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] bs1 = new byte[]{1, 2, 3, 4, 5};
        out.write(bs1);

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        byte[] bs2 = new byte[1024];
        int len = in.read(bs2);
        for (int i = 0; i < len; i++) {
            System.out.println(bs2[i]);
        }
        PipedInputStream pis = new PipedInputStream();
    }
}
