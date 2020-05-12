package com.lyl.concurrency.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 一个简单的死锁类
 * 如果有客户找柜员张三做个转账业务：账户 A 转账户 B 100 元，此时另一个客户找柜员李四也做个转账业务：账户 B 转账户 A 100 元，
 * 于是张三和李四同时都去文件架上拿账本，这时候有可能凑巧张三拿到了账本 A，李四拿到了账本 B。张三拿到账本 A 后就等着账本 B（账本 B 已经被李四拿走），而李四拿到账本 B 后就等着账本 A（账本 A 已经被张三拿走），
 * 他们要等多久呢？他们会永远等待下去…因为张三不会把账本 A 送回去，李四也不会把账本 B 送回去。
 *
 */

@Slf4j
public class DeadLock2 {

    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(200); //A的初始账户余额200
        Account b = new Account(300); //B的初始账户余额200
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
        // 锁定转出账户  Thread1锁定了A Thread2锁定了B
        synchronized (source) {
            Thread.sleep(1000);
            log.info("持有锁{} 等待锁{}",source,target);
            // 锁定转入账户  Thread1需要获取到B,可是被Thread2锁定了。Thread2需要获取到A，可是被Thread1锁定了。所以互相等待、死锁
            synchronized (target) {
                if (source.getBalance() > amt) {
                    source.setBalance(source.getBalance() - amt);
                    target.setBalance(target.getBalance() + amt);
                }
            }
        }
    }


}
