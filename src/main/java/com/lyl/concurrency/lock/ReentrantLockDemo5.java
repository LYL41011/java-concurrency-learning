package com.lyl.concurrency.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Liuyanling
 * @Date 2020/5/12 10:17
 * @Descripiton �������� ����5���ṩ�ܹ��жϵȴ������̵߳Ļ��ƣ�lockInterruptibly����
 * ReentrantLock���жϺͷ��жϼ���ģʽ���������ڣ��̳߳��Ի�ȡ������ʧ�ܺ��ڵȴ������У�������̱߳������߳��ж��ˣ����������Ӧ�ж�����ģ���
 * lock����������ж����󣬼�����ȡ��ֱ���ɹ�����lockInterruptibly��ֱ���׳��ж��쳣��������Ӧ�жϣ����ϲ�����ߴ����жϡ�
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
                System.out.println("�߳�A���ж�");
            }
        }, "A");
        Thread th2 = new Thread(() -> {
            try {
                deadLock(reentrantLock2, reentrantLock1);
            } catch (InterruptedException e) {
                System.out.println("�߳�B���ж�");
            }
        }, "B");
        th1.start();
        th2.start();
        th1.interrupt();

    }


    public static void deadLock(Lock lock1, Lock lock2) throws InterruptedException {
        lock1.lockInterruptibly(); //����ĳ���lock��ô�ǻ�һֱ������
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock2.lockInterruptibly();
        try {
            System.out.println("ִ�����");
        } finally {
            lock1.unlock();
            lock2.unlock();
        }

    }


}
