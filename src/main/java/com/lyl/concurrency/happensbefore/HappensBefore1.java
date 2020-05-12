package com.lyl.concurrency.happensbefore;

import java.util.concurrent.*;

/**
 * 一、程序顺序性规则
 * 这条规则是指在一个线程中，按照程序顺序，前面的操作 Happens-Before 于后续的任意操作。这规则挺好理解的，毕竟是在一个线程中呐。
 */
public class HappensBefore1 {
    static int x = 0;
    static boolean v = false;

    public static void main(String[] args) {
        x = 42;
        v = true;
        //程序前面对某个变量的修改一定是对后续操作可见的。
        if(v){
            System.out.println(x);
        }
    }
}

