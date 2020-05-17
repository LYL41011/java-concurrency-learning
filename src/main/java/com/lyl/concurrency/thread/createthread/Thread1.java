package com.lyl.concurrency.thread.createthread;

/**
 * 创建线程的方式一
 * 创建一个类继承Thread类，重写run()方法，将所要完成的任务代码写进run()方法中；
 * @Author: 微信公众号【胖滚猪学编程】
 * @Date 2020/5/16
 */
public class Thread1 extends Thread{
    @Override
    public void run(){
        System.out.println(Thread.currentThread().getName()+":"+"胖滚猪学编程");
    }
}
