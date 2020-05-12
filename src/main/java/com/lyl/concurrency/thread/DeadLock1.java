package com.lyl.concurrency.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * һ���򵥵�������
 * ��DeadLock��Ķ���flag==1ʱ��td1����������o1,˯��500����
 * ��td1��˯�ߵ�ʱ����һ��flag==0�Ķ���td2���߳�������������o2,˯��500����
 * td1˯�߽�������Ҫ����o2���ܼ���ִ�У�����ʱo2�ѱ�td2������
 * td2˯�߽�������Ҫ����o1���ܼ���ִ�У�����ʱo1�ѱ�td1������
 * td1��td2�໥�ȴ�������Ҫ�õ��Է���������Դ���ܼ���ִ�У��Ӷ�������
 */

@Slf4j
public class DeadLock1 implements Runnable {
    public int flag = 1;
    //��̬������������ж������
    private static Object o1 = new Object(), o2 = new Object();

    @Override
    public void run() {
        log.info("flag:{}", flag);
        if (flag == 1) {
            synchronized (o1) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    log.info("1--end");
                }
            }
        }
        if (flag == 0) {
            synchronized (o2) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    log.info("0--end");
                }
            }
        }
    }

    public static void main(String[] args) {
        DeadLock1 td1 = new DeadLock1();
        DeadLock1 td2 = new DeadLock1();
        td1.flag = 1;
        td2.flag = 0;
        //td1,td2�����ڿ�ִ��״̬����JVM�̵߳�����ִ���ĸ��߳��ǲ�ȷ���ġ�
        //td2��run()������td1��run()֮ǰ����
        new Thread(td1).start();
        new Thread(td2).start();
    }
}
