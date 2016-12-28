package com.verdant.jtools.common.web.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author verdant
 * @Desc: URL工具类
 */
public class UrlUtils {

    private static final Logger logger = LoggerFactory.getLogger(UrlUtils.class);

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
            logger.error("URL[{}] is invalid!", url);
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

    public static String handleURL(String baseUrl, Map<String, Object> params) {
        if (params == null) {
            return baseUrl;
        }
        logger.debug(params.toString());
        StringBuilder url = new StringBuilder(baseUrl);
        if (url.indexOf("?") < 0)
            url.append('?');

        for (String name : params.keySet()) {
            url.append('&');
            url.append(name);
            url.append('=');
            url.append(String.valueOf(params.get(name)));
        }
        return url.toString().replace("?&", "?");
    }
}
