package com.verdant.demo.common.base;

import com.verdant.demo.common.net.socket.tcp.aio.client.AioConnectHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Random版HelloWorld
 *
 * @author verdant
 * @since 2016/08/05
 */
public class HelloWorld {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);

    public static String randomString(int i) {
        Random ran = new Random(i);
        StringBuilder sb = new StringBuilder();
        while (true) {
            int k = ran.nextInt(27);
            if (k == 0)
                break;

            sb.append((char) ('`' + k));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
//        logger.info("3##{timestamp}##{field1}##{field2}");
        logger.info(randomString(-229985452) + " " + randomString(-147909649));
    }
}
