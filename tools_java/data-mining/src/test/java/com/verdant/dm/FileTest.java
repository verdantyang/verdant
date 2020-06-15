package com.verdant.dm;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * <p>文件名称：FileTest.java</p>
 * <p>文件描述：</p>
 * <p>其他说明： </p>
 * <p>版权所有： 版权所有(C)2016-2099</p>
 * <p>公   司： 新华智云 </p>
 * <p>完成日期：2019-01-04</p>
 *
 * @author yangcongyu@shuwen.com
 * @version 1.0
 */
public class FileTest {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        //相对路径(相对于项目工程为根目录)
        //FileInputStream fiStream = new FileInputStream("src\\text1\\FileInputStreamText.java");
        //绝对路径
        FileInputStream fiStream = new FileInputStream("dict/CustomDictionary.txt");
        //创建一个长度为1024字节的数组 存储
        byte[] bs = new byte[1024];
        //用于保存实际读取的字节数
        int hasread = 0;
        while ((hasread = fiStream.read(bs)) > 0) {
            //
            System.out.println(new String(bs, 0, hasread));
        }
        fiStream.close();//关闭
    }
}
