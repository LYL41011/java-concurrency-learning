package com.lyl.concurrency.happensbefore;

import com.lyl.concurrency.volatiledemo.TestVolatile1;

import java.util.concurrent.*;

/**
 * 二、volatile变量规则
 * 这条规则是指对一个 volatile 变量的写操作， Happens-Before 于后续对这个 volatile 变量的读操作。
 * 因为缓存的原因，每个线程有自己的工作内存，如果共享变量没有及时刷到主内存中，那就会导致可见性问题，线程B没有及时读到线程A的写。
 * 但是只要加上Volatile，就可以避免这个问题，相当于volatile的作用是对变量的修改会立刻刷新到主存。
 * 不过要注意一下，volatile除了保证可用性，它还可以禁止指定重排序哦！
 */
public class HappensBefore2 {
    private volatile static int count = 0;
    public static void main(String[] args) throws Exception {
        final TestVolatile1 test = new TestVolatile1();
        Thread th1 = new Thread(() -> {
            count = 10;
        });
        Thread th2 = new Thread(() -> {
            //没有volatile修饰count的话极小概率会出现等于0的情况
            System.out.println("count=" + count);
        });
        // 启动两个线程
        th1.start();
        th2.start();
    }
}

