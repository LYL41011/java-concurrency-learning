package com.lyl.concurrency.lock;

/**
 * count ++是非原子性操作，volatile在这里没用了！只能用synchronized
 * synchronized 是独占锁/排他锁（就是有你没我的意思），同时只能有一个线程调用 add10K 方法，其他调用线程会被阻塞。
 * 所以三行 CPU 指令都是同一个线程执行完之后别的线程才能继续执行
 */
public class TestSynchronized1 {
    private long count = 0;

    private synchronized void add10K(){
        int start = 0;
        while (start ++ < 10000){
            this.count ++;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        TestSynchronized1 test = new TestSynchronized1();
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

