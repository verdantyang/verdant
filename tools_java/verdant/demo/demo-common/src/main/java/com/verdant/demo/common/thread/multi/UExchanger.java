package com.verdant.demo.common.thread.multi;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Exchanger线程交换数据用例
 *
 * @author verdant
 * @since 2016/07/14
 */
public class UExchanger {

    private static final String exchangeFormat = "线程 %s 正在把数据 %s 换出去";
    private static final String getFormat = "线程 %s 接收到的数据为 %s";

    public static void excahgerUsage() {
        ExecutorService service = Executors.newCachedThreadPool();
        final Exchanger exchanger = new Exchanger();
        service.execute(new Runnable() {
            public void run() {
                try {
                    String data1 = "test1";
                    System.out.println(String.format(exchangeFormat,
                            Thread.currentThread().getName(), data1));
                    Thread.sleep((long) (Math.random() * 10000));
                    String data2 = (String) exchanger.exchange(data1);
                    System.out.println(String.format(getFormat,
                            Thread.currentThread().getName(), data2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        service.execute(new Runnable() {
            public void run() {
                try {
                    String data1 = "test2";
                    System.out.println(String.format(exchangeFormat,
                            Thread.currentThread().getName(), data1));
                    Thread.sleep((long) (Math.random() * 10000));
                    String data2 = (String) exchanger.exchange(data1);
                    System.out.println(String.format(getFormat,
                            Thread.currentThread().getName(), data2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        excahgerUsage();
    }
}
