package com.lyl.concurrency.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Liuyanling
 * @Date 2020/5/12 10:17
 */
public class ReentrantLockDemo2 {
    // 分别测试为true �? 为false的输�?
    private static final ReentrantLock reentrantLock = new ReentrantLock(true);
    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo2 demo2 = new ReentrantLockDemo2();
        Thread a = new Thread(() -> { test(); }, "A");
        Thread b = new Thread(() -> { test(); }, "B");
        Thread c = new Thread(() -> { test(); }, "C");
        a.start();b.start();c.start();

    }
    public static void test() {
        reentrantLock.lock();
        try {
            System.out.println("线程" + Thread.currentThread().getName());
        } finally {
            reentrantLock.unlock();
        }
    }

}
