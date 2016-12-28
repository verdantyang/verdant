package com.verdant.storm.window;

import org.apache.storm.thrift.TException;
import org.apache.storm.thrift.transport.TTransportException;
import org.apache.storm.utils.DRPCClient;

import java.util.Arrays;
import java.util.List;


public class TridentWordCountTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        TridentWordCountTest test = new TridentWordCountTest();
        DRPCClient client = null;
        try {
            client = new DRPCClient(null, "10.0.18.14", 3772);
        } catch (TTransportException e) {
            e.printStackTrace();
        }
        if (args == null || args.length == 0) {
            test.cal1(client);
        } else if (args[0].equalsIgnoreCase("2")) {
            test.cal2(client);
        } else if (args[0].equalsIgnoreCase("1")) {
            test.cal1(client);
        }
    }

    private void cal1(DRPCClient client) {
        String params = "cat dog the man";

        try {
            String[] parr = params.split(" ");
            for (String word : parr) {
                System.out.println(word + ": " + client.execute("words", word));
            }
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    public void cal2(DRPCClient client) {
        List<String> urls = Arrays.asList("/index.html", "/pay.html", "/user.xhtml", "/stat.xhtml", "/shop.xhtml");
        for (String url : urls) {
            try {
                System.out.println("url: " + url);
                System.out.println(url + "'s  visit count: " + client.execute("urlCount", url));
            } catch (TException e) {
                e.printStackTrace();
            }
        }
    }

}
