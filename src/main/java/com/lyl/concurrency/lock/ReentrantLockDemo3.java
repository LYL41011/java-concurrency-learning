package com.lyl.concurrency.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Liuyanling
 * @Date 2020/5/12 10:17
 * @Descripiton �������� ����3��tryLock()�������ػ�ȡ����
 *
 * ����ȡ��ʱ����������̳߳��и������޿�������Դ��ֱ�ӷ���false����ʱ���̲߳��������ȴ���������ȥ���������飻�������������������
 * 1��lock(), �ò���lock�Ͳ����ݣ���Ȼ�߳̾�һֱblock�� �Ƚ�������������
 * 2��tryLock()�����Ϸ��أ��õ�lock�ͷ���true����Ȼ����false�� �Ƚ�������������
 * 3����ʱ�����Ƶ�tryLock()���ò���lock���͵�һ��ʱ�䣬��ʱ����false���Ƚϴ�����������
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
        //tryLock()�������ػ�ȡ��
        try {
            if(reentrantLock.tryLock()){
                System.out.println("�߳�" + Thread.currentThread().getName());
            }
        }finally {
            //reentrantLock.unlock();
        }
    }


    public static void testError() {
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
