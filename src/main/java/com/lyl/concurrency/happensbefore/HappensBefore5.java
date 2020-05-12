package com.lyl.concurrency.happensbefore;

/**
 * 五、线程启动规则
 * 主线程 A 启动子线程 B 后(线程 A 调用线程 B 的 start() 方法)，子线程 B 能够看到主线程在启动子线程 B 前的操作。
 */
public class HappensBefore5 {
    private static long count = 0;
    public static void main(String[] args) throws InterruptedException {
        Thread B = new Thread(() -> {
            // 主线程调用 B.start() 之前 所有对共享变量的修改，此处皆可见
            // 因此count肯定为10
            System.out.println(count);
        });
        // 此处对共享变量count修改
        count = 10;
        // 主线程启动子线程
        B.start();
    }
}

