package com.spring.demo.web;
import com.verdant.jtools.common.spring.utils.PropUtils;
/**
 * Created by verdant on 2016/1/15.
 */
public class propTest {
    public static void main(String[] args) {

//        System.out.println(PropUtils.get("mysql.client"));
        System.out.println(PropUtils.getPattern("mysql.*"));
    }
}
