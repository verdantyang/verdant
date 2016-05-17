package com.verdant.demo.common.dataStructure.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Author: verdant
 * Func:   List操作
 */
public class ListOperate {

    //线程安全的列表
    private final List<String> list = Collections.synchronizedList(new ArrayList<String>());

    /**
     * List遍历
     *
     * @param list
     */
    private static void forEach(List<String> list) {
        //方法一（for循环遍历）
        for (String tmp : list) {
            System.out.println(tmp);
        }
        //方法二（Iterator）
        Iterator it1 = list.iterator();
        while (it1.hasNext()) {
            System.out.println(it1.next());
        }
        //方法三（下标）
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    /**
     * List转数组
     * @param list
     * @return
     */
    private static String[] toArray(List<String> list) {
        return list.toArray(new String[0]);
    }

    public static void main(String[] args) {

    }
}
