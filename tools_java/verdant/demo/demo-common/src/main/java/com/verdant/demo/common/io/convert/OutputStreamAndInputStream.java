package com.verdant.demo.common.io.convert;

import java.io.*;

/**
 * Author: verdant
 * Create: 2016/4/13
 * Desc:   OutputStream转InputStream
 */
public abstract class OutputStreamAndInputStream {

    private static final int PIPE_BUFFER = 2048;

    /**
     * Write data on OutputStream here
     *
     * @param os
     * @return
     */
    public abstract void writeDataToOutputStream(OutputStream os);

    /**
     * Porcess data from InputStrea here
     *
     * @param is
     * @return
     */
    public abstract void processDataFromInputStream(InputStream is);

    /**
     * OutputStream转InputStream
     * 方法一：使用ByteArrayInputStream
     * 备注：适合于数据量不大，且内存足够全部容纳这些数据的情况
     *
     * @throws IOException
     */
    public void OutputStreamToInputStream1() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writeDataToOutputStream(baos);
        byte[] data = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        processDataFromInputStream(bais);
        //eventually you can use it twice:
        //processAgainDataFromInputSream(new ByteArrayInputStream(data));
        bais.close();
        baos.close();
    }

    /**
     * OutputStream转InputStream
     * 方法二：使用PipedInputStream和PipedOutputStream
     * 备注：适合于数据量大的情况，一个类专门负责产生数据，另一个类负责读取数据
     *
     * @throws IOException
     */
    public void OutputStreamToInputStream2(OutputStream os) throws IOException {
        PipedInputStream pis = new PipedInputStream(PIPE_BUFFER);
        final PipedOutputStream pos = new PipedOutputStream(pis);
        // 启动线程，让数据产生者单独运行
        new Thread(new Runnable() {
            public void run() {
                writeDataToOutputStream(pos);
            }
        }).start();
        processDataFromInputStream(pis);
        pis.close();
    }

    /**
     * OutputStream转InputStream
     * 方法三：使用临时文件
     *
     * @throws IOException
     */
    public void OutputStreamToInputStream3() throws IOException {
        // create a temporary file.
        File tempFile = File.createTempFile("tempFile", ".tmp");
        OutputStream os = new FileOutputStream(tempFile);
        writeDataToOutputStream(os);

        //get an InputStream from the previous file.
        InputStream is = new FileInputStream(tempFile);

        processDataFromInputStream(is);

        is.close();
        os.close();
        tempFile.delete();
    }
}
