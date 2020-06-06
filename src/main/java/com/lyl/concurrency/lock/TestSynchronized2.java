package com.lyl.concurrency.lock;

/**
 * 目的:解决原子性问题
 * “同一时刻只有一个线程执行” 我们称之为互斥。如果我们能够保证对共享变量的修改是互斥的，那么，无论是单核 CPU 还是多核 CPU，就都能保证原子性了。当谈到互斥，杀手级解决方案是锁
 * 锁模型:
 * 1、创建保护资源R的锁 LR
 * 2、加锁操作 lock LR
 * 3、临界区 一段代码 受保护资源为R
 * 4、解锁操作 unlock LR
 *
 * 锁是一种通用的技术方案，Java 语言提供的 synchronized 关键字，就是锁的一种实现。
 * synchronized并不能改变CPU时间片切换的特点，只是当其他线程要访问这个资源时，发现锁还未释放，所以只能在外面等待
 * synchronized 关键字可以用来修饰方法，也可以用来修饰代码块
 *
 * 问题1：可以保证原子性吗？
 * 答案：可以，因为被 synchronized 修饰后，无论是单核 CPU 还是多核 CPU，只有一个线程能够执行add10K法，所以一定能保证原子操作
 * 问题2：可以保证可见性吗？
 * 答案：可以，根据Happens-Before 规则之管程中锁的规则：对一个锁的解锁 Happens-Before 于后续对这个锁的加锁。即前一个线程的解锁操作对后一个线程的加锁操作可见
 * 综合 Happens-Before 的传递性原则，我们就能得出前一个线程在临界区修改的共享变量（该操作在解锁之前），对后续进入临界区（该操作在加锁之后）的线程是可见的。
 */
public class TestSynchronized2 {
    private long count = 0;

    // 修饰非静态方法 当修饰非静态方法的时候，锁定的是当前实例对象 this。
    // 当该类中有多个普通方法被Synchronized修饰（同步），那么这些方法的锁都是这个类的一个对象this。多个线程访问这些方法时，如果这些线程调用方法时使用的是同一个该类的对象，虽然他们访问不同方法，但是他们使用同一个对象来调用，那么这些方法的锁就是一样的，就是这个对象，那么会造成阻塞。如果多个线程通过不同的对象来调用方法，那么他们的锁就是不一样的，不会造成阻塞。
    private synchronized void add10K(){
        int start = 0;
        while (start ++ < 10000){
            this.count ++;
        }
    }
    // 修饰静态方法 当修饰静态方法的时候，锁定的是当前类的 Class 对象，即Class TestSynchronized2 。这个范围就比对象锁大。这里就算是不同对象，但是只要是该类的对象，就使用的是同一把锁。
    synchronized static void bar() {
        // 临界区
    }
    // 修饰代码块
    private volatile static TestSynchronized2 instance;
    public static TestSynchronized2 getInstance() {
        if (instance == null) {
            synchronized (TestSynchronized2.class) {
                if (instance == null) {
                    instance = new TestSynchronized2();
                }
            }
        }
        return instance;
    }
    public static void main(String[] args) throws InterruptedException {
        TestSynchronized2 test = new TestSynchronized2();
        // 创建两个线程，执行 add() 操作
        Thread th1 = new Thread(()->{
            test.add10K();
        });
        Thread th2 = new Thread(()->{
            test.add10K();
        });
        // 启动两个线程
        th1.start();
        th2.start();
        // 等待两个线程执行结束
        th1.join();
        th2.join();
        System.out.println(test.count);
    }

}

