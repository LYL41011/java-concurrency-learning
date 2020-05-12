package com.lyl.concurrency.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Liuyanling
 * @Date 2020/5/12 10:17
 * @Descripiton �������� ����4��tryLock()֧�ֳ�ʱ
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
        //���ó�ʱʱ��
        if (reentrantLock.tryLock(10, TimeUnit.SECONDS)) {
            try {
                System.out.println("�߳�" + Thread.currentThread().getName());
            }finally {
                reentrantLock.unlock();
            }
        }
        System.out.println(System.currentTimeMillis());
    }


    public static void testLock() {
        //��lock���жԱ� ʹ��lock����������ͷ�������ô��һֱ���С�
        reentrantLock.lock();
        try {
            System.out.println("�߳�" + Thread.currentThread().getName());
        } finally {
            // ��֤�����ͷ�
            //reentrantLock.unlock();
        }
    }

}
