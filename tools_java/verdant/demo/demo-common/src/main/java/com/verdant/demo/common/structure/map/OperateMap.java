package com.verdant.demo.common.structure.map;

import java.util.Iterator;
import java.util.Map;

/**
 * Author: verdant
 * Func:   Map操作
 */
public class OperateMap {
    /**
     * Map遍历
     *
     * @param map
     */
    private static void forEach(Map<String, String> map) {
        //方法一（keySet + for循环遍历）
        for (String key : map.keySet()) {
            System.out.println("key= " + key + " and value= " + map.get(key));
        }
        //方法二（entrySet + Iterator）
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
        //方法三（entrySet + for循环遍历）
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
    }

    public static void main(String[] args) {

    }
}
