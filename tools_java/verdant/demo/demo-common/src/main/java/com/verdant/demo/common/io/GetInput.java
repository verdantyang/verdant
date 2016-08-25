package com.verdant.demo.common.io;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * 获取输入
 *
 * @author verdant
 * @since 2016/07/14
 */
public class GetInput {
    /**
     * 获取系统输入
     *
     * @throws IOException
     */
    public void getSystemIn() throws IOException {
        //方法一：使用System.in.read()读取字符
        char i = (char) System.in.read();

        //方法二：使用BufferedReader接收一个字符串
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str1 = br.readLine();

        //方法三：使用Scanner
        Scanner sc = new Scanner(System.in);
        String str2 = sc.nextLine();
    }

    /**
     * 从文件中获取字节流
     *
     * @throws Exception
     */
    public void inputFromFile(String path) throws Exception {
        //方法一：使用System.in.read()读取字符
        FileInputStream fi = new FileInputStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(fi, "GBK"));

        //方法二：使用Scanner
        Scanner in = new Scanner(new File(path));
    }

    /**
     * 文件输出
     *
     * @param path
     * @throws Exception
     */
    public void outputToFile(String path) throws Exception {
        PrintWriter out = new PrintWriter("myfile.txt");
    }

    /**
     * 从URL中获取字节流
     *
     * @param address
     * @throws Exception
     */
    public void inputFromUrl(String address) throws Exception {
        URL url = new URL(address);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream in = connection.getInputStream();
    }



    public static void main(String[] args) {

    }
}
