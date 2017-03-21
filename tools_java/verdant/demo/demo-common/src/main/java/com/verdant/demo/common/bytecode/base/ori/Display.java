package com.verdant.demo.common.bytecode.base.ori;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 待增强类
 *
 * @author verdant
 * @since 2017/02/09
 */
public class Display {

    private static final String FLAG = "constant";

    // 普通方法
    public void display() {
        for (int i = 0; i < 1; i++) {
            System.out.println("display >>>>" + FLAG);
        }
    }

    // 带有List返回值
    public List<String> testList() {
        List<String> list = new ArrayList<>();
        list.add("Tome");
        list.add("Jack");
        list.add("Lily");
        System.out.println("testList >>>> list.size = " + list.size());
        return list;
    }

    // 泛型返回值，包含List和Map
    public List<Map<String, String>> testMapList(boolean val, Map<String, String>... map) {
        List<Map<String, String>> list = new ArrayList<>();
        if (val) {
            for (Map<String, String> m : map) {
                list.add(m);
            }
        }
        System.out.println("testMapList >>>> list.size = " + list.size());
        return list;
    }

}
