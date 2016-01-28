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

/**
 * PropUtils Spring配置文件获取工具
 * Author: verdant
 * Create: 2016/01/18
 */
public class PropUtils {

    public static String FOLDER = "prop";
    private static Properties properties;

    static {
        if (properties == null) {
            try {
                properties = new Properties();
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
        return properties.getProperty(key);
    }

    public static String get(String key, Object defaultVal) {
        return properties.getProperty(key, String.valueOf(defaultVal));
    }

    /**
     * 根据前缀查找配置参数
     * @param keyPrefix
     * @return
     */
    public static Map<String, String> getPrefix(String keyPrefix) {
        Hashtable result = new Hashtable();
        Iterator i$ = properties.stringPropertyNames().iterator();

        while (i$.hasNext()) {
            String key = (String) i$.next();
            if (key.startsWith(keyPrefix + ".")) {
                result.put(key, get(key));
            }
        }
        return result;
    }

    /**
     * 根据正则表达式查找配置参数
     * @param keyPattern
     * @return
     */
    public static Map<String, String> getPattern(String keyPattern) {
        Hashtable result = new Hashtable();
        Iterator i$ = properties.stringPropertyNames().iterator();
        Pattern p = Pattern.compile(keyPattern);

        while (i$.hasNext()) {
            String key = String.valueOf(i$.next());
            Matcher m = p.matcher(key);
            if (m.matches()) {
                result.put(key, get(key));
            }
        }
        return result;
    }
}
