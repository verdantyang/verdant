package com.verdant.demo.common.buffer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

/**
 * 共享内存
 *
 * @author verdant
 * @since 2016/11/08
 */
public class ShareMemory {
    /**
     * 获取文件映射
     *
     * @throws IOException
     */
    public void getShareBuffer(String filename) throws IOException {
        //获得一个可读写的随机存取文件对象
        RandomAccessFile RAFile = new RandomAccessFile(filename, "rw");
        //获得相应的文件通道
        FileChannel fc = RAFile.getChannel();
        long size = fc.size();
        //获得共享内存缓冲区，该共享内存可读写
        MappedByteBuffer mapBuf = fc.map(FileChannel.MapMode.READ_WRITE, 0, size);
        //获取头部消息：存取权限
        int mode = mapBuf.getInt();
    }

    public boolean startWrite(int mode, MappedByteBuffer mapBuf) {
        if (mode == 0) {     //存储模式是0，表示可写
            mode = 1;        //意味着别的应用不可写
            mapBuf.flip();
            mapBuf.putInt(mode);  //写入共享内存的头部信息
            return true;
        } else {
            //表明已经有应用在写该共享内存了，本应用不能够针对共享内存再做写操作
            return false;
        }
    }

    public boolean stopWrite(int mode, MappedByteBuffer mapBuf) {
        mode = 0;  //释放写权限
        mapBuf.flip();
        mapBuf.putInt(mode);  //写入共享内存头部信息
        return true;
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                // 关闭
            }

        });
    }

    public static void main(String[] args) {

    }
}
