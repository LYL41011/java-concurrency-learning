package com.lyl.concurrency.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Liuyanling
 * @Date 2020/5/12 10:17
 */
public class ReentrantLockDemo1 {
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static int count;

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo1 demo1 = new ReentrantLockDemo1();
        // 创建两个线程，执�? add() 操作
        Thread th1 = new Thread(() -> {
            demo1.add10K();
        });
        Thread th2 = new Thread(() -> {
            demo1.add10K();
        });
        // 启动两个线程
        th1.start();
        th2.start();
        // 等待两个线程执行结束
        th1.join();
        th2.join();
        System.out.println(get());

    }
    public static int get() {
        // 再次获取�?
        reentrantLock.lock();
        try {
            return count;
        } finally {
            // 保证锁能释放
            reentrantLock.unlock();
        }
    }

    private void add10K() {
        // 获取�?
        reentrantLock.lock();
        try {
            int idx = 0;
            while (idx++ < 10000) {
                count++;
            }
        } finally {
            // 保证锁能释放
            reentrantLock.unlock();
        }

    }
}
