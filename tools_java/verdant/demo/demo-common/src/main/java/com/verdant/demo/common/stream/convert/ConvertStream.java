package com.verdant.demo.common.stream.convert;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * Created by Administrator on 2016/4/8.
 */
public class ConvertStream {

    /**
     * String转InputStream
     *
     * @param str
     * @param charsetName
     * @return
     */
    public InputStream stringToStream(String str, String charsetName) throws UnsupportedEncodingException {
        charsetName = checkCharset(charsetName);
        InputStream is = new ByteArrayInputStream(str.getBytes(charsetName));
        return is;
    }

    /**
     * InputStream转String
     * 方法一：使用BufferedReader
     *
     * @param is
     * @return
     */
    public String streamToString1(InputStream is, String charsetName) throws UnsupportedEncodingException {
        charsetName = checkCharset(charsetName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, charsetName));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * InputStream转String
     * 方法二：直接用InputStream的read方法
     *
     * @param is
     * @return
     * @throws IOException
     */
    public String streamToString2(InputStream is, String charsetName) {
        charsetName = checkCharset(charsetName);
        StringBuffer out = new StringBuffer();
        byte[] buffer = new byte[4096];
        int n = -1;
        try {
            while ((n = is.read(buffer)) != -1) {
                out.append(new String(buffer, 0, n, charsetName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    /**
     * InputStream转String
     * 方法三：使用ByteArrayOutputStream
     * 备注：中间使用BufferInputStream可以提高大块数据读取效率（不用也行）
     *
     * @param is
     * @return
     */
    public static String streamToString3(InputStream is, String charsetName) throws UnsupportedEncodingException {
        charsetName = checkCharset(charsetName);
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] buffer = new byte[4096];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int n = -1;
        try {
            while ((n = bis.read(buffer)) != -1) {
                baos.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                baos.flush();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new String(baos.toByteArray(), charsetName);
    }

    /**
     * 字符集检查
     * @param charsetName
     * @return
     */
    private static String checkCharset(String charsetName) {
        if (StringUtils.isEmpty(charsetName))
            return "UTF-8";
        return charsetName;
    }

}
