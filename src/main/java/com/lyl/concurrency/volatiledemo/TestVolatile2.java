package com.lyl.concurrency.volatiledemo;

/**
 * 但是Volatile绝对不能代替synchronized 因为它的使用有限制：
 * 1.运行结果并不依赖变量的当前值，或者能够确保只有单一的线程修改变量的值。见TestVolatile2
 * 2.变量不需要与其他的状态变量共同参与不变约束。见TestVolatile3
 *
 * TestVolatile2与TestVolatile1的不同之处 只在于 简单赋值操作 （this.value = value;）变成了  （this.count ++;）形式，
 * volatile加上了就能保证是2w吗？错误：你会发现，count的值始终是处于1w和2w之间的
 * 但是如果用synchronized来改造，结果就是准确的，详见TestSynchronized1
 *
 * 为啥在本例中，volatile关键字没有达到想要的效果。因为本例是非原子性操作。count++  程序代码是一行，但是翻译成 CPU 指令确是三行( 不信你用 javap -c 命令试试）
 * 而volatile 是非阻塞算法（也就是不排他），当遇到三行 CPU 指令自然就不能保证别的线程不插足了
 * 那为啥synchronized可以呢？
 * synchronized 是独占锁/排他锁（就是有你没我的意思），同时只能有一个线程调用 add10K 方法，其他调用线程会被阻塞。所以三行 CPU 指令都是同一个线程执行完之后别的线程才能继续执行
 * 由此得出：volatile 能保证内存可见性，但是不能保证原子性。如果写入变量值不依赖变量当前值或者能够确保只有单一的线程修改变量的值，才可以用 volatile。
 */
public class TestVolatile2 {
    private volatile long count = 0;

    private void add10K() {
        int idx = 0;
        while (idx++ < 10000) {
            count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestVolatile2 test = new TestVolatile2();
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
        // 即使加了volatile也达不到2w
        System.out.println(test.count);
    }


}

