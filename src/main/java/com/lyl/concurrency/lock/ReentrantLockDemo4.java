package com.lyl.concurrency.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Liuyanling
 * @Date 2020/5/12 10:17
 */
public class ReentrantLockDemo4 {
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo4 demo2 = new ReentrantLockDemo4();
        Thread th1 = new Thread(() -> {
            testLock();
        }, "A");
        Thread th2 = new Thread(() -> {
            try {
                testTryLock();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }, "B");
        th1.start();
        th2.start();
        th1.join();
        th2.join();

    }


    public static void testTryLock() throws InterruptedException {

        System.out.println(System.currentTimeMillis());
        //设置超时时间
        if (reentrantLock.tryLock(10, TimeUnit.SECONDS)) {
            try {
                System.out.println("线程" + Thread.currentThread().getName());
            }finally {
                reentrantLock.unlock();
            }
        }
        System.out.println(System.currentTimeMillis());
    }


    public static void testLock() {
        //与lock进行对比 使用lock方法如果不释放锁，那么会�?直持有�??
        reentrantLock.lock();
        try {
            System.out.println("线程" + Thread.currentThread().getName());
        } finally {
            // 保证锁能释放
            //reentrantLock.unlock();
        }
    }

}
