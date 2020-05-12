package com.lyl.concurrency.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author���ֹ���ѧ��̡�
 * @Date 2020/5/12 10:17
 * @Descripiton �������� ����1�������ظ���ȡ�����������ͽ�����Ҫ�ֶ����У��Ҵ�����һ�������������߳��޷��������
 */
public class ReentrantLockDemo1 {
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static int count;

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo1 demo1 = new ReentrantLockDemo1();
        // ���������̣߳�ִ�� add() ����
        Thread th1 = new Thread(() -> {
            demo1.add10K();
        });
        Thread th2 = new Thread(() -> {
            demo1.add10K();
        });
        // ���������߳�
        th1.start();
        th2.start();
        // �ȴ������߳�ִ�н���
        th1.join();
        th2.join();
        System.out.println(get());

    }
    public static int get() {
        // �ٴλ�ȡ��
        reentrantLock.lock();
        try {
            return count;
        } finally {
            // ��֤�����ͷ�
            reentrantLock.unlock();
        }
    }

    private void add10K() {
        // ��ȡ��
        reentrantLock.lock();
        try {
            int idx = 0;
            while (idx++ < 10000) {
                count++;
            }
        } finally {
            // ��֤�����ͷ�
            reentrantLock.unlock();
        }

    }
}
