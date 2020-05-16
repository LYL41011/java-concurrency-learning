package com.lyl.concurrency.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Liuyanling
 * @Date 2020/5/12 10:17
 * @Descripiton 可重入锁 功能3：tryLock()非阻塞地获取锁。
 *
 * 当获取锁时，如果其他线程持有该锁，无可用锁资源，直接返回false，这时候线程不用阻塞等待，可以先去做其他事情；。这样不会造成死锁。
 * 1）lock(), 拿不到lock就不罢休，不然线程就一直block。 比较无赖的做法。
 * 2）tryLock()，马上返回，拿到lock就返回true，不然返回false。 比较潇洒的做法。
 * 3）带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false。比较聪明的做法。
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
        //tryLock()非阻塞地获取锁
        try {
            if(reentrantLock.tryLock()){
                System.out.println("线程" + Thread.currentThread().getName());
            }
        }finally {
            //reentrantLock.unlock();
        }
    }


    public static void testError() {
        //与lock进行对比 使用lock方法如果不释放锁，那么会一直持有。
        reentrantLock.lock();
        try {
            System.out.println("线程" + Thread.currentThread().getName());
        } finally {
            // 保证锁能释放
            //reentrantLock.unlock();
        }
    }

}
