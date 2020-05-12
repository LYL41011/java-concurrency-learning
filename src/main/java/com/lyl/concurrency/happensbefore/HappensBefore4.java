package com.lyl.concurrency.happensbefore;

import com.lyl.concurrency.synchroniz.TestSynchronized1;

import java.util.concurrent.*;

/**
 * 四、管程锁规则
 * 管程是一种通用的同步原语，synchronized 是 Java 里对管程的实现。
 * 管程中的锁在 Java 里是隐式实现的，在进入同步块之前，会自动加锁，而在代码块执行完会自动释放锁，加锁以及释放锁都是编译器帮我们实现的。
 */
public class HappensBefore4 {
    private long count = 0;

    private synchronized void add10K(){ // 此处自动加锁
        int start = 0;
        while (start ++ < 10000){
            this.count ++;
        }
    }// 此处自动解锁
    public static void main(String[] args) throws InterruptedException {
        HappensBefore4 test = new HappensBefore4();
        // 创建两个线程，执行 add() 操作
        Thread th1 = new Thread(()->{
            test.add10K();
        });
        Thread th2 = new Thread(()->{
            test.add10K();
        });
        // 启动两个线程
        th1.start();
        th2.start();
        // 等待两个线程执行结束
        th1.join();
        th2.join();
        System.out.println(test.count);
    }
}

