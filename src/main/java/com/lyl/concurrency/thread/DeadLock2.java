package com.lyl.concurrency.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * һ���򵥵�������
 * ����пͻ��ҹ�Ա��������ת��ҵ���˻� A ת�˻� B 100 Ԫ����ʱ��һ���ͻ��ҹ�Ա����Ҳ����ת��ҵ���˻� B ת�˻� A 100 Ԫ��
 * ��������������ͬʱ��ȥ�ļ��������˱�����ʱ���п��ܴ��������õ����˱� A�������õ����˱� B�������õ��˱� A ��͵����˱� B���˱� B �Ѿ����������ߣ����������õ��˱� B ��͵����˱� A���˱� A �Ѿ����������ߣ���
 * ����Ҫ�ȶ���أ����ǻ���Զ�ȴ���ȥ����Ϊ����������˱� A �ͻ�ȥ������Ҳ������˱� B �ͻ�ȥ��
 *
 */

@Slf4j
public class DeadLock2 {

    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(200); //A�ĳ�ʼ�˻����200
        Account b = new Account(300); //B�ĳ�ʼ�˻����200
        Thread threadA = new Thread(()->{
            try {
                transfer(a,b,100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread threadB = new Thread(()->{
            try {
                transfer(b,a,100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadA.start();
        threadB.start();
    }

    static void transfer(Account source,Account target, int amt) throws InterruptedException {
        // ����ת���˻�  Thread1������A Thread2������B
        synchronized (source) {
            Thread.sleep(1000);
            log.info("������{} �ȴ���{}",source,target);
            // ����ת���˻�  Thread1��Ҫ��ȡ��B,���Ǳ�Thread2�����ˡ�Thread2��Ҫ��ȡ��A�����Ǳ�Thread1�����ˡ����Ի���ȴ�������
            synchronized (target) {
                if (source.getBalance() > amt) {
                    source.setBalance(source.getBalance() - amt);
                    target.setBalance(target.getBalance() + amt);
                }
            }
        }
    }


}
