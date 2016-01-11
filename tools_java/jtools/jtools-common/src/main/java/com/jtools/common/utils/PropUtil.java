package com.jtools.common.utils;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropUtil {

    public static String FOLDER = "prop";
    private static Properties properties;

    private static void initCheck() {
        if (properties == null) {
            try {
                properties = new Properties();
                Properties files = PropertiesLoaderUtils.loadAllProperties(FOLDER);
                for (Object key : files.keySet()) {
                    PropertiesLoaderUtils.fillProperties(properties, new FileSystemResource(ResourceUtils.getFile(ClassUtils.getDefaultClassLoader().getResource(FOLDER + "/" + key.toString()))));
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

    /**
     * 从keystring中解析patten得到key再从配置文件中获取
     * @param keyString ${proxy.platform.ip}
     * @param keyPatten  default \$\{(.+)\}
     * @param defaultVal null
     * @return
     */
    public static String getPlaceholder(String keyString,String keyPatten,String defaultVal) {
        initCheck();
        if(keyPatten==null)
        {
            keyPatten="\\$\\{(.+)\\}";
        }
        Pattern p = Pattern.compile(keyPatten);
        Matcher m = p.matcher(keyString);
        String str = "";
        if (m.find()) {
            return PropUtil.get(m.group(1).trim());
        }else{
            return defaultVal;
        }
    }



    public static String get(String key, Object defaultVal) {
        initCheck();
        return properties.getProperty(key, String.valueOf(defaultVal));
    }

}
