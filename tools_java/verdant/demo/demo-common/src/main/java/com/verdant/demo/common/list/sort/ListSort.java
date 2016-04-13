package com.verdant.demo.common.list.sort;

import com.verdant.demo.common.list.sort.model.PersonWay1;
import com.verdant.demo.common.list.sort.model.PersonWay2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Author: verdant
 * Create: 2016/1/28
 * Func:   列表排序
 * Desc:   方法一（实现Comparable接口的compareTo方法）
 */
public class ListSort {

    /**
     * 方法一（实现Comparable接口的compareTo方法）
     */
    public static void sort1() {
        List<PersonWay1> listA = new ArrayList<PersonWay1>();
        PersonWay1 p1 = new PersonWay1();
        PersonWay1 p2 = new PersonWay1();
        PersonWay1 p3 = new PersonWay1();

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
        for (PersonWay1 p : listA) {
            System.out.println(p.getName());
        }
    }

    /**
     * 方法二（重载Collections.sort方法）
     */
    public static void sort2() {
        List<PersonWay2> listA = new ArrayList<>();
        PersonWay2 p1 = new PersonWay2();
        PersonWay2 p2 = new PersonWay2();
        PersonWay2 p3 = new PersonWay2();

        p1.setName("name1");
        p1.setOrder(1);
        p2.setName("name2");
        p2.setOrder(2);
        p3.setName("name3");
        p3.setOrder(3);

        listA.add(p2);
        listA.add(p1);
        listA.add(p3);

        Collections.sort(listA, new Comparator<PersonWay2>() {
            @Override
            public int compare(PersonWay2 arg0, PersonWay2 arg1) {
                return arg0.getOrder().compareTo(arg1.getOrder());
            }
        });

        for (PersonWay2 p : listA) {
            System.out.println(p.getName());
        }
    }
}
