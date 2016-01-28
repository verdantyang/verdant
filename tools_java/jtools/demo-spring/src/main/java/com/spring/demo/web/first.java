package com.spring.demo.web;
import com.jtools.spring.utils.PropUtils;
/**
 * Created by verdant on 2016/1/15.
 */
public class first {
    public static void main(String[] args) {

//        System.out.println(PropUtils.get("mysql.client"));
        System.out.println(PropUtils.getPattern("mysql.*"));
    }
}
