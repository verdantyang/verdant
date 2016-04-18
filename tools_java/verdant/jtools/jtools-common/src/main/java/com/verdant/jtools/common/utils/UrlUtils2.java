package com.verdant.jtools.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/4/15.
 */
class UrlUtils2 {

    private static final Logger log = LoggerFactory.getLogger(UrlUtils2.class);

    private static final Pattern IP_ADDRESS = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    private static final Pattern DOMAIN_WITH_PATH = Pattern.compile("(?<=//).*(?=/)");
    private static final Pattern DOMAIN_NO_PATH = Pattern.compile("(?<=//).*");

    /**
     * 检查 IP地址是否是 合法的
     *
     * @param ip
     * @return
     */
    public static boolean checkValid(String ip) {
        return IP_ADDRESS.matcher(ip).matches();
    }

    /**
     * 抽取域名
     *
     * @param url
     * @return
     */
    public static String extractHost(String url) {
        Integer len = url.split("/").length;
        Matcher matcher;
        System.out.println(len);
        if (len < 3) {
            log.error("URL[{}] is invalid!", url);
            return null;
        } else if (len > 3) {
            matcher = DOMAIN_WITH_PATH.matcher(url);
            if (matcher.find())
                return matcher.group(0);
        }
        matcher = DOMAIN_NO_PATH.matcher(url);
        if (matcher.find())
            return matcher.group(0);
        return null;
    }
}
