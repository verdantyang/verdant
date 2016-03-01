package com.verdant.demo.common.list.sort;

import com.verdant.demo.common.list.sort.model.Way2_Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Author: verdant
 * Create: 2016/1/28
 * Func:   列表排序
 * Desc：  方法二（重载Collections.sort方法）
 */
public class Way2_Sort {
    public static void main(String[] args) {
        List<Way2_Person> listA = new ArrayList<>();
        Way2_Person p1 = new Way2_Person();
        Way2_Person p2 = new Way2_Person();
        Way2_Person p3 = new Way2_Person();

        p1.setName("name1");
        p1.setOrder(1);
        p2.setName("name2");
        p2.setOrder(2);
        p3.setName("name3");
        p3.setOrder(3);

        listA.add(p2);
        listA.add(p1);
        listA.add(p3);

        Collections.sort(listA, new Comparator<Way2_Person>() {
            @Override
            public int compare(Way2_Person arg0, Way2_Person arg1) {
                return arg0.getOrder().compareTo(arg1.getOrder());
            }
        });

        for (Way2_Person p : listA) {
            System.out.println(p.getName());
        }
    }
}
