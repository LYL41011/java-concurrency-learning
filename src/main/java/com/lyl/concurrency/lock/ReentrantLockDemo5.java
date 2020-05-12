package com.lyl.concurrency.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Liuyanling
 * @Date 2020/5/12 10:17
*/
public class ReentrantLockDemo5 {
    private static final ReentrantLock reentrantLock1 = new ReentrantLock();
    private static final ReentrantLock reentrantLock2 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo5 demo2 = new ReentrantLockDemo5();
        Thread th1 = new Thread(() -> {
            try {
                deadLock(reentrantLock1, reentrantLock2);
            } catch (InterruptedException e) {
                System.out.println("线程A被中�?");
            }
        }, "A");
        Thread th2 = new Thread(() -> {
            try {
                deadLock(reentrantLock2, reentrantLock1);
            } catch (InterruptedException e) {
                System.out.println("线程B被中�?");
            }
        }, "B");
        th1.start();
        th2.start();
        th1.interrupt();

    }


    public static void deadLock(Lock lock1, Lock lock2) throws InterruptedException {
        lock1.lockInterruptibly(); //如果改成用lock那么是会�?直死锁的
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock2.lockInterruptibly();
        try {
            System.out.println("执行完成");
        } finally {
            lock1.unlock();
            lock2.unlock();
        }

    }


}
