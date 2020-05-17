package com.lyl.concurrency.thread.createthread;

/**
 * 实现Runnable接口创建线程的步骤为：
 *（1）创建一个类并实现Runnable接口
 *（2）重写run()方法，将所要完成的任务代码写进run()方法中
 *（3）创建实现Runnable接口的类的对象，将该对象当做Thread类的构造方法中的参数传进去
 *（4）使用Thread类的构造方法创建一个对象，并调用start()方法即可运行该线程
 * @Author: 微信公众号【胖滚猪学编程】
 * @Date 2020/5/16
 */
public class Thread2 implements Runnable{
    @Override
    public void run(){
        System.out.println(Thread.currentThread().getName()+":"+"胖滚猪学编程");
    }
}
