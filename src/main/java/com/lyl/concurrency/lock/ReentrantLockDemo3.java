package com.lyl.concurrency.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Liuyanling
 * @Date 2020/5/12 10:17
 * @Descripiton 可重入锁 功能3：tryLock()非阻塞地获取锁�??
 */
public class ReentrantLockDemo3 {
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo3 demo2 = new ReentrantLockDemo3();
        Thread th1 = new Thread(() -> {
            testTryLock();
        }, "A");
        Thread th2 = new Thread(() -> {
            testTryLock();
        }, "B");
        th1.start();
        th2.start();
        th1.join();
        th2.join();

    }


    public static void testTryLock() {
        //tryLock()非阻塞地获取�?
        try {
            if(reentrantLock.tryLock()){
                System.out.println("线程" + Thread.currentThread().getName());
            }
        }finally {
            //reentrantLock.unlock();
        }
    }


    public static void testError() {
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
