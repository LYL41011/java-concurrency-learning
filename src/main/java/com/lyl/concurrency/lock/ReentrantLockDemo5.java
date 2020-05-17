package com.lyl.concurrency.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author 公众号【胖滚猪学编程】
 * @Date 2020/5/12 10:17
 * @Descripiton 可重入锁 功能5：提供能够中断等待锁的线程的机制，lockInterruptibly方法
 * ReentrantLock的中断和非中断加锁模式的区别在于：线程尝试获取锁操作失败后，在等待过程中，如果该线程被其他线程中断了，它是如何响应中断请求的？？
 * lock方法会忽略中断请求，继续获取锁直到成功；而lockInterruptibly则直接抛出中断异常来立即响应中断，由上层调用者处理中断。
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
                System.out.println("线程A被中断");
            }
        }, "A");
        Thread th2 = new Thread(() -> {
            try {
                deadLock(reentrantLock2, reentrantLock1);
            } catch (InterruptedException e) {
                System.out.println("线程B被中断");
            }
        }, "B");
        th1.start();
        th2.start();
        th1.interrupt();

    }


    public static void deadLock(Lock lock1, Lock lock2) throws InterruptedException {
        lock1.lockInterruptibly(); //如果改成用lock那么是会一直死锁的
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
