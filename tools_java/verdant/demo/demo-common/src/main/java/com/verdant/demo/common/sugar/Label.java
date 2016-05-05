package com.verdant.demo.common.sugar;

/**
 * Author: verdant
 * Func:   标签语法
 */
public class Label {
    public static void main(String[] args) {
        lable1:
        for (int i = 0; i < 5; i++) {
            label2:
            for (int j = 0; j < 33; j++) {
                if (j % 3 == 0)
                    break lable1;        //调出i循环，所有循环结束
                if (j % 5 == 0)
                    break label2;        //跳出j循环，继续i循环
                if (j % 7 == 0)
                    continue lable1;    //跳转到i循环，开始下一次循环
                if (j % 11 == 0)
                    continue label2;    //跳转到i循环，开始下一次循环
            }
        }
    }

}
