package com.verdant.demo.common.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

/**
 * Author: verdant
 * Desc:   UPushbackStream 类使用示例
 */
public class UPushbackStream {
    /**
     * 读取字符和ASCII码混合的文件
     * @param fileName
     */
    public void readFile(String fileName){
        try {
            PushbackInputStream pushbackInputStream =
                    new PushbackInputStream(new FileInputStream(fileName));

            byte[] array = new byte[2];

            int tmp = 0;
            int count = 0;
            while ((count = pushbackInputStream.read(array)) != -1) {
                //两个字节转换为整数
                tmp = (short) ((array[0] << 8) | (array[1] & 0xff));
                tmp = tmp & 0xFFFF;

                //判断是否为BIG5，如果是则显示BIG5中文字
                if (tmp >= 0xA440 && tmp < 0xFFFF) {
                    System.out.println("BIG5:" + new String(array));
                } else {
                    //将第二个字节推回流
                    pushbackInputStream.unread(array, 1, 1);
                    //显示ASCII范围的字符
                    System.out.println("ASCII: " + (char) array[0]);
                }
            }
            pushbackInputStream.close();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("请指定文件名称");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new UPushbackStream().readFile(args[0]);
    }
}
