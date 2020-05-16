package com.lyl.concurrency.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一个简单的活锁类
 * 首先不会存在死锁的情况 因为并非不可抢占 而会释放锁
 * 活锁的情况：互相持有各自的锁，发现需要的对方的锁都被对方持有，就会释放当前持有的锁，导致大家都在不停持锁，释放锁，但事情还没做。当然还是会存在转账成功的情景，不过效率低下。
 * 线程较多的情况会导致部分线程始终无法获取到锁，导致活锁
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
                log.info("{}已获取锁 time{}", source.getLock(),System.currentTimeMillis());
                try {
                    if (target.getLock().tryLock()) {
                        log.info("{}已获取锁 time{}", target.getLock(),System.currentTimeMillis());
                        try {
                            log.info("开始转账操作");
                            source.setBalance(source.getBalance() - amt);
                            target.setBalance(target.getBalance() + amt);
                            log.info("结束转账操作 source{} target{}", source.getBalance(), target.getBalance());
                            isContinue=false;
                        } finally {
                            log.info("{}释放锁 time{}", target.getLock(),System.currentTimeMillis());
                            target.getLock().unlock();
                        }
                    }
                } finally {
                    log.info("{}释放锁 time{}", source.getLock(),System.currentTimeMillis());
                    source.getLock().unlock();
                }
            }
        }
    }

}
