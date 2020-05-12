package com.lyl.concurrency.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Liuyanling
 * @Date 2020/5/11 16:16
 * @Descripiton
 */
public class Account {
    private int balance;
    private final Lock lock = new ReentrantLock();


    public Lock getLock(){
        return this.lock;
    }
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Account(int balance) {
        this.balance = balance;
    }



}
