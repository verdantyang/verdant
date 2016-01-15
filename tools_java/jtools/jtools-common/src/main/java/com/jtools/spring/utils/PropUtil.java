package com.jtools.spring.utils;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropUtil {

    public static final String FOLDER = "prop";
    private static Properties properties;

    private static void initCheck() {
        if (properties == null) {
            properties = new Properties();
            try {
                Properties files = PropertiesLoaderUtils.loadAllProperties(FOLDER);
                
                for (Object key : files.keySet()) {
                    URL url = ClassUtils.getDefaultClassLoader().getResource(FOLDER + "/" + key.toString());
                    FileSystemResource fileSystemResource = new FileSystemResource(ResourceUtils.getFile(url));
                    PropertiesLoaderUtils.fillProperties(properties, fileSystemResource);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String get(String key) {
        initCheck();
        return properties.getProperty(key);
    }

    public static Map<String, String> getPrefix(String keyPrefix) {
        Hashtable result = new Hashtable();
        Iterator i$ = properties.stringPropertyNames().iterator();

        while(i$.hasNext()) {
            String key = (String)i$.next();
            if(key.startsWith(keyPrefix + ".")) {
                result.put(key, get(key));
            }
        }
        return result;
    }

    /**
     * 从keystring中解析patten得到key再从配置文件中获取
     *
     * @param keyString  ${proxy.platform.ip}
     * @param keyPatten  default \$\{(.+)\}
     * @param defaultVal null
     * @return
     */
    public static String getPlaceholder(String keyString, String keyPatten, String defaultVal) {
        initCheck();
        if (keyPatten == null) {
            keyPatten = "\\$\\{(.+)\\}";
        }
        Pattern p = Pattern.compile(keyPatten);
        Matcher m = p.matcher(keyString);
        String str = "";
        if (m.find()) {
            return PropUtil.get(m.group(1).trim());
        } else {
            return defaultVal;
        }
    }


    public static String get(String key, Object defaultVal) {
        initCheck();
        return properties.getProperty(key, String.valueOf(defaultVal));
    }

}
