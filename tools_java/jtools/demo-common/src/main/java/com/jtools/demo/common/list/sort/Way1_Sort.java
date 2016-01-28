package com.jtools.demo.common.list.sort;

import com.jtools.demo.common.list.sort.model.Way1_Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: verdant
 * Create: 2016/1/28
 * Func:   列表排序
 * Desc:   方法一（实现Comparable接口的compareTo方法）
 */
public class Way1_Sort {
    public static void main(String[] args) {
        List<Way1_Person> listA = new ArrayList<Way1_Person>();
        Way1_Person p1 = new Way1_Person();
        Way1_Person p2 = new Way1_Person();
        Way1_Person p3 = new Way1_Person();

        p1.setName("name1");
        p1.setOrder(1);
        p2.setName("name2");
        p2.setOrder(2);
        p3.setName("name3");
        p3.setOrder(3);

        listA.add(p2);
        listA.add(p1);
        listA.add(p3);

        Collections.sort(listA);
        for (Way1_Person p : listA) {
            System.out.println(p.getName());
        }
    }
}
