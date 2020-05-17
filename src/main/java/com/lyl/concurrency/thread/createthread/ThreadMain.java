package com.lyl.concurrency.thread.createthread;

/**
 * @Author: 微信公众号【胖滚猪学编程】
 * @Date 2020/5/16
 */
public class ThreadMain {
    public static void main(String[] args) {
        Thread thread1 = new Thread1();
        thread1.setName("Thread");
        thread1.start();

        Thread thread2 = new Thread(new Thread2());
        thread2.setName("Runnable");
        thread2.start();


        Thread thread3 = new Thread(()->{
            System.out.println("Lambda");
        });
        thread3.start();
    }
}
