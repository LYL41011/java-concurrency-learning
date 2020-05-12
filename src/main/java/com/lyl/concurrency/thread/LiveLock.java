package com.lyl.concurrency.thread;

import lombok.extern.slf4j.Slf4j;


/**
 * һ���򵥵Ļ�����
 * ���Ȳ��������������� ��Ϊ���ǲ�����ռ �����ͷ���
 * �����������������и��Ե�����������Ҫ�ĶԷ����������Է����У��ͻ��ͷŵ�ǰ���е��������´�Ҷ��ڲ�ͣ�������ͷ����������黹û������Ȼ���ǻ����ת�˳ɹ����龰������Ч�ʵ��¡�
 * �߳̽϶������ᵼ�²����߳�ʼ���޷���ȡ���������»���
 */

@Slf4j
public class LiveLock {
    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(200);
        Account b = new Account(300);
        Thread threadA = new Thread(() -> {
            try {
                transfer(a, b, 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread threadB = new Thread(() -> {
            try {
                transfer(b, a, 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadA.start();
        threadB.start();

    }

    static void transfer(Account source, Account target, int amt) throws InterruptedException {
        Boolean isContinue = true;
        while (isContinue) {
            if (source.getLock().tryLock()) {
                log.info("{}�ѻ�ȡ�� time{}", source.getLock(),System.currentTimeMillis());
                try {
                    if (target.getLock().tryLock()) {
                        log.info("{}�ѻ�ȡ�� time{}", target.getLock(),System.currentTimeMillis());
                        try {
                            log.info("��ʼת�˲���");
                            source.setBalance(source.getBalance() - amt);
                            target.setBalance(target.getBalance() + amt);
                            log.info("����ת�˲��� source{} target{}", source.getBalance(), target.getBalance());
                            isContinue=false;
                        } finally {
                            log.info("{}�ͷ��� time{}", target.getLock(),System.currentTimeMillis());
                            target.getLock().unlock();
                        }
                    }
                } finally {
                    log.info("{}�ͷ��� time{}", source.getLock(),System.currentTimeMillis());
                    source.getLock().unlock();
                }
            }
        }
    }

}
